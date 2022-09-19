package com.example.spring.db2;

import com.example.spring.db2.config.JdbcTemplateV1Config;
import com.example.spring.db2.config.JpaConfig;
import com.example.spring.db2.config.MemoryConfig;
import com.example.spring.db2.config.MyBatisConfig;
import com.example.spring.db2.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


@Import({
		JpaConfig.class
//		MyBatisConfig.class
//		JdbcTemplateV1Config.class,
//		MemoryConfig.class
})
@SpringBootApplication(scanBasePackages = "com.example.spring.db2.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

}
