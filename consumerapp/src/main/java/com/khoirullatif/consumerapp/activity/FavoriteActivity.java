package com.khoirullatif.consumerapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.khoirullatif.consumerapp.MainItems;
import com.khoirullatif.consumerapp.R;
import com.khoirullatif.consumerapp.adapter.FavoriteAdapter;
import com.khoirullatif.consumerapp.db.DatabaseContract;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ArrayList<MainItems> listUser = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavoriteAdapter adapter = new FavoriteAdapter();
        getData();
        adapter.setData(listUser);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(mainItems -> showSelectedUser(mainItems));

        fab = findViewById(R.id.fab_refresh);
        fab.setOnClickListener(view -> {
            super.recreate();
        });
    }

    private void getData() {
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(DatabaseContract.UserColumns.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MainItems mainItems = new MainItems();
            mainItems.setRealname(cursor.getString(cursor.getColumnIndex(DatabaseContract.UserColumns.COLUMN_NAME_REALNAME)));
            mainItems.setName(cursor.getString(cursor.getColumnIndex(DatabaseContract.UserColumns.COLUMN_NAME_USERNAME)));
            mainItems.setCompany(cursor.getString(cursor.getColumnIndex(DatabaseContract.UserColumns.COLUMN_NAME_COMPANY)));
            mainItems.setPhoto(cursor.getString(cursor.getColumnIndex(DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL)));
            listUser.add(mainItems);
            cursor.moveToNext();
        }
    }

    private void showSelectedUser(MainItems mainItems) {
        Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_NAME, mainItems.getName());
        startActivity(intent);
    }
}