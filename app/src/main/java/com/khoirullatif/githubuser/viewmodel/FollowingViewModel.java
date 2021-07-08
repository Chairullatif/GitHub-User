package com.khoirullatif.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.khoirullatif.githubuser.BuildConfig;
import com.khoirullatif.githubuser.MainItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowingViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<MainItems>> listUser = new MutableLiveData<>();

    public void setFollower(final String username) {

        final ArrayList<MainItems> listItems = new ArrayList<>();

        String url = "https://api.github.com/users/" + username + "/following";

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", BuildConfig.GITHUB_TOKEN);
        client.addHeader("User-Agent", "request");

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Log.d("onSuccess_following ", "Success");
                    String result = new String(responseBody);
                    JSONArray responArray = new JSONArray(result);

                    for (int i = 0; i < responArray.length(); i++) {
                        JSONObject jsonObject = responArray.getJSONObject(i);
                        MainItems mainItems = new MainItems();

                        mainItems.setName(jsonObject.getString("login"));
                        mainItems.setPhoto(jsonObject.getString("avatar_url"));

                        listItems.add(mainItems);
                    }
                    Log.d("onSuccess_following ", listItems.get(3).getName());
                    listUser.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception ", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailur_following", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MainItems>> getFollower() {
        return listUser;
    }
}
