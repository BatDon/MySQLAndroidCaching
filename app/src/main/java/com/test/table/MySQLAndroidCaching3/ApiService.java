package com.test.table.MySQLAndroidCaching3;

import com.test.table.MySQLAndroidCaching3.pojo.Client;
import com.test.table.MySQLAndroidCaching3.pojo.ClientList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface ApiService {



//    @GET("/")
    //correct
    @GET("/test/index.php")
    Call<ClientList> getMyJSON();

    //@POST("/node/androidInputClient")
    @POST("/node/androidInputClient")
    Call<Client> clientData(@Body Client client);

}
