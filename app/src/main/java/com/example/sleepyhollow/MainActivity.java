package com.example.sleepyhollow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.loginButton);
        EditText usernameText = findViewById(R.id.usernameText);
        EditText passwordText = findViewById(R.id.passwordText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTextValue = usernameText.getText().toString();
                String passwordTextValue = passwordText.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url= "http://10.0.2.2:8080/sleepyhollow/login?user="+usernameTextValue+"&"+"pass="+passwordTextValue;
                StringRequest request1 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String loginResult = s.trim();
                        String [] getSSN = loginResult.split(":");
                        String decisionVar = getSSN[0];
                        switch (decisionVar) {
                            case "Yes":
                                Toast.makeText(MainActivity.this,loginResult,Toast.LENGTH_LONG).show();
                                Intent page1IntentSend = new Intent(MainActivity.this, MainActivity2.class);
                                String loginSSN = getSSN[1];
                                page1IntentSend.putExtra("ssn",loginSSN);
                                startActivity(page1IntentSend);
                                break;
                            case "No":
                                Toast.makeText(MainActivity.this, loginResult, Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(MainActivity.this, "Unexpected response", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }, null);
                queue.add(request1);
            }
        });
    }
}