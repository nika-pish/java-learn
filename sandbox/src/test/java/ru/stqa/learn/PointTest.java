package ru.stqa.learn;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void testDistance(){
        Point p1 = new Point (4,4);
        Point p2 = new Point (4,6);
        p1.distance(p2);
        Assert.assertEquals(p1.distance(p2), 20);
    }
}
