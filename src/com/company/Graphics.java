package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Graphics extends JPanel implements Runnable{

    public static int height;
    public static int width;

    public Color[] colors = {new Color(30,80,201),new Color(28,46,216),new Color(109,28,216),new Color(172,28,216),new Color(220,26,104),new Color(226,25,25),
            new Color(31,167,201),new Color(31,201,144),new Color(31,201,65),new Color(195,201,31),new Color(201,155,31)};
    Random r = new Random();
    boolean switchColor = false;
    Color c;

    public Graphics() {
        this.setPreferredSize(new Dimension(1500,1000));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        askForAmount();
    }

    Thread runningThread;
    int FPS = 60;

    ArrayList<Ball> balls = new ArrayList<>();

    public void askForAmount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wie viele Bälle?");
        int amount = scanner.nextInt();
        System.out.println("Welchen Radius?");
        int radius = scanner.nextInt();
        addBalls(amount, radius);
    }

    public void addBalls(int amount, int radius) {
        for (int i = 0; i < amount; i++) {
            balls.add(new Ball(radius));
        }
    }

    public void startThread() {
        runningThread = new Thread(this);
        runningThread.start();
    }

    public void update(boolean switchColor) {

        if (switchColor) {
            for (int j = 0; j < balls.size(); j++) {
                balls.get(j).color = c;
            }
            switchColor = false;
        }



        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).checkForCollision();
            for (int j = i+1; j < balls.size(); j++) {
                if (balls.get(i) != balls.get(j)) {
                    //balls.get(i).checkForBallColission(balls.get(j));
                }
            }

            balls.get(i).moveBall();
        }
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).paint(g2);
        }
        g2.dispose();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        int counter = 0;


        while (runningThread != null) {

            height = this.getHeight();
            width = this.getWidth();
            update(switchColor);
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                counter++;

                if (counter == 90) {
                    //switchColor = true;
                    //c = colors[r.nextInt(colors.length)];
                    counter = 0;
                }

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
