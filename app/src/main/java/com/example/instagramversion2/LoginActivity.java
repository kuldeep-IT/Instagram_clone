package com.example.instagramversion2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {

   private EditText edtLoginEmail,edtLoginPass;
   private Button btnLogin,btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPass = findViewById(R.id.edtLoginPassWord);
        btnSingUp = findViewById(R.id.btnLSignUp1);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        //if u press endter in keyboard of device then it will work as pressed button
        edtLoginPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    loginButtonTapped();
                }

                return false;
            }
        });

        btnLogin = findViewById(R.id.btnLogin);

        loginButtonTapped();





    }

    public void loginButtonTapped()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtLoginEmail.getText().toString(),
                        edtLoginPass.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                if (e==null && user != null)
                                {

                                    FancyToast.makeText(LoginActivity.this,"Voila ! registered as "+user.get("email"),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true);
//                                    Toast.makeText(LoginActivity.this, "vops", Toast.LENGTH_SHORT).show();
                                    moveToSociaMediaActivity();
                                }
                                else
                                {
//                                    FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });
    }


// hide the keyboard for touching outside blank ground
    public void rootTappedlogin(View view) {
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
        Intent i1= new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(i1);
        finish();
    }



}