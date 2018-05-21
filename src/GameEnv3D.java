import Swing3D.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameEnv3D extends JPanel {
    private static int row = 15;
    private static int column = 17;
    private static String[][] mapCells = new String[row][column];
    private static Object3D[][] staticMapCellObjects = new Object3D[row][column];
    private static AnimatedCharacter3D[][] dynamicMapCellObjects = new AnimatedCharacter3D[row][column];
    private static boolean[][] doorOpened = new boolean[row][column];
    private static AnimatedCharacter3D Ehsan;
    private static Vector3 currentDirection = new Vector3(0, 1, 0);
    private static Vector3 neckDirection = new Vector3(0, 1, 0);
    private static Camera camera;
    private static boolean firstPerson = false;
    private static float neckAngel = -0.45f;
    private static BufferedImage skyRight;
    private static BufferedImage skyLeft;
    private static BufferedImage skyUp;
    private static BufferedImage skyDown;
    private static boolean ENABLE_SKYBOX = true;
    private static String[] walkingSoundArray = {
            "Sounds\\walk1.wav",
            "Sounds\\walk2.wav",
            "Sounds\\walk3.wav",
            "Sounds\\walk4.wav",
            "Sounds\\walk5.wav"
    };
    private static int lastWalkSoundPlayed = 0;
    private static boolean isPlayerBusy = false;
    private static JFrame frame;
    private static WindowAdapter panelCloseAdapter;

    public GameEnv3D() {
        initializeSkyBoxImage();
        frame = new JFrame("Map");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Ehsan = new AnimatedCharacter3D(Character3DFactory.createHuman(0.5f)) {
            @Override
            public void render(Graphics2D g2d) {
                defaultRender(g2d);
            }
        };
        Ehsan.translate(new Vector3(0.5f, 0.5f, 0));
//        initializeMap();
        buildMap();
        Vector3 startPoint = new Vector3();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                staticMapCellObjects[i][j] = Object3DFactory.createGround(new Vector2(i, j), 1, 1, Color.GRAY);
                if (mapCells[i][j].equals("Start Point")) {
                    startPoint.x = i;
                    startPoint.y = j;
                    Object3D ground = Object3DFactory.createGround(new Vector2(i, j), 1, 1, Color.GRAY);
                    staticMapCellObjects[i][j] = ground;
                    continue;

                }
                if (mapCells[i][j].equals("Ground")) {
                    Object3D ground = Object3DFactory.createGround(new Vector2(i, j), 1, 1, Color.GRAY);
                    staticMapCellObjects[i][j] = ground;
                }
                if (mapCells[i][j].equals("Wall")) {
                    Object3D wall = Object3DFactory.createNonSymmetricCube(1, 1, 1f, Color.ORANGE);
                    wall = wall.translate(new Vector3(i, j, 0));
                    staticMapCellObjects[i][j] = wall;
                    continue;
                }
                if (mapCells[i][j].equals("Shop")) {
                    AnimatedCharacter3D shop = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.BLUE);
                    shop.translate(new Vector3(i, j, 0));
                    shop.setEnable(true);
                    dynamicMapCellObjects[i][j] = shop;
                }
                if (mapCells[i][j].equals("Battle")) {
                    AnimatedCharacter3D battle = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.RED);
                    battle.translate(new Vector3(i, j, 0));
                    battle.setEnable(true);
                    dynamicMapCellObjects[i][j] = battle;
                }
                if (mapCells[i][j].equals("Power up")) {
                    AnimatedCharacter3D powerUp = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.GREEN);
                    powerUp.translate(new Vector3(i, j, 0));
                    powerUp.setEnable(true);
                    dynamicMapCellObjects[i][j] = powerUp;
                }
                if (mapCells[i][j].contains("Door")) {
                    if (mapCells[i][j].contains("Up") || mapCells[i][j].contains("Down")) {
                        AnimatedCharacter3D animatedDoor = AnimatedCharacter3DFactory.createAnimatedDoor(1, Color.BLACK, Direction.UP);
                        animatedDoor.translate(new Vector3(i, j, 0));
                        dynamicMapCellObjects[i][j] = animatedDoor;
                    }
                    if (mapCells[i][j].contains("Right") || mapCells[i][j].contains("Left")) {
                        AnimatedCharacter3D animatedDoor = AnimatedCharacter3DFactory.createAnimatedDoor(1, Color.BLACK, Direction.RIGHT);
                        animatedDoor.translate(new Vector3(i, j, 0));
                        dynamicMapCellObjects[i][j] = animatedDoor;
                    }
                }
                if (mapCells[i][j].equals("Key")) {
                    AnimatedCharacter3D key = AnimatedCharacter3DFactory.createAnimatedKey(1, Color.BLACK, Color.YELLOW);
                    key.translate(new Vector3(i, j, 0));
                    key.setEnable(true);
                    dynamicMapCellObjects[i][j] = key;

                }
                if (mapCells[i][j].equals("Story")) {
                    AnimatedCharacter3D story = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.MAGENTA);
                    story.translate(new Vector3(i, j, 0));
                    story.setEnable(true);
                    dynamicMapCellObjects[i][j] = story;
                }

            }

        }
        Ehsan.translate(startPoint);
        PointLight pointLight = new PointLight(
                new Vector3(5, 4, 3),
                1.01f,
                Color.WHITE
        );
        Projection.setPointLight(pointLight);
        camera = new Camera(
                new Vector3(6f, -6f, 8f),
                new Vector3(0.1f, 1, 0),
                new Vector3(8, 8, 0),
                1.55f
        );
        if (firstPerson) {
            camera.moveTo(Ehsan.getCharacter3D().getCenter().plus(new Vector3(0, 0, 2)));
            camera.lookAt(camera.getPosition().plus(currentDirection.plus(new Vector3(0, 0, -0.25f))));
        }
        Projection.setCamera(camera);

        Sound.playSound("Sounds\\GameStart.wav");
        frame.add(this);
        frame.setVisible(true);
        initializeWindowAdapter();
        initializeListener();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Projection.getPointLight().setPosition(camera.getPosition());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isPlayerBusy) {
                        repaint();
                    }
                }
            }
        }).start();

    }

    public static void main(String[] args) {
        GameEnv3D gameEnv3D = new GameEnv3D();

    }

    private static void initializeSkyBoxImage() {
        try {
            skyRight = ImageIO.read(new File("Pictures\\SkyBox\\nevada_rt.jpg"));
            skyLeft = ImageIO.read(new File("Pictures\\SkyBox\\nevada_lf.jpg"));
            skyDown = ImageIO.read(new File("Pictures\\SkyBox\\nevada_ft.jpg"));
            skyUp = ImageIO.read(new File("Pictures\\SkyBox\\nevada_bk.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildMap() {
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 17; j++) {
                mapCells[i][j] = "Ground";
            }
        for (int i = 0; i < 15; i++) {
            mapCells[i][0] = "Wall";
            mapCells[i][16] = "Wall";
        }
        for (int i = 0; i < 17; i++) {
            mapCells[14][i] = "Wall";
            mapCells[0][i] = "Wall";
        }
        {
            mapCells[1][1] = "Key";

            mapCells[6][1] = "Battle";
            mapCells[11][1] = "Power up";
            mapCells[12][1] = "Wall";
            mapCells[13][1] = "Battle";
        }
        {
            mapCells[6][2] = "Wall";
            mapCells[12][2] = "Wall";
        }
        {
            mapCells[2][3] = "Down Door";
            mapCells[1][3] = "Wall";
            for (int i = 3; i < 10; i++)
                mapCells[i][3] = "Wall";
            mapCells[12][2] = "Wall";
            mapCells[13][3] = "Story";
        }
        {
            mapCells[4][4] = "Right Door";

            mapCells[6][4] = "Battle";
            mapCells[9][4] = "Right Door";
            mapCells[12][4] = "Wall";
        }
        {
            mapCells[4][5] = "Wall";
            mapCells[5][5] = "Wall";
            mapCells[6][5] = "Wall";
            mapCells[9][5] = "Wall";
            mapCells[12][5] = "Wall";
            mapCells[13][5] = "Up Door";
        }
        {
            mapCells[4][6] = "Wall";
            mapCells[9][6] = "Wall";
        }
        {
            mapCells[4][7] = "Wall";
            mapCells[5][7] = "Key";
            mapCells[9][7] = "Wall";
            mapCells[8][7] = "Story";

        }
        {
            for (int i = 4; i < 10; i++) {
                mapCells[i][8] = "Wall";
            }
        }
        {
            mapCells[2][9] = "Wall";
            mapCells[8][9] = "Power up";
            mapCells[9][9] = "Wall";
            mapCells[10][9] = "Shop";
        }
        {
            mapCells[1][10] = "Story";
            mapCells[2][10] = "Wall";
            mapCells[9][10] = "Wall";
            mapCells[10][10] = "Wall";
            mapCells[11][10] = "Wall";
        }
        {
            mapCells[2][11] = "Wall";
            mapCells[9][11] = "Wall";
            mapCells[11][11] = "Battle";
        }
        {

            mapCells[1][12] = "Battle";
            mapCells[2][12] = "Wall";
            mapCells[3][12] = "Shop";
            mapCells[9][12] = "Wall";
            mapCells[10][12] = "Wall";
            mapCells[11][12] = "Wall";
        }
        {
            mapCells[2][13] = "Wall";
            mapCells[3][13] = "Wall";
            mapCells[4][13] = "Wall";
            mapCells[7][13] = "Wall";
            mapCells[8][13] = "Wall";
            mapCells[9][13] = "Wall";
        }
        {
            mapCells[3][14] = "Shop";
            mapCells[4][14] = "Wall";
            mapCells[9][14] = "Wall";
        }
        {
            mapCells[3][15] = "Power up";
            mapCells[4][15] = "Wall";
            mapCells[9][15] = "Wall";
            mapCells[10][15] = "Key";
            mapCells[13][15] = "Story";
            mapCells[12][3] = "Wall";
        }

        mapCells[8][14] = "Battle";
        mapCells[1][15] = "Start Point";
        mapCells[9][11] = "Right Door";
    }

    private static void initializeWindowAdapter() {
        panelCloseAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            }
        };
    }

    private static void initializeListener() {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Vector3 nextPosition;
                Vector3 currentPosition;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        currentPosition = Ehsan.getCharacter3D().getCenter();
                        if (mapCells[((int) currentPosition.x)][((int) currentPosition.y)].equals("Shop")) {
                            System.out.println("Entering Shop...");
                        }
                        if (mapCells[((int) currentPosition.x)][((int) currentPosition.y)].equals("Story")) {
                            Sound.playSound("Sounds\\TellStory.wav");

                        }
                        if (mapCells[((int) currentPosition.x)][((int) currentPosition.y)].equals("Battle")) {
                            Sound.playSound("Sounds\\EnterBattle.wav");
                        }
                        if (mapCells[((int) currentPosition.x)][((int) currentPosition.y)].equals("PowerUp")) {
                            Sound.playSound("Sounds\\EnterPowerUp.wav");
                        }
                        if (mapCells[((int) currentPosition.x)][((int) currentPosition.y)].equals("Key")) {
                            dynamicMapCellObjects[((int) currentPosition.x)][((int) currentPosition.y)] = null;
                            Sound.playSound("Sounds\\KeyPickUp.wav");
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!isPlayerBusy) {
                            currentDirection = currentDirection.rotateDeg(new Vector3(0, 0, 1), -90);
                            Ehsan.rotateDeg(Ehsan.getCharacter3D().getCenter(), new Vector3(0, 0, 1), -90);
                            currentDirection.normalize();
                            if (firstPerson) {
                                neckDirection = neckDirection.rotateDeg(new Vector3(0, 0, 1), -90);
                                camera.setUpVector(camera.getUpVector().rotateDeg(new Vector3(0, 0, 1), -90));
                                camera.lookAt(camera.getPosition().plus(currentDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!isPlayerBusy) {
                            currentDirection = currentDirection.rotateDeg(new Vector3(0, 0, 1), 90);
                            Ehsan.rotateDeg(Ehsan.getCharacter3D().getCenter(), new Vector3(0, 0, 1), 90);
                            currentDirection.normalize();
                            if (firstPerson) {
                                neckDirection = neckDirection.rotateDeg(new Vector3(0, 0, 1), 90);
                                camera.setUpVector(camera.getUpVector().rotateDeg(new Vector3(0, 0, 1), +90));
                                camera.lookAt(camera.getPosition().plus(currentDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (!isPlayerBusy) {
                            nextPosition = Ehsan.getCharacter3D().getCenter().plus(currentDirection);
                            if (mapCells[((int) nextPosition.x)][((int) nextPosition.y)].contains("Door")) {
                                if (!doorOpened[((int) nextPosition.x)][((int) nextPosition.y)]) {
                                    AnimatedCharacter3D animatedDoor = dynamicMapCellObjects[((int) nextPosition.x)][((int) nextPosition.y)];
                                    Sound.playSound("Sounds\\DoorOpening.wav");
                                    animatedDoor.setEnable(true);
                                    doorOpened[((int) nextPosition.x)][((int) nextPosition.y)] = true;
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_UP:
                        if (!isPlayerBusy) {
                            nextPosition = Ehsan.getCharacter3D().getCenter().plus(currentDirection);
                            if (mapCells[((int) nextPosition.x)][((int) nextPosition.y)].equals("Wall")) {
                                Sound.playSound("Sounds\\Error.wav");
                                break;
                            }
                            if (mapCells[((int) nextPosition.x)][((int) nextPosition.y)].contains("Door")) {
                                if (!doorOpened[((int) nextPosition.x)][((int) nextPosition.y)]) {
                                    AnimatedCharacter3D animatedDoor = dynamicMapCellObjects[((int) nextPosition.x)][((int) nextPosition.y)];
                                    Sound.playSound("Sounds\\DoorOpening.wav");
                                    animatedDoor.setEnable(true);
                                    doorOpened[((int) nextPosition.x)][((int) nextPosition.y)] = true;
                                    break;
                                }
                                Ehsan.translate(currentDirection.mult(2.0f));
                                if (firstPerson) {
                                    camera.setPosition(Ehsan.getCharacter3D().getCenter().plus(new Vector3(0, 0, 2)));
                                }
                            }
                            lastWalkSoundPlayed++;
                            if (lastWalkSoundPlayed == walkingSoundArray.length) {
                                lastWalkSoundPlayed = 0;
                            }
                            Sound.playSound(walkingSoundArray[lastWalkSoundPlayed]);
                            Ehsan.translate(currentDirection);
                            if (firstPerson) {
                                camera.setPosition(Ehsan.getCharacter3D().getCenter().plus(new Vector3(0, 0, 2)));
                            }
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (!isPlayerBusy) {
                            if (firstPerson) {
                                neckAngel = neckAngel + 0.1f;
                                if (neckAngel > 0 && neckAngel <= 0.1f) {
                                    camera.setUpVector(camera.getUpVector().negative());
                                }
                                camera.lookAt(camera.getPosition().plus(currentDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (!isPlayerBusy) {
                            if (firstPerson) {
                                neckAngel = neckAngel - 0.1f;
                                if (neckAngel < 0 && neckAngel >= -0.1f) {
                                    camera.setUpVector(camera.getUpVector().negative());
                                }
                                camera.lookAt(camera.getPosition().plus(currentDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (!isPlayerBusy) {
                            if (firstPerson) {
//                                neckDirection = neckDirection.rotateDeg(new Vector3(0, 0, 1), 5);
//                                camera.setUpVector(camera.getUpVector().rotateDeg(new Vector3(0, 0, 1), 5));
//                                camera.lookAt(camera.getPosition().plus(neckDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (!isPlayerBusy) {
                            if (firstPerson) {
//                                neckDirection = neckDirection.rotateDeg(new Vector3(0, 0, 1), -5);
//                                camera.setUpVector(camera.getUpVector().rotateDeg(new Vector3(0, 0, 1), -5));
//                                camera.lookAt(camera.getPosition().plus(neckDirection.plus(new Vector3(0, 0, neckAngel))));
                            }
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (ENABLE_SKYBOX) {
            if (firstPerson) {
                switch (currentDirection.getDirection()) {
                    case UP:
                        g.drawImage(skyUp, 0, 0, null);
                        break;
                    case RIGHT:
                        g.drawImage(skyRight, 0, 0, null);
                        break;
                    case DOWN:
                        g.drawImage(skyDown, 0, 0, null);
                        break;
                    case LEFT:
                        g.drawImage(skyLeft, 0, 0, null);
                        break;
                }
            } else {
                g.drawImage(skyDown, 0, 0, null);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                staticMapCellObjects[i][j].render(g2d);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (dynamicMapCellObjects[i][j] != null) {
                    dynamicMapCellObjects[i][j].render(g2d);
                }
            }
        }

        Ehsan.render(g2d);
        zBuffer.renderAll(g2d);
    }

}

