package com.example.teodora.tictac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount; //if we have 9 round and nobody wins we have a draw because we only have 9 houses

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_pl1);
        textViewPlayer2 = findViewById(R.id.text_view_pl2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                //int res=findViewById(R.id.buttonID) you can't do that because the ID is different, is dynamic
                //00 01 02 10 11 12 20 21 22
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button butttonReset = (Button) findViewById(R.id.button_reset);

        butttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button)v).setText("X");
        }
        else{
            ((Button)v).setText("0");
        }

        roundCount++;

        if(checkIfWinPlayer()){
            if(player1Turn){
                player1Wins();
            }
            else
                player2Wins();
        }
        else if(roundCount==9)
            draw();
        else{
            player1Turn = !player1Turn;
        }
    }

    private boolean checkIfWinPlayer(){
        String[][] matrice = new String[3][3];
        for(int i=0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrice[i][j] = buttons[i][j].getText().toString();
            }
        }
        for(int i=0; i < 3; i++) {
            if(matrice[i][0].equals(matrice[i][1])&&matrice[i][0].equals(matrice[i][2])&& !matrice[i][2].equals(""))
                return true;
        }
        for(int i=0; i < 3; i++) {
            if(matrice[0][i].equals(matrice[1][i])&&matrice[0][i].equals(matrice[2][i])&& !matrice[2][i].equals(""))
                return true;
        }

        if(matrice[0][0].equals(matrice[1][1])
                && matrice[0][0].equals(matrice[2][2])
                && !matrice[0][0].equals("")) {
            return true;
        }

        if(matrice[0][2].equals(matrice[1][1])
                && matrice[0][2].equals(matrice[2][0])
                && !matrice[0][2].equals("")) return true;
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Congrats to Player 1!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Congrats to Player 2!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player 1:"+player1Points);
        textViewPlayer2.setText("Player 2:"+player2Points);
    }

    private void resetBoard(){

        player1Turn=true;
        roundCount=0;
        for(int i=0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void resetGame(){
        resetBoard();
        updatePointsText();
        player1Points =0;
        player2Points=0;
    }

    private void draw(){
        Toast.makeText(this,"It's a draw!",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }


}