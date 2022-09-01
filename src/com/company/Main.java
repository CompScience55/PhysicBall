package com.company;

import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame window = new JFrame("2D Physics");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Graphics graphics = new Graphics();
        graphics.startThread();
        window.add(graphics);
        window.setLocation(250,50);
        window.setVisible(true);
        window.pack();
    }
}
