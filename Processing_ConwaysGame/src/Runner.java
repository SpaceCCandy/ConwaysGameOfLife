import processing.core.PApplet;
import processing.core.PImage;

public class Runner extends PApplet{
    public static void main(String[] args) {
        PApplet.main("Runner", args); // Name must match the class name
    }
    public int CELL_SIZE = 50;
    public int time = 0;
    public int speed = 1;
    public int placing = -1; // -1 indicates no placement
    public int genCount;
    public boolean isRunning;

    Grid grid;
    FunctionButton play, reset, random, patternButton;
    Pattern currPattern;
    Pattern P_block, P_loaf, P_beeHive, P_boat, P_blinker, P_beacon, P_toad, P_glider, P_ship;
    PImage Pattern, block;
    Pattern[] P_statics;
    Pattern[] P_flash;
    Pattern[] P_customs;


    public void settings(){
        size(1000, 800);
        pixelDensity(1);
    }

    public void setup(){
        // Buttons
        grid = new Grid(CELL_SIZE, 700, 700, 400, 400);
        play = new FunctionButton("Play", 100, 50, 875, 500);
        reset = new FunctionButton("Reset", 100, 50, 875, 600);
        random = new FunctionButton("Random", 100, 50, 875, 700);
        block = loadImage("Assets/BlockIcon.png");
        patternButton = new FunctionButton(block, 50, 50, 875, 400);

        // Patterns
        P_block = new Pattern("Block", 1, new int[]{0, -1, -1, 0}, new int[]{0, 0, 1, 1});

        P_statics = new Pattern[]{P_block, P_loaf, P_boat}; // 1-5
        P_flash = new Pattern[]{P_blinker, P_beacon, P_toad}; // 6-10
        P_customs = new Pattern[]{P_glider, P_ship}; // 11-15
    }

    public void draw(){
        background(230); // Clears screen

        // Speed control
        if (grid.updateStatus) {
            if (time < 10 - speed) {
                time++;
            } else {
                grid.updateGrid();
                genCount++;
                time = 0;
            }
        }

//        if (placing < 5) {
//            currPattern = P_statics[placing];
//        } else if (placing < 10) {
//            currPattern = P_flash[placing-5];
//        } else if (placing < 20){
//            currPattern = P_customs[placing-10];
//        } else {
//            currPattern = P_statics[1];
//        }

        // Place mode
        if (placing > -1) {
            if (mousePressed & grid.mouseHover(this)) {
                grid.placePattern(this, P_block);
                placing = -1;
            }
        }

        // Button functions
        if (reset.mousePressed) {
            grid.reset();
        }
        if (patternButton.mousePressed) {
            placing = 1;
            System.out.println("placing = 1");
        }
        if (random.mousePressed && !random.clickState) {
            grid.random();
        }
        random.clickState = random.mousePressed;

        if (play.mousePressed && !play.clickState) {
            isRunning = !isRunning;
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

        grid.display(this);
        play.display(this);
        reset.display(this);
        random.display(this);
        patternButton.display(this);

        this.fill(0);
        this.text("Generation: " + genCount, 100, 50);
    }

    public void mousePressed() {
        isRunning = false;
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