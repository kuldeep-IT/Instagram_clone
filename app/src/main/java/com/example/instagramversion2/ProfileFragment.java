package com.example.instagramversion2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession ,edtProfileHobbies,edtProfileSports;
    private Button btnUpdate;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment=new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileProfession =view.findViewById(R.id.edtProfileProfession);
        edtProfileSports = view.findViewById(R.id.edtProfileSports);

        btnUpdate = view.findViewById(R.id.btnUpdate);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("PNAME") == null)
        {
            edtProfileName.setText("");
            edtProfileBio.setText("");
            edtProfileHobbies.setText("");
            edtProfileProfession.setText("");
            edtProfileSports.setText("");
        }
        else
        {
            edtProfileName.setText(parseUser.get("PNAME").toString());
            edtProfileBio.setText(parseUser.get("PBIO").toString());
            edtProfileHobbies.setText(parseUser.get("PHOBBIES").toString());
            edtProfileProfession.setText(parseUser.get("PPROFESSION").toString());
            edtProfileSports.setText(parseUser.get("PSPORTS").toString());
        }

//        edtProfileName.setText(parseUser.get("PNAME")+"");
//        edtProfileBio.setText(parseUser.get("PBIO")+"");
//        edtProfileHobbies.setText(parseUser.get("PHOBBIES")+"");
//        edtProfileProfession.setText(parseUser.get("PPROFESSION")+"");
//        edtProfileSports.setText(parseUser.get("PSPORTS")+"");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    parseUser.put("PNAME",edtProfileName.getText().toString());
                    parseUser.put("PBIO",edtProfileBio.getText().toString());
                    parseUser.put("PHOBBIES",edtProfileHobbies.getText().toString());
                    parseUser.put("PPROFESSION",edtProfileProfession.getText().toString());
                    parseUser.put("PSPORTS",edtProfileSports.getText().toString());

                ProgressDialog pd =new ProgressDialog(getContext());
                pd.setMessage("please wait ");
                pd.show();

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                if ( TextUtils.isEmpty(edtProfileName.getText().toString()) ||
                                       TextUtils.isEmpty(edtProfileBio.getText().toString()) ||
                                        TextUtils.isEmpty(edtProfileProfession.getText().toString()) ||
                                        TextUtils.isEmpty(edtProfileSports.getText().toString()) ||
                                        TextUtils.isEmpty(edtProfileBio.getText().toString())
                                ) {

                                    Toast.makeText(getContext(), "please fill data...", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Data updated Successfully! " + parseUser.get("PNAME"), Toast.LENGTH_SHORT).show();
                                }
                            }

                            else
                            {
                                Toast.makeText(getContext(), "Error! "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            pd.dismiss();
                        }
                    });
            }
        });

        return view;
    }
}