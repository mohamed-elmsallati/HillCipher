import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner Console_Scanner = new Scanner(System.in);

        System.out.println("======================================================");
        System.out.println("welcome to the Hill Cipher Encryption and Decryption :");

        int[][] keyMatrix = create_matrix();

        System.out.print("Enter the text to encrypt: ");
        String text = Console_Scanner.nextLine();
        text = text.toUpperCase();
        text = text.replaceAll("[^A-Z]", "");

        int matrixSize = keyMatrix.length;
        while (text.length() % matrixSize != 0) {
            System.out.print("Text length is not divisible by the matrix size. Please enter a new text : ");
            text = Console_Scanner.nextLine();
            text = text.toUpperCase();
            text = text.replaceAll("[^A-Z]", "");
        }

        String encryptedText = encrypt(text, keyMatrix);
        System.out.println("Encrypted Text: " + encryptedText);

        int[][] inverseKeyMatrix = inverseMatrix(keyMatrix);
        String decryptedText = decrypt(encryptedText, inverseKeyMatrix);

        System.out.println("Inverse Key Matrix: ");
        for (int i = 0; i < inverseKeyMatrix.length; i++) {
            for (int j = 0; j < inverseKeyMatrix[0].length; j++) {
                System.out.print(inverseKeyMatrix[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Decrypted Text: " + decryptedText);

        System.out.println("======================================================");
    }

    public static int[][] create_matrix() {
        Scanner Console_Scanner = new Scanner(System.in);
        System.out.print("Enter the size of the key row size : ");
        int matrixrow = Console_Scanner.nextInt();
        System.out.print("Enter the size of the key colomn size : ");
        int matrixcol = Console_Scanner.nextInt();

        int[][] keyMatrix = new int[matrixrow][matrixcol];

        System.out.println("Enter the elements of the key matrix:");
        for (int i = 0; i < keyMatrix.length; i++) {
            for (int j = 0; j < keyMatrix[0].length ; j++) {
                System.out.print("Enter the element at position [" + i + ", " + j + "] : ");
                keyMatrix[i][j] = Console_Scanner.nextInt();
            }
        }

        return keyMatrix;
    }

    public static String encrypt(String text, int[][] keyMatrix) {
        int matrixSize = keyMatrix.length;
        int[] textMatrix = new int[matrixSize];
        char[] encryptedText = new char[text.length()];

        for (int i = 0; i < text.length(); i += matrixSize) {
            for (int j = 0; j < matrixSize; j++) {
                textMatrix[j] = text.charAt(i + j) - 'A';
            }

            int[] multipliedMatrix = multiplyMatrixVector(keyMatrix, textMatrix); 

            for (int j = 0; j < matrixSize; j++) {
                encryptedText[i+j] = (char) (multipliedMatrix[j] + 'A');
            }
        }

        return new String(encryptedText);
    }



    public static String decrypt(String text, int[][] inverseKeyMatrix) {
        int matrixSize = inverseKeyMatrix.length;
        int[] textVector = new int[matrixSize];
        char[] decryptedText = new char[text.length()];

        for (int i = 0; i < text.length(); i += matrixSize) {
            for (int j = 0; j < matrixSize; j++) {
                textVector[j] = text.charAt(i + j) - 'A';
            }

            int[] decryptedVector = multiplyMatrixVector(inverseKeyMatrix, textVector);

            for (int j = 0; j < matrixSize; j++) {
                decryptedText[i + j] = (char) (decryptedVector[j] + 'A');
            }
        }

        return new String(decryptedText);
    }

    public static int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int matrixSize = matrix.length;
        int[] result = new int[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            result[i] = 0;
            for (int j = 0; j < matrixSize; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] = (result[i] % 26 + 26) % 26;
        }

        return result;
    } 

    public static int[][] inverseMatrix(int[][] matrix) {
        int matrixSize = matrix.length;
        int[][] adjoint = new int[matrixSize][matrixSize];
        int[][] inverse = new int[matrixSize][matrixSize];
        int determinant = determinant(matrix, matrixSize);

        if (determinant == 0) {
            System.err.println("Determinant must be positive or negative");
        }

        int determinantInverse = modInverse(determinant, 26);


        adjoint(matrix, adjoint);

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                inverse[i][j] = (adjoint[i][j] * determinantInverse) % 26;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }

        return inverse;
    }

    public static void adjoint(int[][] matrix, int[][] adjoint) { 
        int matrixSize = matrix.length;
        if (matrixSize == 1) {
            adjoint[0][0] = 1;
            return;
        }

        int sign = 1;
        int[][] temp = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) { 
            for (int j = 0; j < matrixSize; j++) {
                getCofactor(matrix, temp, i, j, matrixSize);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adjoint[j][i] = (sign * determinant(temp, matrixSize - 1)) % 26;
                if (adjoint[j][i] < 0) {
                    adjoint[j][i] += 26;
                }
            }
        }
    }

    public static void getCofactor(int[][] matrix, int[][] temp, int p, int q, int matrixSize) {  // exlpain this
        int i = 0, j = 0;

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                if (row != p && col != q) {  
                    temp[i][j++] = matrix[row][col];
                    if (j == matrixSize - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public static int determinant(int[][] matrix, int matrixSize) {
        int determinant = 0;

        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int[][] temp = new int[matrixSize][matrixSize];
        int sign = 1;

        for (int f = 0; f < matrixSize; f++) {
            getCofactor(matrix, temp, 0, f, matrixSize);
            determinant += sign * matrix[0][f] * determinant(temp, matrixSize - 1);
            sign = -sign;
        }

        return determinant;
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }
}