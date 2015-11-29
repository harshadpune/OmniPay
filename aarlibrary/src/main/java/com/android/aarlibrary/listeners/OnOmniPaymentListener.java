package com.android.aarlibrary.listeners;

import com.android.aarlibrary.dao.PaymentSuccessDetails;

/**
 * Created by HARSHAD on 29/11/2015.
 */
public interface OnOmniPaymentListener {
    void getPaymentInfo(PaymentSuccessDetails paymentSuccessDetails);
}
