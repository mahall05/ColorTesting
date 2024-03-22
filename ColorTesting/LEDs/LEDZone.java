package LEDs;
import java.util.ArrayList;

import Effects.LightingEffect;
import Effects.SolidColorEffect;
import Effects.LightingEffect.Type;

import java.awt.Color;

public class LEDZone {
    private int length, location;
    private Color[] pixels;
    private String key;

    private ArrayList<LightingEffect> activeEffects;

    public LEDZone(String key, int location, int length){
        setLength(length);
        setLocation(location);
        this.key = key;
        pixels = new Color[length];
        activeEffects = new ArrayList<LightingEffect>();
    }

    public void addEffect(LightingEffect... es){
        for(LightingEffect e : es){
            if(e.getType()==Type.status){
                activeEffects.add(e);
            }else{
                for(int i = activeEffects.size()-1; i>=0; i--){
                    if(activeEffects.get(i).getType()==Type.cosmetic){
                        activeEffects.add(i+1, e);
                    }
                }
            }
        }
    }
    public void setEffect(LightingEffect... e){
        clearEffects();
        addEffect(e);
    }
    public void clearEffects(){
        activeEffects.clear();
        activeEffects.add(new SolidColorEffect(0, length, new Color(0,0,0), Type.cosmetic));
        //addEffect(new SolidColorEffect(0, length, new Color(0,0,0), Type.cosmetic));
    }

    public void setLocation(int l){
        if(l<0) location = 0;
        else location = l;
    }
    public int getLocation(){
        return location;
    }
    public void setLength(int l){
        if(l<0) length=0;
        else length = l;
    }
    public int getLength(){
        return length;
    }

    public void update(){
        for(LightingEffect e : activeEffects){
            e.update();
        }

        for(int i = 0; i < activeEffects.size(); i++){
            Color[] effectPixels = activeEffects.get(i).getPixels();
            for(int j = 0; j < activeEffects.get(i).getLength(); j++){
                if(i==0 || !equal(effectPixels[j], new Color(0,0,0))){
                    pixels[j+activeEffects.get(i).getLocation()] = effectPixels[j];
                }
            }
        }
    }

    public Color[] getPixels(){
        return pixels;
    }

    public static boolean equal(Color c1, Color c2){
        if(c1==null || c2==null) return false;
        return c1.getRed()==c2.getRed() && c1.getGreen()==c2.getGreen() && c1.getGreen()==c2.getGreen();
    }
}
