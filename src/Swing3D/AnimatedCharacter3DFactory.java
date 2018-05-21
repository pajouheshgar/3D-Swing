package Swing3D;

import java.awt.*;

/**
 * Created by ehsan_PC on 7/14/2016.
 */
public class AnimatedCharacter3DFactory {
    public static AnimatedCharacter3D createAnimatedBuilding(float size, Color color) {
        AnimatedCharacter3D building = new AnimatedCharacter3D(Character3DFactory.createBuilding(size, color)) {
            @Override
            public void render(Graphics2D g2d) {
                rotateDeg(getCharacter3D().getCenter(), new Vector3(0, 0, 1), 5);
                defaultRender(g2d);
            }
        };
        return building;
    }

    public static AnimatedCharacter3D createAnimatedDoor(float size, Color color, Direction direction) {
        Character3D door = Character3DFactory.createDoor(size, color, direction);
        AnimatedCharacter3D animatedDoor = new AnimatedCharacter3D(door) {
            @Override
            public void render(Graphics2D g2d) {
                if (isEnable()) {
                    scale(new Vector3(1, 1, 0.9f));
                }
                defaultRender(g2d);

            }
        };
        return animatedDoor;
    }

    public static AnimatedCharacter3D createAnimatedKey(float size, Color bodyColor, Color headColor) {

        AnimatedCharacter3D key = new AnimatedCharacter3D(Character3DFactory.createKey(size, bodyColor, headColor)) {

            @Override
            public void render(Graphics2D g2d) {

                rotateDeg(getCharacter3D().getCenter(), new Vector3(0, 0, 1), 5);
                defaultRender(g2d);
            }


        };
        return key;
    }

}
