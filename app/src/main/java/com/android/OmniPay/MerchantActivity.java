package com.android.OmniPay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aarlibrary.OmniPayButton;
import com.android.aarlibrary.OnOmniPayClickListener;
import com.android.aarlibrary.PaymentHandler;


public class MerchantActivity extends ActionBarActivity implements OnOmniPayClickListener {

    private OmniPayButton btnOmniPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        btnOmniPay = (OmniPayButton) findViewById(R.id.btnOmniPay);
        btnOmniPay.setOnOmniPayClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_merchant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity
        // in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setPaymentDetails() {
        Toast.makeText(MerchantActivity.this, "Button Clicked.. Set Data", Toast.LENGTH_LONG).show();
        PaymentHandler paymentHandler = PaymentHandler.getInstance();
        paymentHandler.setPaymentId("42");
        paymentHandler.setPaymentAmount("$24.42");
    }
}
