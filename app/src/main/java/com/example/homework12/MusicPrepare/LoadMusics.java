package com.example.homework12.MusicPrepare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.homework12.Model.Music;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoadMusics {
    private static List<Music> sMusicList;
    private static Context mContext;

    public static List<Music> getSortedMusic(Context context){
        mContext = context.getApplicationContext();

        sortMusics();

        return sMusicList;
    }

    public static List<Music> getShuffledMusic(Context context){
        mContext = context.getApplicationContext();

        shuffleMusics();

        return sMusicList;
    }

    private static void getExternalMusicList() {
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = musicResolver.query(uri, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast()) {
                Long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                Music music = new Music(id);
                music.setTitle(title);
                music.setAlbum(album);
                music.setSinger(artist);

                sMusicList.add(music);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    private static void getInternalMusicList() {
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

        Cursor cursor = musicResolver.query(uri, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast()) {
                Long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                Music music = new Music(id);
                music.setTitle(title);
                music.setAlbum(album);
                music.setSinger(artist);

                sMusicList.add(music);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    private static void sortMusics(){
        getInternalMusicList();
        getExternalMusicList();

        Collections.sort(sMusicList, new Comparator<Music>() {
            @Override
            public int compare(Music first, Music second) {
                return first.getTitle().compareTo(second.getTitle());
            }
        });
    }

    private static void shuffleMusics(){
        sortMusics();

        Collections.shuffle(sMusicList);
    }
}
