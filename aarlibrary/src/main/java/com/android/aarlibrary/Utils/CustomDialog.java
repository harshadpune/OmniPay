package com.android.aarlibrary.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.aarlibrary.R;
import com.android.aarlibrary.dao.PaymentHandler;
import com.android.aarlibrary.nfcreader.TagViewer;
import com.android.aarlibrary.paypal.PaypalLandingActivity;
import com.android.aarlibrary.ui.PayUMoney;

/**
 * Created by HARSHAD on 26/11/2015.
 */
public class CustomDialog extends Dialog implements DialogInterface.OnClickListener, View.OnClickListener {

    private final Context appContext;
    private ImageView ivPayU;
    private ImageView ivPayPal;
    private ImageView ivPayNFC;

    public CustomDialog(Context context) {
        super(context);
        this.appContext = context;
        setContentView(R.layout.custom_dialog);
        init();
        setListeners();
    }


    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.appContext = context;
        setContentView(R.layout.custom_dialog);
        init();
        setListeners();
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.appContext = context;
        setContentView(R.layout.custom_dialog);
        init();
        setListeners();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    private void init(){
        ivPayU = (ImageView) findViewById(R.id.ivPayU);
        ivPayPal = (ImageView) findViewById(R.id.ivPayPal);
        ivPayNFC = (ImageView) findViewById(R.id.ivPayNFC);

        setTitle("Choose your payment option");

//        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(appContext,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
//        Builder builder = new Builder(themeWrapper);
    }

    private void setListeners() {
        ivPayU.setOnClickListener(this);
        ivPayPal.setOnClickListener(this);
        ivPayNFC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ivPayU) {
            Intent intentPayU = new Intent(appContext,PayUMoney.class);
            appContext.startActivity(intentPayU);
            Log.d("TAG", "ID="+ PaymentHandler.getInstance().getPaymentId()+" Amount="+PaymentHandler.getInstance().getPaymentAmount());

            dismiss();
        }

        if(i == R.id.ivPayPal){
            Intent payPalIntent = new Intent(appContext, PaypalLandingActivity.class);
            appContext.startActivity(payPalIntent);
            dismiss();
        }

        if(i == R.id.ivPayNFC){
            Intent tagViewer = new Intent(appContext, TagViewer.class);
            appContext.startActivity(tagViewer);
            dismiss();
        }
    }
}
