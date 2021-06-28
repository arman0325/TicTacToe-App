package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
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

public class win3main1p extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private String round_winner = "";
    private int roundCount;
    private int player1Points;
    private boolean gameComplete = false;

    private TextView textViewPlayer1;
    private int temp1;
    private int temp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win3_main_1p);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewPlayer1 = findViewById(R.id.textViewPlayer1);


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
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (round_winner.equals("") || round_winner.equals("1")) {
            for (int m = 0; m < 3; m++) {
                for (int n = 0; n < 3; n++) {
                    if (buttons[m][n].getId() == v.getId())
                    {
                        temp1 = m;
                        temp2 = n;
                        break;
                    }
                }
            }
            Intent intent = new Intent(win3main1p.this, win3sub1p.class);
            intent.putExtra("first_turn", "p1");
            startActivityForResult(intent, 1);
        }
        roundCount++;
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

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,"Winner is player1!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updatePointsText();
                resetBoard();
            }
        }, 3000);
    }

    private void player2Wins() {
        Toast.makeText(this,"Winner is player2!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetGame();
            }
        }, 3000);
    }

    private void draw() {
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                resetBoard();
            }
        }, 1000);
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Score: " + player1Points);
    }

    private void resetBoard() {
        for (int i = 0; i<3;i++){
            for (int j = 0; j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        gameComplete = false;
        round_winner = "";
    }

    //reset the game and the data
    private void resetGame() {
        player1Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCCount", roundCount);
        outState.putInt("player1Points",player1Points);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

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
                break;
            case R.id.about:
                // print ou the about the game

                androidx.appcompat.app.AlertDialog.Builder customizeDialog =
                        new AlertDialog.Builder(win3main1p.this);
                customizeDialog.setTitle(R.string.win3Title1p);
                customizeDialog.setMessage(R.string.win31p);
                customizeDialog.show();
                break;
        }
        return false;
    }

    //onActivityResult used to retrieve value from child activity (win3sub1p)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                round_winner = data.getStringExtra("round_winner");
                if (round_winner.equals("1")) {
                    buttons[temp1][temp2].setText("O");
                    buttons[temp1][temp2].setTextColor(Color.parseColor("#2038E2"));
                }
                else if (round_winner.equals("2")) {
                    buttons[temp1][temp2].setText("X");
                    buttons[temp1][temp2].setTextColor(Color.parseColor("#CE2929"));
                    if (checkWinner()) {
                        //stop auto call child (win3sub1p) if AI won the game
                    }
                    else {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                AI2();
                            }
                        }, 2000);
                    }
                }
                if (checkWinner()) {
                    if (round_winner.equals("1")) {
                        player1Wins();
                    }
                    else if (round_winner.equals("2")) {
                        player2Wins();
                    }
                } else if (roundCount == 9) {
                    draw();
                }
            }
            else {
                finish();
            }
        }
    }

    private void AI(){
        Random rd = new Random();
        if (!gameComplete) {
            for (int i = 0; i < 9; i++) {
                int a = rd.nextInt(3);
                int b = rd.nextInt(3);
                if (buttons[a][b].getText() == "") {
                    roundCount++;
                    temp1 = a;
                    temp2 = b;
                    callChild();
                    break;
                }
            }
        }
    }

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
                temp1 = 1;
                temp2 = 1;
                callChild();
            } else if (map[1][2]=="" &&
                    ((map[2][2]=="O" && map[3][2]=="O") ||
                            (map[1][1]=="O" && map[1][3]=="O"))) {
                temp1 = 1;
                temp2 = 2;
                callChild();
            } else if(map[1][3]=="" &&
                    ((map[1][1]=="O" && map[1][2]=="O") ||
                            (map[3][1]=="O" && map[2][2]=="O") ||
                            (map[2][3]=="O" && map[3][3]=="O"))) {
                temp1 = 1;
                temp2 = 3;
                callChild();
            } else if(map[2][1]=="" &&
                    ((map[2][2]=="O" && map[2][3]=="O") ||
                            (map[1][1]=="O" && map[3][1]=="O"))){
                temp1 = 2;
                temp2 = 1;
                callChild();
            } else if(map[2][2]=="" &&
                    ((map[1][1]=="O" && map[3][3]=="O") ||
                            (map[1][2]=="O" && map[3][2]=="O") ||
                            (map[3][1]=="O" && map[1][3]=="O") ||
                            (map[2][1]=="O" && map[2][3]=="O"))) {
                temp1 = 2;
                temp2 = 2;
                callChild();
            } else if(map[2][3]=="" &&
                    ((map[2][1]=="O" && map[2][2]=="O") ||
                            (map[1][3]=="O" && map[3][3]=="O"))) {
                temp1 = 2;
                temp2 = 3;
                callChild();
            } else if(map[3][1]=="" &&
                    ((map[1][1]=="O" && map[2][1]=="O") ||
                            (map[3][2]=="O" && map[3][3]=="O") ||
                            (map[2][2]=="O" && map[1][3]=="O"))){
                temp1 = 3;
                temp2 = 1;
                callChild();
            } else if(map[3][2]=="" &&
                    ((map[1][2]=="O" && map[2][2]=="O") ||
                            (map[3][1]=="O" && map[3][3]=="O"))) {
                temp1 = 3;
                temp2 = 2;
                callChild();
            } else if( map[3][3]=="" &&
                    ((map[1][1]=="O" && map[2][2]=="O") ||
                            (map[1][3]=="O" && map[2][3]=="O") ||
                            (map[3][1]=="O" && map[3][2]=="O"))) {
                temp1 = 3;
                temp2 = 3;
                callChild();
            }
            else {
                AI(); //else call to random
            }
        }
    }

    //auto call child (win3sub1p) if it is an AI turn
     public void callChild() {
         Intent intent = new Intent(win3main1p.this, win3sub1p.class);
         intent.putExtra("first_turn", "p2");
         startActivityForResult(intent, 1);
     }
}