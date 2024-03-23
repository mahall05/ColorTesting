package Effects;

import java.awt.Color;
import java.util.function.Supplier;

public class ColorWaveEffect extends LightingEffect {
    private Color c1, c2;
    private int wavePeriod; // Length of the wave in pixels
    private int transitionPeriod; // Length of time for full transition between color in ms/transition
    private double transFrequency; // Frequency in transition/ms
    private int timer = 0;

    private Color[] waveBase;
    private Color[] transBase;
    private double[] waveSlopes;
    private double[] transSlopes;

    public ColorWaveEffect(int location, int length, Color c1, Color c2, int wavePeriod, int transitionPeriod, Type type){
        super(location, length, type);
        this.c1 = c1;
        this.c2 = c2;
        this.wavePeriod = wavePeriod;
        this.transitionPeriod = transitionPeriod;
        transFrequency = 1.0/transitionPeriod;

        waveSlopes = new double[] {(double)(c2.getRed()-c1.getRed())/(wavePeriod), (double)(c2.getGreen()-c1.getGreen())/(wavePeriod), (double)(c2.getBlue()-c1.getBlue())/(wavePeriod)};
        transSlopes = new double[] {(double)(c2.getRed()-c1.getRed())/(transitionPeriod), (double)(c2.getGreen()-c1.getGreen())/(transitionPeriod), (double)(c2.getBlue()-c1.getBlue())/(transitionPeriod)};

        System.out.println("Wave Slope: "+waveSlopes[0]);

        waveBase = new Color[wavePeriod];
        transBase = new Color[transitionPeriod];

        for(int i = 0; i < wavePeriod/2; i++){
            waveBase[i] = new Color((int)(c1.getRed()+waveSlopes[0]*i), (int)(c1.getGreen()+waveSlopes[1]*i), (int)(c1.getBlue()+waveSlopes[2]*i));
            waveBase[wavePeriod-i-1] = waveBase[i];
        }

        for(int i = 0; i < (int)transitionPeriod/2; i++){
            transBase[i] = new Color((int)(c1.getRed()+transSlopes[0]*i), (int)(c1.getGreen()+transSlopes[1]*i), (int)(c1.getBlue()+transSlopes[2]*i));
            transBase[transitionPeriod-i-1] = transBase[i];
        }
        System.out.println("Cock");
    }

    @Override
    public void update() {
        timer+=200000*Math.pow(2, transitionPeriod/2000);

        for(int i = 0; i < length; i++){
            int relativePosition = i%wavePeriod;
            double start = (double)relativePosition/(double)wavePeriod*transitionPeriod;
            double x = (start + timer*transFrequency)%transitionPeriod;

            if(i==49) System.out.println("Timer: "+timer+"  Relative Position: "+relativePosition+"  Start Location (Based on Index): " + start + "  x (based on start location & timer): "+x);

            int r = (int)(transBase[(int)x].getRed());
            int g = (int)(transBase[(int)x].getGreen());
            int b = (int)(transBase[(int)x].getBlue());

            if(i==49) System.out.println("Pixel: "+i+"  Start: "+start+"  x: "+x+"  R: "+r+"  G: "+g+"  B: "+b);
            pixels[i] = new Color(r, g, b);
        }
    }

    
}
