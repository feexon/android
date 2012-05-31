package com.feexon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Administrator
 * @version 1.0 12-5-30,下午8:10
 */
public class ViewActivity extends Activity {
    public static final String ACTION_VIEW = "com.feexon.action.VIEW";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view);
        Log.v("onCreate(" + savedInstanceState + ")");

    }

}