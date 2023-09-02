package com.hbu.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbu.productservice.dao.ProductRequest;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	
	@Container
	static MySQLContainer mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.24"))
		.withDatabaseName("test");
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	static {
		mysqlContainer.start();
	}
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry properties) {
		properties.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
		properties.add("spring.datasource.driverClassName", mysqlContainer::getDriverClassName);
		properties.add("spring.datasource.username", mysqlContainer::getUsername);
		properties.add("spring.datasource.password", mysqlContainer::getPassword);
		properties.add("spring.flyway.enabled", () -> "true");
	}

	@Test
	void shouldCreateProductTest() throws Exception {
		
		ProductRequest productRequest = getProductRequest();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest)))
		.andExpect(status().isCreated());
	
		;
	}
	
	private ProductRequest getProductRequest() {
		
		return ProductRequest.builder()
				.name("Test Product")
				.brand("Test brand")
				.price(0)
				.build();
	}

}
