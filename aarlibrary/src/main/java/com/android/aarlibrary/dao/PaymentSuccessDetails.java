package com.android.aarlibrary.dao;

/**
 * Created by HARSHAD on 29/11/2015.
 */
public class PaymentSuccessDetails {
    private String paymentServiceProvider;
    private String paymentAmount;
    private String paymentConfirmationId;
    private String dateAndTime;

    public String getPaymentServiceProvider() {
        return paymentServiceProvider;
    }

    public void setPaymentServiceProvider(String paymentServiceProvider) {
        this.paymentServiceProvider = paymentServiceProvider;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentConfirmationId() {
        return paymentConfirmationId;
    }

    public void setPaymentConfirmationId(String paymentConfirmationId) {
        this.paymentConfirmationId = paymentConfirmationId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
