package edu.pdx.cs410j.betruong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sayHello(View view) {
        PhoneCall call = new PhoneCall();
        Toast.makeText(this, "Call detail: " + call, Toast.LENGTH_SHORT).show();
    }
}