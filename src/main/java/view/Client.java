package view;

import service.CommandSv;
import service.Mess;
import service.SqliteHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
//    public void connectClientSide(){
//        try {
//            Socket socket = new Socket("127.0.0.1", 9999);
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//            dos.writeUTF("ping");
//            dos.flush();
//            DataInputStream dis = new DataInputStream(socket.getInputStream());
//            String result = dis.readUTF();
//            System.out.println(result);
//            dis.close();
//            dos.close();
//            socket.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private static Mess mMess;
    private static String lenh = "1. Xem toàn bộ thông tin sinh viên \n2. Thêm điểm của sinh viên\n3. Sửa điểm của sinh viên\n4. Xóa điểm của sinh viên ";
    private static SqliteHelper mSqliteHelper;

    public Client(String serverAndress, int serverPort) throws IOException {
        // tao mot socket tai dia chi serverAndress va serverPort
        socket = new Socket(serverAndress, serverPort);
        // tao 2 stream mot la luong di hai la luong vao
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    // gui tin nhan
    private void send(String message) {
        try {
            out.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Can't send");
        }
    }

    // nhan tin nhan
    @Override
    public void run() {
        byte[] buff = new byte[2048];
        while (true) {
            try {
                // doc tin nhan tu luong vao tra ve so luong byte da doc
                int receivedBytes = in.read(buff);
                if (receivedBytes < 1)
                    break;
                // convert mang buff sang kieu string
                String message = new String(buff, 0, receivedBytes);
                if (message.equals(CommandSv.ALLOW)) {
                    Client.layCauLenhTruyVan(mMess.getKieuCauLenh());
                }
                lenh = CommandSv.COMMAND;
                System.out.println(lenh);
            } catch (IOException e) {
                System.out.println("Can't received");
            }
        }
    }

    public static void requestToServer() {
        Client client;
        try {
            client = new Client("localhost", 9999);
            Scanner scan = new Scanner(System.in);
            // tao ra 2 thread, 1 thread la de nhan tin nhan thead con lai la
            // thread main dung de gui tin nhan
            client.start();
            while (true) {
                String messageTemp = "";
                int message = 0;
                try {
                    System.out.println(lenh);
                    scan.nextLine();
                    messageTemp = scan.nextLine();
                    message = Integer.valueOf(messageTemp);
                } catch (Exception e) {
                    System.out.println("Nhập sai mời nhập lại...");
                    e.printStackTrace();
                    continue;
                }

                switch (message) {
                    case 1: {
                        lenh = CommandSv.WAIT_FOR_SERVER;
                        mMess = new Mess("Người dùng muốn xem tất cả sinh viên", "Select * from SINHVIEN", CommandSv.MODE_GETALL);
                        client.send(mMess.getContent());
                        break;
                    }
                    case 2: {
                        lenh = CommandSv.WAIT_FOR_SERVER;
                        mMess = new Mess("Sinh viên muốn thêm điểm cho môn học", "", CommandSv.MODE_THEM);
                        System.out.println(CommandSv.NHAP_MASINHVIEN);
                        String msv = scan.nextLine();
                        mSqliteHelper = new SqliteHelper();
                        mSqliteHelper.getAllMonHoc(msv);
                        String maMonHoc = scan.nextLine();
                        System.out.println(CommandSv.NHAPDIEM_GIUAKI);
                        double diemGiuaKi = scan.nextDouble();
                        System.out.println(CommandSv.NHAPDIEM_CUOIKI);
                        double diemCuoiKi = scan.nextDouble();

                        // process
                        String commandSql = "INSERT INTO DIEMTHI(DIEM_GIUAKI,DIEM_CUOIKI,ID_MONHOC,ID_SINHVIEN) VALUES (" + diemGiuaKi + ", " + diemCuoiKi + ",'" + maMonHoc + "','" + msv + "')";
                        mMess.setCauLenh(commandSql);
                        client.send(mMess.getContent());
                        break;
                    }
                    case 3: {
                        lenh = CommandSv.WAIT_FOR_SERVER;

                        mMess = new Mess("Người dùng muốn sửa điểm của sinh viên", "", CommandSv.MODE_SUA);
                        System.out.println(CommandSv.NHAP_MASINHVIEN);
                        String msv = scan.nextLine();
                        mSqliteHelper = new SqliteHelper();
                        mSqliteHelper.getAllMonHoc(msv);
                        String maMonHoc = scan.nextLine();
                        System.out.println(CommandSv.NHAPDIEM_GIUAKI);
                        double diemGiuaKi = scan.nextDouble();
                        System.out.println(CommandSv.NHAPDIEM_CUOIKI);
                        double diemCuoiKi = scan.nextDouble();

                        // process
                        String commandSql = "UPDATE DIEMTHI " +
                                "SET DIEM_GIUAKI=" + diemGiuaKi + ",DIEM_CUOIKI=" + diemCuoiKi + ",ID_MONHOC='" + maMonHoc + "',ID_SINHVIEN='" + msv + "'";
                        mMess.setCauLenh(commandSql);
                        client.send(mMess.getContent());
                        break;
                    }
                    case 4: {
                        lenh = CommandSv.WAIT_FOR_SERVER;
                        client.send("Muốn xóa điểm của sinh viên");
                        break;
                    }
                    default:
                        System.out.println("Nhập sai mời nhập lại...");

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void layCauLenhTruyVan(int type) {
        switch (type) {
            case 1: {
                mSqliteHelper = new SqliteHelper();
                mSqliteHelper.getAllSinhVien(mMess.getCauLenh());
                break;
            }
            case 2: {
                mSqliteHelper = new SqliteHelper();

                System.out.println(mMess.getCauLenh());
                mSqliteHelper.addDiemMonHoc(mMess.getCauLenh());
                break;
            }
            case 3: {
                mSqliteHelper = new SqliteHelper();
                mSqliteHelper.suaDiemMonHoc(mMess.getCauLenh());
                break;
            }
            case 4: {
                mSqliteHelper = new SqliteHelper();
                mSqliteHelper.xoaDiemMonHoc(mMess.getCauLenh());
                break;
            }
        }

    }

    public static void main(String[] args) {
        requestToServer();
    }


}
