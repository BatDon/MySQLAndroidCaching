package com.test.table.MySQLAndroidCaching3.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientList {
    @SerializedName("client")
    @Expose
    private ArrayList<Client> clientArr = null;

    public ArrayList<Client> getClient() {
        return clientArr;
    }

    public void setClient(ArrayList<Client> client) {
        this.clientArr = client;
    }
}

