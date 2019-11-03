package com.example.homework12.MusicPrepare;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.homework12.Model.Music;

import java.io.IOException;
import java.util.List;

public class MusicManager {
    private static MusicManager ourInstance = null;

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private List<Music> mMusicList;

    private double startTime = 0;
    private double finalTime = 0;

    public static MusicManager getInstance(Context context, List<Music> musics) {
        if (ourInstance == null){
            ourInstance = new MusicManager(context, musics);
        }
        return ourInstance;
    }

    private MusicManager(Context context, List<Music> musics) {
        mContext = context.getApplicationContext();
        mMusicList = musics;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public double getStartTime() {
        return startTime;
    }

    public double getFinalTime() {
        return finalTime;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void play(Long musicId)  {
        Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicId);

        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();

        try {
            mMediaPlayer.setDataSource(mContext, musicUri);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error for second music", "play: " + e.getMessage() );
        }

        mMediaPlayer.start();
        finalTime = mMediaPlayer.getDuration();
        startTime = mMediaPlayer.getCurrentPosition();
    }

    public void seekToPosition(int progress){
        mMediaPlayer.seekTo(progress);
    }
}
