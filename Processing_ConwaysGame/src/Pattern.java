public class Pattern {
    public String name;
    public int type;
    public int[] posX;
    public int[] posY;

    public Pattern() {
    }
    public Pattern(String name, int type, int[] posX, int[] posY) {
        this.name = name;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
}
