package com.example.admin.healofytest.utils;

import com.example.admin.healofytest.Constants;
import com.example.admin.healofytest.model.Song;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by admin on 18/04/18.
 */

public class SongDbUtils {

    public static void addSongstoDb(JSONObject song){

        try {
            String name  = song.getString(Constants.KEY_NAME);
            String artist = song.getString(Constants.KEY_ARTIST);
            String album = song.getString(Constants.KEY_ALBUM);
            saveSongtoDatabase(name,artist,album);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private  static  void saveSongtoDatabase(String name,String artist,String album) {
        Realm realm = Realm.getDefaultInstance();
        // Creating a new movie
        final Song song = new Song();
        song.setAlbum(album);
        song.setName(name);
        song.setArtist(artist);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // This will create a new object in Realm or throw an exception if the
                // object already exists (same primary key)
                // realm.copyToRealm(obj);

                // This will update an existing object with the same primary key
                // or create a new object if an object with no primary key = 42
                realm.copyToRealmOrUpdate(song);
            }
        });
    }

    public static RealmResults<Song> getSongs(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Song> songs = realm.where(Song.class).findAll();
        return songs;
    }

    public static void clearMovies(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Song> songs = realm.where(Song.class).findAll();
        songs.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
