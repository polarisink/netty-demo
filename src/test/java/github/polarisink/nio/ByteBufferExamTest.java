<<<<<<<< HEAD:src/test/java/github/polarisink/ByteBufferExamTest.java
package github.polarisink;

========
package github.polarisink.nio;

import github.polarisink.nio.c1.ByteBufferUtil;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ByteBufferExamTest.java
import org.junit.Test;

import java.nio.ByteBuffer;

<<<<<<<< HEAD:src/test/java/github/polarisink/ByteBufferExamTest.java
import static github.polarisink.c1.ByteBufferUtil.*;

========
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ByteBufferExamTest.java
/**
 * @author aries
 * @date 2021/7/18
 */
public class ByteBufferExamTest {
  /**
   * 黏包，半包
   */
  @Test
  public void test() {
    ByteBuffer buffer = ByteBuffer.allocate(32);
    buffer.put("Hello,World\nI am lqs,\nHo".getBytes());
    split(buffer);
    buffer.put("w\nare\nyou?".getBytes());
    split(buffer);
    ByteBufferUtil.debugAll(buffer);
  }

  private void split(ByteBuffer source) {
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
