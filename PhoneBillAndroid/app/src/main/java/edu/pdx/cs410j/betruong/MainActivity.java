package edu.pdx.cs410j.betruong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {


    private static final int GET_CUSTOMER_FROM_ADD_PHONE_CALL = 42;

    private ArrayAdapter<PhoneBill> bills;

    @SuppressLint({"SetTextI18n", "VisibleForTests"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.mainPhoneBills);

        bills = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        try {
            bills.addAll(readFromBillsFile());
        } catch (IOException | ParserException | PhoneCall.PhoneCallException e) {
            Toast.makeText(this, "Something wrong while reading files. Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        listView.setAdapter(bills);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            PhoneBill bill = (PhoneBill) adapterView.getItemAtPosition(i);
            Toast.makeText(MainActivity.this, bill.getPrettyBillString(), Toast.LENGTH_LONG).show();
        });

        TextView textView = findViewById(R.id.billsCount);
        textView.setText(bills.getCount() + " bills found");

    }

    @SuppressLint("VisibleForTests")
    protected ArrayList<PhoneBill> readFromBillsFile() throws IOException, ParserException, PhoneCall.PhoneCallException {
        ArrayList<PhoneBill> bills = new ArrayList<>();
        File billsFile = getBillsFile();
        Pattern c = Pattern.compile("^Customer: (.*)$");
        Pattern p = Pattern.compile("^Phone call from (.*) to (.*) from (.*) to (.*)$");

        try (BufferedReader br = new BufferedReader(new FileReader(billsFile))) {

            String customer;
            PhoneBill bill = null;
            String toRead;
            while ((toRead = br.readLine()) != null) {
                Matcher customerMatcher = c.matcher(toRead);
                Matcher phoneCallMatcher = p.matcher(toRead);
                if (customerMatcher.matches()) {
                    if (bill != null) {
                        bills.add(bill);
                    }
                    customer = customerMatcher.group(1);
                    bill = new PhoneBill(customer);
                    bills.add(bill);
                } else if (phoneCallMatcher.matches()) {
                    PhoneCall call = new PhoneCall(
                            phoneCallMatcher.group(1),
                            phoneCallMatcher.group(2),
                            phoneCallMatcher.group(3),
                            phoneCallMatcher.group(4));
                    assert bill != null;
                    bill.addPhoneCall(call);
                }
            }
        } catch (IOException e){
            Toast.makeText(this, "This is the first time the app is ran and there is no data yet", Toast.LENGTH_SHORT).show();
        }
        return bills;
    }

        public void displayREADME (View view){
            Intent intent = new Intent(this, ReadMe.class);
            startActivity(intent);
        }

        public void addPhoneCall (View view){
            Intent intent = new Intent(this, AddPhoneCall.class);
            startActivityForResult(intent, GET_CUSTOMER_FROM_ADD_PHONE_CALL);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            super.onActivityResult(requestCode, resultCode, data);
            TextView textView = findViewById(R.id.billsCount);
            ListView listView = findViewById(R.id.mainPhoneBills);


            if (requestCode == GET_CUSTOMER_FROM_ADD_PHONE_CALL && resultCode == RESULT_OK) {
                PhoneCall call = (PhoneCall) data.getSerializableExtra(AddPhoneCall.EXTRA_CALL);
                String customer = data.getStringExtra(AddPhoneCall.EXTRA_CUSTOMER);
                for (int i = 0; i < bills.getCount(); ++i) {
                    if (bills.getItem(i).getCustomer().equals(customer)) {
                        bills.getItem(i).addPhoneCall(call);
                        textView.setText(bills.getCount() + " bills found");
                        listView.setAdapter(bills);
                        try {
                            writeToBillsFile();
                        } catch (IOException e) {
                            Toast.makeText(this, "Something wrong while writing files. Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                }
                PhoneBill bill = new PhoneBill(customer);
                bill.addPhoneCall(call);
                bills.add(bill);
                textView.setText(bills.getCount() + " bills found");
                listView.setAdapter(bills);
                try {
                    writeToBillsFile();
                } catch (IOException e) {
                    Toast.makeText(this, "Something wrong while writing files. Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }

        private void writeToBillsFile () throws IOException {
            File billFile = getBillsFile();
            try (PrintWriter pw = new PrintWriter(new FileWriter(billFile))) {
                for (int i = 0; i < bills.getCount(); ++i) {
                    pw.println("Customer: " + bills.getItem(i).getCustomer());
                    for (PhoneCall call : bills.getItem(i).getPhoneCalls()) {
                        pw.println(call.toFile());
                    }
                }
            }
        }

        @NonNull
        private File getBillsFile() {
            File dataDirectory = this.getDataDir();
            return new File(dataDirectory, "bills.txt");
        }

    }