package tasks;

import static main.Main.socketQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.Socket;

public class Task implements Runnable{

    private static final Logger logger = LogManager.getLogger(Task.class.getName());

    @Override
    public void run() {
        logger.info("Started thread: {},{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        final String finalMessage = "Bue.";

        Socket socket = socketQueue.poll();
        String input;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            while ((input = reader.readLine()) != null) {
                logger.info("I received the following message: {}", input);
                writer.println(input);
                if (input.equals(finalMessage))
                    break;

            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Shutting down thread: {},{}", Thread.currentThread().getName(), Thread.currentThread().getId());
    }
}
