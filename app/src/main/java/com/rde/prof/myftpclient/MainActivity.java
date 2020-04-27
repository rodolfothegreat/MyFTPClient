package com.rde.prof.myftpclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edUrl;
    EditText edUserId;
    EditText edPassword;
    EditText edDir;
    Button btnOpen;
    TextView tvResult;

    private MyRequestReceiver receiver = null;
    IntentFilter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUrl = findViewById(R.id.etUrl);
        edUserId = findViewById(R.id.edUserId);
        edPassword = findViewById(R.id.edPassword);
        edDir = findViewById(R.id.edDir);
        tvResult = findViewById(R.id.tvResult);
        btnOpen = findViewById(R.id.btnLoad);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edUrl.getText().toString();
                String anId = edUserId.getText().toString();
                String aPassword = edPassword.getText().toString();
                String aDir = edDir.getText().toString();
                FtpClientService.startActionDir(MainActivity.this, url, anId, aPassword, aDir, true);
                //
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        try {
            if (receiver != null) {
                unregisterReceiver(receiver);
                receiver = null;
            }
        } catch  (IllegalArgumentException e){

        }catch (Exception e) {
            // TODO: handle exception
        }


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        receiver = new MyRequestReceiver();
        filter = new IntentFilter(FtpClientService.SERVICE_DIR);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver, filter);

    }


    public void showReceivedData(String thefiles)
    {
        tvResult.setText(thefiles);
    }

    public class MyRequestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra(FtpClientService.SERVICE_DIR_DATA) != null) {
                String theFiles = intent.getStringExtra(FtpClientService.SERVICE_DIR_DATA);
                showReceivedData(theFiles);
            }
        }
    }


}
