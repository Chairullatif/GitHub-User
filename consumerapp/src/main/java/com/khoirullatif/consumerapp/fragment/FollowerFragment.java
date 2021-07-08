package com.khoirullatif.consumerapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khoirullatif.consumerapp.R;
import com.khoirullatif.consumerapp.adapter.FollowerAdapter;
import com.khoirullatif.consumerapp.viewmodel.FollowerViewModel;

public class FollowerFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private FollowerAdapter adapter;

    private String username;

    public static FollowerFragment newInstance(String username) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_follower);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new FollowerAdapter();
        recyclerView.setAdapter(adapter);

        FollowerViewModel followerViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel.class);
        followerViewModel.setFollower(username);

        followerViewModel.getFollower().observe(getViewLifecycleOwner(), mainItems -> {
            if (mainItems != null) {
                adapter.setData(mainItems);
            }
        });
        return view;
    }
}