package ra.prensentation;

import ra.businees.BookTypesBusiness;
import ra.businees.BooksBusiness;
import ra.entity.BookTypes;
import ra.entity.Books;

import java.util.List;
import java.util.Scanner;

public class BookMenuManagement {
    public static void displayBookMenu(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("**********************BOOK-MENU***********************");
            System.out.println("1. Danh sách sách");
            System.out.println("2. Tạo mới sách");
            System.out.println("3. Cập nhật thông tin sách");
            System.out.println("4. Xóa sách");
            System.out.println("5. Hiển thị danh sách các cuốn sách theo giá giảm dần");
            System.out.println("6. Tìm kiếm sách theo tên hoặc nội dung");
            System.out.println("7. Thống kê số lượng sách theo nhóm");
            System.out.println("8. Thoát");
            System.out.println("Lựa chọn của bạn:");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        displayBooks();
                        break;
                    case 2:
                        createBook(scanner);
                        break;
                    case 3:
                        updateBook(scanner);
                        break;
                    case 4:
                        deleteBook(scanner);
                        break;
                    case 5:
                        displayBooksByPriceDESC();
                        break;
                    case 6:
                        searchBookByNameOrContent(scanner);
                        break;
                    case 7:
                        statisticBooksByTypeId(scanner);
                        break;
                    case 8:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Vui lòng nhập từ 1-8");
                }

            } catch (NumberFormatException ex) {
                System.err.println("Vui lòng nhập vào số nguyên");
            }
        } while (isExit);
    }

    private static void displayBooks() {
        List<Books> booksList = BooksBusiness.findBookIsExit();
        if (booksList.isEmpty()) {
            System.out.println("Danh sách sách trống");
        } else {
            for (Books book : booksList) {
                book.displayData();
            }
        }
    }

    private static void createBook(Scanner scanner) {
        Books book = new Books();
        book.inputData(scanner);
        boolean result = BooksBusiness.addBook(book);
        if (result) {
            System.out.println("Thêm sách thành công");
        }else {
            System.err.println("Thêm sách thất bại");
        }
    }

    private static void updateBook(Scanner scanner) {
        System.out.println("Nhập và mã sách cần cập nhật");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());
            Books updateBook = BooksBusiness.findBookById(updateId);
            if (updateBook != null) {
                boolean isExit = true;
                do {
                    System.out.println("1. Cập nhật tên sách");
                    System.out.println("2. Cập nhật tiêu đề sách");
                    System.out.println("3. Cập nhật tên tác giả ");
                    System.out.println("4. Cập nhật số trang sách");
                    System.out.println("5. Cập nhật nội dung sách");
                    System.out.println("6. Cập nhật nhà xuất bản");
                    System.out.println("7. Cập nhật giá sách");
                    System.out.println("8. Cập nhật mã loại sách");
                    System.out.println("9. Thoát");
                    System.out.println("Lựa chọn của bạn");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Nhập vào tên sách mới");
                            updateBook.setBookName(scanner.nextLine());
                            break;
                        case 2:
                            System.out.println("Nhập vào tiêu đề sách mới");
                            updateBook.setTitle(scanner.nextLine());
                            break;
                        case 3:
                            System.out.println("Nhập vào tên tác giả mới");
                            updateBook.setAuthor(scanner.nextLine());
                            break;
                        case 4:
                            System.out.println("Nhập vào tổng số trang sách mới");
                            updateBook.setTotalPages(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 5:
                            System.out.println("Nhập vào nội dung sách mới");
                            updateBook.setContent(scanner.nextLine());
                            break;
                        case 6:
                            System.out.println("Nhập vào nhà xuất bản mới");
                            updateBook.setPublisher(scanner.nextLine());
                            break;
                        case 7:
                            System.out.println("Nhập vào giá sách mới");
                            updateBook.setPrice(Double.parseDouble(scanner.nextLine()));
                            break;
                        case 8:
                            System.out.println("Nhập vào mã sách loại sách mới");
                            updateBook.setTypeId(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 9:
                            isExit = false;
                            break;

                        default:
                            System.err.println("Vui lòng chọn từ 1-9");
                    }

                } while (isExit);
                boolean result = BooksBusiness.updateBook(updateBook);
                if (result) {
                    System.out.println("Cập nhật thành công");
                }else {
                    System.err.println("Cập nhật thất bại");
                }

            } else {
                System.err.println("Mã sách không tồn tại");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập vào số nguyên");
        }
    }
    private static void deleteBook(Scanner scanner) {
        System.out.println("Nhập vào mã sách cần xóa");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine());
            Books deleteBook = BooksBusiness.findBookById(deleteId);
            if (deleteBook != null) {
                boolean result = BooksBusiness.deleteBook(deleteId);
                if (result) {
                    System.out.println("Xóa sách thành công");
                }else {
                    System.err.println("Xóa sách thất bại");
                }
            }else {
                System.err.println("Mã sách không tồn tại");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập vào số nguyên");
        }

    }
    private static void displayBooksByPriceDESC(){
        List<Books> booksList = BooksBusiness.sortBookByPrice();
        if (booksList != null) {
            for (Books book : booksList) {
                book.displayData();
            }
        }else {
            System.err.println("Không có sách nào trong dữ liệu");
        }
    }
    private static void searchBookByNameOrContent(Scanner scanner) {
        System.out.println("Nhập vào tên sách hoặc nội dung sách");
        String searchValue = scanner.nextLine();
        List<Books> searchBooksList = BooksBusiness.findBookByNameOrByContent(searchValue);

        if (searchBooksList != null) {
            System.out.printf("Có %d kết quả  phù hợp với từ khóa tìm kiếm: %s\n", searchBooksList.size(), searchValue);
            for (Books book : searchBooksList) {
                book.displayData();
            }
        }else {
            System.err.printf("Không tìm thấy kết quả nào phù hợp với từ khóa : %s\n", searchValue);
        }
    }
    private static void statisticBooksByTypeId(Scanner scanner) {
        System.out.println("Nhập vào mã sách sần tìm kiếm sách");
        try {
            int statisticTypeId = Integer.parseInt(scanner.nextLine());
            BookTypes bookType = BookTypesBusiness.findById(statisticTypeId);
            if (bookType != null) {
                int countBooks = BooksBusiness.statisticBooksByTypeId(statisticTypeId);
                System.out.printf("có %d sách trong %s\n",
                        countBooks, BookTypesBusiness.findById(statisticTypeId).getBookTypeName());
            }else {
                System.err.println("Mã loại sách không tồn tại");
            }

        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập vào số nguyên");
        }
    }

}
