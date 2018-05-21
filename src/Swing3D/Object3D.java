package Swing3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Object3D implements Drawable {
    private ArrayList<Mesh3D> mesh3DList;
    private boolean transparent;
    private Color color;

    public Object3D() {
        mesh3DList = new ArrayList<>();
    }

    public Object3D(ArrayList<Mesh3D> mesh3DList) {
        this.mesh3DList = mesh3DList;
    }

    public void addMesh3D(Mesh3D mesh3D) {
        mesh3DList.add(mesh3D);
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        for (Mesh3D mesh3D : mesh3DList) {
            mesh3D.setTransparent(transparent);
        }
        this.transparent = transparent;
    }

    public ArrayList<Mesh3D> getMesh3DList() {
        return mesh3DList;
    }

    public void setMesh3DList(ArrayList<Mesh3D> mesh3DList) {
        this.mesh3DList = mesh3DList;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        for (Mesh3D mesh3D : mesh3DList) {
            mesh3D.setColor(color);
        }
    }

    public void render(Graphics2D g2d) {
        for (Mesh3D mesh3D : mesh3DList) {
            mesh3D.render(g2d);
        }
    }

    public Object3D clone() {
        Object3D object3D = new Object3D();
        object3D.setTransparent(transparent);
        for (Mesh3D mesh3D : mesh3DList) {
            object3D.addMesh3D(mesh3D.clone());
        }
        return object3D;
    }

    public Object3D scale(Vector3 scale) {
        Object3D result = new Object3D();
        result.setTransparent(transparent);
        for (Mesh3D mesh3D : mesh3DList) {
            result.addMesh3D(mesh3D.scale(scale));
        }
        return result;
    }

    public Object3D translate(Vector3 vec3) {
        Object3D result = new Object3D();
        result.setTransparent(transparent);
        for (Mesh3D mesh3D : mesh3DList) {
            result.addMesh3D(mesh3D.translate(vec3));
        }
        return result;
    }

    public Object3D rotateRad(Vector3 source, Vector3 direction, float rad) {
        Object3D result = this.clone();
        for (int i = 0; i < result.getMesh3DList().size(); i++) {
            result.getMesh3DList().get(i).assign(result.getMesh3DList().get(i).rotateRad(source, direction, rad));
        }
        return result;
    }

    public Object3D rotateDeg(Vector3 source, Vector3 direction, float deg) {
        Object3D result = this.clone();
        for (int i = 0; i < result.getMesh3DList().size(); i++) {
            result.getMesh3DList().get(i).assign(result.getMesh3DList().get(i).rotateDeg(source, direction, deg));
        }
        return result;
    }

    public void assign(Object3D object3D) {
        for (int i = 0; i < getMesh3DList().size(); i++) {
            getMesh3DList().get(i).assign(object3D.getMesh3DList().get(i));
        }
    }



}
