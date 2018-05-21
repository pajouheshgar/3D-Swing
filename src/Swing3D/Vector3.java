package Swing3D;
/**
 * Created by ehsan_PC on 7/12/2016.
 */
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec3) {
        this.x = vec3.x;
        this.y = vec3.y;
        this.z = vec3.z;
    }

    public static Vector3 xyzTortp(Vector3 xyz) {
        //TODO
        return null;
    }

    public static Vector3 rtpToxyz(Vector3 rtp) {
        Vector3 result = new Vector3();
        result.x = (float) (rtp.x * Math.sin(rtp.y) * Math.cos(rtp.z));
        result.y = (float) (rtp.x * Math.sin(rtp.y) * Math.sin(rtp.z));
        result.z = (float) (rtp.x * Math.cos(rtp.y));
        return result;
    }

    public void normalize() {
        Vector3 result = this.normal();
        this.x = result.x;
        this.y = result.y;
        this.z = result.z;
    }

    public Vector3 mult(float c) {
        Vector3 result = new Vector3();
        result.x = c * x;
        result.y = c * y;
        result.z = c * z;
        return result;
    }

    public Vector3 devise(float c) {
        if (c == 0) {
            throw new RuntimeException("cannot devise to zero");
        }
        Vector3 result = this.mult(1 / c);
        return result;
    }

    public Vector3 plus(Vector3 vec3) {
        Vector3 result = new Vector3();
        result.x = x + vec3.x;
        result.y = y + vec3.y;
        result.z = z + vec3.z;
        return result;
    }

    public Vector3 sub(Vector3 vec3) {
        Vector3 result = this.plus(vec3.mult(-1));
        return result;
    }

    public float dot(Vector3 vec3) {
        float result = x * vec3.x + y * vec3.y + z * vec3.z;
        return result;
    }

    public Vector3 cross(Vector3 vec3) {
        Vector3 result = new Vector3();
        result.x = y * vec3.z - z * vec3.y;
        result.y = z * vec3.x - x * vec3.z;
        result.z = x * vec3.y - y * vec3.x;
        return result;
    }

    public Vector3 normal() {
        if (x == 0 && y == 0 && z == 0) {
            throw new RuntimeException("cant normalize zero vector");
        }
        float len = (float) Math.sqrt(x * x + y * y + z * z);
        Vector3 result = this.mult(1 / len);
        return result;
    }

    public Vector3 negative() {
        Vector3 result = new Vector3();
        result.x = -x;
        result.y = -y;
        result.z = -z;
        return result;
    }

    public float length() {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        return length;
    }

    public float distance(Vector3 vec3) {
        return (this.sub(vec3)).length();
    }

    public Vector3 rotateRad(Vector3 direction, float rad) {
        Vector3 vector3 = new Vector3(this);
        Vector3 nHat = direction.normal();
        float u = nHat.x;
        float v = nHat.y;
        float w = nHat.z;
        float costh = (float) Math.cos(rad);
        float sinth = (float) Math.sin(rad);
        float x = vector3.x;
        float y = vector3.y;
        float z = vector3.z;
        vector3.x =
                x * (u * u + (1 - u * u) * costh)
                        + y * (u * v * (1 - costh) - w * sinth)
                        + z * (u * w * (1 - costh) + v * sinth);
        vector3.y =
                x * (u * v * (1 - costh) + w * sinth)
                        + y * (v * v + (1 - v * v) * costh)
                        + z * (v * w * (1 - costh) - u * sinth);
        vector3.z =
                x * (u * w * (1 - costh) - v * sinth)
                        + y * (v * w * (1 - costh) + u * sinth)
                        + z * (w * w + (1 - w * w) * costh);
        return vector3;
    }

    public Vector3 rotateDeg(Vector3 direction, float deg) {
        float rad = (float) (deg * Math.PI / 180.0f);
        return rotateRad(direction, rad);
    }

    public Vector3 rotateRad(Vector3 source, Vector3 direction, float rad) {
        Vector3 translatedVector = this.sub(source);
        translatedVector = translatedVector.rotateRad(direction, rad);
        Vector3 result = translatedVector.plus(source);
        return result;
    }

    public Vector3 rotateDeg(Vector3 source, Vector3 direction, float deg) {
        Vector3 translatedVector = this.sub(source);
        translatedVector = translatedVector.rotateDeg(direction, deg);
        Vector3 result = translatedVector.plus(source);
        return result;
    }

    public void assign(Vector3 vec3) {
        x = vec3.x;
        y = vec3.y;
        z = vec3.z;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Direction getDirection() {
        int x = Math.round(this.x);
        int y = Math.round(this.y);
        if(x == 1) {
            return Direction.RIGHT;
        }
        if(x == -1) {
            return Direction.LEFT;
        }
        if(y == -1) {
            return Direction.UP;
        }
        if(y == 1) {
            return Direction.DOWN;
        }
        throw new RuntimeException("cant find direction");
    }
}
