package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/14/2016.
 */
public abstract class AnimatedCharacter3D implements Drawable {
    private Character3D character3D;
    private boolean isEnable;

    public AnimatedCharacter3D(Character3D character3D) {
        this.character3D = character3D;
    }

    public Character3D getCharacter3D() {
        return character3D;
    }

    public void setCharacter3D(Character3D character3D) {
        this.character3D = character3D;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void scale(Vector3 vec3) {
        character3D = character3D.scale(vec3);
    }

    public void translate(Vector3 vec3) {
        character3D = character3D.translate(vec3);
    }

    public void rotateRad(Vector3 source, Vector3 direction, float rad) {
        character3D = character3D.rotateRad(source, direction, rad);
    }

    public void rotateDeg(Vector3 source, Vector3 direction, float deg) {
        character3D = character3D.rotateDeg(source, direction, deg);
    }

    public void defaultRender(Graphics2D g2d) {
        character3D.render(g2d);
    }

    public abstract void render(Graphics2D g2d);


}
