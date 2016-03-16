package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tasks.Task;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    public static final Queue<Socket> socketQueue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws Exception {
        final int THREAD_NUMBER = 10;
        final int port = 5050;
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Listening at http://127.0.0.1:" + port);
        Socket socket;
        logger.info("Server started");

        for (int i = 0; i < 10; i++) {
            socket = serverSocket.accept();
            logger.info("Connection accepted");
            socketQueue.add(socket);
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
            executor.submit(new Task());
        }
    }
}
