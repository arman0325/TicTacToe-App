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

public class win3sub extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn;
    private int roundCount;
    private TextView roundStatus;
    private String first_turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win3_sub);
        roundStatus = findViewById(R.id.textStatus);
        //get value from win3main which player take the first turn
        Intent i1 = getIntent();
        first_turn = i1.getStringExtra("first_turn");
        //p1 represent player 1 take the first turn
        if (first_turn.equals("p1")) {
            player1Turn = true;
            roundStatus.setText("Turn: Player 1 [O]");
        }
        else if (first_turn.equals("p2")) {
            player1Turn = false;
            roundStatus.setText("Turn: Player 2 [X]");
        }

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
        if(player1Turn) {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#2038E2"));
            //current player's turn finnish, update next turn is which player
            roundStatus.setText("Turn: Player 2 [X]");
        }
        else {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#CE2929"));
            roundStatus.setText("Turn: Player 1 [O]");
        }

        roundCount++;

        if(checkWinner()) {
            if(player1Turn) {
                player1Wins();
            }
            else {
                player2Wins();
            }
        }
        else if (roundCount == 9) {
            draw();
        }
        else {
            player1Turn = !player1Turn;
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
                win3sub.this.setResult(RESULT_OK, intent);
                win3sub.this.finish();
            }
        }, 1000);
    }

    private void player2Wins() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("round_winner","2");
                win3sub.this.setResult(RESULT_OK, intent);
                win3sub.this.finish();
            }
        }, 1000);
    }

    private void draw() {
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
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
        if (first_turn.equals("p1")) {
            player1Turn = true;
            roundStatus.setText("Turn: Player 1 [O]");
        }
        else if (first_turn.equals("p2")) {
            player1Turn = false;
            roundStatus.setText("Turn: Player 2 [X]");
        }
        //player1Turn = !player1Turn;
        /*if (player1Turn){
            Toast.makeText(this,"Player1 first", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Player2 first", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCCount", roundCount);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}