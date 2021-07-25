package com.lqs.write;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author aries
 * @date 2021/7/19
 */

@Slf4j
public class WriteServer {
  public static void main(String[] args) throws IOException {
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);
    Selector selector = Selector.open();
    ssc.register(selector, SelectionKey.OP_ACCEPT);
    ssc.bind(new InetSocketAddress(8080));
    while (true) {
      selector.select();
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        if (key.isAcceptable()) {
          SocketChannel sc = ssc.accept();
          sc.configureBlocking(false);
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < 30000; i++) {
            sb.append("a");
          }
          ByteBuffer buffer = Charset.defaultCharset().encode(CharBuffer.wrap(sb));
          //实际写入的字节数
          while (buffer.hasRemaining()){

          int write = sc.write(buffer);
          log.info("实际写入字节数,{}",write);
          }
        }
      }
    }
  }
}
