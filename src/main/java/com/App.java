package com;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 长不大的韭菜
 * @date 2020/5/30 11:24 下午
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication sp = new SpringApplication(App.class);
        sp.setBannerMode(Banner.Mode.OFF);
        sp.run(args);
    }
}
