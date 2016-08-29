package com.example.gateway.tipcalculator;

import android.annotation.TargetApi;
import java.text.NumberFormat;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener ;
import android.view.View.OnClickListener ;


public class MainActivity extends AppCompatActivity implements OnEditorActionListener {

    private EditText billAmountEditText;
    private TextView tipPercentTextView;
    private SeekBar tipSeekBar;
    private TextView tipAmountTextView;
    private TextView totalTextView;

    private String billAmountString = "";
    private float  tipPercent = .15f;

    private SharedPreferences savedValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setListeners();
    }

    private void initializeVariables(){

        billAmountEditText = (EditText) findViewById(R.id.billAmountEditText);
        tipPercentTextView = (TextView) findViewById(R.id.tipPercentTextView);
        tipSeekBar         = (SeekBar)  findViewById(R.id.tipSeekBar);
        tipAmountTextView  = (TextView) findViewById(R.id.tipAmountTextView);
        totalTextView      = (TextView) findViewById(R.id.totalTextView);

    }
    private void setListeners(){

        billAmountEditText.setOnEditorActionListener(this);
        billAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateAndDisplay();
            }
            //not using
            @Override
            public void afterTextChanged(Editable editable){}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            //done not using
        });

        tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tipPercentTextView.setText(String.valueOf(i)+"%");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calculateAndDisplay();
            }
            //not using
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            //done not using
        });
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {if(i == EditorInfo.IME_ACTION_DONE || i   == EditorInfo.IME_ACTION_UNSPECIFIED){
        calculateAndDisplay();
    }
        return false;
    }

    private void calculateAndDisplay() {
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if(billAmountString.equals("")){
            billAmount = 0;
        }else{
            billAmount = Float.parseFloat(billAmountString);
        }

        int tempTipAmount = tipSeekBar.getProgress();
        tipPercent = (float) tempTipAmount/100;

        float tipAmount = billAmount *tipPercent;
        float totalAmount = billAmount +tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipAmountTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        tipPercentTextView.setText(percent.format(tipPercent));
    }

}
