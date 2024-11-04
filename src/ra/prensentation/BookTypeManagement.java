package ra.prensentation;

import ra.businees.BookTypesBusiness;
import ra.entity.BookTypes;

import java.util.List;
import java.util.Scanner;

public class BookTypeManagement {
    public static void displayBookTypesManagement(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("**********************BOOKTYPE-MENU********************");
            System.out.println("1. Danh sách loại sách");
            System.out.println("2. Tạo mới loại sách");
            System.out.println("3. Cập nhật thông tin loại sách");
            System.out.println("4. Xóa loại sách");
            System.out.println("5. Thống kê số lượng sách theo mã loại sách");
            System.out.println("6. Thoát");
            System.out.println("Lựa chọn của bạn");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayBookTypes();
                        break;
                    case 2:
                        createBookType(scanner);
                        break;
                    case 3:
                        updateBookType(scanner);
                        break;
                    case 4:
                        deleteBookType(scanner);
                        break;
                    case 5:
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Vui lòng nhập số từ 1-6");
                }

            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập vào số nguyên");
            }
        } while (isExit);
    }

    public static void displayBookTypes() {
        List<BookTypes> bookTypesList = BookTypesBusiness.findByBookTypeHas();
        if (!bookTypesList.isEmpty()) {
            for (BookTypes bookType : bookTypesList) {
                bookType.displayData();
            }
        }
    }

    public static void createBookType(Scanner scanner) {
        BookTypes bookType = new BookTypes();
        bookType.inputData(scanner);
        boolean result = BookTypesBusiness.createType(bookType);
        if (result) {
            System.out.println("Thêm mới thành công");
        } else {
            System.err.println("Thêm mới thất bại");
        }
    }

    public static void updateBookType(Scanner scanner) {
        System.out.println("Nhập vào mã loại sách cần cập nhật");
        try {
            int typeIdUpdate = Integer.parseInt(scanner.nextLine());
            BookTypes bookTypeUpdate = BookTypesBusiness.findById(typeIdUpdate);
            if (bookTypeUpdate != null) {
                boolean isExit = true;
                do {
                    System.out.println("1. Cập nhật tên loại sách");
                    System.out.println("2. Cập nhật mô tả loại sách");
                    System.out.println("4. Thoát");
                    System.out.println("Lựa chọn của bạn");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            bookTypeUpdate.setBookTypeName(scanner.nextLine());
                            break;
                        case 2:
                            bookTypeUpdate.setBookTypeDescription(scanner.nextLine());
                            break;
                        case 3:
                            bookTypeUpdate.setDeleted(Boolean.parseBoolean(scanner.nextLine()));
                            break;
                        case 4:
                            isExit = false;
                            break;
                        default:
                            System.err.println("Vui lòng nhập từ 1-4");
                    }
                } while (isExit);
                boolean result = BookTypesBusiness.updateType(bookTypeUpdate);
                if (result) {
                    System.out.println("Cập nhật thành công");
                }else {
                    System.err.println("Cập nhật thất bại");
                }
            }else {
                System.err.println("Mã loại sách không tồn tại");
            }
        } catch (NumberFormatException ex) {
            System.err.println("Vui lòng nhập vào đúng định dạng");
        }

    }
    public static void deleteBookType(Scanner scanner){
        System.out.println("Nhập vào mã loại sách cần xóa: ");
        try {
            int typeIdDelete = Integer.parseInt(scanner.nextLine());
            BookTypes bookTypeDelete = BookTypesBusiness.findById(typeIdDelete);
            if (bookTypeDelete != null) {
                boolean result = BookTypesBusiness.deleteType(typeIdDelete);
                if (result) {
                    System.out.println("Xóa loại sách thành công");
                }else {
                    System.err.println("Xóa loại sách thất bại");
                }

            }else {
                System.err.println("Mã loại sách không tồn tại");
            }
        }catch (NumberFormatException ex) {
            System.err.println("Vui lòng nhập vào số nguyên");
        }
    }
}
