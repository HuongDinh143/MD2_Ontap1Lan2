package ra.entity;

import ra.businees.BookTypesBusiness;

import java.util.List;
import java.util.Scanner;

public class BookTypes implements IBookManagement {
    private int bookTypeId;
    private String bookTypeName;
    private String bookTypeDescription;
    private boolean isDeleted = false;

    public BookTypes() {

    }

    public BookTypes(int bookTypeId, String bookTypeName, String bookTypeDescription, boolean isDeleted) {
        this.bookTypeId = bookTypeId;
        this.bookTypeName = bookTypeName;
        this.bookTypeDescription = bookTypeDescription;
        this.isDeleted = isDeleted;
    }

    public int getBookTypeId() {
        return this.bookTypeId;
    }

    public void setBookTypeId(int bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookTypeName() {
        return this.bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getBookTypeDescription() {
        return this.bookTypeDescription;
    }

    public void setBookTypeDescription(String bookTypeDescription) {
        this.bookTypeDescription = bookTypeDescription;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bookTypeName = inputBookTypeName(scanner);
        this.bookTypeDescription = inputBookTypeDescription(scanner);
        this.isDeleted = false;
    }

    public static String inputBookTypeName(Scanner scanner) {
        System.out.println("Mời bạn nhập tên loại sách");

        List<BookTypes> bookTypes = BookTypesBusiness.findAll();
        do {
            String bookTypeName = scanner.nextLine().trim();
            if (bookTypeName.isEmpty()) {
                System.err.println("Tên loại sách không được trống");
                continue;
            } else if (bookTypes != null) {
                boolean isExist = false;
                for (BookTypes bookType : bookTypes) {
                    if (bookType.getBookTypeName().equals(bookTypeName)) {
                        System.err.println("tên loại sách không được trùng, vui lòng nhập lại");
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    return bookTypeName;
                }
            } else {
                return bookTypeName;
            }
        } while (true);
    }
    public static String inputBookTypeDescription(Scanner scanner) {
        System.out.println("Nhập vào mô tả loại sách");
        return scanner.nextLine();
    }

    @Override
    public void displayData() {
        System.out.printf("Mã loại sách: %d - Tên loại sách: %s - Mô tả: %s - isDeleted: %s\n",
                bookTypeId, bookTypeName, bookTypeDescription, isDeleted ? "Đã xóa" : "Chưa xóa");

    }
}
