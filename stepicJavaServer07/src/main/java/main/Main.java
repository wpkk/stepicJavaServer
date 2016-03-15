package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        final int port = 5050;

        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Listening at http://127.0.0.1:" + port);

        Socket socket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String input;
        while ((input = reader.readLine())!= null) {
            logger.info("Message received is {}", input);
            writer.write(input+ "\n");
            writer.flush();
            System.out.println("Here");
            Thread.sleep(1000);
        }
        reader.close();
        writer.close();
        logger.info("Server started");

    }
}
