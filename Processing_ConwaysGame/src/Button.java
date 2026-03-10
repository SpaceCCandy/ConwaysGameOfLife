import processing.core.PApplet;

/*
Methods:
- mouseHover(PApplet pa)
- display(PApplet pa)
*/

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
    public Boolean state;

    // Constructors
    public Button() {
        state = false;
    }
    public Button(int wSize, int hSize, int xPos, int yPos) {
        this(); // Calls default constructor
        this.hSize = hSize;
        this.wSize = wSize;
        this.xPos = xPos;
        this.yPos = yPos;
        this.R = this.G = this.B = 250;
    }
    public Button(int wSize, int hSize, int xPos, int yPos, int RGB) {
        this(wSize, hSize, xPos, yPos);
        this.R = this.G = this.B = RGB;
    }
    public Button(int wSize, int hSize, int xPos, int yPos, int R, int G, int B) {
        this(wSize, hSize, xPos, yPos);
        this.R = R;
        this.G = G;
        this.B = B;
    }
    // End of Constructors

    // Returns (True) only when mouse is in the range of the interactive graphic
    public boolean mouseHover(PApplet pa){
        return (pa.mouseX <= xPos+this.wSize/2 && pa.mouseX >= xPos-this.wSize/2) && (pa.mouseY <= yPos+this.hSize/2 && pa.mouseY >= yPos-this.hSize/2);
    }

    // Display Method
    public void display(PApplet pa) {
        // Visual Effect for pressed
        if (mouseHover(pa)) {
            pa.fill(127);
            if (pa.mousePressed && !state) {
            }
            state = pa.mousePressed;
        }
        else {
            pa.fill(255);
        }
        // Display button interactive graphic
        pa.rect(xPos-wSize, yPos-hSize, wSize, hSize);
    }
}