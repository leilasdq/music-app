package com.example.homework12.MusicPrepare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.homework12.Model.Music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoadMusics {
    private static List<Music> sMusicList = new ArrayList<>();
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
        Long id;
        String title, album, artist, albumId, picPath = null;

        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
//        Cursor albumCursor = musicResolver.query(albumUri, null, null, null, null);

        try {
            musicCursor.moveToFirst();
            while (!musicCursor.isAfterLast()) {
                id = musicCursor.getLong(musicCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                title = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                album = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                artist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                albumId = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(albumId)},
                        null);

                try {
                    cursor.moveToFirst();
                    picPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                }finally {
                    cursor.close();
                }

                Music music = new Music(id);
                music.setTitle(title);
                music.setAlbum(album);
                music.setSinger(artist);
                music.setPicPath(picPath);

                sMusicList.add(music);

                musicCursor.moveToNext();
            }
        } finally {
            musicCursor.close();
        }
    }

    private static void sortMusics(){
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
