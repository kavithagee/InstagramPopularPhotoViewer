package com.example.kavitha.codepathproject1instagramviewer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MediaActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "d68393e2ae994a7aa7efa383275acfb8";
    private ArrayList <InstagramMedia>  allInstagramMedia;
    private InstagramMediaAdapter aMedia;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        allInstagramMedia = new ArrayList<InstagramMedia>();
        aMedia = new InstagramMediaAdapter(this, allInstagramMedia);

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aMedia);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //        send API req
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //for the first launch
        fetchPopularPhotos();
    }
    public void fetchPopularPhotos(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        client.get(url, null, new JsonHttpResponseHandler(){
//            onSuccess
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                data received, populate the model
                JSONArray mediaJSON = null;
                aMedia.clear();
                try{
                    mediaJSON = (JSONArray) response.get("data");
                    for (int i=0; i < mediaJSON.length(); i++) {
                        JSONObject mediaUnitJSON = (JSONObject) mediaJSON.getJSONObject(i);
                        InstagramMedia mediaUnit = new InstagramMedia();
                        mediaUnit.userName = mediaUnitJSON.getJSONObject("user").getString("username");
                        mediaUnit.caption = !mediaUnitJSON.isNull("caption") ? mediaUnitJSON.getJSONObject("caption").getString("text") : "";
                        mediaUnit.userPhotoUrl = mediaUnitJSON.getJSONObject("user").getString("profile_picture");
                        mediaUnit.type = mediaUnitJSON.getString("type");
                        mediaUnit.mediaUrl = mediaUnitJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        mediaUnit.likesCount = mediaUnitJSON.getJSONObject("likes").getInt("count");
                        mediaUnit.mediaHeight = mediaUnitJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        if (!mediaUnitJSON.isNull("comments")) {
                            JSONArray comments = mediaUnitJSON.getJSONObject("comments").getJSONArray("data");
                            for (int j = 0; j < comments.length(); j++) {
                                JSONObject comment = (JSONObject)comments.get(j);
                                mediaUnit.comments.add(new CommentObj(comment.getString("text"), comment.getInt("created_time"), comment.getJSONObject("from").getString("username"), comment.getJSONObject("from").getString("profile_picture")));
                            }
                        }
                        allInstagramMedia.add(mediaUnit);

                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                aMedia.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);

            }
//            onFailure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class CommentObj {
        public String text;
        public int createdTime;
        public String fromUsername;
        public String userPhotoUrl;

        public CommentObj(String text, int timestamp, String username, String userPhotoUrl) {
            this.text = text;
            this.createdTime = timestamp;
            this.fromUsername = username;
            this.userPhotoUrl = userPhotoUrl;
        }

        @Override
        public String toString() {
            return "CommentObj{" +
                    "text='" + text + '\'' +
                    ", createdTime=" + createdTime +
                    ", fromUsername='" + fromUsername + '\'' +
                    ", userPhotoUrl='" + userPhotoUrl + '\'' +
                    '}';
        }
    }
}
