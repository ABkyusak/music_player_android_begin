package com.example.kabhishek1.musicv1;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

public class musicPlayerTab2 extends Fragment {


    String mode = "articles";
    ProgressDialog pd;
    ListView list;
    String str;
    public static  TextView t;
    static String trackName;
    static String artistName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.musicplayertab2, container, false);

        System.out.println(mode+"inside onCreateView");

//        Button b1 = (Button) rootView.findViewById(R.id.ply);
//        b1.setOnClickListener(this);
        t = (TextView) rootView.findViewById(R.id.txt);
//        own();


//


        return rootView;
    }



    public static void settext(String str){
        t.setText(str);

    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        Log.d(mode +" inside the fragment");
         System.out.println("1111");



    }
    @Override
    public void onResume(){

        super.onResume();
        System.out.println("1111");

    }












}
