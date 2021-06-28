package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static MediaPlayer audio;
    Animation scale_up,scale_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //background music
        audio= MediaPlayer.create(MainActivity.this,R.raw.cancanoffenbach);
        audio.setLooping(true);
        Button btn = (Button)findViewById(R.id.button);
        //button animation
        scale_up= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scale_down=AnimationUtils.loadAnimation(this,R.anim.scale_down);


        //2 player mode touch animation
        //2 player mode click event
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, gamePage.class);
                startActivity(intent);
            }
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btn.startAnimation(scale_up);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btn.startAnimation(scale_down);

                }
                return false;
            }
        });
        //set button touch animation
        Button btn_onePlayerMode = (Button) findViewById(R.id.button2);

        //set button touch animation
        //buttonclick
        btn_onePlayerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, onePlayerActivity.class);
                startActivity(intent);
            }

            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btn_onePlayerMode.startAnimation(scale_up);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_onePlayerMode.startAnimation(scale_down);

                }
                return false;
            }
        });

        Button btn_win3Mode = (Button) findViewById(R.id.button4);
        //button click animation
        btn_win3Mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, win3main.class);
                startActivity(intent);
            }

            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btn_win3Mode.startAnimation(scale_up);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_win3Mode.startAnimation(scale_down);

                }
                return false;
            }
        });

        Button btn_win3Mode1p = (Button) findViewById(R.id.button5);
        //button click animation
        btn_win3Mode1p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, win3main1p.class);
                startActivity(intent);
            }

            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btn_win3Mode1p.startAnimation(scale_up);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_win3Mode1p.startAnimation(scale_down);

                }
                return false;
            }
        });

        Button btn_Score= (Button) findViewById(R.id.buttonScore);
        //button click event
        btn_Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, getScore.class);
                startActivity(intent);
            }

            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btn_Score.startAnimation(scale_up);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_Score.startAnimation(scale_down);

                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(MainActivity.this, pref.class);
                startActivity(i);
                return true;
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Tic Tac Toe");
                String aboutMsg = getString(R.string.aboutText);
                builder.setMessage(aboutMsg);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            case R.id.developer:
                androidx.appcompat.app.AlertDialog.Builder customizeDialog =
                        new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.dialog_developer,null);
                customizeDialog.setView(dialogView);
                customizeDialog.show();
                return true;
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean audio_enable = prefs.getBoolean(
                getString(R.string.audio),
                getResources().getBoolean(R.bool.audio_enable));

        String checkSong = prefs.getString(
                getString(R.string.key),
                getString(R.string.mp3_default)
        );

        switch (checkSong){
            case "CanCan":
                audio.release();
                audio= MediaPlayer.create(MainActivity.this,R.raw.cancanoffenbach);
                audio.setLooping(true);
                break;
            case "CoffeeStains":
                audio.release();
                audio= MediaPlayer.create(MainActivity.this,R.raw.coffeestainsriot);
                audio.setLooping(true);
                break;
            case "Silver":
                audio.release();
                audio= MediaPlayer.create(MainActivity.this,R.raw.silverriot);
                audio.setLooping(true);
                break;
            case "WishYouComeTrue":
                audio.release();
                audio= MediaPlayer.create(MainActivity.this,R.raw.wishyoucometruethe126ers);
                audio.setLooping(true);
                break;
            case "SecretConversations":
                audio.release();
                audio= MediaPlayer.create(MainActivity.this,R.raw.secretconversationsthe126ers);
                audio.setLooping(true);
                break;
            default:
                break;
        }

        if (audio_enable){
            audio.start();
        }else{
            audio.stop();
            audio.release();
        }


    }
    public static class pref extends PreferenceActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FragmentManager mFragmentManager = getFragmentManager();
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

            prefFragment mPrefsFragment = new prefFragment();
            mFragmentTransaction.replace(android.R.id.content, mPrefsFragment);
            mFragmentTransaction.commit();
        }
    }

    public static class prefFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }
    }

}