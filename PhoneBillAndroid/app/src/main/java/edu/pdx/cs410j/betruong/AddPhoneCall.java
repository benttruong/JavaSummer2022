package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.pdx.cs410j.betruong.PhoneCall.PhoneCallException;

public class AddPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }


    public void addPhoneCall(View view) {
        EditText customerText = findViewById(R.id.customer);

        EditText callerText = findViewById(R.id.caller);
        EditText calleeText = findViewById(R.id.callee);
        EditText beginDateTimeText = findViewById(R.id.beginDateTime);
        EditText endDateTimeText = findViewById(R.id.endDateTime);

        String customer = customerText.getText().toString();
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
        PhoneBill bill = new PhoneBill(customer);

        PhoneCall call;
        try {
            call = new PhoneCall(caller, callee, beginDateTime, endDateTime);
        } catch (PhoneCallException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        bill.addPhoneCall(call);

        Toast.makeText(this, call.toString(), Toast.LENGTH_LONG).show();
    }
}