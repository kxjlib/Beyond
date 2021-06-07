import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Display.Display;
import Gfx.ImageLoader;

public class Game implements Runnable {

    public int width, height;
    public String title;

    private Display display;

    private boolean running = false;

    private Thread thread;

    private BufferStrategy bs;

    private Graphics g;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init() {
        this.display = new Display(title, width, height);
        display.getCanvas().createBufferStrategy(3);
    }

    private void update() {

    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        g = bs.getDrawGraphics();

        //--- BEGIN DRAW ---

        // Clear Screen
        g.clearRect(0,0, width,height);


        //---  END DRAW  ---

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        while (running) {
            update();
            render();
        }

        stop();
    }

    public synchronized void start() {
        // Ensure no duplicate start
        if (running) return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        // Ensure no duplicate stop
        if (!running) return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
