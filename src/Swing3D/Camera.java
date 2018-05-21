package Swing3D;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Camera {
    private Vector3 position;
    private Vector3 upVector;
    private Vector3 lookingPoint;
    private Vector3 nHat;
    private Vector3 uHat;
    private Vector3 vHat;
    private float fov;

    public Camera(Vector3 position, Vector3 upVector, Vector3 lookingPoint, float fov) {
        this.fov = fov;
        this.position = position;
        this.upVector = upVector;
        this.lookingPoint = lookingPoint;
        nHat = (lookingPoint.sub(position)).normal();
        vHat = (nHat.cross(upVector.cross(nHat))).normal();
        uHat = (nHat.cross(vHat)).normal();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getnHat() {
        return nHat;
    }

    public void setnHat(Vector3 nHat) {
        this.nHat = nHat.normal();
    }

    public Vector3 getuHat() {
        return uHat;
    }

    public void setuHat(Vector3 uHat) {
        this.uHat = uHat.normal();
    }

    public Vector3 getvHat() {
        return vHat.normal();
    }

    public void setvHat(Vector3 vHat) {
        this.vHat = vHat;
    }

    public Vector3 getLookingPoint() {
        return lookingPoint;
    }

    public void setLookingPoint(Vector3 lookingPoint) {
        this.lookingPoint = lookingPoint;
    }

    public Vector3 getUpVector() {
        return upVector;
    }

    public void setUpVector(Vector3 upVector) {
        this.upVector = upVector;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void lookAt(Vector3 lookingPoint) {
        this.lookingPoint = lookingPoint;
        nHat = (lookingPoint.sub(position)).normal();
        vHat = (nHat.cross(upVector.cross(nHat))).normal();
        uHat = (nHat.cross(vHat)).normal();
    }

    public void moveTo(Vector3 newPosition) {
        this.position = newPosition;
        nHat = (lookingPoint.sub(position)).normal();
        vHat = (nHat.cross(upVector.cross(nHat))).normal();
        uHat = (nHat.cross(vHat)).normal();
    }
}
