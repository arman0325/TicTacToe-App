package com.example.tictactoe;

import androidx.annotation.Nullable;
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

public class win3main extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private String round_winner = "";
    private int temp1;
    private int temp2;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView turnStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win3_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewPlayer1 = findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = findViewById(R.id.textViewPlayer2);
        turnStatus = findViewById(R.id.textStatus); //a row of message tell who is taking the current turn

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
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        //"" represent initial round, "1" represent player 1 won previous round
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
            Intent intent = new Intent(win3main.this, win3sub.class);
            intent.putExtra("first_turn", "p1"); //value here send to child activity, and represent who takes the first turn of the sub layer
            startActivityForResult(intent, 1);  //start child activity(win3sub) which will return value to parent activity(win3main)
        }
        else if (round_winner.equals("2")){
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
            Intent intent = new Intent(win3main.this, win3sub.class);
            intent.putExtra("first_turn", "p2");
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

    //print the player 1 win message
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

    //print the player 2 win message
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this,"Winner is player2!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                updatePointsText();
                resetBoard();
                round_winner = "2"; //make player 2 can take the first turn after won a complete round of game
            }
        }, 3000);
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
            }
        }
        roundCount = 0;
        round_winner = "";
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
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
                // print the about the game
                androidx.appcompat.app.AlertDialog.Builder customizeDialog =
                        new AlertDialog.Builder(win3main.this);
                customizeDialog.setTitle(R.string.win3Title);
                customizeDialog.setMessage(R.string.win3);
                customizeDialog.show();
                break;
        }
        return false;
    }

    //onActivityResult used to retrieve value from child activity (win3sub)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                round_winner = data.getStringExtra("round_winner");
                // "1" represent player 1 won the sub layer game
                // button setText also done in here
                if (round_winner.equals("1")) {
                    buttons[temp1][temp2].setText("O");
                    buttons[temp1][temp2].setTextColor(Color.parseColor("#2038E2"));
                    turnStatus.setText("Turn: Player 1 [O]");

                }
                else if (round_winner.equals("2")) {
                    buttons[temp1][temp2].setText("X");
                    buttons[temp1][temp2].setTextColor(Color.parseColor("#CE2929"));
                    turnStatus.setText("Turn: Player 2 [X]");
                }
                // checking winner also called in here
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
}