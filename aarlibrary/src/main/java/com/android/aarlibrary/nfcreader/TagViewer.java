/*
 * Copyright (C) 2010 The Android Open Source Project
 * Copyright (C) 2011 Adam Nybäck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.aarlibrary.nfcreader;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aarlibrary.CardDetails;
import com.android.aarlibrary.OmniPayButton;
import com.android.aarlibrary.R;
import com.android.aarlibrary.nfcreader.record.ParsedNdefRecord;

import org.w3c.dom.Text;

/**
 * An {@link ActionBarActivity} which handles a broadcast of a new tag that the device just discovered.
 */
public class TagViewer extends ActionBarActivity implements View.OnClickListener {

    private static final DateFormat TIME_FORMAT = SimpleDateFormat.getDateTimeInstance();
    private LinearLayout mTagContent;

    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;

    private AlertDialog mDialog;
    private TextView tvBankName;
    private TextView tvCardNumber;
    private TextView tvExpDt;
    private TextView tvCardName;
    private final String HTAG = "1443619815";
    private final String NTAG = "36073274803562244";
    private final String STAG = "36097286890293508";
    private ImageView ivContactless;
    private LinearLayout llCard;
    private Button btnPay;
    private TextView tagViewerText;
    private EditText etCvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_viewer);
        tvBankName = (TextView) findViewById(R.id.tvBankName);
        tvCardNumber = (TextView) findViewById(R.id.tvCardNumber);
        tvExpDt = (TextView) findViewById(R.id.tvExpDt);
        tvCardName = (TextView) findViewById(R.id.tvCardName);
        tagViewerText = (TextView) findViewById(R.id.tag_viewer_text);

        ivContactless = (ImageView) findViewById(R.id.iv_contactless);
        llCard = (LinearLayout) findViewById(R.id.llCard);
        etCvv = (EditText) findViewById(R.id.etCvv);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);
        

        mTagContent = (LinearLayout) findViewById(R.id.list);
        resolveIntent(getIntent());

        mDialog = new AlertDialog.Builder(this).setNeutralButton("Ok", null).create();

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            showMessage(R.string.error, R.string.no_nfc);
            finish();
            return;
        }

        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mNdefPushMessage = new NdefMessage(new NdefRecord[] { newTextRecord(
                "Message from NFC Reader :-)", Locale.ENGLISH, true) });
    }

    private void showMessage(int title, int message) {
        mDialog.setTitle(title);
        mDialog.setMessage(getText(message));
        mDialog.show();
    }

    private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showWirelessSettingsDialog();
            }
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
            mAdapter.disableForegroundNdefPush(this);
        }
    }

    private void showWirelessSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
        return;
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
                msgs = new NdefMessage[] { msg };
            }
            // Setup the views
            buildTagViews(msgs);
        }
    }

    private String dumpTagData(Parcelable p) {
        StringBuilder sb = new StringBuilder();
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
//        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
//        sb.append("Tag ID (dec): ").append(getDec(id)).append("\n");
//        sb.append("ID (reversed): ").append(getReversed(id)).append("\n\n");

        CardDetails cardDetails = getDummyResponse(""+getDec(id));
        if(cardDetails!=null) {
            tvBankName.setText("" + cardDetails.getBankName());
            tvCardNumber.setText("" + cardDetails.getCardNumber());
            tvCardName.setText("" + cardDetails.getCardName());
            tvExpDt.setText("" + cardDetails.getExpDate());
            llCard.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.VISIBLE);
            ivContactless.setVisibility(View.GONE);
            tagViewerText.setVisibility(View.GONE);
        }else{
            llCard.setVisibility(View.GONE);
            btnPay.setVisibility(View.GONE);
            ivContactless.setVisibility(View.VISIBLE);
            tagViewerText.setVisibility(View.VISIBLE);
        }


//        sb.append("Payment Id : ").append(PaymentHandler.getInstance().getPaymentId()).append("\n");
//        sb.append("Amount     : ").append(PaymentHandler.getInstance().getPaymentAmount()).append("\n\n");

        String prefix = "android.nfc.tech.";
//        sb.append("Technologies: ");
//        for (String tech : tag.getTechList()) {
//            sb.append(tech.substring(prefix.length()));
//            sb.append(", ");
//        }
//        sb.delete(sb.length() - 2, sb.length());
//        for (String tech : tag.getTechList()) {
//            if (tech.equals(MifareClassic.class.getName())) {
//                sb.append('\n');
//                MifareClassic mifareTag = MifareClassic.get(tag);
//                String type = "Unknown";
//                switch (mifareTag.getType()) {
//                case MifareClassic.TYPE_CLASSIC:
//                    type = "Classic";
//                    break;
//                case MifareClassic.TYPE_PLUS:
//                    type = "Plus";
//                    break;
//                case MifareClassic.TYPE_PRO:
//                    type = "Pro";
//                    break;
//                }
//                sb.append("Mifare Classic type: ");
//                sb.append(type);
//                sb.append('\n');
//
//                sb.append("Mifare size: ");
//                sb.append(mifareTag.getSize() + " bytes");
//                sb.append('\n');
//
//                sb.append("Mifare sectors: ");
//                sb.append(mifareTag.getSectorCount());
//                sb.append('\n');
//
//                sb.append("Mifare blocks: ");
//                sb.append(mifareTag.getBlockCount());
//            }
//
//            if (tech.equals(MifareUltralight.class.getName())) {
//                sb.append('\n');
//                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
//                String type = "Unknown";
//                switch (mifareUlTag.getType()) {
//                case MifareUltralight.TYPE_ULTRALIGHT:
//                    type = "Ultralight";
//                    break;
//                case MifareUltralight.TYPE_ULTRALIGHT_C:
//                    type = "Ultralight C";
//                    break;
//                }
//                sb.append("Mifare Ultralight type: ");
//                sb.append(type);
//            }
//        }

        return sb.toString();
    }

    private CardDetails getDummyResponse(String tagId) {
        CardDetails cardDetails = new CardDetails();
        if(tagId.equalsIgnoreCase(HTAG)){
            cardDetails.setBankName("Barclays");
            cardDetails.setCardNumber("4659 0216 6785 7066");
            cardDetails.setCardName("H D Bongale");
            cardDetails.setExpDate("07/19");
        }else if(tagId.equalsIgnoreCase(STAG)){
            cardDetails.setBankName("ICICI");
            cardDetails.setCardNumber("0216 3264 8749 3659");
            cardDetails.setCardName("Shruti Sinha");
            cardDetails.setExpDate("05/18");
        }else if(tagId.equalsIgnoreCase(NTAG)){
            cardDetails.setBankName("State Bank of India");
            cardDetails.setCardNumber("3546 6598 2458 6998");
            cardDetails.setCardName("Neha Agrawal");
            cardDetails.setExpDate("04/17");
        }
        else{
            cardDetails = null;
        }

        return cardDetails;
    }

    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private long getReversed(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout content = mTagContent;

        // Parse the first message in the list
        // Build views for all of the sub records
        Date now = new Date();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();
        for (int i = 0; i < size; i++) {
            TextView timeView = new TextView(this);
            timeView.setText(TIME_FORMAT.format(now));
//            content.addView(timeView, 0);
            ParsedNdefRecord record = records.get(i);
//            content.addView(record.getView(this, inflater, content, i), 1 + i);
//            content.addView(inflater.inflate(R.layout.tag_divider, content, false), 2 + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.menu_main_clear:
//              menuMainClearClick();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void menuMainClearClick() {
        for (int i = mTagContent.getChildCount() -1; i >= 0 ; i--) {
            View view = mTagContent.getChildAt(i);
            if (view.getId() != R.id.tag_viewer_text) {
                mTagContent.removeViewAt(i);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.btnPay){
            Log.d("TAG","-----------Clicked");
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etCvv.getWindowToken(), 0);

            new AlertDialog.Builder(TagViewer.this)
                    .setTitle("Success!")
                    .setMessage("Your payment is successful!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }


    }
}
