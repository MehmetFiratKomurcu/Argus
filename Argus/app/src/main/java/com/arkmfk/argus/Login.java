package com.arkmfk.argus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextView username_tb;
    TextView password_tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_tb = findViewById(R.id.username_tb);
        password_tb = findViewById(R.id.password_tb);

        final Button login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent i = new Intent(getBaseContext(), GPS.class);
                //startActivity(i);
                loginControl();
            }
        });
        final Button reset_btn = (Button) findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SignUpChangePass(Login.this, username_tb.getText().toString(),
                        password_tb.getText().toString(), "changepass");
            }
        });
        final Button signup_btn = (Button) findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SignUpChangePass(Login.this, username_tb.getText().toString(),
                        password_tb.getText().toString(), "register");
            }
        });
        final Button settings_btn = (Button) findViewById(R.id.settings_btn);
        settings_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    public void loginControl(){
        //Intent i = new Intent(getBaseContext(), JSONParse.class);
        //startActivity(i);
        SignInControl sign = new SignInControl(this, username_tb.getText().toString(),
                password_tb.getText().toString());
    }


    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Location Permission")
                .setMessage("Please grant location permission to this app for use all features.")
                .setPositiveButton("Permission Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
}

