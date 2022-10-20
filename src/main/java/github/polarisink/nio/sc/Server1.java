package github.polarisink.nio.sc;

import github.polarisink.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author aries
 * @date 2021/7/18
 */
@Slf4j
public class Server1 {
  public static void main(String[] args) throws IOException {
    //创建selector，管理多个channel
    Selector selector = Selector.open();
    ByteBuffer buffer = ByteBuffer.allocate(16);
    //创建服务器
    ServerSocketChannel ssc = ServerSocketChannel.open();
    //设置非阻塞模式
    ssc.configureBlocking(false);
    //channel注册在selector中
    //register,事情发生后，通过此可以知道事件和channel的联系
    SelectionKey selectionKey = ssc.register(selector, 0, null);
    //只关注accept事件
    selectionKey.interestOps(SelectionKey.OP_ACCEPT);
    //绑定端口
    ssc.bind(new InetSocketAddress(8080));
    //连接集合
    //会导致cpu 100%,由此引入Selector
    while (true) {
      //阻塞方法，没有事件发生时，就会等待
      selector.select();
      //处理事件,包含所有发生的事件
      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        //处理key时，要从selectKey中集中删除，否则下次处理有问题
        iterator.remove();
        //accept类型
        if (key.isAcceptable()) {
          log.info("key,{}", key);
          ServerSocketChannel channel = (ServerSocketChannel) key.channel();
          SocketChannel sc = channel.accept();
          sc.configureBlocking(false);
          //讲一个byteBuffer作为附加关联到selectionKey上
          SelectionKey register = sc.register(selector, 0, buffer);
          //只关注读事件
          register.interestOps(SelectionKey.OP_READ);
          sc.configureBlocking(false);
          log.info("sc,{}", sc);
          //read事件
        } else if (key.isReadable()) {
          try {
            SocketChannel sc = (SocketChannel) key.channel();
            //获取附件
            ByteBuffer attachment = (ByteBuffer) key.attachment();
            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
            int read = sc.read(byteBuffer);
            if (read == -1) {
              key.cancel();
              //需要扩容
              if (buffer.position() == buffer.limit()) {
                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() << 1);
                newBuffer.flip();
                newBuffer.put(buffer);
                key.attach(newBuffer);
              }
            } else {
              byteBuffer.flip();
              ByteBufferUtil.debugAll(byteBuffer);
              System.out.println(Charset.defaultCharset().decode(buffer));
              //split(buffer);
            }
          } catch (IOException e) {
            //不成功就取消
            key.cancel();
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static void split(ByteBuffer source) {
    source.flip();
    for (int i = 0; i < source.limit(); i++) {
      //完整信息
      if (source.get(i) == '\n') {
        int length = i + 1 - source.position();
        //把信息存进新bytebuffer
        ByteBuffer target = ByteBuffer.allocate(length);
        //从source读，向target写
        for (int j = 0; j < length; j++) {
          target.put(source.get());
        }
        ByteBufferUtil.debugAll(target);
      }
    }
    source.compact();
  }
}
