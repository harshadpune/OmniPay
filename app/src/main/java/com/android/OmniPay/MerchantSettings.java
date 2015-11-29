package com.android.OmniPay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by HARSHAD on 29/11/2015.
 */
public class MerchantSettings extends ActionBarActivity implements View.OnClickListener{

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_settings);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        setData();
    }

    private void setData() {
        SharedPreferences spf = getSharedPreferences("APP_SHARED",MODE_PRIVATE);
       cb1.setChecked(spf.getBoolean("isPayPalEnabled",true));
       cb2.setChecked(spf.getBoolean("isPayUEnabled",true));
       cb3.setChecked(spf.getBoolean("isNFCEnabled",true));
       cb4.setChecked(spf.getBoolean("isScanEnabled",true));
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnSubmit) {
            SharedPreferences spf = getSharedPreferences("APP_SHARED", MODE_PRIVATE);
            SharedPreferences.Editor se = spf.edit();
            if (cb1.isChecked()) {
                se.putBoolean("isPayPalEnabled", true);
//            se.commit();
            } else {
                se.putBoolean("isPayPalEnabled", false);
//            se.commit();
            }

            if (cb2.isChecked()) {
                se.putBoolean("isPayUEnabled", true);
//            se.commit();
            } else {
                se.putBoolean("isPayUEnabled", false);
//            se.commit();
            }

            if (cb3.isChecked()) {
                se.putBoolean("isNFCEnabled", true);
//            se.commit();
            } else {
                se.putBoolean("isNFCEnabled", false);
//            se.commit();
            }

            if (cb4.isChecked()) {
                se.putBoolean("isScanEnabled", true);
//            se.commit();
            } else {
                se.putBoolean("isScanEnabled", false);
//            se.commit();
            }

            se.commit();
            finish();
        }
    }
}
