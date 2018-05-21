package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Object3DFactory {
    public static Color defaultColor = Color.BLACK;

    public static Object3D createCube(float length, Color color) {
        Object3D cube = new Object3D();
        Mesh3D front = new Mesh3D();
        front.addNode(new Vector3(1, 0, 0));
        front.addNode(new Vector3(1, 1, 0));
        front.addNode(new Vector3(1, 1, 1));
        front.addNode(new Vector3(1, 0, 1));
        cube.addMesh3D(front);
        Mesh3D back = new Mesh3D();
        back.addNode(new Vector3(0, 1, 0));
        back.addNode(new Vector3(0, 0, 0));
        back.addNode(new Vector3(0, 0, 1));
        back.addNode(new Vector3(0, 1, 1));
        cube.addMesh3D(back);
        Mesh3D right = new Mesh3D();
        right.addNode(new Vector3(1, 1, 0));
        right.addNode(new Vector3(0, 1, 0));
        right.addNode(new Vector3(0, 1, 1));
        right.addNode(new Vector3(1, 1, 1));
        cube.addMesh3D(right);
        Mesh3D left = new Mesh3D();
        left.addNode(new Vector3(0, 0, 0));
        left.addNode(new Vector3(1, 0, 0));
        left.addNode(new Vector3(1, 0, 1));
        left.addNode(new Vector3(0, 0, 1));
        cube.addMesh3D(left);
        Mesh3D top = new Mesh3D();
        top.addNode(new Vector3(1, 0, 1));
        top.addNode(new Vector3(1, 1, 1));
        top.addNode(new Vector3(0, 1, 1));
        top.addNode(new Vector3(0, 0, 1));
        cube.addMesh3D(top);
        Mesh3D bottom = new Mesh3D();
        bottom.addNode(new Vector3(1, 1, 0));
        bottom.addNode(new Vector3(1, 0, 0));
        bottom.addNode(new Vector3(0, 0, 0));
        bottom.addNode(new Vector3(0, 1, 0));
        cube.addMesh3D(bottom);
//        cube = cube.translate(new Vector3())
        cube.setColor(color);
        cube = cube.scale(new Vector3(length, length, length));
        return cube;
    }

    public static Object3D createPyramid(float length, Color color) {
        Object3D pyramid = new Object3D();
        Mesh3D face1 = new Mesh3D();
        face1.addNode(new Vector3(1, 0, 0));
        face1.addNode(new Vector3(0, 1, 0));
        face1.addNode(new Vector3(0, 0, 1));
        pyramid.addMesh3D(face1);
        Mesh3D face2 = new Mesh3D();
        face2.addNode(new Vector3(0, 0, 0));
        face2.addNode(new Vector3(1, 0, 0));
        face2.addNode(new Vector3(0, 0, 1));
        pyramid.addMesh3D(face2);
        Mesh3D face3 = new Mesh3D();
        face3.addNode(new Vector3(0, 0, 0));
        face3.addNode(new Vector3(0, 0, 1));
        face3.addNode(new Vector3(0, 1, 0));
        pyramid.addMesh3D(face3);
        Mesh3D face4 = new Mesh3D();
        face4.addNode(new Vector3(0, 0, 0));
        face4.addNode(new Vector3(0, 1, 0));
        face4.addNode(new Vector3(1, 0, 0));
        pyramid.addMesh3D(face4);
        pyramid.setColor(color);
        pyramid = pyramid.scale(new Vector3(length, length, length));
        return pyramid;
    }

    public static Object3D createSphere(float radius, Color color, int step) {
        Object3D sphere = new Object3D();
        int theta = 0;
        int phi = 0;
        for (theta = 0; theta < 180; theta = theta + step) {
            for (phi = 0; phi < 360; phi = phi + step) {
                Mesh3D mesh3D = new Mesh3D();
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi))));
                sphere.addMesh3D(mesh3D);
            }
        }
        sphere.setColor(color);
        sphere = sphere.scale(new Vector3(radius, radius, radius));
        return sphere;
    }

    public static Object3D createGradientSphere(float radius, int step) {
        Object3D sphere = new Object3D();
        int theta = 0;
        int phi = 0;
        for (theta = 0; theta < 180; theta = theta + step) {
            for (phi = -180; phi < 180; phi = phi + step) {
                Mesh3D mesh3D = new Mesh3D();
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi))));
                mesh3D.setColor(new Color(theta * 255 / 180, (phi + 180) * 255 / 360, 0));
                sphere.addMesh3D(mesh3D);
            }
        }
        sphere = sphere.scale(new Vector3(radius, radius, radius));
        return sphere;
    }

    public static Object3D createGround(Vector2 source, float width, float height, Color color) {
        Object3D ground = new Object3D();
        Mesh3D mesh3D = new Mesh3D();
        mesh3D.addNode(new Vector3(source.x, source.y, 0));
        mesh3D.addNode(new Vector3(source.x + width, source.y, 0));
        mesh3D.addNode(new Vector3(source.x + width, source.y + height, 0));
        mesh3D.addNode(new Vector3(source.x, source.y + height, 0));
        ground.addMesh3D(mesh3D);
        ground.setColor(color);
        return ground;
    }



    public static Object3D createNonSymmetricCube(float a, float b, float c, Color color) {
        Object3D cube = createCube(1, color);
        cube = cube.scale(new Vector3(a, b, c));
        cube.setColor(color);
        return cube;
    }

    public static Object3D createPansSphere(float radius, int step, Color pansColor) {
        Object3D sphere = new Object3D();
        int theta = 0;
        int phi = 0;
        Color color;
        for (theta = 0; theta < 180; theta = theta + step) {
            for (phi = -180; phi < 180; phi = phi + step) {
                Mesh3D mesh3D = new Mesh3D();
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi + step))));
                mesh3D.addNode(Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta + step), (float) Math.toRadians(phi))));
                float x = Vector3.rtpToxyz(new Vector3(1.0f, (float) Math.toRadians(theta), (float) Math.toRadians(phi))).x;
                if (theta > 90 || (x <= 0.5 && x >= 0.3) || (x >= -0.5 && x <= -0.3)) {
                    color = pansColor;
                } else {
                    color = new Color(theta * 255 / 180, (phi + 180) * 255 / 360, 0);
                }
                mesh3D.setColor(color);
                sphere.addMesh3D(mesh3D);
            }
        }
        sphere = sphere.scale(new Vector3(radius, radius, radius));
        return sphere;
    }




}
