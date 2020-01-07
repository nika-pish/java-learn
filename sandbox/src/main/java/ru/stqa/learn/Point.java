package ru.stqa.learn;

public class Point {
    public double x;
    public double y;


    public Point (double x, double y){
        this.x = x;
        this.y = y;

    }
    public double distance(Point Point){
        double distance =  Math.sqrt ((Point.x-this.x)*(Point.x-this.x)+(Point.y-this.y)*(Point.y-this.y));
        return distance;
    }


}
