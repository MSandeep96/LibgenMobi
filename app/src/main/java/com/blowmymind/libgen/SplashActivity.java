package com.blowmymind.libgen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blowmymind.libgen.MainActivity_MVP.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent inte = new Intent(this,MainActivity.class);
        startActivity(inte);
        finish();
    }
}
