package com.android.aarlibrary;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by HARSHAD on 26/11/2015.
 */
public class PayUMoney extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu_money);
        Log.d("TAG","----------------------"+PaymentHandler.getInstance().getPaymentAmount());
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setText(" ID: " + PaymentHandler.getInstance().getPaymentId() + " Amount: " + PaymentHandler.getInstance().getPaymentAmount());
    }
}
