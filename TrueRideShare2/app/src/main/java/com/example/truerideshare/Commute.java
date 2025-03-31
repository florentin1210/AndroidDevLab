package com.example.truerideshare;

public class Commute {
    private String date;
    private String time;
    private int maxSeats;
    private int occupiedSeats;
    private String destination;
    private String departure;

    public Commute(String date, String time, int maxSeats, int occupiedSeats, String destination, String departure) {
        this.date = date;
        this.time = time;
        this.maxSeats = maxSeats;
        this.occupiedSeats = occupiedSeats;
        this.destination = destination;
        this.departure = departure;
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

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
}
