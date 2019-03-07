package service;

import entities.SqliteApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteHelper {
    private SqliteApp mSqliteApp;

    public void getAllSinhVien(String cauLenh) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            String sql =cauLenh;

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);

            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String idSinhVien = rs.getString(1);
                String tenSinhVien = rs.getString(2);
                String sdtSinhVien = rs.getString("SDT_SINHVIEN");
                String emailSinhVien = rs.getString("EMAIL_SINHVIEN");
                System.out.println("--------------------");
                System.out.println("idSinhVien:" + idSinhVien);
                System.out.println("tenSinhVien:" + tenSinhVien);
                System.out.println("sdtSinhVien:" + sdtSinhVien);
                System.out.println("emailSinhVien:" + emailSinhVien);
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
    public void getAllMonHoc(String maSinhVien) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            String sql ="SELECT * FROM MONHOC mh,SinhVien sv WHERE sv.ID_SINHVIEN='"+maSinhVien+"'";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);

            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String idMonHoc = rs.getString(1);
                String tenMonHoc = rs.getString(2);
                String idSinhVien = rs.getString(3);

                System.out.println("--------------------");
                System.out.println("ID Môn Học : " + idMonHoc);
                System.out.println("Tên Môn Học : " + tenMonHoc);
                System.out.println("ID Sinh Viên : " + idSinhVien);
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
