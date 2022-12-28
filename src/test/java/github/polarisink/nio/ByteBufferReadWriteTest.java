<<<<<<<< HEAD:src/test/java/github/polarisink/ByteBufferReadWriteTest.java
package github.polarisink;
========
package github.polarisink.nio;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ByteBufferReadWriteTest.java

import github.polarisink.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

<<<<<<<< HEAD:src/test/java/github/polarisink/ByteBufferReadWriteTest.java
import static github.polarisink.c1.ByteBufferUtil.*;

========
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ByteBufferReadWriteTest.java
/**
 * @author aries
 * @date 2021/7/18
 */
@Slf4j
public class ByteBufferReadWriteTest {
  @Test
  public void testReadWrite() {
    ByteBuffer buffer = ByteBuffer.allocate(10);
    buffer.put((byte) 0x61);
    ByteBufferUtil.debugAll(buffer);
    buffer.put(new byte[]{0x62, 0x63, 0x64});
    buffer.flip();
    log.info("get1,{}", buffer.get());
    ByteBufferUtil.debugAll(buffer);
  }
}
