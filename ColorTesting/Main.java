import javax.swing.*;

import Effects.*;
import Effects.LightingEffect.Type;
import LEDs.LEDZone;

import java.awt.*;

public class Main extends JComponent{
    public static final int WIDTH = 350, HEIGHT = 100;
    private boolean running = true;

    private Color[] pixels;
    private LEDZone[] ledZones;

    public static void main(String[] args){
        Main main = new Main();

        JFrame frame = new JFrame("My first JFrame");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().add(main);
        main.start();
    }

    public Main(){
        pixels = new Color[WIDTH];
        ledZones = new LEDZone[] {new LEDZone("Full", 0, WIDTH)};
        ledZones[0].setEffect(new ColorWaveEffect(0, WIDTH, new Color(0, 255, 255), new Color(150,0,255), 25, 4000, Type.status));
    }
    public void start(){
        run();
    }
    private void tick(){
        // Tick color buffer
        for(int i = 0; i < ledZones.length; i++){
            ledZones[i].update();
            for(int j = 0; j < ledZones[i].getLength(); j++){
                pixels[j+ledZones[i].getLocation()] = ledZones[i].getPixels()[j];
            }
        }
    }
    private void render(Graphics g){
        for(int i = 0; i < WIDTH; i++){
            g.setColor(pixels[i]);
            g.drawLine(i, 0, i, HEIGHT);
        }
    }

    public void run(){
        long timer = System.currentTimeMillis();
        while(running){
            if(System.currentTimeMillis() - timer > 20){
                tick();
                render(this.getGraphics());
                timer+=20;
            }
        }
    }
}