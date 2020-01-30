package com.test.table.MySQLAndroidCaching3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.test.table.MySQLAndroidCaching3.pojo.Client;
import com.test.table.MySqlAndroidCaching3.R;
import java.util.ArrayList;


public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.CustomViewHolder> {
    private ArrayList<Client> clients;
    Context mainContext;

    public ClientsAdapter(ArrayList<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }


    public ClientsAdapter() {
        if(this.clients==null){
            this.clients=null;
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list, parent, false);
                mainContext=parent.getContext();


        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if(position==0&&clients==null){
            holder.id.setText("0");
            holder.firstName.setText(mainContext.getString(R.string.firstName));
            holder.lastName.setText(mainContext.getString(R.string.lastName));
            holder.address.setText(mainContext.getString(R.string.address));
            holder.phone.setText(mainContext.getString(R.string.phone));
        }
        else {
            Client client = clients.get(position);
            String id = Integer.toString(client.getId());
            holder.id.setText(id);
            holder.firstName.setText(client.getFirstName());
            holder.lastName.setText(client.getLastName());
            holder.address.setText(client.getAddress());
            holder.phone.setText(client.getPhone());
        }
    }

    public void setClients(ArrayList<Client> clients){
        this.clients = clients;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return clients == null ? 0 : clients.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView id, firstName, lastName, address, phone;

        public CustomViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            firstName = (TextView) view.findViewById(R.id.firstName);
            lastName = (TextView) view.findViewById(R.id.lastName);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
        }
    }

}
