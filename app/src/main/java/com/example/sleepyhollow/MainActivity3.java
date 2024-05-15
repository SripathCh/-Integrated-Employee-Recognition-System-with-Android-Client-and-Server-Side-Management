package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        TextView displayTransactions = findViewById(R.id.displayTransactions);
        TextView dateDisplay = findViewById(R.id.dateDisplay);
        TextView amountDisplay = findViewById(R.id.amountDisplay);
        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);
        Intent page3IntentRecieve = getIntent();
        String ssn = page3IntentRecieve.getStringExtra("ssn");
        String transactions_url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn="+ssn;
        StringRequest request3 = new StringRequest(StringRequest.Method.GET, transactions_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String txn_result = s.trim();
                String [] txn_result_array = txn_result.split("#");
                String concatString="";
                String dateCat="";
                String amountCat="";
                for(int i=0;i<txn_result_array.length;i++) {
                    String[] cols =  txn_result_array[i].split(",");
                    concatString = concatString + cols[0]+"\n";
                    dateCat = dateCat + cols[1]+"\n";
                    amountCat = amountCat + cols[2]+"\n";
                }
                displayTransactions.setText(concatString);
                dateDisplay.setText(dateCat);
                amountDisplay.setText(amountCat);
                concatString="";
                dateCat="";
                amountCat="";

            }
        }, null);
        queue.add(request3);
    }
}