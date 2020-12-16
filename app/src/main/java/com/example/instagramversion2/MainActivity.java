package com.example.instagramversion2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private EditText edtUserName,edtEmail,edtPass;
   private Button btnSignUp,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUserName  = findViewById(R.id.edtSingUpUserName);
        edtEmail = findViewById(R.id.edtSingUpEmail);
        edtPass = findViewById(R.id.edtSingUpPassWord);

        edtPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                    event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(btnSignUp);
                }

                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(MainActivity.this);


//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ParseUser userData = new ParseUser();
//                userData.setUsername(edtUserName.getText().toString());
//                userData.setEmail(edtEmail.getText().toString());
//                userData.setPassword(edtPass.getText().toString());
//
//                ProgressDialog pd= new ProgressDialog(MainActivity.this);
//                pd.setMessage("Please wait... "+ edtUserName.getText().toString());
//                pd.show();
//
//                userData.signUpInBackground(new SignUpCallback() {
//                    @Override
//                    public void done(ParseException e) {
//
//                        if (e==null)
//                        {
//                         FancyToast.makeText(MainActivity.this,"Voila ! registered as "+userData.get("email"),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true);
//
//                            Intent i1 = new Intent(MainActivity.this,LoginActivity.class);
//                            startActivity(i1);
//
//
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                        pd.dismiss();
//                    }
//                });
//
//
//
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i2);
            }
        });

        if (ParseUser.getCurrentUser() != null)
        {
            moveToSociaMediaActivity();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignUp:

                if (TextUtils.isEmpty(edtUserName.getText().toString()) ||
                        TextUtils.isEmpty(edtEmail.getText().toString()) ||
                        TextUtils.isEmpty(edtPass.getText().toString()) )
                {
//                    FancyToast.makeText(MainActivity.this, "Please fill the data! ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false);
                    Toast.makeText(this, "Errrrrror", Toast.LENGTH_SHORT).show();
                }

                else {

                    ParseUser userData=new ParseUser();
                    userData.setUsername(edtUserName.getText().toString());
                    userData.setEmail(edtEmail.getText().toString());
                    userData.setPassword(edtPass.getText().toString());

                    ProgressDialog pd=new ProgressDialog(MainActivity.this);
                    pd.setMessage("Please wait... " + edtUserName.getText().toString());
                    pd.show();

                    userData.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, "Voila ! registered as " + userData.get("email"), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true);

                                Intent i1=new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i1);

                                moveToSociaMediaActivity();

                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();
                        }
                    });


                }

        }
    }

    public void rootTapped(View view) {

        try {
            InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void moveToSociaMediaActivity()
    {
        Intent i1= new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(i1);
        finish();
    }
}