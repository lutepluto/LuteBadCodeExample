/**
 * 
 */
package com.demo2do.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;

/**
 * @author Downpour
 */
public abstract class JsonUtils {
	
	/**
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parse(String jsonText) {
		return (Map<String, Object>) JSON.parse(jsonText);
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> parse(File file) {
		try {
			String jsonText = FileUtils.readFileToString(file);
			return (Map<String, Object>) JSON.parse(jsonText);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map<String, Object> parse(Resource resource) {
		try {
			String jsonText = FileUtils.readFileToString(resource.getFile());
			return (Map<String, Object>) JSON.parse(jsonText);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> parseArray(String jsonText) {
		return (List) JSON.parseArray(jsonText);
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> parseArray(File file) {
		try {
			String jsonText = FileUtils.readFileToString(file);
			return (List) JSON.parseArray(jsonText);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> parseArray(Resource resource) {
		try {
			String jsonText = FileUtils.readFileToString(resource.getFile());
			return (List) JSON.parseArray(jsonText);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param jsonText
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String jsonText, Class<T> clazz) {
		return JSON.parseArray(jsonText, clazz);
	}
	
	/**
	 * 
	 * @param file
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(File file, Class<T> clazz) {
		try {
			String jsonText = FileUtils.readFileToString(file);
			return JSON.parseArray(jsonText, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param resource
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(Resource resource, Class<T> clazz) {
		try {
			String jsonText = FileUtils.readFileToString(resource.getFile());
			return JSON.parseArray(jsonText, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
