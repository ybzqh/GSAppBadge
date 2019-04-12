package com.example.billwangbw.appiconnumtest;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    String mTvView;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        try {
            mTvView = getIntent().getStringExtra("test");
        } catch (Exception e) {

        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(mTvView);
                NotificationManager managers = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
               // managers.cancel(R.mipmap.ic_launcher);
                managers.cancel(666);
            }
        });
    }
}
