package com.android.aarlibrary.dao;

import com.android.aarlibrary.listeners.OnOmniPaymentListener;

/**
 * Created by HARSHAD on 26/11/2015.
 */
public class PaymentHandler {

    private static PaymentHandler paymentHandler = new PaymentHandler( );
    private PaymentHandler(){ }

    public static PaymentHandler getInstance( ) {
        return paymentHandler;
    }
    private String paymentId;
    private String paymentAmount;
    private OnOmniPaymentListener onOmniPaymentListener;


    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public OnOmniPaymentListener getOnOmniPaymentListener() {
        return onOmniPaymentListener;
    }

    public void setOnOmniPaymentListener(OnOmniPaymentListener onOmniPaymentListener) {
        this.onOmniPaymentListener = onOmniPaymentListener;
    }
}
