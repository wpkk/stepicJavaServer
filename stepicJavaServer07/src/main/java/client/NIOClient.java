package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException, InterruptedException  {
        final String host = "127.0.0.1";
        final int port = 5050;
        final int NUMBER_OF_MESSAGES = 5;
        final int MS_TO_SLEEP = 0;
//        String input;
        SocketChannel client = SocketChannel.open(new InetSocketAddress(host, port));
        ByteBuffer buffer;
        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            buffer = ByteBuffer.wrap(("Message #" + (i + 1)).getBytes());
            client.write(buffer);
//            input = reader.readLine();
//            System.out.println("Client received message: " + input);
            buffer.clear();
            Thread.sleep(MS_TO_SLEEP);
        }
        buffer = ByteBuffer.wrap("Bye.".getBytes());
        client.write(buffer);
//        input = reader.readLine();
//        if (input.equals("Bye."))
//            System.out.printf("I am done");

        client.close();
    }
}
