package github.polarisink.sc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author aries
 * @date 2021/7/18
 */
@Slf4j
public class Client {
  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("localhost", 8080));
    SocketAddress localAddress = socketChannel.getLocalAddress();
    log.info("waiting......");
    socketChannel.read(Charset.defaultCharset().encode("hello\nworld\n"));
    System.in.read();
  }
}
