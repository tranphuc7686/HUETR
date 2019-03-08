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
            String sql = cauLenh;

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);

            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String idSinhVien = rs.getString(1);
                String tenSinhVien = rs.getString(2);
                String sdtSinhVien = rs.getString("SDT_SINHVIEN");
                String emailSinhVien = rs.getString("EMAIL_SINHVIEN");
                System.out.println("--------------------");
                System.out.println("Mã Sinh Viên : " + idSinhVien);
                System.out.println("Tên Sinh Viên : " + tenSinhVien);
                System.out.println("SDT Sinh Viên : " + sdtSinhVien);
                System.out.println("Email Sinh Viên : " + emailSinhVien);
            }
            System.out.println("--------------------");
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
            String sql = "SELECT MH.ID_MONHOC,MH.TEN_MONHOC,HIHI.DIEM_GIUAKI,HIHI.DIEM_CUOIKI,HIHI.TEN_SINHVIEN FROM MONHOC MH " +
                    "LEFT JOIN (select * FROM DIEMTHI DT,SinhVien SV WHERE DT.ID_SINHVIEN = '"+ maSinhVien + "' AND SV.ID_SINHVIEN = DT.ID_SINHVIEN) HIHI " +
                    "ON HIHI.ID_MONHOC = MH.ID_MONHOC";

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);

            String tenSinhVien = rs.getString(5);

            System.out.println("Tên Sinh Viên : " + tenSinhVien);
            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String idMonHoc = rs.getString(1);
                String tenMonHoc = rs.getString(2);
                String diemGiuaKi = rs.getString(3);
                String diemCuoiKi = rs.getString(4);

                System.out.println("--------------------");
                System.out.println("Mã Môn Học : " + idMonHoc);
                System.out.println("Tên Môn Học : " + tenMonHoc);
                System.out.println("Điểm Giữa Kì : " + (diemGiuaKi == null ? "Chưa có" : diemGiuaKi));
                System.out.println("Điểm Cuối Kì : " + (diemCuoiKi == null ? "Chưa có" : diemCuoiKi));
            }
            System.out.println("--------------------");

            System.out.println(CommandSv.CHON_MONHOC);
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addDiemMonHoc(String caulenh) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            String sql = caulenh;
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            int rowsCount = statement.executeUpdate(sql);
            if (rowsCount >= 1) {
                System.out.println("Thêm thành Công");
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("Không tồn tại Môn học hoặc Sinh Viên hoặc "+CommandSv.ERROR_DADANGKIDIEM);
            }


        }


    }

    public void suaDiemMonHoc(String caulenh) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            String sql = caulenh;

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            int rowsCount = statement.executeUpdate(sql);
            if (rowsCount >= 1) {
                System.out.println("Sửa thành Công");
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            System.out.println("Sửa thất bài vì : ");
            e.printStackTrace();

        }


    }

    public void xoaDiemMonHoc(String caulenh) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            String sql = caulenh;

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            int rowsCount = statement.executeUpdate(sql);
            if (rowsCount >= 1) {
                System.out.println("Xóa thành Công");
            }
            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            System.out.println("Xóa thất bài vì : ");
            e.printStackTrace();

        }


    }
    public void checkDb(String caulenh) {
        mSqliteApp = new SqliteApp();

        Connection connection = null;
        // Tạo đối tượng Statement.
        Statement statement = null;
        try {
            // Lấy ra đối tượng Connection kết nối vào DB.
            connection = mSqliteApp.connectSQLiteApp();
            statement = connection.createStatement();
            System.out.println(caulenh);
            String sql =caulenh;

            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            ResultSet rs = statement.executeQuery(sql);


            // Duyệt trên kết quả trả về.
            while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String idMonHoc = rs.getString(1);
                String tenMonHoc = rs.getString(2);
                String diemGiuaKi = rs.getString(3);
                String diemCuoiKi = rs.getString(4);

                System.out.println("--------------------");
                System.out.println("ID Môn Học : " + idMonHoc);
                System.out.println("Tên Môn Học : " + tenMonHoc);
                System.out.println("Điểm Giữa Kì : " + (diemGiuaKi == null ? "Chưa có" : diemGiuaKi));
                System.out.println("Điểm Cuối Kì : " + (diemCuoiKi == null ? "Chưa có" : diemCuoiKi));
            }

            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
