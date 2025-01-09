/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package kase.aptechsaigon.projectsem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ProjectSem2 {

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Nhap so dong: ");
            int rows = scanner.nextInt();

            System.out.print("Nhap so cot: ");
            int cols = scanner.nextInt();

            int[][] matrix = new int[rows][cols];

            System.out.println("Nhap cac phan tu cua ma tran:");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print("Phan tu [" + i + "][" + j + "]: ");
                    matrix[i][j] = scanner.nextInt();
                }
            }

            System.out.print("Nhap ten file de ghi (bao gom duong dan neu can): ");
            scanner.nextLine(); 
            String fileName = scanner.nextLine();
            
            System.out.print("Nhap ten file de doc (bao gom duong dan neu can): ");
            String readFileName = scanner.nextLine();
            docFile(readFileName);

            xuatMaTran(matrix, fileName);

        } catch (IOException e) {
            System.out.println("Da xay ra loi khi ghi file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Da xay ra loi: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void xuatMaTran(int[][] matrix, String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                text.append(matrix[i][j]).append("\t");
            }
            text.append("\r\n");
        }

        File file = new File(fileName);

        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("Khong the tao file!");
                return;
            }
        }

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(text.toString());
        }

        System.out.println("Ghi file thanh cong!");
    }
    
    public static void docFile (String fileName) throws FileNotFoundException, IOException {
       /*File file = new File(fileName);
       Scanner scanner = new Scanner(file);       
       int row = scanner.nextInt();
       int col = scanner.nextInt();
       for (int i = 0; i < row; i++){
           for (int j = 0; j < row; j++) {
               System.out.println("\t" + scanner.nextInt());
           }
           System.out.println("");*/
       
           File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File khong ton tai!");
            return;
        }

        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        System.out.println("Noi dung file" + "\n" + content);
       }
}
