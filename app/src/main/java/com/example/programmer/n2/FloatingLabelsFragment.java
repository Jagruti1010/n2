package com.example.programmer.n2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class FloatingLabelsFragment extends Fragment{
    private WebView webannc;
    private RecyclerView recycle;

    private DatabaseReference mdatabaseReference;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_floating_labels, container, false);
        recycle=(RecyclerView)rootView.findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);



        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Announcements");
        mdatabaseReference.keepSynced(true);
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<pubs, BlogViewHolder>(
                pubs.class,
                R.layout.anncs,
                BlogViewHolder.class,
                mdatabaseReference
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, final pubs model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setdesc(model.getDesc());
                viewHolder.setimg(getActivity().getApplicationContext(),model.getImage());
                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),model.getTitle(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };



        recycle.setAdapter(firebaseRecyclerAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;

    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {

        View view;
        public BlogViewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }
        public void setTitle(String Title)
        {
            TextView posttitle = (TextView)view.findViewById(R.id.posttitle);
            posttitle.setText(Title);
        }
        public void setdesc(String postdesc)
        {
            TextView postdes = (TextView)view.findViewById(R.id.postdes);
            postdes.setText(postdesc);
        }
        public void setimg(final Context ctx, final String postimag)
        {
            final ImageView postimage = (ImageView)view.findViewById(R.id.postimage);
            Picasso.with(ctx).load(postimag).networkPolicy(NetworkPolicy.OFFLINE).into(postimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(postimag).into(postimage);

                }
            });


        }
    }

}
