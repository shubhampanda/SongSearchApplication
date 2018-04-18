package com.example.admin.healofytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.healofytest.adapters.SongViewAdapter;
import com.example.admin.healofytest.model.Song;
import com.example.admin.healofytest.network.SongFetchTask;
import com.example.admin.healofytest.utils.SongDbUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText searchText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initViews();
        if(SongDbUtils.getSongs().size() == 0) {
            Toast.makeText(this,"Fetching the songs data",Toast.LENGTH_SHORT).show();
            fetchSongs();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.song_list);
        searchText = (EditText) findViewById(R.id.song_search);
        setAdapter();
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<Song>  songs = realm.where(Song.class).contains("artist",charSequence.toString(), Case.INSENSITIVE).or().contains("album",charSequence.toString(),Case.INSENSITIVE).findAll();
                SongViewAdapter songViewAdapter = new SongViewAdapter(songs,true);
                recyclerView.setAdapter(songViewAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setAdapter() {
        RealmResults<Song> songs= SongDbUtils.getSongs();
        SongViewAdapter movieDbAdapter =new SongViewAdapter(songs,true);
        recyclerView.setAdapter(movieDbAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchSongs() {
        SongFetchTask songFetchTask = new SongFetchTask(Constants.SONG_FETCH_URL, this, new SongFetchTask.ResponseListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("SongRequestTask","The response is " + response);
                Toast.makeText(MainActivity.this,"The data is successfully fetched",Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("SongRequestTask","The jsonObject is " + jsonObject.toString());
                        SongDbUtils.addSongstoDb(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MainActivity.this,"Error in loading data",Toast.LENGTH_SHORT).show();

            }
        });
        songFetchTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
