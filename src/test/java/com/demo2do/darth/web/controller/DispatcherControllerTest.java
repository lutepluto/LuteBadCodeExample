package com.demo2do.darth.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

/**
 * 
 * @author David
 */
public class DispatcherControllerTest {

	/**
	 * Test receive text message
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRecevieTextMessage() throws Exception {

		Resource resource = new ClassPathResource("/message/request/text-message-sample.xml");
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			HttpPost httppost = new HttpPost("http://localhost:8080/darth/dispatcher/biolife365");
            InputStreamEntity reqEntity = new InputStreamEntity(resource.getInputStream(), -1);
            reqEntity.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
            httppost.setEntity(reqEntity);
            
            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                System.out.println(getStringFromInputStream(resEntity.getContent()));
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
		} finally {
			httpclient.close();
		}
	}
	
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

}
