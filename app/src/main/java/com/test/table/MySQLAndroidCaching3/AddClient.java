package com.test.table.MySQLAndroidCaching3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.table.MySqlAndroidCaching3.R;


public class AddClient extends AppCompatActivity {

    public static final String EXTRA_ID="com.test.table.MySQLAndroidCaching3.EXTRA_ID";
    public static final String EXTRA_FIRST_NAME="com.test.table.MySQLAndroidCaching3.FIRST_NAME";
    public static final String EXTRA_LAST_NAME="com.test.table.MySQLAndroidCaching3.LAST_NAME";
    public static final String EXTRA_PHONE="com.test.table.MySQLAndroidCaching3.PHONE";
    public static final String EXTRA_ADDRESS="com.test.table.MySQLAndroidCaching3.ADDRESS";


    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText address;

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("AddClient");
        }
    }

    public void saveClient(View v){
        String firstNameString=firstName.getText().toString();
        String lastNameString=lastName.getText().toString();
        String phoneString=phone.getText().toString();
        String addressString=address.getText().toString();

        if (firstNameString.trim().isEmpty() || lastNameString.trim().isEmpty() || phoneString.trim().isEmpty() || addressString.trim().isEmpty()) {
            Toast.makeText(this, "Please first name, last name, phone number and address", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent clientData=new Intent();
        clientData.putExtra(EXTRA_FIRST_NAME,firstNameString);
        clientData.putExtra(EXTRA_LAST_NAME,lastNameString);
        clientData.putExtra(EXTRA_PHONE,phoneString);
        clientData.putExtra(EXTRA_ADDRESS,addressString);

        setResult(RESULT_OK, clientData);
        finish();
    }
}










