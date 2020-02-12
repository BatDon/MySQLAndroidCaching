package com.test.table.MySQLAndroidCaching3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import retrofit2.Callback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.test.table.MySQLAndroidCaching3.pojo.Client;
import com.test.table.MySQLAndroidCaching3.pojo.ClientList;
import com.test.table.MySqlAndroidCaching3.R;

import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<Client> clientList;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private ClientsAdapter cAdapter;
    public static final int ADD_CLIENT_REQUEST = 1;
    private TextView emptyView;
    private ApiService sg;
    private ApiService sgPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        sg=ServiceGenerator.getApi();
        sgPost=ServiceGenerator.getApiPost();

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClient.class);
                startActivityForResult(intent, ADD_CLIENT_REQUEST);
            }
        });


        initRecyclerView();

        searchApi("d");




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CLIENT_REQUEST && resultCode == RESULT_OK) {
            final String firstNameString = data.getStringExtra(AddClient.EXTRA_FIRST_NAME);
            String lastNameString = data.getStringExtra(AddClient.EXTRA_LAST_NAME);
            String phoneString = data.getStringExtra(AddClient.EXTRA_PHONE);
            String addressString = data.getStringExtra(AddClient.EXTRA_ADDRESS);

            Client client = new Client(5,firstNameString,lastNameString,phoneString,addressString);
            Call<Client> callPost = sgPost.clientData(client);
            callPost.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(@NonNull Call<Client> callPost, @NonNull Response<Client> response) {
                       Client client1=null;
                            if(response.isSuccessful()){
                                if (response.body()!=null){
                                    client1=response.body();
                                }
                                else{
                                    client1=null;
                                    Toast.makeText(MainActivity.this, "Data not parsed correctly", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Unsuccessfuly response", Toast.LENGTH_SHORT).show();
                            }

                    Toast.makeText(getApplicationContext(), "Post finished +"+ response.code(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Client> callPost, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error posting data: "+t, Toast.LENGTH_SHORT).show();
                    callPost.cancel();
                }
            });
        }
    }


    private void searchApi(String idno) {
        hideKeyboard();
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
