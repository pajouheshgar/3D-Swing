package Swing3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class ZBuffer {
    private static boolean ENABLE_Z_BUFFER = true;
    private static List<Mesh2D> renderingList = new ArrayList<>();
    private static Comparator<Mesh2D> mesh2DComparator = (o1, o2) -> {
        if (o1.getZ() < o2.getZ()) {
            return 1;
        }
        if (o1.getZ() > o2.getZ()) {
            return -1;
        }
        return 0;
    };

    public static void addMesh2D(Mesh2D mesh2D) {
        renderingList.add(mesh2D);
    }

    private static void renderMesh2D(Graphics2D g2d, Mesh2D mesh2D) {
        ArrayList<Vector2> nodeVector2List = mesh2D.getNodeVector2List();
        ArrayList<Boolean> nodeVisibilityList = mesh2D.getNodeVisibilityList();
        boolean transparent = mesh2D.isTransparent();
        Color color = mesh2D.getColor();
        int n = nodeVector2List.size();
        int w = Projection.ScreenWidth;
        int h = Projection.ScreenHeight;
        boolean flag = false;
        if (transparent) {
            for (int i = 0; i < n; i++) {
                if (nodeVisibilityList.get(i)) {
                    flag = true;
                }
            }
            if (flag) {
                for (int i = 0; i < n - 1; i++) {
                    Vector2 vec2source = nodeVector2List.get(i);
                    Vector2 vec2dest = nodeVector2List.get(i + 1);
                    float x1 = vec2source.x;
                    int x1Screen = (int) ((x1 + 1) * w / 2);
                    float y1 = vec2source.y;
                    int y1Screen = (int) ((1 - y1) * h / 2);
                    float x2 = vec2dest.x;
                    int x2Screen = (int) ((x2 + 1) * w / 2);
                    float y2 = vec2dest.y;
                    int y2Screen = (int) ((1 - y2) * w / 2);
                    g2d.setColor(color);
                    g2d.drawLine(x1Screen, y1Screen, x2Screen, y2Screen);

                }
                Vector2 vec2source = nodeVector2List.get(n - 1);
                Vector2 vec2dest = nodeVector2List.get(0);
                float x1 = vec2source.x;
                int x1Screen = (int) ((x1 + 1) * w / 2);
                float y1 = vec2source.y;
                int y1Screen = (int) ((1 - y1) * h / 2);
                float x2 = vec2dest.x;
                int x2Screen = (int) ((x2 + 1) * w / 2);
                float y2 = vec2dest.y;
                int y2Screen = (int) ((1 - y2) * w / 2);
                g2d.setColor(color);
                g2d.drawLine(x1Screen, y1Screen, x2Screen, y2Screen);
            }
        } else {
            for (Boolean check : nodeVisibilityList) {
                if (check) {
                    flag = true;
                }
            }
            if (flag) {
                int[] xPositionArray = new int[n];
                int[] yPositionArray = new int[n];
                for (int i = 0; i < n; i++) {
                    float x = nodeVector2List.get(i).x;
                    float y = nodeVector2List.get(i).y;
                    int xScreen = (int) ((x + 1) * w / 2);
                    int yScreen = (int) ((1 - y) * h / 2);
                    xPositionArray[i] = xScreen;
                    yPositionArray[i] = yScreen;
                }
                g2d.setColor(Color.BLACK);
                g2d.drawPolygon(xPositionArray, yPositionArray, n);
                g2d.setColor(color);
                g2d.fillPolygon(xPositionArray, yPositionArray, n);
            }
        }

    }

    public static void renderAll(Graphics2D g2d) {
        if (ENABLE_Z_BUFFER) {
            renderingList = renderingList.stream().sorted(mesh2DComparator).collect(Collectors.toList());
//            renderingList.stream().sorted(mesh2DComparator).collect(Collectors.toList());
        }
        for (Mesh2D mesh2D : renderingList) {
            renderMesh2D(g2d, mesh2D);
        }
        renderingList.removeAll(renderingList);
    }
}


