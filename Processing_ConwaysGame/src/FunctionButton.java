import processing.core.PApplet;
import processing.core.PImage;

public class FunctionButton extends Button {
    public String text;
    public PImage img;
    public int TEXT_SIZE = 20;
    public boolean mousePressed;
    public boolean clickState;

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
                mousePressed = true;
                pa.fill(R-10, G-10, B-10);
            }
            state = pa.mousePressed;
        }
        else {
            mousePressed = false;
            pa.fill(R, G, B);
        }

        // Display button interactive graphic
        pa.rect(xPos-wSize/2, yPos-hSize/2, wSize, hSize, 10);
        pa.fill(0);
        if (text != null) {
            pa.text(text, xPos-(TEXT_SIZE/4 *text.length()) + 1, yPos+TEXT_SIZE/4);
            pa.textSize(20);
        }
        else {
            pa.image(img, xPos-wSize/2, yPos-hSize/2, 50, 50);
        }
    }

}