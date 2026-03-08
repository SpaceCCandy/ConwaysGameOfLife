import java.util.Scanner;

public class HW {
    public static void main(String[] args) {
        Scanner kbinput = new Scanner(System.in);

        //8.23
        int[][] matrix = new int[6][6];
        int flipedR = -1;
        int flipedC = -1;
        int temp;

        System.out.println("Enter a 6-by-6 matrix row by row: ");
        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                matrix[row][col] = kbinput.nextInt();
            }
        }

        for(int r = 0; r < 5; r++) {
            temp = 0;
            for(int c = 0; c < 5; c++) {
                temp += matrix[r][c];
            }
            if (temp % 2 == 0) {
                flipedR = r;
                break;
            }
        }

        for(int c = 0; c < 5; c++) {
            temp = 0;
            for(int r = 0; r < 5; r++) {
                temp += matrix[r][c];
            }
            if (temp % 2 == 0) {
                flipedC = c;
                break;
            }
        }

        System.out.println("The flipped cell is at (" + flipedR + ". " + flipedC + ")");



    }
}
