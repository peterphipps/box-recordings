package com.youview.tinydnssd.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PairBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_box);

        Intent intent = getIntent();
        //Get data passed from previous activity
        SetTopBox setTopBox = intent.getParcelableExtra("setTopBox");
        //Set TextView values to be relevant for selected box
        TextView mTextModel = (TextView) findViewById(R.id.text_model);
        mTextModel.setText(setTopBox.getModel());
        TextView mTextSerial = (TextView) findViewById(R.id.text_serial);
        mTextSerial.setText(setTopBox.getSerial());

    }

}
