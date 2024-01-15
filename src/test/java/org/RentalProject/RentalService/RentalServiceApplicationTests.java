package org.RentalProject.RentalService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
class RentalServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
