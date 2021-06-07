import Display.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    public int width, height;
    public String title;

    private Display display;

    private boolean running = false;

    private Thread thread;

    private BufferStrategy bs;

    private Graphics g;

    private int bX, bY = 0;

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
        bX++;
        bY++;
    }

    private void render() {
        // IF IT DOESN'T WORK MOVE LINE 29
        bs = display.getCanvas().getBufferStrategy();
        g = bs.getDrawGraphics();

        //--- BEGIN DRAW ---

        // Clear Screen
        g.clearRect(0,0, width,height);

        g.drawRect(bX,bY, 50,50);

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
