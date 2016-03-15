package client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args)  {
        final String host = "127.0.0.1";
        final int port = 5050;
        String input;
        try {
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("ASDD\n");
            writer.flush();
//            writer.close();
            System.out.println("Here");
            while ((input = reader.readLine()) != null) {
                System.out.println(input);
                writer.write(input + "\n");
                writer.flush();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
