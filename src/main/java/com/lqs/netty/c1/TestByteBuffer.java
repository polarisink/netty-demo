package com.lqs.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author aries
 * @date 2021/7/17
 */

@Slf4j
public class TestByteBuffer {
  public static void main(String[] args) {
    //获取输入流
    try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
      //获取buffer
      ByteBuffer buffer = ByteBuffer.allocate(10);
      //从channel读数据，写入buffer
      while (true) {
        int len = channel.read(buffer);
        log.info("length,{}", len);
        if (len == -1) {
          break;
        }
        //切换至读模式
        buffer.flip();
        //打印buffer
        while (buffer.hasRemaining()) {//有剩余
          byte b = buffer.get();
          log.debug("读到的字节,{}", b);
        }
        //切换至写模式
        buffer.clear();
      }

    } catch (IOException e) {
    }

  }
}