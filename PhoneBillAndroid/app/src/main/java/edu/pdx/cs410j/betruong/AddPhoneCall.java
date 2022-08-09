package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import edu.pdx.cs410j.betruong.PhoneCall.PhoneCallException;

public class AddPhoneCall extends AppCompatActivity {

    static final String EXTRA_CUSTOMER = "CUSTOMER";
    static final String EXTRA_CALL = "CALL";
    private PhoneCall call;
    private String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }


    @SuppressLint("VisibleForTests")
    public void addPhoneCall(View view) {
        EditText customerText = findViewById(R.id.customerToSearch);

        EditText callerText = findViewById(R.id.caller);
        EditText calleeText = findViewById(R.id.callee);
        EditText beginDateTimeText = findViewById(R.id.beginDateTime);
        EditText endDateTimeText = findViewById(R.id.endDateTime);

        customer = customerText.getText().toString();
        String caller = callerText.getText().toString();
        String callee = calleeText.getText().toString();
        String beginDateTime = beginDateTimeText.getText().toString();
        String endDateTime = endDateTimeText.getText().toString();

        if (    customer.length() == 0 ||
                caller.length() == 0 ||
                callee.length() == 0 ||
                beginDateTime.length() == 0 ||
                endDateTime.length() == 0){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            this.call = new PhoneCall(caller, callee, beginDateTime, endDateTime);
        } catch (PhoneCallException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch prettyCall = (Switch) findViewById(R.id.printCall);

        if (prettyCall.isChecked()) {
            Toast.makeText(this, call.getPrettyCallString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Phone call added", Toast.LENGTH_LONG).show();
        }
        savePhoneCall(view);
    }

    public void savePhoneCall(View view){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUSTOMER, this.customer);
        intent.putExtra(EXTRA_CALL, call);
        setResult(RESULT_OK, intent);
        finish();
    }

}