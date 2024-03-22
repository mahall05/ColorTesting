package Effects;

import java.awt.Color;

public class ColorWaveEffect extends LightingEffect {
    private Color c1, c2;
    private double wavePeriod; // Length of the wave in pixels
    private double transitionPeriod; // Length of time for full transition between color in seconds/transition
    private int timer = 0;

    private Color[] base;

    public ColorWaveEffect(int location, int length, Color c1, Color c2, double wavePeriod, double transitionPeriod, Type type){
        super(location, length, type);
        this.c1 = c1;
        this.c2 = c2;
        this.wavePeriod = wavePeriod;
        this.transitionPeriod = transitionPeriod;

        base = new Color[length];

        for(int i = 0; i < length; i++){
            base[i] = new Color(
                (int) (((c1.getRed()-c2.getRed())/2.0) *Math.sin(2.0*Math.PI/wavePeriod * i) + ((c1.getRed()+c2.getRed())/2.0)),
                (int) (((c1.getGreen()-c2.getGreen())/2.0) *Math.sin(2.0*Math.PI/wavePeriod * i) + ((c1.getGreen()+c2.getGreen())/2.0)),
                (int) (((c1.getBlue()-c2.getBlue())/2.0) *Math.sin(2.0*Math.PI/wavePeriod * i) + ((c1.getBlue()+c2.getBlue())/2.0))
            );
        }
    }

    @Override
    public void update() {
        timer+=20;
        if(timer==2000000) timer=0;

        /*
        for(int i = 0; i < length; i++){
            pixels[i] = base[i];
        }
        */

        for(int i = 0; i < length; i++){
            pixels[i] = new Color(
                (int) (((c1.getRed()-c2.getRed())/2.0)  *  Math.sin(  2.0*Math.PI/transitionPeriod  *  (timer-  (-Math.asin((base[i].getRed() - (c1.getRed()+c2.getRed())/2.0)/Math.abs((c1.getRed()-c2.getRed())/2.0))) / 2.0*Math.PI/transitionPeriod  ))+  (c1.getRed()+c2.getRed())/2.0  ),
                (int) (((c1.getGreen()-c2.getGreen())/2.0)  *  Math.sin(  2.0*Math.PI/transitionPeriod  *  (timer-  (-Math.asin((base[i].getGreen() - (c1.getGreen()+c2.getGreen())/2.0)/Math.abs((c1.getGreen()-c2.getGreen())/2.0))) / 2.0*Math.PI/transitionPeriod  ))+  (c1.getGreen()+c2.getGreen())/2.0  ),
                (int) (((c1.getBlue()-c2.getBlue())/2.0)  *  Math.sin(  2.0*Math.PI/transitionPeriod  *  (timer-  (-Math.asin((base[i].getBlue() - (c1.getBlue()+c2.getBlue())/2.0)/Math.abs((c1.getBlue()-c2.getBlue())/2.0))) / 2.0*Math.PI/transitionPeriod  ))+  (c1.getBlue()+c2.getBlue())/2.0  )
            );
        }
    }
    
}
