package com.example.instagramversion2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserPost extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        linearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        String receivedUserName = receivedIntentObject.getStringExtra("username");
        Toast.makeText(this, receivedUserName+"'s world", Toast.LENGTH_SHORT).show();

        setTitle(receivedUserName+"'s world");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog pd =  new ProgressDialog(UserPost.this);
        pd.setMessage("Please wait...");
        pd.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size()>0 && e == null)
                {

                    for (ParseObject post : objects) {

                        TextView txtDescription=new TextView(UserPost.this);
                        txtDescription.setText(post.get("description")+"");

                        ParseFile postPicture =(ParseFile) post.get("picture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null)
                                {

                                    Bitmap bitmap =BitmapFactory.decodeByteArray(data,0,data.length);

                                    ImageView postImage = new ImageView(UserPost.this);
                                    LinearLayout.LayoutParams postImage_Params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    postImage_Params.setMargins(5,5,5,5);

                                    postImage.setLayoutParams(postImage_Params);
                                    postImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImage.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams description_Params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    description_Params.setMargins(5,5,5,5);
                                    txtDescription.setLayoutParams(description_Params);
                                    txtDescription.setGravity(Gravity.CENTER);
                                    txtDescription.setBackgroundColor(Color.RED);
                                    txtDescription.setTextColor(Color.WHITE);
                                    txtDescription.setTextSize(30f);

                                    linearLayout.addView(postImage);
                                    linearLayout.addView(txtDescription);


                                }


                            }
                        });

                    }
                }
                else
                {
                    Toast.makeText(UserPost.this, receivedUserName+" Dosen't have a post!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                pd.dismiss();
            }
        });

    }
}