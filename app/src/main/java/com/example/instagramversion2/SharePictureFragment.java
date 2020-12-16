package com.example.instagramversion2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePictureFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    private EditText edtName;
    private Button btnShare;
    private Bitmap receivedImageBitmap;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SharePictureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharePictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SharePictureFragment newInstance(String param1, String param2) {
        SharePictureFragment fragment=new SharePictureFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture, container, false);

        edtName = view.findViewById(R.id.edtShareName);
        imageView = view.findViewById(R.id.imageView2);
        btnShare = view.findViewById(R.id.btnShareImage);

        imageView.setOnClickListener(SharePictureFragment.this);
        btnShare.setOnClickListener(SharePictureFragment.this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.imageView2:

                if (Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
               != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);

//                    now its send to onRequestPermissionsResult function
                }
                else
                {
                    getChosenImage();
                }


                break;

            case R.id.btnShareImage:

                if (receivedImageBitmap != null)
                {
                    if (edtName.getText().toString().equals(""))
                    {
                        Toast.makeText(getContext(), "Error...: please select image nd fill the text!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //first we convert image into byte
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        //convert into byteArray
                        byte[] bytes = byteArrayOutputStream.toByteArray();

                        ParseFile parseFile = new ParseFile("img.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("description",edtName.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        ProgressDialog pd = new ProgressDialog(getContext());
                        pd.setMessage("Loading...");
                        pd.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null)
                                {
                                    Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                                }
                                pd.dismiss();
                            }
                        });

                    }
                }


                break;


        }

    }

    private void getChosenImage() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000)
        {
            if (grantResults.length>0 && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED)
            {
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000)
        {
            if (resultCode == Activity.RESULT_OK)
            {

                //for the captured image
                try {

                    Uri selectedImages = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getActivity().getContentResolver().query(selectedImages,
                            filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    // Getting selected image into Bitmap.
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(receivedImageBitmap);

                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}