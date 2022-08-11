package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Search extends AppCompatActivity {

    private static final int SEARCH = 84;
    ArrayList<PhoneBill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bills = (ArrayList<PhoneBill>) getIntent().getSerializableExtra(MainActivity.BILLS);
    }

    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH && resultCode == RESULT_OK){
            this.bills = (ArrayList<PhoneBill>) data.getSerializableExtra(MainActivity.BILLS);
        }
    }


    @SuppressLint({"SetTextI18n", "VisibleForTests"})
    public void search(View view) {
        if (bills == null ) {
            Toast.makeText(this, "There is no phone bill", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText customerET = findViewById(R.id.customerToSearch);
        EditText fromTimeET = findViewById(R.id.fromTimeToSearch);
        EditText toTimeET = findViewById(R.id.toTimeToSearch);

        String customer = customerET.getText().toString();
        String fromTime = fromTimeET.getText().toString();
        String toTime = toTimeET.getText().toString();

        StringBuilder result = new StringBuilder();
        if (    customer.length() == 0 &&
                fromTime.length() == 0 &&
                toTime.length() == 0) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show();
        } else {
            Date from = getTime(fromTime);
            Date to = getTime(toTime);

            if (to.compareTo(from) < 0){
                Toast.makeText(this, "Error: begin time is after end time", Toast.LENGTH_SHORT).show();
                return;
            }

            PhoneBill bill;
            assert bills != null;
            for (int i = 0; i<bills.size(); ++i) {
                if (customer.equals(bills.get(i).getCustomer())) {
                    bill = this.bills.get(i);
                    for (PhoneCall call : bill.getPhoneCalls()) {
                        @SuppressLint("VisibleForTests") Date beginTime = call.getBeginTime();
                        if (beginTime.compareTo(from) >= 0 && beginTime.compareTo(to) <= 0) {
                            result.append(call.getPrettyCallString()).append('\n');
                        }
                    }
                }
            }


            TextView output = findViewById(R.id.searchOutput);
            if (result.length() == 0) {
                output.setText("No Phone Call Found");
            } else {
                output.setText(result.toString());
            }
        }
    }

    private Date getTime(String time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy hh:mm aa");
        Date result = null;
        try {
            result = sdf.parse(time);
        } catch (ParseException e) {
            Toast.makeText(this, "Could not parse the time: " + time, Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
