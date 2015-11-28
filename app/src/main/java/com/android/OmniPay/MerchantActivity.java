package com.android.OmniPay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.aarlibrary.OmniPayButton;
import com.android.aarlibrary.OnOmniPayClickListener;
import com.android.aarlibrary.PaymentHandler;


public class MerchantActivity extends ActionBarActivity implements OnOmniPayClickListener {

    private OmniPayButton omniPayButton;
    private CheckBox mCbProduct1, mCbProduct2;
    private TextView mProductName1, mProdcutName2;
    private TextView mPrice1, mPrice2;
    private TextView mTotalPrice1, mTotalPrice2;
    private TextView mTotalPrice;
    private Button mPlus1, mMinus1, mPlus2, mMinus2;
    private TextView mQty1, mQty2;
    private int qty1, qty2, price1, price2, totalPrice1, totalPrice2, totalPrice;
    private LinearLayout mTotalPriceProduct1LinearLayout, mTotalPriceProduct2LinearLayout, mTotalPriceProductLinearLayout;
    private String toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        omniPayButton = (OmniPayButton) findViewById(R.id.btnOmniPay);
        omniPayButton.setOnOmniPayClickListener(this);
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
