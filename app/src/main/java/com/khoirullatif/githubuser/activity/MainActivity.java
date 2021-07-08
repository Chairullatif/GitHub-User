package com.khoirullatif.githubuser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khoirullatif.githubuser.MainItems;
import com.khoirullatif.githubuser.R;
import com.khoirullatif.githubuser.adapter.MainAdapter;
import com.khoirullatif.githubuser.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private Button btnSearch;
    private MainViewModel mainViewModel;
    private MainAdapter adapter;
    private ProgressBar progressBar;
    private EditText edtName;
    private ImageView ivSearch;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btn_search);
        progressBar = findViewById(R.id.progress_bar);
        edtName = findViewById(R.id.edt_name);
        tvSearch = findViewById(R.id.tv_search);
        ivSearch = findViewById(R.id.iv_search);
        Toolbar toolbar = findViewById(R.id.toolbar_main);

        showPicture(true);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        edtName.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                btnSearch.performClick();
            }
            return false;
        });

        btnSearch.setOnClickListener(view -> {
            String nameId = edtName.getText().toString();

            if (TextUtils.isEmpty(nameId)) return;
            showPicture(false);
            showLoading(true);

            mainViewModel.setUserItem(nameId);
        });

        mainViewModel.getUserItems().observe(this, mainItems -> {
            if (mainItems != null) {
                adapter.setData(mainItems);
                showLoading(false);
                showPicture(false);
            }
        });

        adapter.setOnItemClickCallback(this::showSelectedUser);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_to_favorite:
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_setting:
                Intent intent1 = new Intent(this, SettingActivity.class);
                startActivity(intent1);
                return true;
            default:
                return true;
        }
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showPicture(boolean state) {
        if (state) {
            ivSearch.setVisibility(View.VISIBLE);
            tvSearch.setVisibility(View.VISIBLE);
        } else {
            ivSearch.setVisibility(View.GONE);
            tvSearch.setVisibility(View.GONE);
        }
    }

    private void showSelectedUser(MainItems mainItems) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_NAME, mainItems.getName());
        startActivity(intent);
    }
}