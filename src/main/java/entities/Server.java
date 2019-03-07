package entities;



import service.CommandSv;
import service.Worker;




import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Server {
//   public void connectServiceSide(){
//       try {
//           ServerSocket serverSocket = new ServerSocket(9999);
//           System.out.println("Waiting for client connect...");
//           DataInputStream dis = null;
//           DataOutputStream dos = null;
//           while (true) {
//               Socket socket = serverSocket.accept();
//               System.out.println("Just connected to " + socket.getRemoteSocketAddress());
//               dis = new DataInputStream(socket.getInputStream());
//               dos = new DataOutputStream(socket.getOutputStream());
//               String string = dis.readUTF();
//               if (string.equals("ping")) {
//                   dos.writeUTF("kenhlaptrinh.net");
//               }
//               dos.flush();
//               dos.close();
//               dis.close();
//           }
//       } catch (IOException ex) {
//           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//       }
//
//   }
private ServerSocket serverSocket;
    private InputStream in;
    private OutputStream out;

    public Server(int port) throws UnknownHostException, IOException {
        // tao mot server socket
        serverSocket = new ServerSocket(port);
    }

    private void waitForConnection() {
        Scanner scan = new Scanner(System.in);
        // lang nghe ket noi, trong nay no se chay 2 thread, mot thread chinh
        // dung de gui tin nhan, thread con lai nhan tin nhan
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                // moi lan co ket noi tu phia client toi thi tao mot thang
                // worker
                Worker worker = new Worker(socket);
                worker.start();

                while (true) {
                    int message = scan.nextInt();
                    switch (message){
                        case 1 : {
                            worker.send(CommandSv.ALLOW);
                            break;
                        }
                        case 2 : {
                            worker.send(CommandSv.NOT_ALLOW);
                        }
                        default: {
                            System.out.println("Nhập sai lệnh vui lòng nhập lại...");
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Can't connect");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(9999);
            server.waitForConnection();
            Scanner scan = new Scanner(System.in);
        } catch (IOException e) {
            System.out.println("Can't create server socket");
        }
    }
}
