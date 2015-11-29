package com.android.OmniPay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends Activity {

    private ImageView mRotatingLogo;
    private TextView mMaqueeSplashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent productIntent = new Intent(SplashScreen.this, MerchantActivity.class);
                SplashScreen.this.startActivity(productIntent);
                SplashScreen.this.finish();
            }
        }, 4000);

        initComponents();
        addListeners();
        setRotateAnimation();
    }

    private void setRotateAnimation() {
//        RotateAnimation rotate = new RotateAnimation(0, 360,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//
//        rotate.setDuration(8000);
//        rotate.setInterpolator(new LinearInterpolator());
//
//        rotate.setRepeatCount(Animation.INFINITE);
        Animation rotate = new AnimationUtils().loadAnimation(this, R.anim.rotate_anim);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        mRotatingLogo.startAnimation(rotate);
    }

    private void addListeners() {

    }

    private void initComponents() {
        mRotatingLogo = (ImageView) findViewById(R.id.ivSplashPayIcon);
        mMaqueeSplashTextView = (TextView) findViewById(R.id.tvSplashMarquee);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mMaqueeSplashTextView.animate().x(100).setDuration(8000).setStartDelay(3000);
                mMaqueeSplashTextView.setSelected(true);
            }
        }, 2000);
    }

}
