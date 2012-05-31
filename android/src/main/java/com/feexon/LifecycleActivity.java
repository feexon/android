package com.feexon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Administrator
 * @version 1.0 12-5-30,上午11:24
 */
public class LifecycleActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.lifecycle);
        super.onCreate(savedInstanceState);
        log("onCreate:"+savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    private void log(String message) {
        Log.v("lifecycle",message);
    }
}