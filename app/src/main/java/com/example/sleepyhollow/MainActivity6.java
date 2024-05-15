package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        TextView idView = findViewById(R.id.idView);
        TextView amountView = findViewById(R.id.amountView);
        Button executeBtn = findViewById(R.id.executeBtn);
        Intent page6IntentRecieve = getIntent();
        String srcssn = page6IntentRecieve.getStringExtra("ssn");
        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destssn = idView.getText().toString();
                String transferamount = amountView.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity6.this);
                String transfer_url = "http://10.0.2.2:8080/sleepyhollow/Transfer.jsp?ssn1="+srcssn+"&ssn2="+destssn+"&amount="+transferamount;
                StringRequest request8 = new StringRequest(StringRequest.Method.GET, transfer_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String switchVar = s.trim();
                        switch (switchVar) {
                            case "Successful":
                                String result = "Transfer Executed Successfully!";
                                Toast.makeText(MainActivity6.this,result,Toast.LENGTH_LONG).show();
                                break;
                            case "Failed":
                                String alt_result = "Transfer Failed!";
                                Toast.makeText(MainActivity6.this, alt_result, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(MainActivity6.this, "Unexpected response", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }, null);
                queue.add(request8);
            }
        });
    }
}