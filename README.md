# Hill Cipher Encryption and Decryption

This project implements the Hill Cipher encryption and decryption algorithm in Java. The Hill Cipher is a polygraphic substitution cipher based on linear algebra.

## Features

- Encrypts plaintext using a key matrix.
- Prints the inverse of the  key matrix
- Decrypts ciphertext using the inverse of the key matrix.
- Handles input text and key matrix from the user.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine.
- A Java IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code , NeoVim).

### Running the Program

1. Clone the repository or download the source code.
2. Open the project in your preferred Java IDE or text editor.
3. Compile and run the `App.java` file.

### Example Usage

1. The program will prompt you to enter the size of the key matrix (rows and columns).
2. Enter the elements of the key matrix.
3. Enter the text to encrypt.
4. The program will display the encrypted text, the inverse key matrix, and the decrypted text.

### Sample Output
``` rust
====================================================== 

Welcome to the Hill Cipher Encryption and Decryption:

Enter the size of the key row size: 3
Enter the size of the key column size: 3 
Enter the elements of the key matrix: 
Enter the element at position [0, 0]: 2 
Enter the element at position [0, 1]: 4 
Enter the element at position [0, 2]: 5 
Enter the element at position [1, 0]: 9 
Enter the element at position [1, 1]: 2 
Enter the element at position [1, 2]: 1 
Enter the element at position [2, 0]: 3 
Enter the element at position [2, 1]: 17 
Enter the element at position [2, 2]: 7
Enter the text to encrypt: ACB 
Encrypted Text: NFP
Inverse Key Matrix: 
15 17 20 
20 9  17 
9  3  2 
Decrypted Text: ACB
```
### License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

