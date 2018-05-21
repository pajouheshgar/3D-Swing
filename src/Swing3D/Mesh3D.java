package Swing3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Mesh3D implements Drawable {
    private ArrayList<Vector3> nodeVector3List;
    private ArrayList<Vector2> textureCoordinateList;
    private BufferedImage texture;
    private Color color;
    private boolean transparent;


    public Mesh3D() {
        nodeVector3List = new ArrayList<>();
    }

    public Mesh3D(ArrayList<Vector3> nodeVector3List) {
        this.nodeVector3List = nodeVector3List;
    }

    public void addNode(Vector3 nodeVector3) {
        nodeVector3List.add(nodeVector3);
    }

    public ArrayList<Vector3> getNodeVector3List() {
        return nodeVector3List;
    }

    public void setNodeVector3List(ArrayList<Vector3> nodeVector3List) {
        this.nodeVector3List = nodeVector3List;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void render(Graphics2D g2d) {
        Mesh2D projectedMesh = Projection.getProjectedMesh(this);
        projectedMesh.setTransparent(transparent);
        float z = 0;
        for (Vector3 vector3 : nodeVector3List) {
            z = z + vector3.distance(Projection.getCamera().getPosition());
        }
        z = z / nodeVector3List.size();
        projectedMesh.setZ(z);
        projectedMesh.render(g2d);
    }

    public Mesh3D clone() {
        Mesh3D result = new Mesh3D();
        result.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
        result.setTransparent(transparent);
        for (Vector3 vector3 : nodeVector3List) {
            result.addNode(new Vector3(vector3));
        }
        return result;
    }

    public Mesh3D scale(Vector3 scale) {
        Mesh3D result = this.clone();
        for (Vector3 vec3 : result.getNodeVector3List()) {
            vec3.x = vec3.x * scale.x;
            vec3.y = vec3.y * scale.y;
            vec3.z = vec3.z * scale.z;
        }
        return result;
    }

    public Mesh3D translate(Vector3 vec3) {
        Mesh3D result = this.clone();
        for (Vector3 vector3 : result.getNodeVector3List()) {
            vector3.x = vector3.x + vec3.x;
            vector3.y = vector3.y + vec3.y;
            vector3.z = vector3.z + vec3.z;
        }
        return result;
    }

    public Mesh3D rotateRad(Vector3 source, Vector3 direction, float rad) {
        Mesh3D result = this.clone();
        for (Vector3 vector3 : result.getNodeVector3List()) {
            Vector3 rotatedVector = vector3.rotateRad(source, direction, rad);
            vector3.x = rotatedVector.x;
            vector3.y = rotatedVector.y;
            vector3.z = rotatedVector.z;
        }
        return result;
    }

    public Mesh3D rotateDeg(Vector3 source, Vector3 direction, float deg) {
        Mesh3D result = this.clone();
        for (Vector3 vector3 : result.getNodeVector3List()) {
            Vector3 rotatedVector = vector3.rotateDeg(source, direction, deg);
            vector3.x = rotatedVector.x;
            vector3.y = rotatedVector.y;
            vector3.z = rotatedVector.z;
        }
        return result;
    }

    public void assign(Mesh3D mesh3D) {
        for (int i = 0; i < nodeVector3List.size(); i++) {
            nodeVector3List.get(i).assign(mesh3D.getNodeVector3List().get(i));
        }
    }


}
