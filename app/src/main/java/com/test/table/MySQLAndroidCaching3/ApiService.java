package com.test.table.MySQLAndroidCaching3;

import com.test.table.MySQLAndroidCaching3.pojo.ClientList;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiService {

    @GET("/")
    Call<ClientList> getMyJSON();

}
