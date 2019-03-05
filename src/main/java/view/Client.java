package view;

import service.Mess;
import service.SqliteHelper;
import service.SubmitServerListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread  {
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
    private static String lenh = "1. Xem toàn bộ thông tin sinh viên \n2. Thêm điểm của sinh viên\n3. Sửa điểm của sinh viên\n4. Xóa điểm của sinh viên";


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
                if(message.equals("ok")){
                    SqliteHelper mSqliteHelper = new SqliteHelper();
                    mSqliteHelper.getAllSinhVien(mMess.getCauLenh());
                }
                lenh = "1. Xem toàn bộ thông tin sinh viên \n2. Thêm điểm của sinh viên\n3. Sửa điểm của sinh viên\n4. Xóa điểm của sinh viên";
                System.out.println(lenh);
            } catch (IOException e) {
                System.out.println("Can't received");
            }
        }
    }
    public static void requestToServer(){
        Client client;
        try {
            client = new Client("localhost", 1996);
            Scanner scan = new Scanner(System.in);
            // tao ra 2 thread, 1 thread la de nhan tin nhan thead con lai la
            // thread main dung de gui tin nhan
            client.start();
           while(true){
               System.out.println(lenh);

               int message = scan.nextInt();
               lenh = "waiting for server return......";
               switch (message){
                   case 1 : {
                       mMess = new Mess("Muốn xem tất cả sinh viên","Select * from SINHVIEN");
                       client.send(mMess.getContent());
                       break;
                   }
                   case 2 : {
                       client.send("Muốn thêm điểm của sinh viên");
                       break;
                   }
                   case 3 : {
                       client.send("Muốn sửa điểm của sinh viên");
                       break;
                   }
                   case 4 : {
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

    public static void main(String[] args) {
        requestToServer();
    }


}
