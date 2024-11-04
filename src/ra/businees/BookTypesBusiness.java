package ra.businees;

import ra.entity.BookTypes;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookTypesBusiness {
    public static List<BookTypes> findAll(){
        Connection conn = null;
        CallableStatement callSt = null;
        List<BookTypes> listBookTypes = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_all_book_type()}");
            ResultSet rs = callSt.executeQuery();
            listBookTypes = new ArrayList<>();
            while (rs.next()) {
                BookTypes bookType = new BookTypes();
                bookType.setBookTypeId(rs.getInt("type_id"));
                bookType.setBookTypeName(rs.getString("type_name"));
                bookType.setBookTypeDescription(rs.getString("type_description"));
                bookType.setDeleted(rs.getBoolean("is_deleted"));
                listBookTypes.add(bookType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listBookTypes;
    }

    public static List<BookTypes> findByBookTypeHas(){
        Connection conn = null;
        CallableStatement callSt = null;
        List<BookTypes> listBookTypes = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_all_book_type_true()}");
            ResultSet rs = callSt.executeQuery();
            listBookTypes = new ArrayList<>();
            while (rs.next()) {
                BookTypes bookType = new BookTypes();
                bookType.setBookTypeId(rs.getInt("type_id"));
                bookType.setBookTypeName(rs.getString("type_name"));
                bookType.setBookTypeDescription(rs.getString("type_description"));
                bookType.setDeleted(rs.getBoolean("is_deleted"));
                listBookTypes.add(bookType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listBookTypes;
    }
    public static BookTypes findById(int id){
        Connection conn = null;
        CallableStatement callSt = null;
        BookTypes bookType = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call find_type_book_by_id(?)}");
            callSt.setInt(1,id);
            ResultSet rs = callSt.executeQuery();
            if(rs.next()){
                bookType = new BookTypes();
                bookType.setBookTypeId(rs.getInt("type_id"));
                bookType.setBookTypeName(rs.getString("type_name"));
                bookType.setBookTypeDescription(rs.getString("type_description"));
                bookType.setDeleted(rs.getBoolean("is_deleted"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bookType;
    }
    public static boolean createType(BookTypes bookType){
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call save_book_type(?,?,?)}");
            callSt.setString(1,bookType.getBookTypeName());
            callSt.setString(2,bookType.getBookTypeDescription());
            callSt.setBoolean(3,bookType.isDeleted());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static boolean updateType(BookTypes bookType){
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_book_type(?,?,?,?)}");
            callSt.setInt(1,bookType.getBookTypeId());
            callSt.setString(2,bookType.getBookTypeName());
            callSt.setString(3,bookType.getBookTypeDescription());
            callSt.setBoolean(4,bookType.isDeleted());
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
    public static boolean deleteType(int typeId){
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_book_type(?)}");
            callSt.setInt(1,typeId);
            callSt.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            ConnectionDB.closeConnection(conn, callSt);
        }
        return result;
    }
}
