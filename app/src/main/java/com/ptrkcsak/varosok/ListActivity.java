package com.ptrkcsak.varosok;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {

    String data = "";
    TextView list;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        list.setMovementMethod(new ScrollingMovementMethod());
        listaz();
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void listaz() {
        PerformNetworkRequest request = new PerformNetworkRequest("GET");
        request.execute();
    }

    private void frissitMuvelet() {
        this.runOnUiThread(frissites);
    }

    private Runnable frissites = new Runnable() {
        @Override
        public void run() {
            list.setText(data);
        }
    };

    public void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void init() {
        list = findViewById(R.id.list);
        back = findViewById(R.id.back);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String method;

        public PerformNetworkRequest(String method) {
            this.method = method;
        }


        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (method.equals("GET")){
                    data = Request.getData();}
            } catch (IOException e) {
                data = e.getMessage();
            }
            frissitMuvelet();
            return data;
        }
    }
}