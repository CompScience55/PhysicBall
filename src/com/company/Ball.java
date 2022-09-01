package com.company;

import java.awt.*;
import java.awt.Graphics;
import java.util.Random;

public class Ball {


    int centerX;
    int centerY;

    public Color[] colors = {new Color(30,80,201),new Color(28,46,216),new Color(109,28,216),new Color(172,28,216),new Color(220,26,104),new Color(226,25,25),
            new Color(31,167,201),new Color(31,201,144),new Color(31,201,65),new Color(195,201,31),new Color(201,155,31)};
    int radius;
    Color color;
    Vektor v;
    int x;
    int y;

    public Ball(int radius)
    {
        this.radius = radius;
        generateRandom();
    }

    public void generateRandom() {
        Random r = new Random();
        x = r.nextInt(900);
        y = r.nextInt(700);

        int rNum = r.nextInt(colors.length);
        color = colors[rNum];
        v = new Vektor(x,y);
        decideDirection(r);
    }

    public void calculateCenter() {
        centerX = x + radius;
        centerY = y + radius;
    }

    public void decideDirection(Random r) {
        int decide = r.nextInt(100)+1;
        int points = r.nextInt(13)+1;
        int newX;
        int newY;
        if (decide <= 50) {
            newX = x + points;
        } else {
            newX = x - points;
        }
        decide = r.nextInt(100)+1;
        points = r.nextInt(15)+1;
        if (decide <= 50) {
            newY = y + points;
        } else {
            newY = y - points;
        }
        v.recalculateVector(newX, newY);
    }

    public void checkForCollision() {

        if (y > com.company.Graphics.height - 2*radius) {
            v.up = true;
        }
        if (x > com.company.Graphics.width - 2*radius) {
            v.left = true;
        }
        if (y <= 0) {
            v.up = false;
        }
        if (x <= 0) {
            v.left = false;
        }

    }

    public void moveBall() {

        if (v.up) {
            if (v.left) {
                v.recalculateVector(v.startX - v.xDiff, v.startY - v.yDiff);
                v.vectorScreenX = v.startX - v.xDiff;
                v.vectorScreenY = v.startY - v.yDiff;
            } else {
                v.recalculateVector(v.startX + v.xDiff, v.startY - v.yDiff);
                v.vectorScreenX = v.startX + v.xDiff;
                v.vectorScreenY = v.startY - v.yDiff;
            }
        } else if (!v.up) {
            if (v.left) {
                v.recalculateVector(v.startX - v.xDiff, v.startY + v.yDiff);
                v.vectorScreenX = v.startX - v.xDiff;
                v.vectorScreenY = v.startY + v.yDiff;
            } else {
                v.recalculateVector(v.startX + v.xDiff, v.startY + v.yDiff);
                v.vectorScreenX = v.startX + v.xDiff;
                v.vectorScreenY = v.startY + v.yDiff;
            }
        }
        x = v.startX;
        y = v.startY;
    }

    public void checkForBallColission(Ball otherBall) {

        //Geht nich
        Vektor ballBall = new Vektor(otherBall.x, otherBall.y);
        ballBall.recalculateVector(x, y);

        int distance = ballBall.getLength();

        if (distance < otherBall.radius + radius) {

            otherBall.calculateCenter();
            calculateCenter();

            Vektor centerCenter = new Vektor(centerX, centerY);
            centerCenter.recalculateVector(otherBall.centerX, otherBall.centerY);

            Vektor va1 = v.subtract(otherBall.v);
            double multi = v.DotProduct(va1, centerCenter) / (Math.pow(centerCenter.getLength(),2));
            centerCenter.multiplyDouble(multi);
            v = v.subtract(centerCenter);
            reduceVelocity(v);

            Vektor centerCenter2 = new Vektor(otherBall.centerX, otherBall.centerY);
            centerCenter2.recalculateVector(centerX, centerY);

            Vektor va2 = otherBall.v.subtract(v);
            double multi2 = v.DotProduct(va2, centerCenter2) / (Math.pow(centerCenter2.getLength(),2));
            centerCenter2.multiplyDouble(multi2);
            otherBall.v = otherBall.v.subtract(centerCenter2);
            reduceVelocity(otherBall.v);

        }
    }

    public void reduceVelocity(Vektor v) {
        if (v.yDiff > 20 || v.yDiff < -20) {
            v.yDiff /= 7;
        }
        if (v.xDiff > 20 || v.xDiff < -20) {
            v.xDiff /= 7;
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Random r = new Random();
        g2.setColor(color);

        /*
        g2.drawRect(x, y, 2 * radius, 2 * radius);
        g2.fillRect(x, y, 2 * radius, 2 * radius);
         */

        g2.drawArc(x, y, 2 * radius, 2 * radius, 0, 360);
        g2.fillArc(x, y, 2 * radius, 2 * radius, 0, 360);
        //g2.drawLine(x+radius,y+radius,v.vectorScreenX + radius, v.vectorScreenY + radius);
    }

}
