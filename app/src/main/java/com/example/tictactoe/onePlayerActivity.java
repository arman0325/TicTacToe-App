package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Random;

import static com.example.tictactoe.MainActivity.audio;

public class onePlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private boolean gameComplete = false;

    private TextView textViewPlayer1;

    private boolean started = false;
    private boolean first = false; //first run boolean
    long time;
    private String timerTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//the button in toolbar to back to main page
        textViewPlayer1 = findViewById(R.id.textViewPlayer1);

        //setting the button onclick listener of each button
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                String ButtonID = "button_" + x + y;
                int resID = getResources().getIdentifier(ButtonID,"id",getPackageName());
                buttons[x][y] = findViewById(resID);
                buttons[x][y].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            //player 1 select message
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#2038E2"));
            // change the player turn
            player1Turn = !player1Turn;
            if(!started){
                started = true;
                if(!first){ // solve the problem with run more time after reset
                    first=true;
                    startTimer();
                }
            }
        }else{
            //player2 select message
            //((Button) v).setText("X");
        }

        roundCount++;
        if(!gameComplete){
            if(checkWinner()) {
                gameComplete = true;
                player1Wins();
            }else if (roundCount == 9){
                draw();
            } else{
                AI2();
            }
        }


    }

    // using to check the winner
    private boolean checkWinner(){
        String [][] field = new String[3][3];
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //check the vertical is win
        for(int i = 0; i<3; i++){
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                buttons[i][0].setTextColor(Color.parseColor("#FFD145"));
                buttons[i][1].setTextColor(Color.parseColor("#FFD145"));
                buttons[i][2].setTextColor(Color.parseColor("#FFD145"));
                return true;
            }
        }

        //check the horizontal is win
        for(int i = 0; i<3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                buttons[0][i].setTextColor(Color.parseColor("#FFD145"));
                buttons[1][i].setTextColor(Color.parseColor("#FFD145"));
                buttons[2][i].setTextColor(Color.parseColor("#FFD145"));
                return true;
            }
        }

        //check the slash case 1("\") is win
        for(int i = 0; i<3; i++){
            if (field[0][0].equals(field[1][1])
                    && field[0][0].equals(field[2][2])
                    && !field[0][0].equals("")){
                buttons[0][0].setTextColor(Color.parseColor("#FFD145"));
                buttons[1][1].setTextColor(Color.parseColor("#FFD145"));
                buttons[2][2].setTextColor(Color.parseColor("#FFD145"));
                return true;
            }
        }

        //check the slash case 2("/") is win
        for(int i = 0; i<3; i++){
            if (field[0][2].equals(field[1][1])
                    && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")){
                buttons[0][2].setTextColor(Color.parseColor("#FFD145"));
                buttons[1][1].setTextColor(Color.parseColor("#FFD145"));
                buttons[2][0].setTextColor(Color.parseColor("#FFD145"));
                return true;
            }
        }

        return false;
    }

    //print the player 1 win message
    private void player1Wins() {
        player1Points++;
        //Toast.makeText(this,"Winner is player1!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                resetBoard();
                // Actions to do after 0.1 seconds
            }
        }, 3000);// 1000 = 1sec

    }

    //print the player 2 win message
    private void player2Wins() {
        //Toast.makeText(this,"Winner is player2!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        started = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                time = System.currentTimeMillis()-time;
                msg(player1Points,time/1000);
                resetGame();
                // Actions to do after 0.1 seconds
            }
        }, 3000);// 1000 = 1sec
    }

    //print the draw message
    private void draw() {
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetBoard();
                // Actions to do after 0.1 seconds
            }
        }, 3000);// 1000 = 1sec
    }

    // update the point table
    private void updatePointsText() {
        textViewPlayer1.setText("Score: " + player1Points);
    }

    //reset the board
    private void resetBoard() {
        for (int i = 0; i<3;i++){
            for (int j = 0; j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        gameComplete = false;
        player1Turn = true;
    }

    //reset the game and the data
    private void resetGame() {
        player1Points = 0;
        updatePointsText();
        resetBoard();
    }

    // the first AI with all random
    private void AI(){
        Random rd = new Random();
        if (!gameComplete) {
            for (int i = 0; i < 9; i++) {
                int a = rd.nextInt(3);
                int b = rd.nextInt(3);
                if (buttons[a][b].getText() == "") {
                    buttons[a][b].setText("X");
                    buttons[a][b].setTextColor(Color.parseColor("#CE2929"));
                    roundCount++;
                    if (checkWinner()) {
                        player2Wins();
                    } else if (roundCount == 9) {
                        draw();
                    }
                    player1Turn = !player1Turn;
                    //player1Turn = true;
                    break;
                }
            }
        }
    }

    //the second AI with mid level
    private void AI2(){
        if (!gameComplete){
            String[][] map = new String[4][4];
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    map[i][j] = buttons[i][j].getText().toString();
                }
            }
            if(map[1][1]=="" &&
                    ((map[1][2]=="O" && map[1][3]=="O") ||
                            (map[2][2]=="O" && map[3][3]=="O") ||
                            (map[2][1]=="O" && map[3][1]=="O"))) {
                buttons[1][1].setText("X");
                buttons[1][1].setTextColor(Color.parseColor("#CE2929"));
            } else if (map[1][2]=="" &&
                    ((map[2][2]=="O" && map[3][2]=="O") ||
                            (map[1][1]=="O" && map[1][3]=="O"))) {
                buttons[1][2].setText("X");
                buttons[1][2].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[1][3]=="" &&
                    ((map[1][1]=="O" && map[1][2]=="O") ||
                            (map[3][1]=="O" && map[2][2]=="O") ||
                            (map[2][3]=="O" && map[3][3]=="O"))) {
                buttons[1][3].setText("X");
                buttons[1][3].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[2][1]=="" &&
                    ((map[2][2]=="O" && map[2][3]=="O") ||
                            (map[1][1]=="O" && map[3][1]=="O"))){
                buttons[2][1].setText("X");
                buttons[2][1].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[2][2]=="" &&
                    ((map[1][1]=="O" && map[3][3]=="O") ||
                            (map[1][2]=="O" && map[3][2]=="O") ||
                            (map[3][1]=="O" && map[1][3]=="O") ||
                            (map[2][1]=="O" && map[2][3]=="O"))) {
                buttons[2][2].setText("X");
                buttons[2][2].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[2][3]=="" &&
                    ((map[2][1]=="O" && map[2][2]=="O") ||
                            (map[1][3]=="O" && map[3][3]=="O"))) {
                buttons[2][3].setText("X");
                buttons[2][3].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[3][1]=="" &&
                    ((map[1][1]=="O" && map[2][1]=="O") ||
                            (map[3][2]=="O" && map[3][3]=="O") ||
                            (map[2][2]=="O" && map[1][3]=="O"))){
                buttons[3][1].setText("X");
                buttons[3][1].setTextColor(Color.parseColor("#CE2929"));
            } else if(map[3][2]=="" &&
                    ((map[1][2]=="O" && map[2][2]=="O") ||
                            (map[3][1]=="O" && map[3][3]=="O"))) {
                buttons[3][2].setText("X");
                buttons[3][2].setTextColor(Color.parseColor("#CE2929"));
            }else if( map[3][3]=="" &&
                    ((map[1][1]=="O" && map[2][2]=="O") ||
                            (map[1][3]=="O" && map[2][3]=="O") ||
                            (map[3][1]=="O" && map[3][2]=="O"))) {
                buttons[3][3].setText("X");
                buttons[3][3].setTextColor(Color.parseColor("#CE2929"));
            }else{
                AI();//else call to random
            }



        }
    }

    // print the game massage (score and time) in dialog
    // player can input the name to save the record to server
    private void msg(int score, float gameTime){
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(onePlayerActivity.this);
        final View dialogView = LayoutInflater.from(onePlayerActivity.this)
                .inflate(R.layout.dialog_username,null);
        customizeDialog.setTitle("Enter your name to record");
        customizeDialog.setView(dialogView);
        customizeDialog.setMessage("Your score is " + score + "\nThe time is " + timerTime);
        customizeDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // get the text in editText
                        EditText edit_text = (EditText) dialogView.findViewById(R.id.edit_text);
                        String str1 = edit_text.getText().toString();
                        String str2 = score+"";
                        String urlString = "http://armanser.asuscomm.com/sentData.php?id=" + str1 + "&score=" + str2 + "&gameTime=" + timerTime;
                        // the toast is testing message
                        //Toast.makeText(getApplicationContext(), urlString, Toast.LENGTH_LONG).show();
                        try {
                            //send to the server
                            send(urlString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        i=0;
                        started=false;

                    }
                });
        customizeDialog.setNeutralButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        i=0;
                        started=false;
                    }
                }
        );


        customizeDialog.show();
    }

    public static final int CHANGE_TIPS_TIMER_INTERVAL = 1000;
    private Handler mChangeTipsHandler = new Handler(Looper.getMainLooper());
    int i = 0;
    private void startTimer() {
        Runnable mChangeTipsRunnable = new Runnable() {
            @Override
            public void run() {
                TextView tV = (TextView)findViewById(R.id.timer);
                if(started){
                    i=i+1;
                }
                if (i<60){
                    if(i<10){
                        timerTime = "00:0"+i;
                    }else{
                        timerTime = "00:"+i;
                    }
                }else{
                    int min = i/60;
                    int sec = i - (min*60);
                    if(min<10){
                        if(sec<10){
                            timerTime = "0"+ min + ":0"+sec;
                        }else{
                            timerTime = "0"+ min + ":"+sec;
                        }
                    }else{
                        if(sec<10){
                            timerTime = min + ":0"+sec;
                        }else{
                            timerTime = min + ":"+sec;
                        }
                    }
                }
                tV.setText(timerTime);
                mChangeTipsHandler.postDelayed(this, CHANGE_TIPS_TIMER_INTERVAL);
            }
        };

        mChangeTipsHandler.post(mChangeTipsRunnable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCCount", roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.one_player_menu, menu);
        return true;
    }

    // Called when an options menu item is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            audio.release();
            finish();
        }
        switch (item.getItemId()) {
            case R.id.restart:
                // run the reset the game
                resetGame();
                i=0;//reset the timer
                started=false;
                break;
            case R.id.about:
                // print out the about the game
                AlertDialog.Builder customizeDialog =
                        new AlertDialog.Builder(onePlayerActivity.this);
                customizeDialog.setTitle(R.string.oneplayerTitle);
                customizeDialog.setMessage(R.string.onePlayerAbout);
                customizeDialog.show();
                break;
        }

        return false;
    }
    // send the the server using POST,GET method
    private void send(String str) throws IOException {
        StrictMode.enableDefaults();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(str);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //Toast.makeText(getApplicationContext(), str+"Data inserted", Toast.LENGTH_LONG).show();
            //Log.e("pass 1", "connection success");
        }catch (Exception e){
            //Log.e("Fail",e.toString());
            //Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();

        }
    }
}