package com.example.instagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.instagram.R;
import com.example.instagram.adapters.TagAdapter;
import com.example.instagram.adapters.UserAdapter;
import com.example.instagram.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class SearchFragment extends Fragment {

    private RecyclerView usersRecyclerView;
    private List<User> mUsers;
    private UserAdapter mUserAdapter;


    private RecyclerView tagsRecyclerView;
    private List<String> mHashTags;
    private List<String> mHashTagsCount;
    private TagAdapter mTagAdapter;

    private SocialAutoCompleteTextView  searchbar;


  
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_search, container, false);

       usersRecyclerView = view.findViewById(R.id.recycler_view_users);
       usersRecyclerView.setHasFixedSize(true  );
       usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       mUsers   =   new ArrayList<>();
       mUserAdapter = new UserAdapter(getContext(),mUsers,true);
       usersRecyclerView.setAdapter(mUserAdapter);


       tagsRecyclerView = view.findViewById(R.id.recycler_view_tags);
       tagsRecyclerView.setHasFixedSize(true);
       tagsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       mHashTags = new ArrayList<>();
       mHashTagsCount   = new ArrayList<>();
       mTagAdapter = new TagAdapter(getContext(),mHashTags,mHashTagsCount);
       tagsRecyclerView.setAdapter(mTagAdapter);

       searchbar = view.findViewById(R.id.search_bar);

       readUsers();
       readTags();

       searchbar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               searchUser(charSequence.toString());
           }

           @Override
           public void afterTextChanged(Editable editable) {
               filter(editable.toString());
           }
       });

       return view;
    }



    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(TextUtils.isEmpty(searchbar.getText().toString())){
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User user = snapshot.getValue(User.class);
                        mUsers.add(user);
                    }
                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchUser(String s){
        Query query = FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("username").startAt(s).endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    mUsers.add(user);
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private  void readTags(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("HashTags");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mHashTags.clear();
                mHashTagsCount.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mHashTags.add(snapshot.getKey());
                    mHashTagsCount.add(snapshot.getChildrenCount()+"");
                }
                mTagAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void filter(String text) {

        List<String> mSearchTags = new ArrayList<>();
        List<String> mSearchTagsCount = new ArrayList<>();

        for (String s : mHashTags) {
            if (s.toLowerCase().contains(text.toLowerCase())){
                mSearchTags.add(s);
                mSearchTagsCount.add(mHashTagsCount.get(mHashTags.indexOf(s)));
            }
        }

        mTagAdapter.filter(mSearchTags , mSearchTagsCount);
    }
}