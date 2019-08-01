package com.example.debuggers.hackathon;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SMS extends AppCompatActivity {

    EditText num;
    Button send;
    TextView loc;
    private FusedLocationProviderClient clint;
    private static final int REQUEST_LOCATION = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
     requestPermission();
        clint = LocationServices.getFusedLocationProviderClient(this);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SMS.this);

        loc=(TextView)findViewById(R.id.loca);
        num = (EditText) findViewById(R.id.number);
        send = (Button) findViewById(R.id.sndBtn);
        if (ContextCompat.checkSelfPermission(SMS.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SMS.this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(SMS.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            } else {
                ActivityCompat.requestPermissions(SMS.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        } else {

        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//gps

                if (ActivityCompat.checkSelfPermission(SMS.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                clint.getLastLocation().addOnSuccessListener(SMS.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null){


                            loc=(TextView) findViewById(R.id.loca);
                            loc.setText(location.toString());

                            SharedPreferences.Editor editor =  sharedPreferences.edit();
                            editor.putString("Location", String.valueOf(loc));
                        }
                    }
                });
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SMS.this);
                String l =sharedPreferences.getString("Location",null);

                Toast.makeText(SMS.this,l,Toast.LENGTH_SHORT).show();
                String rltv=num.getText().toString();
            String txt = "This is an Emergencey."+"My location is ";
            String num = "01914602985";
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, null, txt, null, null);
                SmsManager nesms=SmsManager.getDefault();
                nesms.sendTextMessage(rltv, null, txt, null, null);
                Toast.makeText(SMS.this,"Sent",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(SMS.this,"Sent",Toast.LENGTH_SHORT).show();
            }

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(rltv, null, txt, null, null);
                Toast.makeText(SMS.this,"Sent",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(SMS.this,"Sent",Toast.LENGTH_SHORT).show();
            }
        }

    });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SMS.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, " NO Permission granted", Toast.LENGTH_SHORT).show();
                }
                return;


        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

}
