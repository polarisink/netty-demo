package com.lqs;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author lqs
 * @date 2022/9/18
 */
public class FilesCopyTest {
  public static void main(String[] args) throws IOException {

    String source = "";
    String target = "";
    Files.walk(Paths.get(source)).forEach(path->{
      try {
        String targetName = path.toString().replace(source, target);
        Path path1 = Paths.get(targetName);
        if (Files.isDirectory(path)) {
          Files.createDirectory(path1);
        } else if (Files.isRegularFile(path)) {
          Files.copy(path, path1);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
