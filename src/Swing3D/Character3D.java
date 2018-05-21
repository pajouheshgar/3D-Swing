package Swing3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Character3D implements Drawable {
    private ArrayList<Object3D> object3DList;
    private Vector3 center;
    private Color color;

    public Character3D() {
        object3DList = new ArrayList<>();
    }

    public Character3D(Character3D character3D) {
        object3DList = new ArrayList<>();
        setCenter(new Vector3(character3D.center));
        for (Object3D object3D : character3D.getObject3DList()) {
            addObject3D(object3D.clone());
        }
    }

    public Character3D(ArrayList<Object3D> object3DList) {
        this.object3DList = object3DList;
    }

    public void addObject3D(Object3D object3D) {
        object3DList.add(object3D);
    }

    public void setColor(Color color) {
        for (Object3D object3D : object3DList) {
            object3D.setColor(color);
        }
    }

    public void render(Graphics2D g2d) {
        for (Object3D object3D : object3DList) {
            object3D.render(g2d);
        }
    }

    public ArrayList<Object3D> getObject3DList() {
        return object3DList;
    }

    public void setObject3DList(ArrayList<Object3D> object3DList) {
        this.object3DList = object3DList;
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public Character3D clone() {
        Character3D result = new Character3D();
        result.setCenter(new Vector3(center));
        for (Object3D object3D : object3DList) {
            result.addObject3D(object3D.clone());
        }
        return result;
    }

    public Character3D scale(Vector3 scale) {
        Character3D result = new Character3D();
        result.setCenter(new Vector3(center.x * scale.x, center.y * scale.y, center.z * scale.z));
        for (Object3D object3D : object3DList) {
            result.addObject3D(object3D.scale(scale));
        }
        return result;
    }

    public Character3D translate(Vector3 vec3) {
        Character3D result = new Character3D();
        result.setCenter(getCenter().plus(vec3));
        for (Object3D object3D : object3DList) {
            result.addObject3D(object3D.translate(vec3));
        }
        return result;
    }

    public Character3D rotateRad(Vector3 source, Vector3 direction, float rad) {
        Character3D result = this.clone();
        result.setCenter(getCenter().rotateRad(source, direction, rad));
        for (Object3D object3D : result.getObject3DList()) {
            object3D.assign(object3D.rotateRad(source, direction, rad));
        }
        return result;
    }

    public Character3D rotateDeg(Vector3 source, Vector3 direction, float deg) {
        Character3D result = this.clone();
        result.setCenter(getCenter().rotateDeg(source, direction, deg));
        for (Object3D object3D : result.getObject3DList()) {
            object3D.assign(object3D.rotateDeg(source, direction, deg));
        }
        return result;
    }

    public void setTransparent(boolean transparent) {
        for (Object3D object3D : object3DList) {
            object3D.setTransparent(transparent);
        }
    }

}
