package com.vavan.contactmanager;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashActivity extends AppCompatActivity implements Handler.Callback {

    private static final int MSG_CONTINUE = 101;
    private static final long DELAY = 1000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler(getMainLooper(), this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_CONTINUE), DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == MSG_CONTINUE) {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }
        return true;
    }
}
