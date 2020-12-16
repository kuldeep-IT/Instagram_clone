package com.example.instagramversion2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment=new UserFragment();
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
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        listView = view.findViewById(R.id.listView);

        listView.setOnItemClickListener(UserFragment.this);
        listView.setOnItemLongClickListener(UserFragment.this);

        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);

        TextView textView = view.findViewById(R.id.textView);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

       parseQuery.findInBackground(new FindCallback<ParseUser>() {
           @Override
           public void done(List<ParseUser> users, ParseException e) {
               if (e == null)
               {
                   if (users.size()>0)
                   {
                       for (ParseUser user:users)
                       {
                            arrayList.add(user.getUsername());
                       }
                       listView.setAdapter(arrayAdapter);
//                       textView.animate().alpha(0).setDuration(2000);
//                       listView.setVisibility(View.VISIBLE);
                   }
               }
           }
       });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(),UserPost.class);
       intent.putExtra("username",arrayList.get(position)); // make Arraylist<String> on up side declaration
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

      ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
      parseQuery.whereEqualTo("username",arrayList.get(position));

      parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
          @Override
          public void done(ParseUser user, ParseException e) {

              if (user != null && e == null)
              {
//                  Toast.makeText(getContext(), user.get("PSPORTS")+"", Toast.LENGTH_SHORT).show();

//                  FancyToast.makeText(getContext(),user.get("PSPORTS")+"",FancyToast.LENGTH_LONG,
//                          FancyToast.SUCCESS,true).show();

                  PrettyDialog pDialog = new PrettyDialog(getContext());
                  pDialog
                          .setTitle(user.getUsername()+"'s Info")
                          .setMessage(user.get("PBIO")+"\n"
                                  +user.get("PPROFESSION")+"\n"
                                  +user.get("PSPORTS")+"\n"
                                  +user.get("PHOBBIES")+"\n"
                                  +user.get("PBIO")+"\n")
                          .setIcon(R.drawable.person)
                          .addButton(
                                  "Ok",
                                  R.color.pdlg_color_white,
                                  R.color.pdlg_color_red,
                                  new PrettyDialogCallback() {
                                      @Override
                                      public void onClick() {
                                          pDialog.dismiss();
                                      }
                                  }
                          )
                          .show();


              }

          }
      });

      return true;
    }
}