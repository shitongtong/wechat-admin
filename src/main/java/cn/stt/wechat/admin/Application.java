package cn.stt.wechat.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 创建应用主类：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/8/1.
 */
@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.debug("容器启动成功");
    }
}
