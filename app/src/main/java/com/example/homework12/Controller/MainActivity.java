package com.example.homework12.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.homework12.Model.Music;
import com.example.homework12.MusicPrepare.LoadMusics;
import com.example.homework12.MusicPrepare.MusicManager;
import com.example.homework12.R;
import com.example.homework12.Utils.PictureUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MusicFragment.getBottomSheet {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private LinearLayout sheetContainer;
    private LinearLayout bottomSize;

    private TextView musicTitle, musicSinger, start, end;
    private ImageView cover, image;
    private ImageButton topPlayButton, play_pause, next, pervious, shuffle, repeat;
    private SeekBar collapsedSeek, expandSeek;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    private Handler myHandler = new Handler();
    private List<Music> mMusicList;
    private Music mMusic;
    private MusicManager mMusicManager;
    private double startTime = 0;
    private double finalTime = 0;
    int currentPosition;

    private MusicFragment mMusicFragment;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        toolbar_setup();
        navigation_setup();
        setUpBottomSheet();
        listeners();
    }

    private void setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(sheetContainer);
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            topPlayButton.setVisibility(View.GONE);
        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            topPlayButton.setVisibility(View.VISIBLE);
        }
    }

    private void listeners() {
        bottomSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandBottomSheet();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandBottomSheet();
            }
        });
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandBottomSheet();
            }
        });
        musicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandBottomSheet();
            }
        });
        musicSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandBottomSheet();
            }
        });
        expandSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser) {
                    mMusicManager.seekToPosition(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicManager.pause();
                setPlayButtons();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicManager.next();
                currentPosition = mMusicManager.getPosition();
                initBottomSheetViews(mMusicList.get(currentPosition));
            }
        });
        pervious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMusicManager.previous();
                currentPosition = mMusicManager.getPosition();
                initBottomSheetViews(mMusicList.get(currentPosition));
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mMusicManager.next();
                currentPosition = mMusicManager.getPosition();
                initBottomSheetViews(mMusicList.get(currentPosition));
            }
        });
    }

    private void expandBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            topPlayButton.setVisibility(View.GONE);
            collapsedSeek.setVisibility(View.GONE);
        } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            topPlayButton.setVisibility(View.VISIBLE);
            collapsedSeek.setVisibility(View.VISIBLE);
        }
    }

    private void toolbar_setup() {
        setSupportActionBar(mToolbar);
    }

    private void navigation_setup() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, MusicFragment.newInstance()).commit();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()){
                    case R.id.bnm_music:
                        selectedFragment = MusicFragment.newInstance();
                        break;
                    case R.id.bnm_album:
                        selectedFragment = AlbumFragment.newInstance();
                        break;
                    case R.id.bnm_singer:
                        selectedFragment = SingerFragment.newInstance();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            }
        });
    }

    private void initViews() {
        mMusicList = LoadMusics.getSortedMusic(this);
        mMusicManager = MusicManager.getInstance(this, mMusicList);
        mediaPlayer = mMusicManager.getMediaPlayer();
        sheetContainer = findViewById(R.id.bottom_sheet);
        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomSize = findViewById(R.id.bottom_size);

        musicTitle = findViewById(R.id.music_name);
        musicSinger = findViewById(R.id.singer_name);
        cover = findViewById(R.id.little_cover);
        image = findViewById(R.id.big_cover);
        topPlayButton = findViewById(R.id.top_plat_btn);
        expandSeek = findViewById(R.id.expanded_seekBar);
        collapsedSeek = findViewById(R.id.collapsed_seekBar);
        expandSeek.setClickable(false);
        start = findViewById(R.id.start_time);
        end = findViewById(R.id.final_time);
        play_pause = findViewById(R.id.play);
        next = findViewById(R.id.next);
        pervious = findViewById(R.id.previous);
        shuffle = findViewById(R.id.shuffle);
        repeat = findViewById(R.id.replay);

        mMusicFragment = MusicFragment.newInstance();
    }

    @Override
    public void onItemSelected(Music music, List<Music> musicList) {
        mMusicList = musicList;
        mMusic = music;
        if (getMusicPosition(music) != null){
            currentPosition = getMusicPosition(music);
        }

        bottomSheetBehavior.setPeekHeight(2 * bottomSize.getHeight());
        expandSeek.setProgress((int)startTime);
        initBottomSheetViews(music);
    }

    private void initBottomSheetViews(Music music) {
        mMusicFragment.onPlaySelected(music, this, currentPosition);
        setPlayButtons();

        musicTitle.setText(music.getTitle());
        musicSinger.setText(music.getSinger());
        String path = music.getPicPath();
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap biggerBitmap = PictureUtils.getScaledBitmap(path, this);
            cover.setImageBitmap(bitmap);
            image.setImageBitmap(biggerBitmap);
        } else {
            cover.setImageResource(R.drawable.no_cover);
            image.setImageResource(R.drawable.no_cover);
        }

        startTime = mMusicManager.getStartTime();
        finalTime = mMusicManager.getFinalTime();
        end.setText(String.format("%d:%2d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime))));
        start.setText(String.format("%d:%2d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime))));

        expandSeek.setMax((int) finalTime);
        expandSeek.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
    }

    private void setPlayButtons() {
        if (mMusicManager.isPlayed()){
            topPlayButton.setImageResource(R.drawable.ic_action_black_pause);
            play_pause.setBackgroundResource(R.drawable.ic_pause);
        } else {
            topPlayButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            play_pause.setBackgroundResource(R.drawable.ic_play_button);
        }
    }

    @Override
    public int bottomMarginSize() {
        return  bottomSize.getHeight();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            topPlayButton.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private Integer getMusicPosition(Music music){
        for (int i = 0; i < mMusicList.size() ; i++) {
            if (mMusicList.get(i) == music){
                return i;
            }
        }
        return null;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            start.setText(String.format("%d:%2d",
                    TimeUnit.MILLISECONDS.toMinutes((long) currentPosition),
                    TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentPosition)))
            );
            setPlayButtons();
            myHandler.postDelayed(this, 100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                expandSeek.setProgress(currentPosition, true);
            } else {
                expandSeek.setProgress(currentPosition);
            }
        }
    };
}
