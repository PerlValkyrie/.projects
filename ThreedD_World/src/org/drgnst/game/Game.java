package org.drgnst.game;

import java.awt.Canvas;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable
{

    private static final long serialVersionUID = 11;

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH * 3 / 4;
    public static final int SCALE = 4;
    public static final String TITLE = "Perspectivie";

    private boolean isRunning = false;

    public static final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game()
    {

    }

    public void start() 
    {
        if (isRunning)
            return;

        isRunning = true;

        new Thread(this).start();
    }

    
    public void run()
    {
        while(isRunning)
        {
            render();
            update();
        }

        dispose();
    }

    public void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = 0xff00ff;
        }

        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        g.dispose();
        bs.show();
    }

    public void update()
    {

    }

    public void stop()
    {
        if(!isRunning)
            return;

        isRunning = false;
    }

    public void dispose()
    {
        System.exit(0);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setTitle(TITLE);
        Game game = new Game();
        Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        game.setMinimumSize(d);
        game.setMaximumSize(d);
        game.setPreferredSize(d);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }
}