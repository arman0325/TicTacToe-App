package com.example.tictactoe;

public class Grades {
    private String no;
    private String id;
    private String score;
    private String gameTime;

    public Grades(String no,String id, String score, String gameTime){
        this.no = no;
        this.id = id;
        this.score = score;
        this.gameTime = gameTime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }
}
