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
    public int lifeSpan;
    public int deathSpan;
    public double TRAIL_DUR = 100.0;

    // Constructors
    public Cell() {
        isAlive = false;
        lifeSpan = 0;
        deathSpan = 0;
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
            deathSpan = 0;

            // Actual color 449E97, saturation scales on how long it's been alive
            R = (int)(68 * ((TRAIL_DUR-lifeSpan)/TRAIL_DUR));
            G = (int)(158 * ((TRAIL_DUR-lifeSpan)/TRAIL_DUR));
            B = (int)(151 * ((TRAIL_DUR-lifeSpan)/TRAIL_DUR));

            // Setting limit
            if (lifeSpan <= TRAIL_DUR) {
                lifeSpan++;
            }
        }
        else {
            // If cell is empty
            lifeSpan = 0;

            // Saturation scales on how long it's been DEAD
            R = 235 + (int)(20 * (deathSpan/(TRAIL_DUR*1.5)));
            G = 215 + (int)(40 * (deathSpan/(TRAIL_DUR*1.5)));
            B = 255;

            // Setting limit
            if (deathSpan <= TRAIL_DUR*1.5) {
                deathSpan++;
            }
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
        pa.rect(xPos-wSize/2, yPos-hSize/2, wSize, hSize, 2);
        pa.stroke(150);
        pa.fill(255);
    }
}