package Effects;

import java.awt.Color;
import java.util.function.Supplier;

public class ColorWaveEffect extends LightingEffect {
    private Color c1, c2;
    private int wavePeriod; // Length of the wave in pixels
    private double transitionPeriod; // Length of time for full transition between color in ms/transition
    private double transFrequency; // Frequency in transition/ms
    private int timer = 0;

    private Color[] base;
    private double[] waveSlopes;
    private double[] transSlopes;

    public ColorWaveEffect(int location, int length, Color c1, Color c2, int wavePeriod, double transitionPeriod, Type type){
        super(location, length, type);
        this.c1 = c1;
        this.c2 = c2;
        this.wavePeriod = wavePeriod;
        this.transitionPeriod = transitionPeriod;

        waveSlopes = new double[] {(c2.getRed()-c1.getRed())/(wavePeriod/2.0), (c2.getGreen()-c1.getGreen())/(wavePeriod/2.0), (c2.getBlue()-c1.getBlue())/(wavePeriod/2.0)};
        transSlopes = new double[] {(c2.getRed()-c1.getRed())/(transitionPeriod/2.0), (c2.getGreen()-c1.getGreen())/(transitionPeriod/2.0), (c2.getBlue()-c1.getBlue())/(transitionPeriod/2.0)};

        base = new Color[wavePeriod];

        for(int i = 0; i < wavePeriod/2.0; i++){
            base[i] = new Color((int)(c1.getRed()+waveSlopes[0]*i), (int)(c1.getGreen()+waveSlopes[1]*i), (int)(c1.getBlue()+waveSlopes[2]*i));
            base[wavePeriod-i-1] = base[i];
        }
    }

    @Override
    public void update() {
        timer+=20;
        if(timer%transitionPeriod==0)timer=0;

        for(int i = 0; i < length; i++){
            int relativePosition = i%wavePeriod;
            double start = (double)relativePosition/(double)wavePeriod*transitionPeriod + timer/transitionPeriod*20.0;
            double adj = ((start+i)%(transitionPeriod-(start+i)));

            int r = (int)(transSlopes[0]*adj +c1.getRed());
            int g = (int)(transSlopes[1]*adj +c1.getGreen());
            int b = (int)(transSlopes[2]*adj +c1.getBlue());

            System.out.println("Pixel: "+i+"  Start: "+start+"  x: "+adj+"  R: "+r+"  G: "+g+"  B: "+b);
            pixels[i] = new Color(r, g, b);
        }
    }

    
}
