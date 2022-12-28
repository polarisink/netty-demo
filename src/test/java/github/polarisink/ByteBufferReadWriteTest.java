package github.polarisink;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

import static github.polarisink.c1.ByteBufferUtil.*;

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
    debugAll(buffer);
    buffer.put(new byte[]{0x62, 0x63, 0x64});
    buffer.flip();
    log.info("get1,{}", buffer.get());
    debugAll(buffer);
  }
}
