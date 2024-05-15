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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        TextView awardDisplay = findViewById(R.id.awardDisplay);
        TextView acenterDisplay = findViewById(R.id.acenterDisplay);
        Spinner awardSpinner = findViewById(R.id.awardSpinner);
        ArrayList<String> list = new ArrayList<String>();
        RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
        Intent page5IntentRecieve = getIntent();
        String ssn = page5IntentRecieve.getStringExtra("ssn");
        String awards_url = "http://10.0.2.2:8080/sleepyhollow/AwardIds.jsp?ssn="+ssn;
        StringRequest request6 = new StringRequest(StringRequest.Method.GET, awards_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String award_id_result = s.trim();
                String [] aw_cols = award_id_result.split("#");
                for(int i=0;i<aw_cols.length;i++) {
                    list.add(aw_cols[0]);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                awardSpinner.setAdapter(adapter);
            }
        }, null);
        queue.add(request6);
        awardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String awd_id = adapterView.getSelectedItem().toString();
                String award_det_url = "http://10.0.2.2:8080/sleepyhollow/GrantedDetails.jsp?awardid="+awd_id+"&ssn="+ssn;
                StringRequest request7 = new StringRequest(StringRequest.Method.GET, award_det_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String award_det_result = s.trim();
                        String [] awd_det_array = award_det_result.split("#");
                        String awdDetConcat="";
                        String centerCat="";
                        for(int l=0;l<awd_det_array.length;l++){
                            String[] cols =  awd_det_array[l].split(",");
                            awdDetConcat = awdDetConcat + cols[0]+"\n";
                            centerCat = centerCat + cols[1]+"\n";
                        }
                        awardDisplay.setText(awdDetConcat);
                        acenterDisplay.setText(centerCat);
                        awdDetConcat="";
                        centerCat="";
                    }
                }, null);
                queue.add(request7);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}