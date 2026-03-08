import processing.core.PApplet;
import processing.core.PConstants;

public class Button {
    public int wSize;
    public int hSize;
    public int xPos;
    public int yPos;
    // Colors
    public int R;
    public int G;
    public int B;
    // Misc
    public int clickCounter;
    public int startTime = -1;

    // Constructors
    public Button() {
        clickCounter = 0;
    }

    public Button(int wSize, int hSize, int xPos, int yPos) {
        this(); // Calls default constructor
        this.hSize = hSize;
        this.wSize = wSize;
        this.xPos = xPos;
        this.yPos = yPos;
        this.R = this.G = this.B = 250;
    }

    public Button(int wSize, int hSize, int xPos, int yPos, int R, int G, int B) {
        this(); // Calls default constructor
        this.hSize = hSize;
        this.wSize = wSize;
        this.xPos = xPos;
        this.yPos = yPos;
        this.R = R;
        this.G = G;
        this.B = B;
    }
    // End of Constructors

    public boolean mouseHover(PApplet pa){
        // Returns (True) only when mouse is in the range of the interactive graphic
        return (pa.mouseX <= xPos+this.wSize/2 && pa.mouseX >= xPos-this.wSize/2) && (pa.mouseY <= yPos+this.hSize/2 && pa.mouseY >= yPos-this.hSize/2);
    }

    public boolean Pressed(PApplet pa) {
        // Returns (True) When pressed
        return pa.mousePressed;
    }

    // Display Method
    public void display(PApplet pa) {

        // Visual Effect for pressed
        if (mouseHover(pa)) {
            pa.fill(R+10, G+10, B+10);
            if (pa.mousePressed) { // When pressed
                pa.fill(R-10, G-10, B-10);
            }
        }
        else {
            pa.fill(R, G, B);
        }

        // Display button interactive graphic
        pa.rect(xPos-wSize/2, yPos-hSize/2, wSize, hSize, 20);
    }
}