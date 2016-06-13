package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOMain {
    private static final Logger logger = LogManager.getLogger(NIOMain.class.getName());
    public static void main(String[] args) {

        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",5050);
            logger.info("Server started");
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.configureBlocking(false);
            int ops = serverSocketChannel.validOps();
            SelectionKey selectionKey = serverSocketChannel.register(selector, ops);
            while (true) {
                int numberOfKeys = selector.select();
                System.out.println("numberOfKeys is: " + numberOfKeys);
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {
                        SocketChannel client = serverSocketChannel.accept();
                        logger.info("Connection accepted");
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        logger.info("Reading started");
                        ByteBuffer buffer = ByteBuffer.allocate(64);
                        client.read(buffer);
                        String output = new String(buffer.array()).trim();
                        logger.info("I received the following message: {}", output);
                        client.register(selector, SelectionKey.OP_WRITE);
                        if (output.equals("Bye.")) {
                            client.close();
                        }
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        logger.info("Writing started");
                        ByteBuffer buffer = ByteBuffer.allocate(64);
                        String response = "Test response\r\n";
                        buffer.put(response.getBytes());
                        client.write(buffer);
                        logger.info("Writing finished");
                    }
                    selector.selectedKeys().remove(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
