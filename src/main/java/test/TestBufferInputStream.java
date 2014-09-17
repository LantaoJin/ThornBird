package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestBufferInputStream {
    public static void main(String[] args) throws IOException {
        TestBufferInputStream test = new TestBufferInputStream();
        
        Thread server = new Thread(test.new TestServer());
        server.start();
        
        ExecutorService clientPool = Executors.newFixedThreadPool(2);
        System.out.println("add 100 clients to pool");
        for (int i = 0; i < 100; i++) {
            clientPool.execute(test.new TestClient());
        }
        
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
        
//        byte[] buf = new byte[8192];
//        PrintStream out = new PrintStream(System.out);
//        int len = 0;
//        BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream(), 8192);
//        while((len = in.read(buf)) != -1) {
//            out.write(buf, 0, len);
//        }
    }
    
    class TestServer implements Runnable {
        ExecutorService handlerPool = Executors.newFixedThreadPool(3);
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(11111);
                while(true) {
                    Socket clientSocket = ss.accept();
                    System.out.println("find a client connection " + clientSocket);
                    handlerPool.execute(new TestHandler(clientSocket));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    class TestHandler implements Runnable {

        private Socket client;
        public TestHandler(Socket client) {
            // TODO Auto-generated constructor stub
            this.client = client;
        }
        public void run() {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
                for (int i = 0; i < 10; i++) {
                    System.out.println(i + " server:" + Thread.currentThread().getName() + " send 100");
                    out.writeInt(100);
                    int len = in.readInt();
                    if (len != 101) {
                        System.out.println(i + " server:" + Thread.currentThread().getName() + " not receive 101");
                    }
                    System.out.println(i + " server:" + Thread.currentThread().getName() + " receive 101");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                /**
                 * 下面的try catch 如果注释掉，进程就会block
                 */
                try {
                    client.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    class TestClient implements Runnable {
        Socket socket; 
        public void run() {
            try {
                socket = new Socket("localhost", 11111);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream din = new DataInputStream(socket.getInputStream());
                int len;
                while(true) {
                    len = din.readInt();
                    System.out.println("client:" + Thread.currentThread().getName() + " receive " + len);
                    len++;
                    System.out.println("client:" + Thread.currentThread().getName() + " send " + len);
                    out.writeInt(len);
                }
//                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//                String line;
//                while ((line = in.readLine()) != null) {
//                    out.write(line.getBytes());
//                }
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
    }
}
