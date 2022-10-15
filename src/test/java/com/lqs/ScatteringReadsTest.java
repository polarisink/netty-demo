package com.lqs;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static com.lqs.c1.ByteBufferUtil.debugAll;

/**
 * @author aries
 * @date 2021/7/18
 */

@Slf4j
public class ScatteringReadsTest {
  @Test
  public void test() {
    try (FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel()) {
      ByteBuffer buffer1 = ByteBuffer.allocate(3);
      ByteBuffer buffer2 = ByteBuffer.allocate(3);
      ByteBuffer buffer3 = ByteBuffer.allocate(3);
      channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});
      buffer1.flip();
      buffer2.flip();
      buffer3.flip();
      debugAll(buffer1);
      debugAll(buffer2);
      debugAll(buffer3);
    } catch (IOException e) {
    }
  }
}
