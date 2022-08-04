package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_call);
    }


    public void addPhoneCall(View view) {
        EditText customer = findViewById(R.id.customer);
        PhoneBill bill = new PhoneBill(customer.getText().toString());

        EditText caller = findViewById(R.id.caller);
        EditText callee = findViewById(R.id.callee);
        EditText beginDateTime = findViewById(R.id.beginDateTime);
        EditText endDateTime = findViewById(R.id.endDateTime);

        PhoneCall call = new PhoneCall( caller.getText().toString(),
                                        callee.getText().toString(),
                                        beginDateTime.getText().toString(),
                                        endDateTime.getText().toString());

        bill.addPhoneCall(call);

        Toast.makeText(this, call.toString(), Toast.LENGTH_LONG).show();
    }
}