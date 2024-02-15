package ru.otus.cherepanovvs;

import java.util.Arrays;

//import java.util.Random;

public class Agent implements Comparable<Agent> {
    private int[] genom;
    private long score;
    private double dist;

    public Agent(int[] genom) {
        this.genom = genom;
        this.score = Integer.MIN_VALUE;
        this.dist = Integer.MIN_VALUE;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getDist() {
        return dist;
    }

    public void addDist(double dist) {
        this.dist += dist;
    }

    public int[] getGenom() {
        return genom;
    }

    @Override
    public int compareTo(Agent o) {
        if (o == null) {
            return Integer.MIN_VALUE;
        }
        return (int)(this.score - o.score);
    }

    @Override
    public String toString() {
        String s = "Score: " + score + " genom: " + Arrays.toString(genom) + " dist: " + dist;
        return s;

    }
}
