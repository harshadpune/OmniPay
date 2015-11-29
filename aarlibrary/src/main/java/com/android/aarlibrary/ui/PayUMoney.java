package com.android.aarlibrary.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.aarlibrary.dao.PaymentHandler;
import com.android.aarlibrary.R;
//import com.payu.india.Model.PaymentParams;
//import com.payu.india.Model.PayuHashes;
//import com.payu.india.Payu.PayuConstants;
//import com.payu.payuui.PayUBaseActivity;

/**
 * Created by HARSHAD on 26/11/2015.
 */
public class PayUMoney extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu_money);
        Log.d("TAG", "----------------------" + PaymentHandler.getInstance().getPaymentAmount());
//        TextView tv2 = (TextView) findViewById(R.id.tv2);
//        tv2.setText(" ID: " + PaymentHandler.getInstance().getPaymentId() + " Amount: " + PaymentHandler.getInstance().getPaymentAmount());
//        setPayUMoneyData();
    }

//    private void setPayUMoneyData() {
//        PaymentParams mPaymentParams = new PaymentParams();
//        mPaymentParams.setKey("gtKFFx");
//        mPaymentParams.setAmount("15.0");
//        mPaymentParams.setProductInfo("Tshirt");
//                mPaymentParams.setFirstName("Harshad");
//        mPaymentParams.setEmail("harshadpune@gmail.com");
//        mPaymentParams.setTxnId("0123479543689");
//        mPaymentParams.setSurl("https://payu.herokuapp.com/success");
//        mPaymentParams.setFurl("https://payu.herokuapp.com/failure");
//        mPaymentParams.setUdf1("udf1l");
//        mPaymentParams.setUdf2("udf2");
//        mPaymentParams.setUdf3("udf3");
//        mPaymentParams.setUdf4("udf4");
//        mPaymentParams.setUdf5("udf5");
//
//        mPaymentParams.setHash("adsfadfds123412sadf");
//
//        Intent payUBaseActivityIntent = new Intent(OmniPayButton.appContext, PayUBaseActivity.class);
//        payUBaseActivityIntent.putExtra(PayuConstants.ENV, PayuConstants.PRODUCTION_ENV);
//        payUBaseActivityIntent.putExtra(PayuConstants.PAYMENT_DEFAULT_PARAMS, mPaymentParams);
//        PayuHashes payuHashes = new PayuHashes();
//        payuHashes.setPaymentHash("dsfsa32432adf");
//        payUBaseActivityIntent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
//        startActivity(payUBaseActivityIntent);
//    }
}
