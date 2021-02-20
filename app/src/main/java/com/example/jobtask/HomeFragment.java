package com.example.jobtask;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    View myView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

        public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_by_name:
                if (checked)
                    // Pirates are the best
                    Toast.makeText(view.getContext(),"Name",Toast.LENGTH_SHORT).show();
                    break;
            case R.id.rb_by_alphabet:
                if (checked)
                    // Ninjas rule
                    Toast.makeText(view.getContext(),"Alphabet",Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);


        RadioGroup radioGroup = (RadioGroup) myView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
//
//                switch(checkedId) {
//                    case R.id.radioButton7:
//                        // switch to fragment 1
//                        break;
//                    case R.id.radioButton6:
//                        // Fragment 2
//                        break;
//                }
                //boolean checked = ((RadioButton) myView).isChecked();

                switch(checkedId) {
                    case R.id.rb_by_name:
                            // Pirates are the best
                            Log.e("tag","name");
                            Toast.makeText(myView.getContext(),"Name",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_by_alphabet:
                            // Ninjas rule
                            Log.e("tag","Alpha");
                            Toast.makeText(myView.getContext(),"Alphabet",Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });
        // Inflate the layout for this fragment
        return myView;
    }
}