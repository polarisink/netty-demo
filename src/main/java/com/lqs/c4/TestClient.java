package com.lqs.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author lqs
 * @date 2022/9/25
 */
public class TestClient {
  public static void main(String[] args) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress("localhost", 8080));
    sc.write(Charset.defaultCharset().encode("1234567890abcdef"));
    System.in.read();
  }
}
