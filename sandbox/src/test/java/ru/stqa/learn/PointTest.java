package ru.stqa.learn;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void testDistance(){
        Point p1 = new Point (4,4,4,6);
        p1.distance();
        Assert.assertEquals(p1.distance(), 2);
    }
}
