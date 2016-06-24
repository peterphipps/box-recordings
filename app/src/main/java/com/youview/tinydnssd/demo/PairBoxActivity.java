package com.youview.tinydnssd.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.youview.core.requestmanager.Callback;
import com.youview.core.requestmanager.DefaultGsonTransformer;
import com.youview.core.requestmanager.HttpRequestFactory;
import com.youview.core.requestmanager.Result;
import java.util.List;

public class PairBoxActivity extends AppCompatActivity {

    SetTopBox setTopBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_box);
        Intent intent = getIntent();
        //Get box data passed from previous activity
        setTopBox = intent.getParcelableExtra("setTopBox");
        //Set TextView values to be relevant for selected box
        TextView mTextModel = (TextView) findViewById(R.id.text_model);
        mTextModel.setText(setTopBox.getVendor() + " " + setTopBox.getModel());
        TextView mTextSerial = (TextView) findViewById(R.id.text_serial);
        mTextSerial.setText("Serial ending in: " +  setTopBox.getSerial());
        TextView mTextIP = (TextView) findViewById(R.id.text_ip);
        mTextIP.setText("IP: " +  setTopBox.getIp());
        //Check if phone is paired with box
        if (pairedWithBox()) {
            //update display
            devicePaired(findViewById(R.id.activity_pair_box));
        }
        //Get profiles
//        ProfileResourceRequest profileResourceRequest = new ProfileResourceRequest(new MyTVConstants.MyTVType("a", "b", "c"),
//                ) {
//            @Override
//            protected String getResourceId(Profile profile) {
//                return null;
//            }
//        };

        Button showRecordingsButton = (Button) findViewById(R.id.show_recordings_button);
        showRecordingsButton.setVisibility(View.GONE);
        TextView textPaired = (TextView) findViewById(R.id.text_paired);
        textPaired.setVisibility(View.INVISIBLE);

    }

    private boolean pairedWithBox() {
        return true;
    }

    public void pairDevice(final View view) {
        //okhttp

        final Button pairButton = (Button) findViewById(R.id.pair_button);
        pairButton.setText("Pairing...");
        pairButton.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));

        //Get application's shared preferences
        final SharedPreferences sharedpreferences = getSharedPreferences("com.youview.tinydnssd.demo", Context.MODE_PRIVATE);
        String clientId = sharedpreferences.getString("client-id","null");

        String ip = setTopBox.getIp();
        Uri uri = Uri.parse("http://" + ip + ":8888/bridge/v1/Zinc/DeviceManager/Pairer/startPairing");

        HttpRequestFactory.createJsonPostRequest(uri,
                DefaultGsonTransformer.forListOf(Void.class),
                "{\"clientInstanceId\":\"df3857b6-65b0-4748-9eed-fe6dbbcf5e1b\",\n" +
                        "    \"friendlyName\":\"My App\"\n" +
                        "}", new Callback<List<Void>>() {
                    @Override
                    public void call(Result<List<Void>> result) {
                        if (result.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Requested Pairing", Toast.LENGTH_SHORT).show();
                            devicePaired(view);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            pairButton.setText("Pair");
                        }
                    }
                }).start();


                //devicePaired(view);

        //df3857b6-65b0-4748-9eed-fe6dbbcf5e1b

    }

    private void devicePaired(View view) {
        //Change colors
        findViewById(R.id.box).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
        ((TextView) findViewById(R.id.text_model)).setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        ((TextView) findViewById(R.id.text_serial)).setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        ((TextView) findViewById(R.id.text_ip)).setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        Button pairButton = (Button) view.findViewById(R.id.pair_button);
        pairButton.setVisibility(View.GONE);
        TextView textPaired = (TextView) findViewById(R.id.text_paired);
        textPaired.setVisibility(View.VISIBLE);
        //pairButton.setText("Paired");
        pairButton.setEnabled(false);
        pairButton.getLayoutParams().width = 400;
        //Reveal show recordings button
        Button showRecordingsButton = (Button) findViewById(R.id.show_recordings_button);
        showRecordingsButton.setVisibility(View.VISIBLE);
    }

    public void viewRecordings(View view) {
        Intent intent = new Intent(view.getContext(), RecordingListActivity.class);
        view.getContext().startActivity(intent);
    }


}
