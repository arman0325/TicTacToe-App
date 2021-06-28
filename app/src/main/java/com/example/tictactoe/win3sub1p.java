package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class win3sub1p extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn;
    private int roundCount;
    private String first_turn;
    private boolean gameComplete = false;

    //private TextView textViewPlayer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win3_sub_1p);
        //textViewPlayer1 = findViewById(R.id.textStatus);
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                String ButtonID = "button_" + x + y;
                int resID = getResources().getIdentifier(ButtonID,"id",getPackageName());
                buttons[x][y] = findViewById(resID);
                buttons[x][y].setOnClickListener(this);
            }
        }
        //get value from win3main which player take the first turn
        Intent i1 = getIntent();
        first_turn = i1.getStringExtra("first_turn");
        //p1 represent player 1 take the first turn
        if (first_turn.equals("p1")) {
            player1Turn = true;
        }
        else if (first_turn.equals("p2")) {
            player1Turn = false;
            AI2();
        }
    }

    @Override
    public void onClick(View v) {
        //textViewPlayer1.setText(String.valueOf(roundCount));
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#2038E2"));
            player1Turn = !player1Turn;
        }
        roundCount++;
        if (!gameComplete) {
            if (checkWinner()) {
                gameComplete = true;
                player1Wins();
            } else if (roundCount >= 9){
                if (!checkWinner()) {
                    draw();
                }
            } else {
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

    private void player1Wins() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("round_winner","1");
                win3sub1p.this.setResult(RESULT_OK, intent);
                win3sub1p.this.finish();
            }
        }, 1000);
    }

    private void player2Wins() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("round_winner","2");
                win3sub1p.this.setResult(RESULT_OK, intent);
                win3sub1p.this.finish();
            }
        }, 2000);
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

    private void resetBoard() {
        for (int i = 0; i<3;i++){
            for (int j = 0; j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        gameComplete = false;
        if (first_turn.equals("p1")) {
            player1Turn = true;
        }
        else if (first_turn.equals("p2")) {
            player1Turn = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCCount", roundCount);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

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
                    } else if (roundCount >= 9){
                        if (!checkWinner()) {
                            draw();
                        }
                    }
                    player1Turn = !player1Turn;

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
            //player1Turn = true;
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
            }
            else {
                AI();
            }
        }
    }
}