import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner extends PApplet {
    public static void main(String[] args) {
        PApplet.main("Runner", args); // Name must match the class name
    }

    public int CELL_SIZE = 20;
    public int time = millis();
    public int speedDelay = 2;
    public int placing = 0; // -1 indicates no placement
    public int genCount;
    public boolean isRunning;
    boolean placeMode;
    static Pattern currPattern;

    Grid grid;
    FunctionButton play, reset, random, patternBtn, leftArrowBtn, rightArrowBtn, upSpeed, downSpeed, rotationBtn;
    Pattern P_block, P_loaf, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship, P_save1, P_save2, P_save3;
    PImage block, loaf, boat, blinker, beacon, toad, glider, ship, saveMIcon;
    Pattern[] P_Patterns;

    File savesInfo;
    Scanner myReader;


    public void settings() {
        size(1100, 800);
        pixelDensity(1);
    }


    public void setup() {
        // Buttons
        grid = new Grid(CELL_SIZE, 700, 700, 400, 400);
        play = new FunctionButton("Play", 100, 50, 925, 500);
        reset = new FunctionButton("Reset", 100, 50, 925, 600);
        random = new FunctionButton("Random", 100, 50, 925, 700);
        patternBtn = new FunctionButton(block, 150, 150, 925, 300);
        leftArrowBtn = new FunctionButton("<", 50, 80, 800, 300, 67, 190, 255);
        rightArrowBtn = new FunctionButton(">", 50, 80, 1050, 300, 67, 190, 255);
        upSpeed = new FunctionButton("<", 50, 50, 850, 150, 67, 190, 255);
        downSpeed = new FunctionButton(">", 50, 50, 1000, 150, 67, 190, 255);
        rotationBtn = new FunctionButton("Rotate", 80, 30, 925, 200, 67, 190, 255);

        // Images
        block = loadImage("Assets/P_BlockIcon.png");
        loaf = loadImage("Assets/P_LoafIcon.png");
        boat = loadImage("Assets/P_BoatIcon.png");
        blinker = loadImage("Assets/P_BlinkerIcon.png");
        beacon = loadImage("Assets/P_BeaconIcon.png");
        toad = loadImage("Assets/P_ToadIcon.png");
        glider = loadImage("Assets/P_GliderIcon.png");
        ship = loadImage("Assets/P_ShipIcon.png");
        saveMIcon = loadImage("Assets/P_SaveMIcon.png");

        // Patterns
        placeMode = false;
        savesInfo = new File("src/Assets/SavesInfo");

        P_block = new Pattern("Block", 0, new int[]{0, 1, 1, 0}, new int[]{0, 0, 1, 1}, block); // 0
        P_loaf = new Pattern("loaf", 0, new int[]{0, 2, 0, 3, 1, 2, 1}, new int[]{0, 0, 1, 1, 2, 2, -1}, loaf); // 1
        P_boat = new Pattern("Boat", 0, new int[]{-1, -2, -2, -1, 0}, new int[]{0, 1, 0, 2, 1}, boat); //2

        P_blinker = new Pattern("Blinker", 1, new int[]{0, 0, 0}, new int[]{0, 1, -1}, blinker); // 5
        P_beacon = new Pattern("Beacon", 1, new int[]{0, -1, -1, 0, -2, -3, -3, -2}, new int[]{0, 0, 1, 1, 2, 2, 3, 3}, beacon); // 6
        P_toad = new Pattern("Toad", 1, new int[]{-1, -2, -3, 0, -1, -2}, new int[]{0, 0, 0, 1, 1, 1}, toad);// 7

        P_glider = new Pattern("Glider", 2, new int[]{0, 0, 0, -1, -2}, new int[]{0, -1, -2, 0, -1}, glider); // 10
        P_ship = new Pattern("Ship", 2, new int[]{0, 0, 0, 0, -1, -3, -1, -2, -3}, new int[]{0, -1, -2, -3, -4, -4, 0, 0, -1}, ship); //11
        P_save1 = new Pattern("Save 1", 2, new int[100], new int[100], saveMIcon);


        P_Patterns = new Pattern[]{P_block, P_loaf, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship, P_save1};
        currPattern = P_block;

        loadSaveFile(P_save1);
    }

    public void draw() {
        background(230); // Clears screen

        // Speed control
        if (grid.updateStatus && !(mousePressed && grid.mouseHover(this))) {
            if (millis() - time > speedDelay * 100) {
                grid.updateGrid();
                genCount++;
                time = millis();
            }
        }

        // Place mode
        if (placeMode) {
            if (mousePressed & grid.mouseHover(this)) {
                grid.placePattern(this, currPattern);
                placeMode = false;
            }
        }

        // Button functions --------------------------

        // Resets Grid
        if (reset.mousePressed) {
            grid.reset();
        }

        // Applies Random config on grid
        if (random.mouseHover(this) && mousePressed && !random.clickState) {
            grid.random();
        }
        random.clickState = mousePressed;

        // Patterns
        if (patternBtn.mousePressed) {
            placeMode = true;
        }

        // Selector L & R for patterns
        if (leftArrowBtn.mouseHover(this) && mousePressed && !leftArrowBtn.clickState) {
            patternBtn.rotateImg = 0;
            if (placing > 0) {
                placing--;
            } else {
                placing = P_Patterns.length - 1;
            }
        }
        leftArrowBtn.clickState = mousePressed;
        if (rightArrowBtn.mouseHover(this) && mousePressed && !rightArrowBtn.clickState) {
            patternBtn.rotateImg = 0;
            if (placing < P_Patterns.length - 1) {
                placing++;
            } else {
                placing = 0;
            }
        }
        rightArrowBtn.clickState = mousePressed;

        if (rotationBtn.mouseHover(this) && mousePressed && !rotationBtn.clickState) {
            patternBtn.rotateImg = patternBtn.rotateImg + (float)(Math.PI/2);

            int[] tempX = currPattern.posX;
            int[] tempY = currPattern.posY;

            currPattern.posX = new int[tempX.length];
            currPattern.posY = new int[tempY.length];

            for (int i = 0; i < tempX.length; i++) {
                currPattern.posX[i] = tempY[i];
                currPattern.posY[i] = -tempX[i];
            }
        }
        rotationBtn.clickState = mousePressed;

        // Speed
        if (upSpeed.mousePressed && !upSpeed.clickState) {
            if (speedDelay > 0) {
                speedDelay--;
            }
        }
        upSpeed.clickState = upSpeed.mousePressed;
        if (downSpeed.mousePressed && !downSpeed.clickState) {
            speedDelay++;
        }
        downSpeed.clickState = downSpeed.mousePressed;

        // Play
        if (play.mouseHover(this) && mousePressed && !play.clickState) {
            isRunning = !isRunning;
        }
        play.clickState = mousePressed;
        grid.updateStatus = isRunning;

        // Play Button color states
        if (isRunning) {
            play.G = 90;
            play.B = 84;
            play.text = "Stop";
        } else {
            play.G = 255;
            play.B = 255;
            play.text = "Play";
        }

        // End of button functions -------------------

        currPattern = P_Patterns[placing];
        patternBtn.img = currPattern.icon;

        // All window displays
        grid.display(this);
        play.display(this);
        reset.display(this);
        random.display(this);
        patternBtn.display(this);
        leftArrowBtn.display(this);
        rightArrowBtn.display(this);
        upSpeed.display(this);
        downSpeed.display(this);
        rotationBtn.display(this);

        // Text Displays
        this.fill(0);
        this.text("Generation: " + genCount, 100, 50);
        this.text(currPattern.name, 900, 400);
        this.text(speedDelay * 100 + " ms", 900, 150);
        this.textAlign(900);
    }

    // KBinput triggers
    public void keyPressed () {
        // When space pressed
        if (key == ' ') {
            isRunning = !isRunning;
        }
        if (key == 'R' || key == 'r') {
            grid.reset();
        }
        if (key == 'E' || key == 'e') {
        }
    }

    public void mousePressed () {
    }

    // Loads pattern from SavesInfo text file
    public void loadSaveFile(Pattern p) {
        // File scanner (Saves into data)
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        int currRow = 0;
        String temp;

        try (Scanner myReader = new Scanner(savesInfo)) {
            while (myReader.hasNextLine()) {
                temp = myReader.nextLine();
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == 'X') {
                        x.add(currRow);
                        y.add(i);
                    }
                }
                currRow++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int[] posX = x.stream().mapToInt(i -> i).toArray();
        int[] posY = y.stream().mapToInt(i -> i).toArray();

        p.posX = posX;
        p.posY = posY;
    }
}