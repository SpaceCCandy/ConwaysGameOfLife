import java.util.Scanner;

public class HW {
    public static void main(String[] args) {
        Scanner kbinput = new Scanner(System.in);

//        //8.23
        int[][] matrix = new int[6][6];
        int flipedR = -1;
        int flipedC = -1;
        int temp;

        System.out.println("Enter a 6-by-6 matrix row by row: ");
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                matrix[row][col] = kbinput.nextInt();
            }
        }

        for (int r = 0; r < 6; r++) {
            temp = 0;
            for (int c = 0; c < 6; c++) {
                temp += matrix[r][c];
            }
            if (temp % 2 == 0) {
                flipedR = r;
                break;
            }
        }

        for (int c = 0; c < 6; c++) {
            temp = 0;
            for (int r = 0; r < 6; r++) {
                temp += matrix[r][c];
            }
            if (temp % 2 == 0) {
                flipedC = c;
                break;
            }
        }

        System.out.println("The flipped cell is at (" + flipedC + ", " + flipedR + ")");

        System.out.println("\n-----------------------------------------------\n");
        // 8.5

        double[][] matrix1, matrix2, matrixSum;
        matrix1 = new double[2][2];
        matrix2 = new double[2][2];
        matrixSum = new double[2][2];

        System.out.print("Enter matrix 1: ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix1[i][j] = kbinput.nextDouble();
            }
        }

        System.out.print("Enter matrix 2: ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix2[i][j] = kbinput.nextDouble();
            }
        }

        matrixSum = addMatrix(matrix1, matrix2);

        System.out.println("The matrices are added as follows");
        System.out.println("\t" + matrix1[0][0] + " " + matrix1[0][1] + "   " +  matrix2[0][0] + " " + matrix2[0][1] + "   " + matrixSum[0][0] + " " + matrixSum[0][1]);
        System.out.println("\t" + matrix1[1][0] + " " + matrix1[1][1] + " + " +  matrix2[1][0] + " " + matrix2[1][1] + " = " + matrixSum[1][0] + " " + matrixSum[1][1]);

        System.out.println("The flipped cell is at (" + flipedC + ", " + flipedR + ")");
        // 8.6

        double[][] matrixp1, matrixp2, matrixProduct;
        matrixp1 = new double[3][3];
        matrixp2 = new double[3][3];
        matrixProduct = new double[3][3];

        System.out.print("Enter matrix 1: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixp1[i][j] = kbinput.nextDouble();
            }
        }

        System.out.print("Enter matrix 2: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixp2[i][j] = kbinput.nextDouble();
            }
        }

        matrixProduct = mulitplyMatrix(matrixp1, matrixp2);

        System.out.println("The matrices are added as follows");
        System.out.println("\t" + matrixp1[0][0] + " " + matrixp1[0][1] + " " + matrixp1[0][2] + "   " +  matrixp2[0][0] + " " + matrixp2[0][1] + " " + matrixp2[0][2] + "   " + matrixProduct[0][0] + " " + matrixProduct[0][1]+ " " + matrixProduct[0][2]);
        System.out.println("\t" + matrixp1[1][0] + " " + matrixp1[1][1] + " " + matrixp1[1][2] + " X " +  matrixp2[1][0] + " " + matrixp2[1][1] + " " + matrixp2[1][2] + " = " + matrixProduct[1][0] + " " + matrixProduct[1][1]+ " " + matrixProduct[1][2]);
        System.out.println("\t" + matrixp1[2][0] + " " + matrixp1[2][1] + " " + matrixp1[2][2] + "   " +  matrixp2[2][0] + " " + matrixp2[2][1] + " " + matrixp2[2][2] + "   " + matrixProduct[2][0] + " " + matrixProduct[2][1]+ " " + matrixProduct[2][2]);

        System.out.println("\n-----------------------------------------------\n");
        // 7.1
        int studentCount;
        int best = 0;

        System.out.print("Enter the number of students: ");
        studentCount = kbinput.nextInt();

        System.out.print("Enter " + studentCount + " scores: ");
        int[] scores = new int[studentCount];
        for (int i = 0; i < studentCount; i++) {
            scores[i] = kbinput.nextInt();
            if (scores[i] > best) {
                best = scores[i];
            }
        }

        for (int i = 0; i < studentCount; i++) {
            System.out.println("Student " + i + " score is " + scores[i] + " and grade is " + letterGrade(best, scores[i]));
        }

        System.out.println("\n-----------------------------------------------\n");
        // 7.2
        int[] numbers = {93, 65, 18, 58, 16, 17, 35, 28, 54, 10, 79};

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > numbers[numbers.length-1]) {
                System.out.println(numbers[i] + " is greater then " + numbers[numbers.length-1]);
            }
            else if (numbers[i] == numbers[numbers.length-1]) {
                System.out.println(numbers[i] + " is equal to " + numbers[numbers.length-1]);
            }
            else {
                System.out.println(numbers[i] + " is less then " + numbers[numbers.length-1]);
            }
        }


    }

    public static double[][] addMatrix(double[][] a, double[][] b) {
        double[][] matrixSum = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrixSum[i][j] = a[i][j] + b[i][j];
            }
        }
        return matrixSum;
    }

    // 8.6
    public static double[][] mulitplyMatrix(double[][] a, double[][] b) {
        double[][] matrixProduct = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixProduct[i][j] = (a[i][0] * b[0][j]) + (a[i][1] * b[1][j]) + (a[i][2] * b[2][j]);
            }
        }
        return matrixProduct;
    }

    // 7.1
    public static String letterGrade(int best, int grade) {
        if (grade >= best-5) {
            return "A";
        }
        else if (grade >= best-10) {
            return "B";
        }
        else if (grade >= best-15) {
            return "C";
        }
        else if (grade >= best-20) {
            return "D";
        }
        else {
            return "F";
        }
    }
}