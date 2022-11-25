package com.ptrkcsak.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {

    String data = "";
    Button insert, back;
    EditText nev, orszag, lakossag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in_nev = nev.getText().toString().trim();
                String in_orszag = orszag.getText().toString().trim();
                String in_lakossag = lakossag.getText().toString().trim();
                String json = "{ \"nev\" : \""+in_nev+"\", \"orszag\" : \""+in_orszag+
                        "\", \"lakossag\" : \""+in_lakossag+"\" }";

                PerformNetworkRequest request = new PerformNetworkRequest("POST", json);
                request.execute();
            }
        });
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void init() {
        insert = findViewById(R.id.insert);
        nev = findViewById(R.id.nev);
        orszag = findViewById(R.id.orszag);
        lakossag = findViewById(R.id.lakossag);
    }
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String method;
        String content;

        public PerformNetworkRequest(String method) {
            this.method = method;
            this.content = "";
        }

        public PerformNetworkRequest(String method, String content) {
            this.method = method;
            this.content = content;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (method.equals("GET")){
                    data = Request.getData();
                } else {
                    data = Request.postData(content);
                }
            } catch (IOException e) {
                data = e.getMessage();
            }

            return data;
        }
    }
}
