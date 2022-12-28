package github.polarisink.nio.write;

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
          SelectionKey scKey = sc.register(selector, 0, null);
          StringBuilder sb = new StringBuilder();
          //1、向客户端发送大量数据
          for (int i = 0; i < 5000000; i++) {
            sb.append("a");
          }
          ByteBuffer buffer = Charset.defaultCharset().encode(CharBuffer.wrap(sb));
          //2、实际写入的字节数
          int write = sc.write(buffer);
          System.out.println(write);
          //3、判断是否有剩余内容
          if (buffer.hasRemaining()) {
            //4、关注可写事件
            scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
            //scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);
            //5、把未写完的数据挂在scKey上
            scKey.attach(buffer);
          }
        } else if (key.isWritable()) {
          ByteBuffer buffer = (ByteBuffer) key.attachment();
          SocketChannel sc = (SocketChannel) key.channel();
          if (!buffer.hasRemaining()) {
            key.attach(null);
            //不再关注可写事件
            key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
          }
        }
      }
    }
  }
}
