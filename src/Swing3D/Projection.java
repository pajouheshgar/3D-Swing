package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Projection {
    public static final int ScreenWidth = 600;
    public static final int ScreenHeight = 600;
    public static final float aspectRatio = (float) ScreenWidth / ScreenHeight;
    public static final boolean WIRED_VIEW = false;
    private static final boolean PERSPECTIVE_PROJECTION = true;
    private static final boolean ORTHOGONAL_PROJECTION = false;
    private static Camera camera;
    private static PointLight pointLight;
    private static boolean ENABLE_POINT_LIGHT = false;
    private static float ambientParameter = 0.3f;

    public static Vector2 getProjectedVector(Vector3 vec3) {
        Vector2 projectedPoint;
        if (PERSPECTIVE_PROJECTION) {
            Vector3 transformedPoint = vec3.sub(camera.getPosition());
            Vector3 transformedRotatedPoint = new Vector3(
                    camera.getuHat().dot(transformedPoint),
                    camera.getvHat().dot(transformedPoint),
                    camera.getnHat().dot(transformedPoint)
            );
            if (transformedRotatedPoint.z == 0) {
//                throw new RuntimeException("");
                //TODO
                projectedPoint = new Vector2();
                projectedPoint.x = 0;
                projectedPoint.y = 0;
                return projectedPoint;
            }
            projectedPoint = new Vector2();
            projectedPoint.x = (float) (transformedRotatedPoint.x / (aspectRatio * transformedRotatedPoint.z * Math.tan(camera.getFov() / 2)));
            projectedPoint.y = (float) (transformedRotatedPoint.y / (transformedRotatedPoint.z * Math.tan(camera.getFov() / 2)));
            return projectedPoint;

        }
        if (ORTHOGONAL_PROJECTION) {
            Vector3 cameraToPoint = vec3.sub(camera.getPosition());
            float u = cameraToPoint.dot(camera.getuHat());
            float v = cameraToPoint.dot(camera.getvHat());
            projectedPoint = new Vector2(u, v);
            return projectedPoint;
        }
        return null;
    }

    public static boolean isVisible(Vector3 vec3) {
        if (PERSPECTIVE_PROJECTION) {
            float cosTheta = ((vec3.sub(camera.getPosition())).normal()).dot(camera.getnHat());
            return cosTheta >= Math.cos(camera.getFov() / 2);
        }
        if (ORTHOGONAL_PROJECTION) {
            return vec3.sub(camera.getPosition()).dot(camera.getnHat()) >= 0;
        }
        return false;
    }

    public static Mesh2D getProjectedMesh(Mesh3D mesh3D) {
        Mesh2D result = new Mesh2D();
        Vector3 mean = new Vector3();
        float meanDistance = 0;
        Color meshColor = mesh3D.getColor();
        for (Vector3 vector3 : mesh3D.getNodeVector3List()) {
            Vector2 vector2 = getProjectedVector(vector3);
            result.addNode(vector2, isVisible(vector3));
            mean = mean.plus(vector3);
            meanDistance = meanDistance + (pointLight.getPosition().sub(vector3)).length();
        }
        meanDistance = meanDistance / mesh3D.getNodeVector3List().size();
//        System.out.println(meanDistance);
        mean = mean.devise(mesh3D.getNodeVector3List().size());
        Vector3 r1 = mesh3D.getNodeVector3List().get(0).sub(mesh3D.getNodeVector3List().get(1));
        Vector3 r2 = mesh3D.getNodeVector3List().get(2).sub(mesh3D.getNodeVector3List().get(1));
        Vector3 nHat;
        Vector3 reflectedLight;
        Vector3 lightToMesh = mean.sub(pointLight.getPosition());
        try {
            nHat = (r2.cross(r1)).normal();
            reflectedLight = (lightToMesh.sub(nHat.mult(2 * nHat.dot(lightToMesh)))).normal();
        } catch (Exception e) {
            nHat = new Vector3();
            reflectedLight = new Vector3();
        }
        Vector3 meshToCamera = (camera.getPosition().sub(mean)).normal();
        float colorIntensity = pointLight.getIntensity() * meshToCamera.dot(reflectedLight);
        colorIntensity = colorIntensity / meanDistance;
        if (colorIntensity < 0) {
            colorIntensity = 0;
        }
        if (ENABLE_POINT_LIGHT) {
            int r = (int) ((meshColor.getRed() * colorIntensity * pointLight.getColor().getRed()) + ambientParameter * meshColor.getRed());
            int g = (int) (meshColor.getGreen() * colorIntensity * pointLight.getColor().getGreen() + ambientParameter * meshColor.getGreen());
            int b = (int) (meshColor.getBlue() * colorIntensity * pointLight.getColor().getBlue() + ambientParameter * meshColor.getBlue());
            if (r > 255) {
                r = 255;
            }
            if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            }
            if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            }
            if (b < 0) {
                b = 0;
            }
            meshColor = new Color(r, g, b);
        }
        result.setColor(meshColor);
        return result;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static void setCamera(Camera camera) {
        Projection.camera = camera;
    }

    public static PointLight getPointLight() {
        return pointLight;
    }

    public static void setPointLight(PointLight pointLight) {
        Projection.pointLight = pointLight;
    }
}
