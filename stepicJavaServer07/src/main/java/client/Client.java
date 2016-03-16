package client;

import java.io.*;
import java.net.Socket;
import java.lang.InterruptedException;
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException  {
        final String host = "127.0.0.1";
        final int port = 5050;
        final int NUMBER_OF_MESSAGES = 50;
        final int MS_TO_SLEEP = 1000;
        String input;
        Socket socket = new Socket(host, port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            writer.println("Message #" + (i + 1));
            input = reader.readLine();
            System.out.println("Client received message: " + input);
            Thread.sleep(MS_TO_SLEEP);
        }
        writer.println("Bye.");
        input = reader.readLine();
        if (input.equals("Bye."))
            System.out.printf("I am done");

        reader.close();
        writer.close();
    }
}
