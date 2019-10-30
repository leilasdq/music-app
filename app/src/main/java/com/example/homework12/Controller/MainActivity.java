package com.example.homework12.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homework12.Model.Music;
import com.example.homework12.R;
import com.example.homework12.Utils.PictureUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity implements MusicFragment.getBottomSheet {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private LinearLayout sheetContainer;
    private LinearLayout bottomSize;

    private TextView musicTitle, musicSinger;
    private ImageView cover, image;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        toolbar_setup();
        navigation_setup();

        bottomSheetBehavior = BottomSheetBehavior.from(sheetContainer);
        bottomSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        image.setOnClickListener(null);
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
        sheetContainer = findViewById(R.id.bottom_sheet);
        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomSize = findViewById(R.id.bottom_size);

        musicTitle = findViewById(R.id.music_name);
        musicSinger = findViewById(R.id.singer_name);
        cover = findViewById(R.id.little_cover);
        image = findViewById(R.id.big_cover);
    }

    @Override
    public void onItemSelected(Music music) {

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

        bottomSheetBehavior.setPeekHeight(2 * bottomSize.getHeight());
    }

    @Override
    public int bottomMarginSize() {
        return  bottomSize.getHeight();
    }
}
