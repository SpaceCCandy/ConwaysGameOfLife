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
    // Variables & declarations
    static public int CELL_SIZE = 20;
    static public int GRID_SIZE = 700;
    public int time = millis();
    public int speedDelay = 2;
    public int placing = 0; // -1 indicates no placement
    public int genCount;
    public boolean isRunning;
    boolean placeMode;
    static Pattern currPattern;

    Grid grid;
    FunctionButton play, reset, random, patternBtn, leftArrowBtn, rightArrowBtn, upSpeed, downSpeed, rotationBtn, saveBtn;
    Pattern P_block, P_loaf, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship, P_save1;
    PImage block, loaf, boat, blinker, beacon, toad, glider, ship, saveMIcon;
    Pattern[] P_Patterns;

    File savesInfo;


    public void settings() {
        size(1100, 800);
        pixelDensity(1);
    }


    public void setup() {
        // Buttons
        grid = new Grid(CELL_SIZE, GRID_SIZE, GRID_SIZE, 400, 400);
        play = new FunctionButton("Play", 250, 50, 925, 360);
        reset = new FunctionButton("Reset", 100, 50, 850, 420);
        random = new FunctionButton("Random", 100, 50, 1000, 420);
        patternBtn = new FunctionButton(block, 150, 150, 925, 180);
        leftArrowBtn = new FunctionButton("<", 50, 80, patternBtn.xPos-125, patternBtn.yPos, 67, 190, 255);
        rightArrowBtn = new FunctionButton(">", 50, 80, patternBtn.xPos+125, patternBtn.yPos, 67, 190, 255);
        rotationBtn = new FunctionButton("Rotate", 80, 30, patternBtn.xPos, patternBtn.yPos-100, 67, 190, 255);
        saveBtn = new FunctionButton("Save", 80, 30, patternBtn.xPos, patternBtn.yPos+125,67, 190, 255);
        upSpeed = new FunctionButton("<", 50, 40, 830, 480, 67, 190, 255);
        downSpeed = new FunctionButton(">", 50, 40, 1020, 480, 67, 190, 255);

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

        // Get the save from perious sessions
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
            if (placing == 8) {
                loadSaveFile(P_save1);
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
            if (placing == 8) {
                loadSaveFile(P_save1);
            }
        }
        rightArrowBtn.clickState = mousePressed;

        // Rotates patterns
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

        // Saves current grid states
        if (saveBtn.mouseHover(this) && mousePressed && !saveBtn.clickState) {
            savesFile();
        }

        // Speed
        if (upSpeed.mouseHover(this) && mousePressed && !upSpeed.clickState) {
            if (speedDelay > 0) {
                speedDelay--;
            }
        }
        upSpeed.clickState = mousePressed;

        if (downSpeed.mouseHover(this) && mousePressed && !downSpeed.clickState) {
            speedDelay++;
        }
        downSpeed.clickState = mousePressed;

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
        if (currPattern == P_save1) {
            saveBtn.display(this);
        }

        // Text Displays
        textAlign(CENTER, CENTER);
        this.fill(0);
        this.text("Generation: " + genCount, 120, 30);
        this.text(speedDelay + " s", 925, 480);
        this.text(currPattern.name, patternBtn.xPos, patternBtn.yPos+90);

        textAlign(LEFT, CENTER);
        this.text("Gen: " + genCount +
                "\nCells alive: " + grid.cellCount +
                "\nSpeed Delay: " + speedDelay * 1000 + " ms" +
                "\n\nHotkeys:" +
                "\n\tStart/stop - Space" +
                "\n\tReset - r" +
                "\n\tRandom - q" +
                "\n\tQuick place - e", 780, 640);
        textAlign(CENTER, CENTER);
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
            placeMode = true;
        }
        if (key == 'Q' || key == 'q') {
            grid.random();
        }
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

    // Saves the current grid into savesInfo
    public void savesFile() {
        StringBuilder data1 = new StringBuilder();

        // Reads grid to create data to transfer into savesInfo
        for (int i = 0; i < grid.cellArray.length; i++) {
            for (int j = 0; j < grid.cellArray.length; j++) {
                if (grid.cellArray[j][i].isAlive) {
                    data1.append("X");
                }
                else {
                    data1.append(".");
                }
            }
            data1.append("\n");
        }

        // Write to file using FileWriter
        try {
            FileWriter myWriter = new FileWriter("src/Assets/SavesInfo");
            myWriter.write(data1.toString());
            myWriter.close();  // must close manually
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}