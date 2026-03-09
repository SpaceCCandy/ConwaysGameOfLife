import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;

public class Runner extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Runner", args); // Name must match the class name
    }
    public int CELL_SIZE = 50;
    public int time = millis();
    public int speedDelay = 1;
    public int placing = 0; // -1 indicates no placement
    public int genCount;
    public boolean isRunning;
    File SavesInfo;

    Grid grid;
    FunctionButton play, reset, random, patternBtn, leftArrowBtn, rightArrowBtn, upSpeed, downSpeed;
    static Pattern currPattern;
    boolean placeMode;
    Pattern P_block, P_loaf, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship, P_save1, P_save2, P_save3;
    PImage block, loaf, boat, blinker, beacon, toad, glider, ship, saveMIcon;
    Pattern[] P_Patterns;

    public void settings(){
        size(1100, 800);
        pixelDensity(1);
    }

    public void setup(){
        // Buttons
        grid = new Grid(CELL_SIZE, 700, 700, 400, 400);
        play = new FunctionButton("Play", 100, 50, 925, 500);
        reset = new FunctionButton("Reset", 100, 50, 925, 600);
        random = new FunctionButton("Random", 100, 50, 925, 700);
        block = loadImage("Assets/P_BlockIcon.png");
        loaf = loadImage("Assets/P_LoafIcon.png");
        boat = loadImage("Assets/P_BoatIcon.png");
        blinker = loadImage("Assets/P_BlinkerIcon.png");
        beacon = loadImage("Assets/P_BeaconIcon.png");
        toad = loadImage("Assets/P_ToadIcon.png");
        glider = loadImage("Assets/P_GliderIcon.png");
        ship = loadImage("Assets/P_ShipIcon.png");
        saveMIcon = loadImage("Assets/P_GliderIcon.png");
        patternBtn = new FunctionButton(block, 150, 150, 925, 300);
        leftArrowBtn = new FunctionButton("<", 50, 50, 800, 300, 67, 190, 255);
        rightArrowBtn = new FunctionButton(">", 50, 50, 1050, 300, 67, 190, 255);
        upSpeed = new FunctionButton("<", 50, 50, 850, 150, 67, 190, 255);
        downSpeed = new FunctionButton(">", 50, 50, 1000, 150, 67, 190, 255);

        // Patterns
        placeMode = false;
        SavesInfo = new File("filename.txt");

        P_block = new Pattern("Block", 0, new int[]{0, 1, 1, 0}, new int[]{0, 0, 1, 1}, block); // 0
        P_loaf = new Pattern("loaf", 0, new int[]{0, 2, 0, 3, 1, 2, 1}, new int[]{0, 0, 1, 1, 2, 2, -1}, loaf); // 1
        P_boat = new Pattern("Boat", 0, new int[]{-1, -2, -2, -1, 0}, new int[]{ 0,  1,  0,  2, 1}, boat); //2

        P_blinker = new Pattern("Blinker", 1, new int[]{0, 0, 0}, new int[]{0, 1, -1}, blinker); // 5
        P_beacon = new Pattern("Beacon", 1, new int[]{0, -1, -1, 0, -2, -3, -3, -2}, new int[]{0,  0,  1, 1,  2,  2,  3,  3}, beacon); // 6
        P_toad = new Pattern("Toad", 1, new int[]{-1, -2, -3, 0, -1, -2}, new int[]{ 0,  0,  0, 1,  1,  1}, toad);// 7

        P_glider = new Pattern("Glider", 2, new int[]{0, -1, -2, 0, -1}, new int[]{0,  0,  0, 1,  2}, glider); // 10
        P_ship = new Pattern("Ship", 2, new int[]{0, -1, -2, -3, -4, -4, 0, 0, -1}, new int[]{0,  0,  0,  0,  -1,  -3, -1, -2,  -3}, ship); //11
        P_save1 = new Pattern("Save 1", 2, new int[100], new int[100], saveMIcon);


        P_Patterns = new Pattern[]{P_block, P_loaf, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship};
        currPattern = P_block;
    }

    public void draw(){
        background(230); // Clears screen

        // Speed control
        if (grid.updateStatus && !mousePressed) {
            if (millis()-time > speedDelay*100) {
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

        // Button functions
        if (reset.mousePressed) {
            grid.reset();
        }
        if (random.mousePressed && !random.clickState) {
            grid.random();
        }
        random.clickState = random.mousePressed;

        // Patterns
        if (patternBtn.mousePressed) {
            placeMode = true;
        }
        if (leftArrowBtn.mousePressed && !leftArrowBtn.clickState) {
            if (placing > 0) {
                placing--;
            }
            else {
                placing = P_Patterns.length-1;
            }
        }
        leftArrowBtn.clickState = leftArrowBtn.mousePressed;
        if (rightArrowBtn.mousePressed && !rightArrowBtn.clickState) {
            if (placing < P_Patterns.length-1) {
                placing++;
            }
            else {
                placing = 0;
            }
        }
        rightArrowBtn.clickState = rightArrowBtn.mousePressed;

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
        if (play.mousePressed && !play.clickState) {
            isRunning = !isRunning;
            System.out.println(isRunning);
        }
        play.clickState = play.mousePressed;
        grid.updateStatus = isRunning;

        if (isRunning) {
            play.G = 90;
            play.B = 84;
            play.text = "Stop";
        } else {
            play.G = 255;
            play.B = 255;
            play.text = "Play";
        }
        // End of button functions

        currPattern = P_Patterns[placing];
        patternBtn.img = currPattern.icon;

        grid.display(this);
        play.display(this);
        reset.display(this);
        random.display(this);
        patternBtn.display(this);
        leftArrowBtn.display(this);
        rightArrowBtn.display(this);
        upSpeed.display(this);
        downSpeed.display(this);

        this.fill(0);
        this.text("Generation: " + genCount, 100, 50);
        this.text(currPattern.name, 900, 400);
        this.text(speedDelay*100 + " ms", 900, 150);
        this.textAlign(900);
    }

    public void keyPressed() {
        // When space pressed
        if (key == ' ') {
            isRunning = !isRunning;
        }
        if (key == 'R' || key == 'r') {
            grid.reset();
        }
    }
}