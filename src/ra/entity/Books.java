package ra.entity;

import ra.businees.BookTypesBusiness;
import ra.businees.BooksBusiness;

import java.util.List;
import java.util.Scanner;

public class Books implements IBookManagement {
    private int bookId;
    private String bookName;
    private String title;
    private String author;
    private int totalPages;
    private String content;
    private String publisher;
    private double price;
    private int typeId;
    private boolean bookIsDeleted = false;

    public Books() {
    }

    public Books(int bookId, String bookName, String title, String author, int totalPages, String content, String publisher, double price, int typeId, boolean bookIsDeleted) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
        this.content = content;
        this.publisher = publisher;
        this.price = price;
        this.typeId = typeId;
        this.bookIsDeleted = bookIsDeleted;
    }

    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isBookIsDeleted() {
        return this.bookIsDeleted;
    }

    public void setBookIsDeleted(boolean bookIsDeleted) {
        this.bookIsDeleted = bookIsDeleted;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bookName = inputBookName(scanner);
        this.title = inputTitle(scanner);
        this.author = inputAuthor(scanner);
        this.totalPages = inputTotalPages(scanner);
        this.content = inputContent(scanner);
        this.publisher = inputPublisher(scanner);
        this.price = inputPrice(scanner);
        this.typeId = inputTypeId(scanner);
        this.bookIsDeleted = false;
    }

    public static String inputBookName(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên sách");
            String bookName = scanner.nextLine().trim();
            if (bookName.isEmpty()) {
                System.err.println("Tên sách không được trống vui lòng nhập lại");
                continue;
            }
            List<Books> booksList = BooksBusiness.findAllBooks();
            if (booksList != null) {
                boolean isExist = false;
                for (Books book : booksList) {
                    if (book.getBookName().equals(bookName)) {
                        System.err.println("Tên sách không được trùng, vui lòng nhập lại");
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    return bookName;
                }
            } else {
                return bookName;
            }
        } while (true);

    }

    public static String inputTitle(Scanner scanner) {
        System.out.println("Nhập vào Title");
        do {
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.err.println("Tiêu đề sách không được bỏ trống");
            } else {
                return title;
            }
        } while (true);
    }

    public static String inputAuthor(Scanner scanner) {
        System.out.println("Nhập tên tác giả: ");
        do {
            String author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                System.err.println("Tên tác giả không được để trống");
            } else {
                return author;
            }
        } while (true);
    }

    public static int inputTotalPages(Scanner scanner) {
        System.out.println("Nhập vào số trang sách: ");
        do {
            try {
                int totalPages = Integer.parseInt(scanner.nextLine());
                if (totalPages <= 0) {
                    System.err.println("Số trang sách phải lớn hơn 0");
                } else {
                    return totalPages;
                }
            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập vào số nguyên");
            }
        } while (true);
    }

    public static String inputContent(Scanner scanner) {
        System.out.println("Nhập vào Nội dung sách: ");
        do {
            String content = scanner.nextLine().trim();
            if (content.isEmpty()) {
                System.err.println("Nội dung sách không được bỏ trống, vui lòng nhập lại");
            } else {
                return content;
            }
        } while (true);
    }

    public static String inputPublisher(Scanner scanner) {
        System.out.println("Nhập vào nhà xuất bản sách: ");
        do {
            String publisher = scanner.nextLine().trim();
            if (publisher.isEmpty()) {
                System.err.println("Tên nhà xuất bản không được bỏ trống, vui lòng nhập lại");
            } else {
                return publisher;
            }
        } while (true);
    }

    public static double inputPrice(Scanner scanner) {
        System.out.println("Nhập vào giá sách");
        do {
            try {
                double price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.err.println("Giá sách phải lớn hơn không");
                } else {
                    return price;
                }
            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập vào kiểu số thực");
            }
        } while (true);
    }

    public static int inputTypeId(Scanner scanner) {
        List<BookTypes> bookTypesList = BookTypesBusiness.findByBookTypeHas();
        System.out.println("Mời bạn chọn mã loại sách");
        if (bookTypesList == null || bookTypesList.isEmpty()) {
            System.err.println("Không có loại sách nào, vui lòng thêm loại sách trước khi thêm sách");
            return -1;
        }
        do {
            for (BookTypes bookType : bookTypesList) {
                System.out.printf("%d - %s\n", bookType.getBookTypeId(), bookType.getBookTypeName());
            }
            System.out.println("Lựa chọn của bạn");
            try {
                int typeId = Integer.parseInt(scanner.nextLine());
                boolean isExist = false;
                for (BookTypes bookType : bookTypesList) {
                    if (bookType.getBookTypeId() == typeId) {
                    }
                    isExist = true;
                    break;
                }
                if (isExist) {
                    return typeId;
                } else {
                    System.err.println("Mã loại sách không tồn tại vui lòng nhập lại");
                }

            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập vào số nguyên");
            }
        } while (true);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sách: %d - Tên sách: %s - Tiêu đề: %s - " +
                        "Tác giả: %s - Số trang: %s - Nội dung: %s" +
                        "NXB: %s - Giá %f - Tên loại sách: %s - Trạng thái: %s\n",
                this.bookId, this.bookName, this.title, this.author, this.totalPages, this.content,
                this.publisher, this.price, BookTypesBusiness.findById(this.typeId).getBookTypeName(),
                this.bookIsDeleted ? "Đã xóa" : "Chưa xóa");

    }
}
