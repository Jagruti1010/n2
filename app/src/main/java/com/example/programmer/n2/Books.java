package com.example.programmer.n2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class Books extends Fragment{
    Firebase firebase1;
    Firebase firebase2;
    private FirebaseAuth firebaseAuth1;
    private ArrayList<String> username1 = new ArrayList<>();
    private ListView listview1;
    private PullRefreshLayout pullRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);
        Firebase.setAndroidContext(getActivity());
        firebase1=new Firebase("https://app1-efc7c.firebaseio.com/");
        firebase2 = firebase1.child("Books");


        pullRefreshLayout=(PullRefreshLayout)rootView.findViewById(R.id.pullRefreshLayout);
        final ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,username1);
        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
                listview1.setAdapter(arrayAdapter1);

            }
        });

                listview1 = (ListView) rootView.findViewById(R.id.listview1);


        listview1.setAdapter(arrayAdapter1);
        firebase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String users = dataSnapshot.getValue(String.class);
                username1.add(users);
                arrayAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                arrayAdapter1.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                arrayAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                arrayAdapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value =(String)listview1.getItemAtPosition(position);

                    Toast.makeText(getActivity(), "" + value, Toast.LENGTH_SHORT).show();

            }
        });


        firebaseAuth1=FirebaseAuth.getInstance();

        if(firebaseAuth1.getCurrentUser()==null)
        {
            getActivity().finish();
            startActivity(new Intent(getActivity(),login.class));
        }
        return rootView;

    }
}
