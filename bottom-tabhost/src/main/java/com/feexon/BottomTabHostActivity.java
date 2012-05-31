package com.feexon;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author Administrator
 * @version 1.0 12-5-30,下午5:04
 */
public class BottomTabHostActivity extends TabActivity implements
        RadioGroup.OnCheckedChangeListener{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        setContentView(R.layout.bottom_tabs);
        initTabs();
        registerListeners();
    }

    private void registerListeners() {
        RadioGroup group = (RadioGroup) findViewById(R.id.tab_group);
        group.setOnCheckedChangeListener(this);
    }

    private void initTabs() {
        addTab(R.string.tagHome);
        addTab(R.string.tagMsg);
        addTab(R.string.tagEstimation);
        addTab(R.string.tagSearch);
        addTab(R.string.tagMore);
    }

    private BottomTabHostActivity addTab(int tagId) {
        String tagName = getText(tagId).toString();
        TabHost tabHost = getTabHost();

        Intent intent = new Intent(ViewActivity.ACTION_VIEW);
        tabHost.addTab(
                tabHost.newTabSpec(tagName).
                        setIndicator(tagName).setContent(intent)
        );
        return this;
    }


    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Object tag = group.findViewById(checkedId).getTag();
        TabHost tabHost = getTabHost();

        tabHost.setCurrentTabByTag(String.valueOf(tag));
    }
}