package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import static com.example.tictactoe.MainActivity.audio;

public class gamePage extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//the button in toolbar to back to main page

        textViewPlayer1 = findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = findViewById(R.id.textViewPlayer2);

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
        }else{
            //player2 select message
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#CE2929"));
        }

        roundCount++;

        if(checkWinner()) {
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if (roundCount == 9){
            draw();
        } else{
            // change the player turn
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

    //print the player 1 win message
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,"Winner is player1!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updatePointsText();
                resetBoard();
                // Actions to do after 0.1 seconds
            }
        }, 3000);// 1000 = 1sec
    }

    //print the player 2 win message
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this,"Winner is player2!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updatePointsText();
                resetBoard();
                // Actions to do after 0.1 seconds
            }
        }, 3000);// 1000 = 1sec
    }

    //print the draw message
    private void draw() {
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    // update the point table
    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    //reset the board
    private void resetBoard() {
        for (int i = 0; i<3;i++){
            for (int j = 0; j<3;j++){
                buttons[i][j].setText("");
                buttons[i][j].setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
        roundCount = 0;
        player1Turn = !player1Turn;//switch the player first
        if (player1Turn){
            Toast.makeText(this,"Player1 first", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Player2 first", Toast.LENGTH_LONG).show();
        }
    }

    //reset the game and the data
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCCount", roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);

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
            case R.id.about:
                // print the about the game
                androidx.appcompat.app.AlertDialog.Builder customizeDialog =
                        new AlertDialog.Builder(gamePage.this);
                customizeDialog.setTitle(R.string.twoplayerTitle);
                customizeDialog.setMessage(R.string.twoPlayerAbout);
                customizeDialog.show();
                break;
            case R.id.restart:
                // run the reset the game
                resetGame();
                break;
        }

        return false;
    }

}