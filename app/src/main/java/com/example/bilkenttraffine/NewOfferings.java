package com.example.bilkenttraffine;

import androidx.appcompat.app.AppCompatActivity;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.util.Base64;

import java.text.DateFormat;
import java.util.Calendar;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class NewOfferings extends AppCompatActivity {

    EditText mRecipientEt, mSubjectEt, mMessageEt;
    Button mSendEmailBtn, btnCamera;
    ImageView imageView;
    Switch autoTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offerings);
        btnCamera = (Button)findViewById(R.id.btnCamera);
        autoTime = (Switch) findViewById(R.id.autoTime);
        imageView = (ImageView)findViewById(R.id.imageView);
        btnCamera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        mMessageEt = findViewById(R.id.messageEt);
        mSubjectEt = findViewById(R.id.subjectEt);//This should be turned into a category
        mSendEmailBtn = findViewById(R.id.sendEmailBtn);
        mSendEmailBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean switchState = autoTime.isChecked();
                String recipient = "hsnyldrmhasan3@hotmail.com";
                String subject = mSubjectEt.getText().toString().trim();
                Date date = Calendar.getInstance().getTime();
                String message = "Dear Bilkent Traffic Office,\n";
                if(switchState==true) {
                    message += "In the date and time of " + date.toString() + " ";
                }
                message += mMessageEt.getText().toString().trim();
                sendEmail(recipient, subject, message );//There is can be location and picture can be added
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)   {
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
    private void sendEmail(String recipient, String subject, String message) {
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto:"));
        mEmailIntent.setType("text/plain");
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient});
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, message);
        try{
            startActivity(Intent.createChooser(mEmailIntent, "Choose an Email Client"));
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
