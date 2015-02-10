package com.jameskelso.android.widget.sample;

import android.app.ListActivity;
import android.os.Bundle;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListAdapter(new SampleAdapter(this));
    }
}
