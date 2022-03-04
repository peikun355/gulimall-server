package xyz.peikun.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
class GulimallGatewayApplicationTests{

    @Test
    void contextLoads() {
        try {
            Files.copy(Paths.get("src.txt"), Paths.get("dest.txt"));  //如果dest文件存在就会报错
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Value("${gateway.user.name}")
    String name;
    @Test
    void pzTest(){
        System.out.println("@@"+name);
    }

}
