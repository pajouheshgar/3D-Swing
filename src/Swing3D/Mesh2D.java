package Swing3D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Mesh2D implements Drawable {
    private ArrayList<Vector2> nodeVector2List;
    private ArrayList<Boolean> nodeVisibilityList;
    private Color color;
    private boolean transparent;
    private float z;

    public Mesh2D(ArrayList<Vector2> nodeVector2List, ArrayList<Boolean> nodeVisibilityList) {
        this.nodeVector2List = nodeVector2List;
        this.nodeVisibilityList = nodeVisibilityList;
    }

    public Mesh2D() {
        nodeVector2List = new ArrayList<>();
        nodeVisibilityList = new ArrayList<>();
    }

    public void addNode(Vector2 vec2, boolean isVisible) {
        nodeVector2List.add(vec2);
        nodeVisibilityList.add(isVisible);
    }

    public void render(Graphics2D g2d) {
        int n = nodeVector2List.size();
//        int w = Projection.ScreenWidth;
//        int h = Projection.ScreenHeight;
//        boolean flag = false;
//        if (transparent) {
//            for (int i = 0; i < n; i++) {
//                if (nodeVisibilityList.get(i)) {
//                    flag = true;
//                }
//            }
//            if (flag) {
//                for (int i = 0; i < n - 1; i++) {
//                    Vector2 vec2source = nodeVector2List.get(i);
//                    Vector2 vec2dest = nodeVector2List.get(i + 1);
//                    float x1 = vec2source.x;
//                    int x1Screen = (int) ((x1 + 1) * w / 2);
//                    float y1 = vec2source.y;
//                    int y1Screen = (int) ((1 - y1) * h / 2);
//                    float x2 = vec2dest.x;
//                    int x2Screen = (int) ((x2 + 1) * w / 2);
//                    float y2 = vec2dest.y;
//                    int y2Screen = (int) ((1 - y2) * w / 2);
//                    g2d.setColor(color);
//                    g2d.drawLine(x1Screen, y1Screen, x2Screen, y2Screen);
//
//                }
//                Vector2 vec2source = nodeVector2List.get(n - 1);
//                Vector2 vec2dest = nodeVector2List.get(0);
//                float x1 = vec2source.x;
//                int x1Screen = (int) ((x1 + 1) * w / 2);
//                float y1 = vec2source.y;
//                int y1Screen = (int) ((1 - y1) * h / 2);
//                float x2 = vec2dest.x;
//                int x2Screen = (int) ((x2 + 1) * w / 2);
//                float y2 = vec2dest.y;
//                int y2Screen = (int) ((1 - y2) * w / 2);
//                g2d.setColor(color);
//                g2d.drawLine(x1Screen, y1Screen, x2Screen, y2Screen);
//            }
//        } else {
//            for (Boolean check : nodeVisibilityList) {
//                if (check) {
//                    flag = true;
//                }
//            }
//            if (flag) {
//                int[] xPositionArray = new int[n];
//                int[] yPositionArray = new int[n];
//                for (int i = 0; i < n; i++) {
//                    float x = nodeVector2List.get(i).x;
//                    float y = nodeVector2List.get(i).y;
//                    int xScreen = (int) ((x + 1) * w / 2);
//                    int yScreen = (int) ((1 - y) * h / 2);
//                    xPositionArray[i] = xScreen;
//                    yPositionArray[i] = yScreen;
//                }
//                g2d.setColor(Color.BLACK);
//                g2d.drawPolygon(xPositionArray, yPositionArray, n);
//                g2d.setColor(color);
//                g2d.fillPolygon(xPositionArray, yPositionArray, n);
//            }
//        }
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (nodeVisibilityList.get(i)) {
                flag = true;
            }
        }
        if (flag) {
            ZBuffer.addMesh2D(this);
        }
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public ArrayList<Vector2> getNodeVector2List() {
        return nodeVector2List;
    }

    public void setNodeVector2List(ArrayList<Vector2> nodeVector2List) {
        this.nodeVector2List = nodeVector2List;
    }

    public ArrayList<Boolean> getNodeVisibilityList() {
        return nodeVisibilityList;
    }

    public void setNodeVisibilityList(ArrayList<Boolean> nodeVisibilityList) {
        this.nodeVisibilityList = nodeVisibilityList;
    }

    @Override
    protected Mesh2D clone() {
        Mesh2D result = new Mesh2D();
        result.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
        for (int i = 0; i < nodeVector2List.size(); i++) {
            result.addNode(new Vector2(nodeVector2List.get(i)), nodeVisibilityList.get(i));
        }
        return result;
    }
}
