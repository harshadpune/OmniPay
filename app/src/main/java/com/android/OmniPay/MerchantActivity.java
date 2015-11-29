package com.android.OmniPay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aarlibrary.Utils.OmniPayButton;
import com.android.aarlibrary.listeners.OnOmniPayClickListener;
import com.android.aarlibrary.listeners.OnOmniPaymentListener;
import com.android.aarlibrary.dao.PaymentHandler;
import com.android.aarlibrary.dao.PaymentSuccessDetails;


public class MerchantActivity extends ActionBarActivity implements OnOmniPayClickListener,OnOmniPaymentListener {

    private OmniPayButton omniPayButton;
    private TextView tvPrice;
    private LinearLayout llPrice;
    private LinearLayout llDescription;
    private LinearLayout llPaymentSuccess;
    private TextView tvServiceProvider;
    private TextView tvConfirmationId;
    private TextView tvPayAmount;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        omniPayButton = (OmniPayButton) findViewById(R.id.btnOmniPay);
        omniPayButton.setOnOmniPayClickListener(this);
        omniPayButton.setOnOmniPaymentListener(this);
        tvPrice = (TextView) findViewById(R.id.tvPrice1);
        llPrice = (LinearLayout) findViewById(R.id.llPrice);
        llDescription = (LinearLayout) findViewById(R.id.llDescription);
        llPaymentSuccess = (LinearLayout) findViewById(R.id.llPaymentSuccess);
        tvServiceProvider = (TextView)  findViewById(R.id.tvServiceProvider);
        tvConfirmationId = (TextView)  findViewById(R.id.tvConfirmationId);
        tvPayAmount = (TextView)  findViewById(R.id.tvPayAmount);
        tvDate = (TextView)  findViewById(R.id.tvDate);

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
        PaymentHandler paymentHandler = PaymentHandler.getInstance();
        paymentHandler.setPaymentId("42");
        paymentHandler.setPaymentAmount("" + tvPrice.getText().toString());
    }

    @Override
    public void getPaymentInfo(PaymentSuccessDetails paymentSuccessDetails) {
        Toast.makeText(MerchantActivity.this, "Received Payment........", Toast.LENGTH_LONG).show();
        if(paymentSuccessDetails!=null){
            showPaymentDetails(true);
            tvServiceProvider.setText("" + paymentSuccessDetails.getPaymentServiceProvider());
            tvConfirmationId.setText("" + paymentSuccessDetails.getPaymentConfirmationId());
            tvPayAmount.setText("" + paymentSuccessDetails.getPaymentAmount());
            tvDate.setText(""+paymentSuccessDetails.getDateAndTime());
        }else{
            showPaymentDetails(false);
        }
    }

    private void showPaymentDetails(boolean paymentSuccess) {
        if(paymentSuccess){
            llPaymentSuccess.setVisibility(View.VISIBLE);
            llDescription.setVisibility(View.GONE);
            omniPayButton.setVisibility(View.GONE);

        }else{
            llPaymentSuccess.setVisibility(View.GONE);
            llDescription.setVisibility(View.VISIBLE);
            omniPayButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(llPaymentSuccess.getVisibility() == View.VISIBLE){
            showPaymentDetails(false);
        }else {
            super.onBackPressed();
        }
    }
}
