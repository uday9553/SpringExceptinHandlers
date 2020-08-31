package com.uday.springcrudopr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.uday.springcrudopr.model.Addition;

@SpringBootTest
class SpringCrudOperationsApplicationTests {

	Addition addition=new Addition();
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void TestingAddition() {
		assertEquals(6,addition.add(2, 3));
	}
	

}
