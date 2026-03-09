import processing.core.PApplet;

/*
Interactive cell class(button)

Methods:
- mouseHover(PApplet pa)
- determineNextState(int surrPop)
- updateState(PApplet pa)
- display(PApplet pa)
*/

public class Cell extends Button {
    // Attributes
    public boolean isAlive;
    public boolean nextCellState;
    public boolean cellHighlight;

    // Constructors
    public Cell() {
        isAlive = false;
    }
    public Cell(int wSize, int hSize, int xPos, int yPos) {
        super(hSize, wSize, xPos, yPos);
        this.R = this.G = this.B = 250;
    }
    public Cell(int wSize, int hSize, int xPos, int yPos, int RGB) {
        this(wSize, hSize, xPos, yPos);
        this.R = this.G = this.B = RGB;
    }
    public Cell(int wSize, int hSize, int xPos, int yPos, int R, int G, int B) {
        this(wSize, hSize, xPos, yPos);
        this.R = R;
        this.G = G;
        this.B = B;
    }
    // End of Constructors

    // Determines the next state cell state given the surrounding population
    public void determineNextState(int surrPop) {
        boolean nextState;

        if (surrPop < 2) {
            // Fewer then 2 dies
            nextState = false;
        } else if (surrPop == 3 && !isAlive) {
            // exactly 3 becomes alive
            nextState = true;
        } else if (surrPop <= 3 && isAlive) {
            // If there is 2 or 3, and they are alive, it stays alive
            nextState = true;
        } else {
            // 3 or more dies
            nextState = false;
        }
        nextCellState = nextState;
    }

    // Updates color based on state
    public void updateState(PApplet pa) {
        // Updates the visuals of the cell given cell state
        if (isAlive) {
            // If cell is alive
            R = G = B = 0;
        }
        else {
            // If cell is empty
            R = G = B = 255;
        }
    }

    // Display Method
    public void display(PApplet pa) {
        // Visual Effect for pressed
        if (mouseHover(pa)) {
            pa.fill(127);
            if (pa.mousePressed && !state) {
                // Invert cell state when pressed
                isAlive = !isAlive;
            }
            state = pa.mousePressed;
        } else {
            updateState(pa);
            pa.fill(R, G, B);
        }

        // Display button interactive graphic
        pa.rect(xPos-wSize/2, yPos-hSize/2, wSize, hSize);
        pa.fill(255);
    }
}