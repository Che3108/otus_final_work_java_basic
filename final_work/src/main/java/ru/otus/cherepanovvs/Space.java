package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private List<Point> points;
    private ArrayList<ArrayList<Integer>> distanceMatrix;

    public Space(List<Point> points) {
        this.points = points;
        this.distanceMatrix = calculateSquareDistanceMatrix();
    }

    private ArrayList<ArrayList<Integer>> calculateSquareDistanceMatrix() {
        ArrayList<ArrayList<Integer>> distanceMatrix = new ArrayList<ArrayList<Integer>>();
        for (Point point1 : points) {
            ArrayList<Integer> row =  new ArrayList<>();
            for (Point point2 : points) {
                int divX = point1.getX() - point2.getX();
                int divY = point1.getY() - point2.getY();
                row.add((divX * divX) + (divY * divY));
            }
            distanceMatrix.add(row);
        }
        return distanceMatrix;
    }

    public int getSquareDistance(int indexPoint1, int indexPoint2) {
        ArrayList<Integer> row = distanceMatrix.get(indexPoint1);
        return row.get(indexPoint2);
    }

    public double getDistance(int indexPoint1, int indexPoint2) {
        int squareDistance = getSquareDistance(indexPoint1, indexPoint2);
        return Math.sqrt(squareDistance);
    }

    public int getPointsCount() {
        return points.size();
    }

}
