package com.khoirullatif.githubuser.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.khoirullatif.githubuser.BuildConfig;
import com.khoirullatif.githubuser.R;
import com.khoirullatif.githubuser.adapter.SectiontionPagerAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_AVATAR_URL;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_COMPANY;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_FOLLOWER;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_FOLLOWING;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_LOCATION;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_REALNAME;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_REPOSITORY;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.COLUMN_NAME_USERNAME;
import static com.khoirullatif.githubuser.db.DatabaseContract.UserColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    private String usernameId, fullName, followers, following, repositories, company, location, ava_url;
    private TextView tvFullName, tvUsernameId, tvFollowers, tvFollowing, tvRepositories, tvCompany, tvLocation;
    private ImageView ivPhoto;
    private Boolean statusFavorite;
    private FloatingActionButton fab;
    private Uri uriWithUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tvFullName = findViewById(R.id.tv_realname_detail);
        tvUsernameId = findViewById(R.id.tv_username_detail);
        ivPhoto = findViewById(R.id.iv_photo_detail);
        tvFollowers = findViewById(R.id.tv_followers_detail);
        tvFollowing = findViewById(R.id.tv_following_detail);
        tvRepositories = findViewById(R.id.tv_repository_detail);
        tvCompany = findViewById(R.id.tv_company_detail);
        tvLocation = findViewById(R.id.tv_location_detail);

        String mainItems = getIntent().getStringExtra(EXTRA_NAME);

        if (mainItems != null) Log.d("oCreate: username  ", mainItems);

        setDetail(mainItems);

        SectiontionPagerAdapter sectiontionPagerAdapter = new SectiontionPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectiontionPagerAdapter);
        sectiontionPagerAdapter.usernameUser = mainItems;
        setTitle(getResources().getString(R.string.title_detail));
        tabLayout.setupWithViewPager(viewPager);

        uriWithUsername = Uri.parse(CONTENT_URI + "/" + mainItems);

        fab = findViewById(R.id.fab_favorite);
        checkStatusFavorite(mainItems);
        fab.setOnClickListener(v -> {
            statusFavorite =!statusFavorite;
            setStatusFavorite(statusFavorite);
        });
    }

    private void setStatusFavorite(Boolean statusFavorite) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USERNAME, usernameId);
        values.put(COLUMN_NAME_REALNAME, fullName);
        values.put(COLUMN_NAME_AVATAR_URL, ava_url);
        values.put(COLUMN_NAME_COMPANY, company);
        values.put(COLUMN_NAME_LOCATION, location);
        values.put(COLUMN_NAME_REPOSITORY, repositories);
        values.put(COLUMN_NAME_FOLLOWING, following);
        values.put(COLUMN_NAME_FOLLOWER, followers);

        uriWithUsername = Uri.parse(CONTENT_URI + "/" + usernameId);

        if (statusFavorite) {
            fab.setImageResource(R.drawable.favorite);
            getContentResolver().insert(CONTENT_URI, values);
            Toast.makeText(getApplicationContext(), String.format(getString(R.string.success_add_to_favorite), usernameId), Toast.LENGTH_SHORT).show();
        } else {
            fab.setImageResource(R.drawable.border_favorite);

            getContentResolver().delete(uriWithUsername, null, null);
            Toast.makeText(getApplicationContext(), String.format(getString(R.string.success_delete_from_favorite), usernameId), Toast.LENGTH_SHORT).show();
        }

    }

    private void checkStatusFavorite(String name) {
        Log.d("chekSF userName", name);

        @SuppressLint("Recycle") Cursor userCursor = getContentResolver().query(uriWithUsername, null,  null, null, null);

        if (userCursor != null) {
            Log.d("chekSF userName", "not null");
        }

        assert userCursor != null;
        userCursor.moveToFirst();

        if (userCursor.getCount() > 0) {
            String username = userCursor.getString(userCursor.getColumnIndexOrThrow(COLUMN_NAME_USERNAME));
            if (username.equals(name)) {
                fab.setImageResource(R.drawable.favorite);
            } else {
                fab.setImageResource(R.drawable.border_favorite);
            }
            statusFavorite = true;
        } else {
            statusFavorite = false;
        }

    }

    private void setDetail(final String username) {
        String url = BuildConfig.GITHUB_DETAIL_USER + username;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN);
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responObject = new JSONObject(result);

                    usernameId = responObject.getString("login");
                    fullName = responObject.getString("name");
                    followers = responObject.getString("followers");
                    following = responObject.getString("following");
                    repositories = responObject.getString("public_repos");
                    company = responObject.getString("company");
                    location = responObject.getString("location");
                    ava_url = responObject.getString("avatar_url");

                    tvFullName.setText(fullName);
                    tvUsernameId.setText(usernameId);
                    Glide.with(DetailActivity.this)
                            .load(ava_url)
                            .placeholder(R.drawable.loading_picture)
                            .error(R.drawable.error_image_loading)
                            .apply(new RequestOptions()).override(150, 150)
                            .into(ivPhoto);
                    tvRepositories.setText(repositories);
                    tvFollowers.setText(followers);
                    tvFollowing.setText(following);

                    if (company.equals("null")) {
                        tvCompany.setText(String.format(getString(R.string.company), "~"));
                    } else {
                        tvCompany.setText(String.format(getString(R.string.company), company));
                    }

                    if (location.equals("null")) {
                        tvLocation.setText(String.format(getString(R.string.location), "~"));
                    } else {
                        tvLocation.setText(String.format(getString(R.string.location), location));
                    }

                    Log.d("onSuccess ", usernameId);

                } catch (Exception e) {
                    Log.d("onFailure ", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure ", Objects.requireNonNull(error.getMessage()));
            }
        });
    }
}