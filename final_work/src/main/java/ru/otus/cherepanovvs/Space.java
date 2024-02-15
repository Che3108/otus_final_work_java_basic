package ru.otus.cherepanovvs;

import java.util.ArrayList;
import java.util.List;

public class Space {
    private List<Point> points;
    private ArrayList<ArrayList<Double>> distanceMatrix;

    public Space(List<Point> points) {
        this.points = points;
        this.distanceMatrix = calculateSquareDistanceMatrix();
    }

    private ArrayList<ArrayList<Double>> calculateSquareDistanceMatrix() {
        ArrayList<ArrayList<Double>> distanceMatrix = new ArrayList<ArrayList<Double>>();
        for (Point point1 : points) {
            ArrayList<Double> row =  new ArrayList<>();
            for (Point point2 : points) {
                int divX = point1.getX() - point2.getX();
                int divY = point1.getY() - point2.getY();
                row.add(Math.sqrt((divX * divX) + (divY * divY)));
            }
            distanceMatrix.add(row);
        }
        return distanceMatrix;
    }

    public Double getDistance(int indexPoint1, int indexPoint2) {
        ArrayList<Double> row = distanceMatrix.get(indexPoint1);
        return row.get(indexPoint2);
    }

    public int getPointsCount() {
        return points.size();
    }

}
