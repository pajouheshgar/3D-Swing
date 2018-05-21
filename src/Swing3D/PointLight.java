package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/15/2016.
 */
public class PointLight {
    private Vector3 position;
    private float intensity;
    private Color color;

    public PointLight(Vector3 position, float intensity, Color color) {
        this.position = position;
        this.intensity = intensity;
        this.color = color;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
