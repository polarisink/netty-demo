package com.lqs.c4;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.lqs.c1.ByteBufferUtil.debugAll;

/**
 * @author lqs
 * @date 2022/9/25
 */
@Slf4j
public class MultiThreadServer {
  public static void main(String[] args) throws IOException {
    Thread.currentThread().setName("boss");
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false);
    Selector boss = Selector.open();
    SelectionKey bossKey = ssc.register(boss, 0, null);
    bossKey.interestOps(SelectionKey.OP_ACCEPT);
    ssc.bind(new InetSocketAddress(8080));
    Worker worker = new Worker("worker-0");
    while (true) {
      boss.select();
      Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
      while (iter.hasNext()) {
        SelectionKey key = iter.next();
        iter.remove();
        if (key.isAcceptable()) {
          SocketChannel sc = ssc.accept();
          sc.configureBlocking(false);
          log.info("connected,{}", sc.getRemoteAddress());
          log.info("before register,{}", sc.getRemoteAddress());
          worker.register(sc);
          sc.register(worker.getWorker(), SelectionKey.OP_READ, null);
          log.info("after register,{}", sc.getRemoteAddress());
        }
      }
    }
  }

  /**
   * 检测读写事件
   */
  @Getter
  static class Worker implements Runnable {
    private Thread thread;
    private String name;
    private Selector worker;
    private volatile boolean start = false;
    private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public Worker(String name) {
      this.name = name;
    }

    public void register(SocketChannel sc) throws IOException {
      if (!start) {
        thread = new Thread(this, name);
        thread.start();
        worker = Selector.open();
        start = true;
      }
      //队列添加任务,但任务没有立刻执行
      queue.add(() -> {
        try {
          sc.register(worker, SelectionKey.OP_READ, null);
        } catch (ClosedChannelException e) {
          e.printStackTrace();
        }
      });
    }

    @Override
    public void run() {
      while (true) {
        try {
          worker.select();
          Runnable task = queue.poll();
          if (task != null) {
            task.run();
          }
          Iterator<SelectionKey> iterator = worker.selectedKeys().iterator();
          while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
              ByteBuffer buffer = ByteBuffer.allocate(16);
              SocketChannel channel = (SocketChannel) key.channel();
              log.debug("read...{}", channel.getRemoteAddress());
              channel.read(buffer);
              buffer.flip();
              debugAll(buffer);
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
