package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
    private int epochs;
    private int genomAgentLen;
    private int populationSize;
    private int bestAgentCount;
    private float mutationPersent;
    private float crossbreedPercent;
    private List<Agent> population;
    private Space space;
    private int fine;

    public GeneticAlgorithm(Space space, int epochs, int populationSize, 
                            int bestAgentCount, float mutationPersent,
                            float crossbreedPercent, int fine) {
        this.space = space;
        this.epochs = epochs;
        this.genomAgentLen = space.getPointsCount();
        this.populationSize = populationSize;
        this.bestAgentCount = bestAgentCount;
        this.mutationPersent = mutationPersent;
        this.crossbreedPercent = crossbreedPercent;
        this.fine = fine;
    }

    public void run() {
        generatePopulation();
        for (int currEpoch = 0; currEpoch < epochs; currEpoch++) {
            for (Agent agent : population) {
                calcAgentScore(agent);
            }
            List<Agent> newPopulation = new ArrayList<>();
            population.sort(Comparator.naturalOrder());
            System.out.println("Best agent " + population.get(0));
            for (int i = 0; i < bestAgentCount; i++) {
                newPopulation.add(population.get(i));
            }

            for (int i = 1; i < bestAgentCount; i++) {
                newPopulation.addAll(
                    crossbreed(
                        population.get(i - 1),
                        population.get(i),
                        crossbreedPercent
                    )
                );
            }
            int newPopulSize = newPopulation.size();
            for (int i = 0; i < newPopulSize; i++) {
                newPopulation.add(mutation(newPopulation.get(i)));
            }
            int remainder = populationSize - newPopulation.size();
            if (remainder > 0) {
                for (int i = 0; i < remainder; i++) {
                    newPopulation.add(new Agent(generateRandomGenom()));
                }
            }
            population = newPopulation;
        }
    }

    public void generatePopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Agent(generateRandomGenom()));
        }
    }

    private int[] generateRandomGenom() {
        int[] genom = new int[genomAgentLen];
        Random random = new Random();
        for (int j = 0; j < genom.length; j++) {
            genom[j] = random.nextInt(genomAgentLen);
        }
        return genom;
    }

    private void calcAgentScore(Agent agent) {
        int[] genom = agent.getGenom();
        int uniqueGenCount = getUniqueGenCount(genom);
        agent.setScore((genom.length - uniqueGenCount) * fine);
        System.out.println(agent.getScore());
        
        int[] genomShift = getGenomShift(genom);
        for (int i = 0; i < genom.length; i++) {
            agent.addScore(space.getDistance(genom[i], genomShift[i]));
        }
    }

    private int getUniqueGenCount(int[] genom) {
        Set<Integer> uniqueGen = new HashSet<>();
        for (int i : genom) {
            uniqueGen.add(i);
        }
        return uniqueGen.size();
    }

    private int[] getGenomShift(int[] genom) {
        int genomLength = genom.length;
        int[] result = new int[genomLength];
        int k = 0;
        for (int i = 1; i < genomLength; i++) {
            result[k] = genom[i];
            k++;
        }
        result[genomLength - 1] = genom[0];
        return result;
    }

    public List<Agent> crossbreed(Agent parent1, Agent parent2, float crossbreedPercent) {
        List<Agent> result = new ArrayList<>();
        int[] genomParent1 = parent1.getGenom();
        int[] genomParent2 = parent2.getGenom();
        int crossbreedGenomCountParent1 = Math.round(genomParent1.length * mutationPersent);
        int[] genomChildren1 = new int[genomParent1.length];
        int[] genomChildren2 = new int[genomParent2.length];
        for (int i = 0; i < genomParent1.length; i++) {
            if (i < crossbreedGenomCountParent1) {
                genomChildren1[i] = genomParent1[i];
                genomChildren2[i] = genomParent2[i];
                continue;
            }
            genomChildren1[i] = genomParent2[i];
            genomChildren2[i] = genomParent1[i];
        }
        result.add(new Agent(genomChildren1));
        result.add(new Agent(genomChildren2));
        return result;
    }

    public Agent mutation(Agent agent) {
        int[] genom = agent.getGenom();
        int mutationGenomCount = Math.round(genom.length * mutationPersent);
        int[] mutationGenomIndexes = getRandomIndexes(genom.length, mutationGenomCount);
        Random random = new Random();
        for (int i : mutationGenomIndexes) {
            genom[i] = random.nextInt(genom.length);
        }
        return new Agent(genom);
    }

    private int[] getRandomIndexes(int arrayLen, int countIndex) {
        int[] result = new int[countIndex];
        Set<Integer> choiceIndexes = new HashSet<>();
        while (choiceIndexes.size() < countIndex) {
            Random random = new Random();
            choiceIndexes.add(random.nextInt(arrayLen));
        }
        int i = 0;
        for (Integer index : choiceIndexes) {
            result[i++] = index;
        }
        return result;
    }

}
