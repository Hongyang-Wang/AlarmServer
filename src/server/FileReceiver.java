package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream; 
import java.net.Socket;

public class FileReceiver implements Runnable {
	
    private Socket client = null;  
    public FileReceiver(Socket client) {
		// TODO Auto-generated constructor stub
    	this.client = client;  
	}        
    
    //处理通信细节的静态方法，这里主要是方便线程池服务器的调用  
    public static void execute(Socket client){  
        try{  
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            
            // 本地保存路径，文件名会自动从服务器端继承而来。
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            long passedlen = 0;
            long len = 0;

            // 获取文件名
            String file = "data/server/" + inputStream.readUTF();
            DataOutputStream fileOut = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file)));
            len = inputStream.readLong();

            System.out.println("文件的长度为:" + len + "\n");
            System.out.println("开始接收文件!" + "\n");

            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read == -1) {
                    break;
                }
                // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
                System.out.println("文件接收了" + (passedlen * 100 / len)
                        + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + file + "\n");

            fileOut.close();
        } catch(Exception e) {  
            e.printStackTrace();  
        }  
    }  	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		execute(client);
	}

}

