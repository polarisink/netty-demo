package com.lqs.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author lqs
 * @date 2022/9/18
 */
public class PathsAndFilesTest {
  public static void main(String[] args) {
    //使用相对路径,user.dir环境变量来定位
    Path path = Paths.get("to.txt");
    //绝对路径
    Path path2 = Paths.get("/Users/lqs/Desktop/WechatIMG16.jpeg");
    //拼接路径
    Path path3 = Paths.get("/Users/lqs/", "Desktop");
    System.out.println(path.toAbsolutePath());
    System.out.println(path2.toAbsolutePath());
    System.out.println(path3.toAbsolutePath());

    System.out.println(Files.exists(path3));

    try {
      Files.createDirectories(path);
      //文件复制
      Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
