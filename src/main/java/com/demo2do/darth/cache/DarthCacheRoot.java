/**
 * 
 */
package com.demo2do.darth.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;

import com.demo2do.core.security.details.Menu;
import com.demo2do.core.security.details.Role;
import com.demo2do.core.utils.JsonUtils;

/**
 * @author Downpour
 */
public class DarthCacheRoot {
	
	/**
	 * Get all menus as a list
	 * 
	 * @return
	 */
	@Cacheable("menus")
	public List<Menu> getMenus() {
		return JsonUtils.parseArray(new ClassPathResource("menus.json"), Menu.class);
	}
	
	/**
	 * Get all submenus as a list
	 * 
	 * @return
	 */
	@Cacheable("submenus")
	public List<Menu> getSubmenus() {
		return JsonUtils.parseArray(new ClassPathResource("submenus.json"), Menu.class);
	}

	/**
	 * Get all roles group by name and alias
	 * 
	 * @return
	 */
	@Cacheable("roles")
	public Map<String, Role> getRoles() {
		List<Role> roles = JsonUtils.parseArray(new ClassPathResource("roles.json"), Role.class);
		Map<String, Role> result = new LinkedHashMap<String, Role>(roles.size() * 2);
		for(Role role : roles) {
			result.put(role.getName(), role);
			result.put(role.getAlias(), role);
		}
		return result;
	}
	
	/**
	 * 
	 * The actual return type is Map<String, Map<String, List<String>>>
	 * 
	 * @return 
	 */
	@Cacheable("resources")
	public Map<String, Object> getResources() {
		return JsonUtils.parse(new ClassPathResource("resources.json"));
	}
	
}
