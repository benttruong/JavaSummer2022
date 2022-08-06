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
import java.util.Collection;
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
            bills.addAll((Collection<? extends PhoneBill>) readFromBillsFile());
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
        Pattern p = Pattern.compile("^Customer: (.*)$");

        try (BufferedReader br = new BufferedReader(new FileReader(billsFile))) {

            String toRead = br.readLine();
            if (toRead == null) {
                throw new ParserException(("File is empty"));
            }
            String customer;
            PhoneBill bill = null;
            while (toRead != null) {
                Matcher m = p.matcher(toRead);
                if (m.matches()) {
                    customer = m.group(1);
                    bill = new PhoneBill(customer);
                    bills.add(bill);
                } else {
                    String[] values = toRead.split("Phone call from | to | from | ");
                    if (values.length != 9) {
                        throw new ParserException("Data is malformed");
                    }

                    if (PhoneCall.isValidPhoneNumber(values[1]) &&
                            PhoneCall.isValidPhoneNumber(values[2]) &&
                            PhoneCall.isValidDate(values[3]) &&
                            PhoneCall.isValidTime(values[4]) &&
                            PhoneCall.isValidMeridiem(values[5]) &&
                            PhoneCall.isValidDate(values[6]) &&
                            PhoneCall.isValidTime(values[7]) &&
                            PhoneCall.isValidMeridiem(values[8])) {
                        try {
                            bill.addPhoneCall(new PhoneCall(values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]));
                        } catch (PhoneCall.PhoneCallException e) {
                            throw new PhoneCall.PhoneCallException();
                        }
                    } else {
                        throw new ParserException("Phone call from file has malformed data");
                    }
                    toRead = br.readLine();
                }

            }
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
                        pw.println(call);
                    }
                }
            }
        }

        @NonNull
        private File getBillsFile() {
            File dataDirectory = this.getDataDir();
            File billFile = new File(dataDirectory, "bills.txt");
            return billFile;
        }

    }