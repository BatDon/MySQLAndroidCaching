package com.test.table.MySQLAndroidCaching3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import retrofit2.Callback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.test.table.MySQLAndroidCaching3.pojo.Client;
import com.test.table.MySQLAndroidCaching3.pojo.ClientList;
import com.test.table.MySqlAndroidCaching3.R;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {



    private static final String TAG = "MainActivity";

    private ArrayList<Client> clientList;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ClientsAdapter cAdapter;


    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        initRecyclerView();

        searchApi("d");

    }

    private void searchApi(String idno) {
        hideKeyboard();

        ApiService sg=ServiceGenerator.getApi();

        Call<ClientList> call=sg.getMyJSON();

                    call.enqueue(new Callback<ClientList>() {

                        @Override
                        public void onResponse(@NonNull Call<ClientList> call,@NonNull Response<ClientList> response) {
                            pDialog.dismiss();

                            if(response.isSuccessful()) {
                                clientList = response.body().getClient();
                            }
                            if(response.body()==null){
                                Toast.makeText(MainActivity.this, "No Users entered", Toast.LENGTH_SHORT).show();
                            }

                            if (response.raw().networkResponse() != null) {
                                Toast.makeText(MainActivity.this, "Response is from Network", Toast.LENGTH_SHORT).show();
                            } else if (response.raw().cacheResponse() != null
                                    && response.raw().networkResponse() == null) {
                                Toast.makeText(MainActivity.this, "Response is from Cache", Toast.LENGTH_SHORT).show();
                            }

                            clientList=response.body().getClient();
                            cAdapter.setClients(clientList);
                        }

                        @Override
                        public void onFailure(Call<ClientList> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error fetching data: "+t, Toast.LENGTH_SHORT).show();
                            cAdapter.setClients(new ArrayList<Client>());
                        }
                    });
        }

        private void initRecyclerView () {
            recyclerView=findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
            recyclerView.addItemDecoration(itemDecorator);
            cAdapter = new ClientsAdapter();
            recyclerView.setAdapter(cAdapter);
        }

        public void hideKeyboard () {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
