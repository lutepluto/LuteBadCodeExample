/**
 * 
 */
package com.demo2do.core.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Downpour
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/context/applicationContext-*.xml", "classpath:/mock/applicationContext-*.xml" })
public class CompositeCacheAccessorTest {

	@Autowired
	private CompositeCacheAccessor cacheAccessor;
	
	@Test
	public void testGetMenus() {

	}
	
	@Test
	public void testGetSubmenus() {
		
	}
	
	@Test
	public void testGetRoles() {
		
	}
	
	@Test
	public void testGetResources() {
		
	}
	
}
