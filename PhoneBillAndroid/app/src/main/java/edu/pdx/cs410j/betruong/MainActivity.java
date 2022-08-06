package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        bills.add(new PhoneBill("Khoa"));
        listView.setAdapter(bills);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            PhoneBill bill = (PhoneBill) adapterView.getItemAtPosition(i);
            Toast.makeText(MainActivity.this, bill.getPrettyBillString(), Toast.LENGTH_LONG).show();
        });

        TextView textView = findViewById(R.id.billsCount);
        textView.setText(bills.getCount() + " bills found");

    }

    public void displayREADME(View view) {
        Intent intent = new Intent(this, ReadMe.class);
        startActivity(intent);
    }

    public void addPhoneCall(View view) {
        Intent intent = new Intent(this, AddPhoneCall.class);
        startActivityForResult(intent, GET_CUSTOMER_FROM_ADD_PHONE_CALL);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        TextView textView = findViewById(R.id.billsCount);
        ListView listView = findViewById(R.id.mainPhoneBills);


        if(requestCode == GET_CUSTOMER_FROM_ADD_PHONE_CALL && resultCode == RESULT_OK) {
            PhoneCall call = (PhoneCall) data.getSerializableExtra(AddPhoneCall.EXTRA_CALL);
            String customer = data.getStringExtra(AddPhoneCall.EXTRA_CUSTOMER);
            for (int i = 0; i < bills.getCount(); ++i){
                if (bills.getItem(i).getCustomer().equals(customer)){
                    bills.getItem(i).addPhoneCall(call);
                    textView.setText(bills.getCount() + " bills found");
                    listView.setAdapter(bills);
                    return;
                }
            }
            PhoneBill bill = new PhoneBill(customer);
            bill.addPhoneCall(call);
            bills.add(bill);
            textView.setText(bills.getCount() + " bills found");
            listView.setAdapter(bills);
        }

    }

}