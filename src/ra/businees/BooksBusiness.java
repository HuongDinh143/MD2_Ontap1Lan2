package ra.businees;

import ra.entity.Books;
import util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksBusiness {
    public static List<Books> findAllBooks() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Books> books = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_all_book()}");
            ResultSet rs = callSt.executeQuery();
            books = new ArrayList<>();
            while (rs.next()) {
                Books book = new Books();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalPages(rs.getInt("total_pages"));
                book.setContent(rs.getString("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getDouble("price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookIsDeleted(rs.getBoolean("is_deleted_book"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return books;
    }

    public static List<Books> findBookIsExit() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Books> books = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_all_book_exit()}");
            ResultSet rs = callSt.executeQuery();
            books = new ArrayList<>();
            while (rs.next()) {
                Books book = new Books();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalPages(rs.getInt("total_pages"));
                book.setContent(rs.getString("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getDouble("price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookIsDeleted(rs.getBoolean("is_deleted_book"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return books;
    }

    public static boolean addBook(Books book) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call save_book(?,?,?,?,?,?,?,?)}");
            callSt.setString(1, book.getBookName());
            callSt.setString(2, book.getTitle());
            callSt.setString(3, book.getAuthor());
            callSt.setInt(4, book.getTotalPages());
            callSt.setString(5, book.getContent());
            callSt.setString(6, book.getPublisher());
            callSt.setDouble(7, book.getPrice());
            callSt.setInt(8, book.getTypeId());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static boolean isBookNameDuplicate(int bookId, String bookName) {
        boolean isDuplicate = false;

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement callSt = conn.prepareCall("{CALL is_Book_Name_Duplicate(?, ?)}")) {

            callSt.setInt(1, bookId);
            callSt.setString(2, bookName);

            try (ResultSet rs = callSt.executeQuery()) {
                if (rs.next() && rs.getInt("duplicate_count") > 0) {
                    isDuplicate = true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking book name duplication: " + e.getMessage());
        }

        return isDuplicate;
    }

    public static boolean updateBook(Books book) {
        boolean result = false;

        // Lấy sách hiện tại từ cơ sở dữ liệu
        Books existingBook = BooksBusiness.findBookById(book.getBookId());
        if (existingBook == null) {
            System.err.println("Sách không tồn tại.");
            return false;
        }

        // Kiểm tra trùng lặp tên sách nếu tên mới khác tên hiện tại
        if (!book.getBookName().equals(existingBook.getBookName()) &&
                BooksBusiness.isBookNameDuplicate(book.getBookId(), book.getBookName())) {
            System.err.println("Tên sách đã tồn tại, vui lòng nhập tên khác.");
            return false;
        }

        try (Connection conn = ConnectionDB.openConnection();
             CallableStatement callSt = conn.prepareCall("{call update_book(?,?,?,?,?,?,?,?,?)}")) {

            callSt.setInt(1, book.getBookId());
            callSt.setString(2, book.getBookName());
            callSt.setString(3, book.getTitle());
            callSt.setString(4, book.getAuthor());
            callSt.setInt(5, book.getTotalPages());
            callSt.setString(6, book.getContent());
            callSt.setString(7, book.getPublisher());
            callSt.setDouble(8, book.getPrice());
            callSt.setInt(9, book.getTypeId());

            callSt.executeUpdate();
            result = true;

        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }

        return result;
    }

    public static Books findBookById(int bookId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Books book = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_book_by_id(?)}");
            callSt.setInt(1, bookId);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                book = new Books();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalPages(rs.getInt("total_pages"));
                book.setContent(rs.getString("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getDouble("price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookIsDeleted(rs.getBoolean("is_deleted_book"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return book;
    }

    public static boolean deleteBook(int bookId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_book(?)}");
            callSt.setInt(1, bookId);
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }

    public static List<Books> sortBookByPrice() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Books> books = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call sort_book_by_pice_desc()}");
            ResultSet rs = callSt.executeQuery();
            books = new ArrayList<>();
            while (rs.next()) {
                Books book = new Books();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalPages(rs.getInt("total_pages"));
                book.setContent(rs.getString("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getDouble("price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookIsDeleted(rs.getBoolean("is_deleted_book"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return books;
    }

    public static List<Books> findBookByNameOrByContent(String searchValue) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Books> books = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_book_by_name_or_by_content(?)}");
            callSt.setString(1, searchValue);
            ResultSet rs = callSt.executeQuery();
            books = new ArrayList<>();
            while (rs.next()) {
                Books book = new Books();
                book.setBookId(rs.getInt("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setTotalPages(rs.getInt("total_pages"));
                book.setContent(rs.getString("content"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getDouble("price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookIsDeleted(rs.getBoolean("is_deleted_book"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return books;
    }

    public static int statisticBooksByTypeId(int typeId) {
        Connection conn = null;
        CallableStatement callSt = null;
        int result = 0;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call statistic_book_by_type_id(?,?)}");
            callSt.setInt(1, typeId);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            result = callSt.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
}
