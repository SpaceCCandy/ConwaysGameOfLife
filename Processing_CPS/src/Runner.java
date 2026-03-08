import processing.core.PApplet;
import processing.core.PConstants;

public class Runner extends PApplet {
    // Variable and objects
    Button Button = new Button(200, 100, 300, 200, 204, 220, 220);

    boolean lastButtonState;
    int activityTimer;

    public static void main(String[] args) {
        PApplet.main("Runner", args); // Name must match the class name
    }


    public void settings() {
        size(600, 400);
    }


    public void setup() {
        Button.startTime = millis();
        activityTimer = millis();
    }


    public void draw() {
        background(230);
        Button.display(this);

        // Counter increase conditions
        if (Button.mouseHover(this) && Button.Pressed(this) && Button.Pressed(this) != lastButtonState) {
            Button.clickCounter++;
            activityTimer = millis();

        }
        lastButtonState = Button.Pressed(this);

        // Resets button Data
        // Only when R/r is pressed or 5 seconds of inactivity
        if ((keyPressed && key == 'r' || key == 'R') || ((double) (millis() - activityTimer) /1000 >= 5.0)) {
            Button.startTime = millis();
            activityTimer = 0;
            Button.clickCounter = 0;

            // Display message
            fill(255, 0, 0);
            text("> Your score has been reset.", Button.xPos, Button.yPos - 60);
            fill(230);
        }

        fill(0); // Set text to black
        textAlign(PConstants.CENTER); // Center text

        // Info display
        text("Clicks: " + Button.clickCounter, Button.xPos, Button.yPos);
        text("CPS: " + Math.round(Button.clickCounter / ((millis()- Button.startTime) / 1000.0) * 100) / 100.0, Button.xPos, Button.yPos - 20);
    }
}
