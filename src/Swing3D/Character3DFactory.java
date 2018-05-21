package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/13/2016.
 */
public class Character3DFactory {
    public static Character3D createHuman(float size) {
        Character3D human = new Character3D();
        Object3D body = Object3DFactory.createPansSphere(1, 10, Color.MAGENTA);
        body = body.scale(new Vector3(size / 1, size / 2, size));
        body = body.translate(new Vector3(0, 0, size));
        //Head
        Object3D head = Object3DFactory.createSphere(size / 4.0f, Color.BLACK, 20);
        head = head.translate(new Vector3(0, 0, 17.5f * size / 8.0f));
        //Hand
        Object3D rightHand = Object3DFactory.createCube(1, Color.BLACK);
        rightHand = rightHand.scale(new Vector3(size * 1.5f, size / 4.0f, size / 4.0f));
        rightHand = rightHand.rotateDeg(new Vector3(0, 0, 0), new Vector3(0, 1, 0), -45);
        rightHand = rightHand.translate(new Vector3(0.0f * size, 0, 1.3f * size));
        Object3D leftHand = Object3DFactory.createCube(1, Color.BLACK);
        leftHand = leftHand.scale(new Vector3(size * 1.5f, size / 4.0f, size / 4.0f));
        leftHand = leftHand.translate(new Vector3(-1.5f * size, 0, 0));
        leftHand = leftHand.rotateDeg(new Vector3(0, 0, 0), new Vector3(0, 1, 0), 45);
        leftHand = leftHand.translate(new Vector3(0, 0, 1.3f * size));
        //eye
        Object3D rightEye = Object3DFactory.createSphere(size / 16.0f, Color.YELLOW, 40);
        Object3D leftEye = Object3DFactory.createSphere(size / 16.0f, Color.YELLOW, 40);
        rightEye = rightEye.translate(new Vector3(size / 6.0f, size / 5.0f, 17.5f * size / 8.0f));
        leftEye = leftEye.translate(new Vector3(-size / 6.0f, size / 5.0f, 17.5f * size / 8.0f));
        //Leg
        Object3D rightLeg = Object3DFactory.createCube(1, Color.BLACK);
        Object3D leftLeg = Object3DFactory.createCube(1, Color.BLACK);
        rightLeg = rightLeg.scale(new Vector3(size * 1.0f, size / 4.0f, size / 4.0f));
        rightLeg = rightLeg.rotateDeg(new Vector3(0, 0, 0), new Vector3(0, 1, 0), 75);
        rightLeg = rightLeg.translate(new Vector3(0.0f * size, 0, 0.23f * size));
        leftLeg = leftLeg.scale(new Vector3(size * 1.0f, size / 4.0f, size / 4.0f));
        leftLeg = leftLeg.translate(new Vector3(-1.0f * size, 0, 0));
        leftLeg = leftLeg.rotateDeg(new Vector3(0, 0, 0), new Vector3(0, 1, 0), -75);
        leftLeg = leftLeg.translate(new Vector3(0, 0, 0.23f * size));
        //add
        human.addObject3D(body);
        human.addObject3D(rightHand);
        human.addObject3D(leftHand);
        human.addObject3D(head);
        human.addObject3D(rightEye);
        human.addObject3D(leftEye);
        human.addObject3D(rightLeg);
        human.addObject3D(leftLeg);
        leftLeg.setColor(Color.MAGENTA);
        rightLeg.setColor(Color.MAGENTA);
        human.setCenter(new Vector3(0, 0, 0));

        human = human.translate(new Vector3(0, 0, 0.25f));
        human.setCenter(new Vector3(0, 0, 0));
        return human;
    }

    public static Character3D createBuilding(float size, Color color) {
        Character3D building = new Character3D();
        Object3D cube = Object3DFactory.createCube(1, Color.RED);
        cube.setColor(color);
        building.setCenter(new Vector3(0, 0, 0));
        building.addObject3D(cube);
        building = building.rotateRad(new Vector3(0, 0, 0), new Vector3(-1, 1, 0), -(float) Math.acos(1 / Math.sqrt(3)));
        building = building.scale(new Vector3(size, size, size));
        building = building.translate(new Vector3(0.5f, 0.5f, 0));
        return building;
    }

    public static Character3D createDoor(float size, Color color, Direction direction) {
        Character3D door = new Character3D();
        Object3D cube1 = Object3DFactory.createNonSymmetricCube(0.1f, 0.1f, 1, color);
        Object3D cube2 = Object3DFactory.createNonSymmetricCube(0.1f, 0.1f, 1, color);
        Object3D cube3 = Object3DFactory.createNonSymmetricCube(0.1f, 0.1f, 1, color);
        Object3D cube4 = Object3DFactory.createNonSymmetricCube(0.1f, 0.1f, 1, color);
        Object3D cube5 = Object3DFactory.createNonSymmetricCube(0.1f, 0.1f, 1, color);
        cube1 = cube1.translate(new Vector3(0.05f,  0.45f, 0));
        cube2 = cube2.translate(new Vector3(1 * 0.2f + 0.05f, 0.45f, 0));
        cube3 = cube3.translate(new Vector3(2 * 0.2f + 0.05f, 0.45f, 0));
        cube4 = cube4.translate(new Vector3(3 * 0.2f + 0.05f, 0.45f, 0));
        cube5 = cube5.translate(new Vector3(4 * 0.2f + 0.05f, 0.45f, 0));
        door.setCenter(new Vector3(0.5f, 0.5f, 0));
        door.addObject3D(cube1);
        door.addObject3D(cube2);
        door.addObject3D(cube3);
        door.addObject3D(cube4);
        door.addObject3D(cube5);
        door.setColor(color);
        door = door.scale(new Vector3(size, size, size));
        switch (direction) {
            case UP:

                break;
            case DOWN:
                break;

            case RIGHT:
                door = door.rotateDeg(door.getCenter(), new Vector3(0, 0, 1), 90);
                break;
            case LEFT:
                door = door.rotateDeg(door.getCenter(), new Vector3(0, 0, 1), -90);
                break;
        }
        return door;
    }

    public static Character3D createKey(float size, Color bodyColor, Color headColor) {
        Character3D key = new Character3D();
        Object3D body = Object3DFactory.createNonSymmetricCube(0.2f, 0.2f, 0.8f, bodyColor);
        Object3D head = Object3DFactory.createSphere(0.2f, headColor, 10);
        head = head.translate(new Vector3(0.1f, 0.1f, 1.0f));
        key.addObject3D(body);
        key.addObject3D(head);
        key.setCenter(new Vector3(0, 0, 0));
        key = key.rotateDeg(new Vector3(0, 0, 0), new Vector3(0, 1, 0), 15);
        key = key.translate(new Vector3(0.5f, 0.5f, 0));
        key = key.scale(new Vector3(size, size, size));
        return key;
    }

}
