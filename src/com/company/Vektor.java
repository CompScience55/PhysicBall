package com.company;

public class Vektor {

    int startX;
    int startY;

    int length;

    boolean left;
    boolean up;

    float normalisedX;
    float normalisedY;

    int vectorScreenX, vectorScreenY;

    int xDiff = 0;
    int yDiff = 0;

    public Vektor(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }

    public int getLength() {
        if (xDiff == 0 || yDiff == 0) {
            length = (int) Math.sqrt(Math.pow(startX, 2) + Math.pow(startY, 2));
        } else {
            length = (int) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        }
        return length;
    }

    public Vektor normalizeVector() {
        xDiff = xDiff / length;
        yDiff = yDiff / length;
        return this;
    }

    public double DotProduct(Vektor v1, Vektor v2) {
        return (v1.xDiff * v2.xDiff) + (v1.yDiff * v2.yDiff);
    }

    public void recalculateVector(int endX, int endY) {
        xDiff = (endX - startX);
        left = false;
        yDiff = (endY - startY);
        up = false;
        this.startX = endX;
        this.startY = endY;

        if (xDiff < 0) {
            left = true;
            xDiff *= -1;
        }
        if (yDiff < 0) {
            up = true;
            yDiff *= -1;
        }
    }

    public Vektor multiply(Vektor v1) {
        this.xDiff *= v1.xDiff;
        this.yDiff *= v1.yDiff;
        return this;
    }

    public Vektor add(Vektor v1) {
        this.xDiff += v1.xDiff;
        this.yDiff += v1.yDiff;
        return this;
    }

    public Vektor subtract(Vektor v1) {
        this.xDiff -= v1.xDiff;
        this.yDiff -= v1.yDiff;
        return this;
    }

    public Vektor multiplyDouble(double multiplier) {
        this.xDiff += multiplier;
        this.yDiff += multiplier;
        return this;
    }

    public Vektor divideDouble(double multiplier) {
        this.xDiff /= multiplier;
        this.yDiff /= multiplier;
        return this;
    }


}
