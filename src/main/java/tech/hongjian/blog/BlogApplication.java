package tech.hongjian.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tech.hongjian.blog.consts.BlogConsts;

// 开启注解事务
@EnableTransactionManagement
@tk.mybatis.spring.annotation.MapperScan(BlogConsts.MAPPER_PACKAGE)
@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BlogApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
