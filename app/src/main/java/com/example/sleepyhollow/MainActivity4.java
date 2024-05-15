package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        TextView transProdDisplay = findViewById(R.id.transProdDisplay);
        TextView totalSumDate = findViewById(R.id.totalSumDate);
        TextView priceDisplay = findViewById(R.id.priceDisplay);
        TextView qtyDisplay = findViewById(R.id.qtyDisplay);
        Spinner transactionSpinner = findViewById(R.id.transactionSpinner);
        ArrayList<String> list = new ArrayList<String>();
        RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);
        Intent page4IntentRecieve = getIntent();
        String ssn = page4IntentRecieve.getStringExtra("ssn");
        String transactions_url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn="+ssn;
        StringRequest request4 = new StringRequest(StringRequest.Method.GET, transactions_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String txn_result = s.trim();
                String [] txn_result_array = txn_result.split("#");
                for(int i=0;i<txn_result_array.length;i++) {
                    String[] txn_cols =  txn_result_array[i].split(",");
                    list.add(txn_cols[0]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                transactionSpinner.setAdapter(adapter);
            }
        }, null);
        queue.add(request4);
        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String txnId = adapterView.getSelectedItem().toString();
                String transaction_details_url = "http://10.0.2.2:8080/sleepyhollow/TransactionDetails.jsp?txnid="+txnId;
                StringRequest request5 = new StringRequest(StringRequest.Method.GET, transaction_details_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String txn_det_result = s.trim();
                        String [] txn_det_array = txn_det_result.split("#");
                        String txnDetConcat="";
                        String priceCat="";
                        String qtyCat="";
                        String txn_date="";
                        double sum=0;
                        for(int l=0;l<txn_det_array.length;l++){
                            String[] cols =  txn_det_array[l].split(",");
                            txnDetConcat = txnDetConcat + cols[0]+"\n";
                            priceCat = priceCat + cols[1]+"\n";
                            qtyCat = qtyCat + cols[2]+"\n";
                            double v1 = Double.parseDouble(cols[1].trim());
                            int v2 = Integer.parseInt(cols[2].trim());
                            sum += v1 * v2;
                            txn_date = cols[3];
                        }
                        totalSumDate.setText(txn_date+"                "+sum);
                        transProdDisplay.setText(txnDetConcat);
                        priceDisplay.setText(priceCat);
                        qtyDisplay.setText(qtyCat);

                        txnDetConcat="";
                        qtyCat="";
                        priceCat="";
                    }
                },null);
                queue.add(request5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}