package ru.otus.cherepanovvs;

import java.util.Arrays;

public class Agent implements Comparable<Agent> {
    private int[] genom;
    private double score;

    public Agent(int[] genom) {
        this.genom = genom;
        this.score = Integer.MIN_VALUE;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void addScore(double score) {
        this.score += score;
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
        String s = "score: " + score + " genom: " + Arrays.toString(genom);
        return s;
    }
}