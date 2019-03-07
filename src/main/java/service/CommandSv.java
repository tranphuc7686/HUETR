package service;

import java.util.UUID;

public class CommandSv {
    public static final String COMMAND ="1. Xem toàn bộ thông tin sinh viên \n2. Thêm điểm của sinh viên\n3. Sửa điểm của sinh viên\n4. Xóa điểm của sinh viên";
    public static final String WAIT_FOR_SERVER = "waiting for server return......";
    public static final String ALLOW = "yes";
    public static final String NOT_ALLOW = "no";
    public static final String NHAPDIEM_GIUAKI = "Mời nhập điểm Giữa Kì : ";
    public static final String NHAPDIEM_CUOIKI = "Mời nhập điểm Cuối Kì : ";
    public static final String NHAP_MASINHVIEN = "Mời nhập mã số Sinh Viên cần thao tác... ";
    public static final String SUBMIT_TO_SERVER = "Nhấn Enter để submit lên server.. ";
    public static final String CHON_MONHOC = "Nhập mã môn học cần thêm điểm : ";
    public static final String ERROR_DADANGKIDIEM = "Môn học đã được thêm điểm, xin vui lòng update điểm";
    public static String randomId(){
        String uuid = UUID.randomUUID().toString();
        return  uuid;
    }

    public static final int MODE_GETALL = 1;
    public static final int MODE_THEM = 2;
    public static final int MODE_SUA = 3;
    public static final int MODE_Xoa = 4;



}
