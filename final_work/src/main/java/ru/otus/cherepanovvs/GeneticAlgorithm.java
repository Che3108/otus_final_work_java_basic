package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    //private Agent bestAgent;

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
        Agent bestAgent = population.get(0).clone();
        for (int currEpoch = 0; currEpoch < epochs; currEpoch++) {
            System.out.println(bestAgent);
            for (Agent agent : population) {
                agent.calcScore(space, fine);
            }
            List<Agent> newPopulation = new ArrayList<>(populationSize);
            population.sort(Comparator.naturalOrder());
            Agent bestOfPopul = population.get(0);
            
            if (bestAgent.getScore() > bestOfPopul.getScore()) {
                System.out.println(bestAgent.getScore() + ", " + population.get(0).getScore());
                bestAgent = bestOfPopul;
            }
            System.out.println(bestAgent);
            System.out.println();
            
            for (int i = 0; i < bestAgentCount; i++) {
                newPopulation.add(population.get(i).clone());
            }

            for (int i = 1; i < bestAgentCount; i++) {
                newPopulation.addAll(
                    population.get(i - 1).crossbreed(population.get(i), crossbreedPercent)
                );
            }
            int newPopulSize = newPopulation.size();
            for (int i = 0; i < newPopulSize; i++) {
                newPopulation.add(
                    newPopulation.get(i).mutation(mutationPersent)
                );
            }
            int remainder = populationSize - newPopulation.size();
            if (remainder > 0) {
                for (int i = 0; i < remainder; i++) {
                    newPopulation.add(new Agent(genomAgentLen));
                }
            }
            population = newPopulation;
        }
    }

    public void generatePopulation() {
        population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            population.add(new Agent(genomAgentLen));
        }
    }
}
