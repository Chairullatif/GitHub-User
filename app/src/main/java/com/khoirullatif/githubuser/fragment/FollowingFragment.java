package com.khoirullatif.githubuser.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khoirullatif.githubuser.R;
import com.khoirullatif.githubuser.adapter.FollowingAdapter;

import com.khoirullatif.githubuser.viewmodel.FollowingViewModel;

public class FollowingFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private FollowingAdapter adapter;

    private String username;

    public static FollowingFragment newInstance(String username) {
        FollowingFragment fragment = new FollowingFragment();
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
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_following);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new FollowingAdapter();
        recyclerView.setAdapter(adapter);

        FollowingViewModel followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);
        followingViewModel.setFollower(username);

        followingViewModel.getFollower().observe(getViewLifecycleOwner(), mainItems -> {
            if (mainItems != null) {
                adapter.setData(mainItems);
            }
        });
        return view;
    }
}