package com.itsp.basicspring.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;

public class CodeGenerator {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        InputStream inputStream = CodeGenerator.class.getResourceAsStream(
            "application-dev.yaml"); //This assumes that youryamlfile.yaml is on the classpath
        Map<String, Object> obj = yaml.load(inputStream);
        final String url = obj.get("spring.datasource.url").toString();
        final String root = obj.get("spring.datasource.username").toString();
        final String password = obj.get("spring.datasource.password").toString(); // need to decrypt

        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create(url, root, password).globalConfig(builder -> builder.author("Vincent Zheng") // 设置作者
                .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java").disableOpenDir() // 输出目录
            ).packageConfig(builder -> builder.parent("com.itsp.basicspring") // 设置父包名
                .entity("model") // 设置实体类包名
                .mapper("dao") // 设置 Mapper 接口包名
                .service("service") // 设置 Service 接口包名
                .serviceImpl("service.impl") // 设置 Service 实现类包名
                .xml("mappers") // 设置 Mapper XML 文件包名
            ).strategyConfig(builder -> builder.addInclude("pro", "team").entityBuilder().enableLombok() // 启用 Lombok
                .enableTableFieldAnnotation() // 启用字段注解
                .controllerBuilder().enableRestStyle() // 启用 REST 风格
                .enableHyphenStyle() // url中驼峰转连字符
                .serviceBuilder() // 启用 Service 层的生成
                .formatServiceFileName("%sService") // 设置 service 文件名的格式
                .mapperBuilder() // 启用 Mapper 层生成
                .enableBaseColumnList() // 启用 baseColumnList
                .enableBaseResultMap() // 启用 baseResultMap，简化 XML 配置
            ).templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
            .execute(); // 执行生成
    }
}
