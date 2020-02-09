package com.test.table.MySQLAndroidCaching3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        //TEST
//        WebView webView=findViewById(R.id.web_view);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://192.168.1.3/node");


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
                //startActivity(intent);
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

            Toast.makeText(this, "phoneString= " + phoneString, Toast.LENGTH_SHORT).show();

            Client client = new Client(5,firstNameString,lastNameString,phoneString,addressString);
            Call<Client> call1 = sgPost.clientData(client);
            call1.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(@NonNull Call<Client> call1, @NonNull Response<Client> response) {
                if (response.code() == 404 ) {
                    Log.d(TAG, "onResponse - Status : " + response.code());
                    Gson gson = new Gson();
                    TypeAdapter<Client> adapter = gson.getAdapter(Client.class);
                    Client registerResponse;
                    try {
                        if (response.errorBody() != null) {
                            registerResponse =
                                    adapter.fromJson(
                                            response.errorBody().string());
                            Log.d("onResponse404",""+registerResponse.getFirstName());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }





                if (response.body()==null){
                    Log.i("onRespone","response.body()==null");
                }
                if(response.isSuccessful()){
                    Log.i("Response is successful",""+response.body());
                    Log.i("firstName",response.body().getFirstName());
                }
                else{
                    Log.i("Response nS","");
                }
                Client client1 = response.body();
//                boolean success=false;
//                success = response.isSuccess();
                if(client1!=null) {
                    String responseString = client1.getFirstName();
                    Toast.makeText(getApplication(), responseString, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "Post finished", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Post finished +"+ response.code(), Toast.LENGTH_SHORT).show();

            }

        @Override
        public void onFailure(Call<Client> call1, Throwable t) {
            Toast.makeText(MainActivity.this, "Error posting data: "+t, Toast.LENGTH_SHORT).show();
            call1.cancel();
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
