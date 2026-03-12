import processing.core.PApplet;

/*
Methods:
- surrPop(int x, int y)
- updateGrid()
- initializeGrid()
- mouseHover()
- reset()
- random()
- display(PApplet pa)
- placePattern()
*/

public class Grid {
    // Attributes
    public Cell[][] cellArray;
    public int gridHeight;
    public int gridWidth;
    public int cellSize;
    public int xPos;
    public int yPos;
    public int cellSelectX;
    public int cellSelectY;
    // Misc
    public boolean updateStatus;
    public int cellCount;

    // Constructors
    public Grid() {
        updateStatus = false;

        // Creates array of cells
        cellArray = new Cell[gridWidth/cellSize][gridHeight/cellSize];
        for(int i = 0; i < cellArray.length; i++) {
            for (int j = 0; j < cellArray[i].length; j++) {
                // Defines each cell with a position
                cellArray[i][j] = new Cell(cellSize, cellSize, i * cellSize + cellSize / 2 + (xPos - gridWidth/2), j * cellSize + cellSize / 2 + (yPos - gridHeight/2), 255);
            }
        }
    }
    public Grid(int cellSize, int windX, int windY, int xPos, int yPos) {
        this.cellSize = cellSize;
        this.gridHeight = windY;
        this.gridWidth = windX;
        this.xPos = xPos;
        this.yPos = yPos;
        this();
    }
    // End of constructors

    // Counts the surrounding population of cell
    public int surrPop(int x, int y) {
        int count = 0;
        int targetRow, targetCol;
        // Surrounding pattern
        int[] rowPos = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colPos = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            targetRow = y + rowPos[i];
            targetCol = x + colPos[i];

            if (targetRow < 0) {
                targetRow = cellArray.length - 1;
            }
            else if (targetRow > cellArray.length - 1) {
                targetRow = 0;
            }
            if (targetCol < 0) {
                targetCol = cellArray[0].length - 1;
            }
            else if (targetCol > cellArray[0].length - 1) {
                targetCol = 0;
            }

            if (cellArray[targetRow][targetCol].isAlive) {count++;}
        }
        return count;
    }

    // Updates the grid given the next cellState
    public void updateGrid() {
        initializeGrid();

        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                cell.isAlive = cell.nextCellState;
            }
        }
    }

    // Load the next cell state base on the cell conditions right now
    public void initializeGrid() {
        for (int row = 0; row < cellArray.length; row++) {
            for (int col = 0; col < cellArray[row].length; col++) {
                cellArray[row][col].determineNextState(surrPop(col, row));
            }
        }
    }

    // Checks if the mouse is over the grid
    public boolean mouseHover(PApplet pa){
        // Returns (True) only when mouse is in the range of the interactive graphic
        return (pa.mouseX <= xPos+this.gridWidth/2 && pa.mouseX >= xPos-this.gridWidth/2) && (pa.mouseY <= yPos+this.gridHeight/2 && pa.mouseY >= yPos-this.gridHeight/2);
    }

    // Sets every cell to a random state
    public void random() {
        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                cell.isAlive = Math.random() >= 0.5;
            }
        }
    }

    // Resets all cells
    public void reset() {
        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                cell.isAlive = false;
            }
        }
    }

    // Places down a pattern on the grid given current mouse position and Pattern object
    public void placePattern(PApplet pa, Pattern pattern){
        cellArray[cellSelectY][cellSelectX].state = true;
        // Initial positions
        int initialRow = cellSelectY;
        int initialCol = cellSelectX;

        for (int i = 0; i < pattern.posX.length; i++) {
            int targetR = initialRow + pattern.posY[i];
            int targetC = initialCol + pattern.posX[i];

            if (targetR < 0) {
                targetR = cellArray.length - 1;
            }
            else if (targetR > cellArray.length - 1) {
                targetR = 0;
            }
            if (targetC < 0) {
                targetC = cellArray[0].length - 1;
            }
            else if (targetC > cellArray[0].length - 1) {
                targetC = 0;
            }

            cellArray[targetR][targetC].isAlive = true;
        }
    }

    public void display(PApplet pa) {
        int row = 0;
        int col;
        cellCount = 0; // Cell count resets every cycle of checks

        // Display Cells
        for(Cell[] thisRow: cellArray) {
            col = 0;
            for (Cell thisCell : thisRow) {
                thisCell.display(pa);
                if (thisCell.isAlive) {
                    // If cell is alive add to cellCount
                    cellCount++;
                }
                if (thisCell.mouseHover(pa)) {
                    // Keep track of cell, mouse is currently on
                    cellSelectX = col;
                    cellSelectY = row;
                }
                col++;
            }
            row++;
        }
    }
}