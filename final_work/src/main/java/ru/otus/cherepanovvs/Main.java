package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        Space space = new Space(generatePoints());
        GeneticAlgorithm gnetic = new GeneticAlgorithm(
            space,
            10000,
            80,
            20,
            0.1f,
            0.5f,
            10000
        );
        gnetic.run();
    }

    public static List<Point> generatePoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(842, 419));
        points.add(new Point(189, 0));
        points.add(new Point(219, 671));
        points.add(new Point(698, 773));
        points.add(new Point(388, 907));
        points.add(new Point(1000, 206));
        points.add(new Point(619, 981));
        points.add(new Point(486, 341));
        points.add(new Point(518, 898));
        return points;
    }

}
