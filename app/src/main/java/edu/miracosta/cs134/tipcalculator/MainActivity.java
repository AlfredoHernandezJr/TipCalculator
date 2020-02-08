package edu.miracosta.cs134.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    // Instance variables
    // Bridge the view and the model
    private Bill currentBill ;
    private EditText amountEditText ;
    private TextView percentTextView ;
    private SeekBar percentSeekBar ;
    private TextView tipTextView ;
    private TextView totalTextView ;

    // Instance variables to format output (currency and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault()) ;
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault()) ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;

        // Wire up instance variables initialize them.
        currentBill = new Bill() ;
        amountEditText = findViewById(R.id.amountEditText) ;
        percentTextView = findViewById(R.id.percentTextView) ;
        percentSeekBar = findViewById(R.id.percentSeekBar) ;
        tipTextView = findViewById(R.id.tipTextView) ;
        totalTextView = findViewById(R.id.totalTextView) ;

        // Let's set the current tip percentage.
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0) ;

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Doesn't matter.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Read the input from the amountExitText 9View) and store the currentBill (Model)
                try
                {
                    double amount = Double.parseDouble(amountEditText.getText().toString()) ;
                    // Store the amount in the bill.
                    currentBill.setAmount(amount) ;
                } catch(NumberFormatException e) {
                    currentBill.setAmount(0.0) ;
                }

                calculateBill() ;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Doesn't matter
            }
        }) ;

        // Implement the interface for the SeekBar.
        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Update the tip percent
                currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0) ;
                percentTextView.setText(percent.format(currentBill.getTipPercent())) ;

                calculateBill() ;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calculateBill()
    {
        // Update the tipTextView and the totalTextView.
        tipTextView.setText(currency.format(currentBill.getTipAmount())) ;
        totalTextView.setText(currency.format(currentBill.getTotalAmount())) ;
    }
}
