import processing.core.PApplet;
import processing.core.PImage;

/*
Interactive buttons with either text, or image displays
*/

public class FunctionButton extends Button {
    // Attributes
    public String text;
    public PImage img;
    public int TEXT_SIZE = 20;
    // Misc
    public boolean mousePressed;
    public boolean clickState;
    public float rotateImg;

    // Constructors
    public FunctionButton() {
        super();
        clickState = false;
    }
    public FunctionButton(String text, int wSize, int hSize, int xPos, int yPos) {
        super(wSize, hSize, xPos, yPos);
        this.text = text;
    }
    public FunctionButton(String text, int wSize, int hSize, int xPos, int yPos, int RGB) {
        super(wSize, hSize, xPos, yPos, RGB);
        this.text = text;
    }
    public FunctionButton(String text, int wSize, int hSize, int xPos, int yPos, int R, int G, int B) {
        super(wSize, hSize, xPos, yPos, R, G, B);
        this.text = text;
    }
    public FunctionButton(PImage img, int wSize, int hSize, int xPos, int yPos) {
        super(wSize, hSize, xPos, yPos);
        this.img = img;
    }
    // End of Constructors

    public void display(PApplet pa) {
        // Visual Effect for pressed
        if (mouseHover(pa)) {
            pa.fill(127);
            if (pa.mousePressed && !state) {
                this.mousePressed = true;
                pa.fill(R-10, G-10, B-10);
            }
            state = pa.mousePressed;
        }
        else {
            this.mousePressed = false;
            pa.fill(R, G, B);
        }

        // Display button interactive graphic
        pa.rect(xPos-wSize/2, yPos-hSize/2, wSize, hSize, 5);
        pa.fill(0);

        // If there is text, display text
        if (text != null) {
            pa.text(text, xPos, yPos);
            pa.textSize(20);
        }
        // If not, display image
        else {
            pa.pushMatrix();
            pa.translate(xPos, yPos);
            pa.rotate(rotateImg); // Rotation state
            pa.image(img, -wSize/2, -hSize/2, wSize, hSize);
            pa.popMatrix();
        }
    }
}