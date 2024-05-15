package com.example.sleepyhollow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView nameDisplayTextView = findViewById(R.id.nameDisplayTextView);
        TextView salesTextView = findViewById(R.id.salesTextView);
        ImageView imageView = findViewById(R.id.imageView);
        Button btnTransactions = findViewById(R.id.buttonTransactions);
        Button btnTransactionDetails = findViewById(R.id.buttonTransactionDetails);
        Button btnAwardDetails = findViewById(R.id.buttonAwardDetails);
        Button btnTransfer = findViewById(R.id.buttonTransfer);
        Button btnExit = findViewById(R.id.buttonExit);
        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        Intent page2IntentRecieve = getIntent();
        String ssn = page2IntentRecieve.getStringExtra("ssn");
        String url = "http://10.0.2.2:8080/sleepyhollow/Info.jsp?ssn="+ssn;
        String url2 = "http://10.0.2.2:8080/sleepyhollow/images/"+ssn+".jpg";
        StringRequest request2 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String profileResult = s.trim();
                String [] profileArray = profileResult.split(",");
                String profileName = profileArray[0];
                String [] ps = profileArray[1].split("#");
                String profileSales = ps[0];
                String salesValue = "$"+profileSales;
                salesTextView.setText(salesValue);
                nameDisplayTextView.setText(profileName);
            }
        }, null);
        queue.add(request2);

        ImageRequest profileImgReq = new ImageRequest(url2, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0,0,null, null);
        queue.add(profileImgReq);

        btnTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page2IntentSend = new Intent(MainActivity2.this, MainActivity3.class);
                page2IntentSend.putExtra("ssn",ssn);
                startActivity(page2IntentSend);
            }
        });

        btnTransactionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page2IntentSend = new Intent(MainActivity2.this, MainActivity4.class);
                page2IntentSend.putExtra("ssn",ssn);
                startActivity(page2IntentSend);
            }
        });

        btnAwardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page2IntentSend = new Intent(MainActivity2.this, MainActivity5.class);
                page2IntentSend.putExtra("ssn",ssn);
                startActivity(page2IntentSend);
            }
        });

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent page2IntentSend = new Intent(MainActivity2.this, MainActivity6.class);
                page2IntentSend.putExtra("ssn",ssn);
                startActivity(page2IntentSend);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}