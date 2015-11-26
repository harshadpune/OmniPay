package com.android.aarlibrary;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by HARSHAD on 25/11/2015.
 */
public class OmniPayButton extends Button implements View.OnClickListener {


    public static Context appContext;
    private OnOmniPayClickListener listener;

    public OmniPayButton(Context context) {
        super(context);
        this.appContext = context;
        init();
    }

    public OmniPayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.appContext = context;
        init();
    }

    public OmniPayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.appContext = context;
        init();
    }

    public OmniPayButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.appContext = context;
        init();
    }

    @Override
    public void onClick(View v) {
        CustomDialog customDialog = new CustomDialog(appContext);
        customDialog.show();
        listener.setPaymentDetails();
    }

    public void init(){
        this.setOnClickListener(this);
        this.setText("OmniPay");
    }

    public void setOnOmniPayClickListener(OnOmniPayClickListener listener){
        this.listener = listener;
    }
}
