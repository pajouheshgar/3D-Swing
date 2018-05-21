package Swing3D;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec2) {
        this.x = vec2.x;
        this.y = vec2.y;
    }
}
