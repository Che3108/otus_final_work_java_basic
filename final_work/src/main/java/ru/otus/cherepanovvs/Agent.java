package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Agent implements Comparable<Agent> {
    private int[] genom;
    private double score;
    private Random random = new Random();
     
    public Agent(int[] genom) {
        this.genom = genom;
        this.score = Integer.MAX_VALUE;
    }

    public Agent(int genomLen) {
        this.genom = generateRandomGenom(genomLen);
        this.score = Integer.MAX_VALUE;
    }

    private int[] generateRandomGenom(int genomLen) {
        int[] genom = new int[genomLen];
        
        for (int j = 0; j < genom.length; j++) {
            genom[j] = random.nextInt(genomLen);
        }
        return genom;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int[] getGenom() {
        return genom;
    }

    @Override
    public int compareTo(Agent o) {
        if (o == null) {
            return Integer.MAX_VALUE;
        }
        return (int)Math.round(this.score - o.score);
    }

    @Override
    public String toString() {
        String s = "score: " + score + " genom: " + Arrays.toString(genom);
        return s;
    }

    public Agent mutation(float mutationPersent) {
        int mutationGenomCount = Math.round(genom.length * mutationPersent);
        Integer[] mutationGenomIndexes = getRandomIndexes(mutationGenomCount);
        for (int i : mutationGenomIndexes) {
            genom[i] = random.nextInt(genom.length);
        }
        return new Agent(genom);
    }

    private Integer[] getRandomIndexes(int countIndex) {
        Set<Integer> choiceIndexes = new HashSet<>();
        while (choiceIndexes.size() < countIndex) {
            choiceIndexes.add(random.nextInt(genom.length));
        }
        return choiceIndexes.toArray(new Integer[countIndex]);
    }

    public List<Agent> crossbreed(Agent parent2, float crossbreedPercent) {
        List<Agent> result = new ArrayList<>();
        int[] genomParent2 = parent2.getGenom();
        int crossbreedGenomCountParent1 = Math.round(genom.length * crossbreedPercent);
        int[] genomChildren1 = new int[genom.length];
        int[] genomChildren2 = new int[genomParent2.length];
        for (int i = 0; i < genom.length; i++) {
            if (i < crossbreedGenomCountParent1) {
                genomChildren1[i] = genom[i];
                genomChildren2[i] = genomParent2[i];
                continue;
            }
            genomChildren1[i] = genomParent2[i];
            genomChildren2[i] = genom[i];
        }
        result.add(new Agent(genomChildren1));
        result.add(new Agent(genomChildren2));
        return result;
    }

    public void calcScore(Space space, int fine) {
        int uniqueGenCount = getUniqueGensCount();
        score = (genom.length - uniqueGenCount) * fine;
        if (genom[0] != space.getStartPointIndex()) {
            score += fine;
        } else {
            score -= fine;
        }
        for (int i = 1; i < genom.length; i++) {
            score += space.getDistance(genom[i - 1], genom[i]);
        }
    }

    private int getUniqueGensCount() {
        Set<Integer> uniqueGen = new HashSet<>(genom.length);
        for (int i : genom) {
            uniqueGen.add(i);
        }
        return uniqueGen.size();
    }

    protected Agent clone() {
        Agent copy = new Agent(genom);
        copy.setScore(score);
        return copy;
    }
}
