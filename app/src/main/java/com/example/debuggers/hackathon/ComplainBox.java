package com.example.debuggers.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComplainBox extends AppCompatActivity {

    private EditText suject;
    private EditText name;
    private EditText complain;
    Button sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_box);

        suject=(EditText)findViewById(R.id.sub);
        name=(EditText)findViewById(R.id.Name);
        complain=(EditText)findViewById(R.id.complain);

        sendMsg=(Button)findViewById(R.id.snd);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }

    private void sendMail() {
//        String recipient="";
        String subj=suject.getText().toString();
        String namComp ="Name of complainer : "+name.getText().toString()+ " \n Complain:" +complain.getText().toString();

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "Mhain.al.hoque@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT,subj);
        intent.putExtra(Intent.EXTRA_TEXT,namComp);

        intent.setType("massge/rfc822");
        startActivity(Intent.createChooser(intent, "Send mail..."));
        //        try {
//            startActivity(Intent.createChooser(intent, "Send mail..."));
//            finish();
//            Toast.makeText(ComplainBox.this, "Finished sending email...", Toast.LENGTH_SHORT).show();
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(ComplainBox.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
    }
}
