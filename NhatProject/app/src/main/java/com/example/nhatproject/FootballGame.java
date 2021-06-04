package com.example.nhatproject;

import java.io.Serializable;

public class FootballGame implements Serializable {
    String key;
    String homeTeam;
    String awayTeam;
    String date;
    String time;

    public FootballGame() {
    }

    public FootballGame(String key, String homeTeam, String awayTeam, String date, String time) {
        this.key = key;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.time = time;
    }
//
//    public FootballGame(String homeTeam, String awayTeam, String date, String time) {
//        this.homeTeam = homeTeam;
//        this.awayTeam = awayTeam;
//        this.date = date;
//        this.time = time;
//    }
//
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
