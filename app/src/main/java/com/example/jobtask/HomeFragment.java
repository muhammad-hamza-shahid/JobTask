package com.example.jobtask;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.network.ApiClient;
import com.example.jobtask.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View myView;
    public static ApiService apiInterface;

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
        apiInterface = ApiClient.getApiClient().create(ApiService.class);
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
        SearchView searchView = (SearchView) myView.findViewById(R.id.searchView);
        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(myView.getContext(),"query" +query,Toast.LENGTH_SHORT).show();

                Call<DrinkResponce> call =apiInterface.getByName(query);

                call.enqueue(new Callback<DrinkResponce>() {
                    @Override
                    public void onResponse(Call<DrinkResponce> call, Response<DrinkResponce> response) {
                        if(response.isSuccessful()){
                        // add your code to get data
                            String detailsJson = response.body().getAllDrinks().toString();
                            List<DrinkResponce> allDrinks = new ArrayList<>();
                         //   DrinkResponce singleDrink = new DrinkResponce();

                        }
                        else if(!response.isSuccessful()){
                            //display error message
                            Toast.makeText(myView.getContext(),""+response.code(),Toast.LENGTH_SHORT).show();
                            Log.i("tag",response.toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<DrinkResponce> call, Throwable t) {
                        Toast.makeText(myView.getContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.i("tag",t.getMessage());
                    }
                });


                return false;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(myView.getContext(),"query new text",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Toast.makeText(myView.getContext(),"Clicked",Toast.LENGTH_SHORT).show();

                                          }
                                      }
        );
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

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