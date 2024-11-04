package ra.prensentation;

import java.util.Scanner;

public class BookManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("******************BOOK-MANAGEMENT******************");
            System.out.println("1. Quản lý loại sách");
            System.out.println("2. Quản lý sách");
            System.out.println("3. Thoát");
            System.out.println("Lựa chọn của bạn");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        BookTypeManagement.displayBookTypesManagement(scanner);
                        break;
                    case 2:
                        BookMenuManagement.displayBookMenu(scanner);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.err.println("Vui lòng chọn từ 1-3");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập vào số nguyên");
            }

        } while (true);
    }
}
