import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pattern {
    public String name;
    public int type;
    public int[] posX;
    public int[] posY;
    public PImage icon;

    public Pattern() {
    }
    public Pattern(String name, int type, int[] posX, int[] posY) {
        this.name = name;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    public Pattern(String name, int type, int[] posX, int[] posY, PImage img) {
        this(name, type, posX, posY);
        this.icon = img;
    }
}