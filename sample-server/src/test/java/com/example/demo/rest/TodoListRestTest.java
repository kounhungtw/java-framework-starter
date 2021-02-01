package com.example.demo.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.dto.TodoListDTO;
import com.google.gson.Gson;
import com.system.SampleServerApplication;
import com.system.dto.LoginInfoDTO;
import com.system.dto.LoginResultDTO;
import com.system.exception.WebApplicationErrorMessage;

@RunWith(SpringJUnit4ClassRunner.class)	
public class TodoListRestTest {

	private static String token = null;
	private static Gson gson = new Gson();
	
	@BeforeEach
	public void beforeEach() throws ClientProtocolException, IOException {
		// 直接啟動spring boot server 進行測試，
		// 測時yml配置spring.jpa.hibernate.ddl-auto，可使資料庫每次都初始化，每次只每一次起停server
		SampleServerApplication.main(new String[] {});
		
		/**
		 * 登錄驗證
		 */
		HttpPost request = new HttpPost("http://localhost:8092/rest/user/login");
		request.setHeader("Content-Type", "application/json");
		
		//構造請求的body
		LoginInfoDTO dto = new LoginInfoDTO();
		dto.setUserName("admin");
		dto.setPassword("test");		
		String jsonStr = gson.toJson(dto);
		StringEntity reqEntity = new StringEntity(jsonStr, "UTF-8");
		request.setEntity(reqEntity);
		
		//執行API
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		
		//驗證結果
		Assert.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		String responseEntityString = EntityUtils.toString(httpResponse.getEntity());
		LoginResultDTO result = gson.fromJson(responseEntityString, LoginResultDTO.class);
		Assert.assertNotNull(result.getToken());
		token = "Bearer " + result.getToken();
	}
	
	@AfterEach
	public void afterEach() {
		SampleServerApplication.close();
	}
	
	@Test
	public void test001() throws ClientProtocolException, IOException {
		
		/**
		 * todolist create
		 */
		HttpPost request2 = new HttpPost("http://localhost:8092/rest/todolist");
		request2.setHeader("Content-Type", "application/json");
		request2.setHeader("Authorization", token);
		TodoListDTO dto2 = new TodoListDTO();
		dto2.setName("test001");
		dto2.setTodoItemList(Collections.emptyList());
		StringEntity reqEntity2 = new StringEntity(gson.toJson(dto2), "UTF-8");
		request2.setEntity(reqEntity2);
		HttpResponse httpResponse2 = HttpClientBuilder.create().build().execute(request2);
		Assert.assertEquals(200, httpResponse2.getStatusLine().getStatusCode());
		
		/**
		 * todolist get
		 */
		HttpGet request3 = new HttpGet("http://localhost:8092/rest/todolist/1");
		request3.setHeader("Content-Type", "application/json");
		request3.setHeader("Authorization",token);
		HttpResponse httpResponse3 = HttpClientBuilder.create().build().execute(request3);
		Assert.assertEquals(200, httpResponse3.getStatusLine().getStatusCode());
		String responseEntityString3 = EntityUtils.toString(httpResponse3.getEntity());
		TodoListDTO result3 = gson.fromJson(responseEntityString3, TodoListDTO.class);
		Assert.assertEquals("test001", result3.getName());
		
		
	}
	
	@Test
	public void test002() throws ClientProtocolException, IOException {		

		/**
		 * todolist create
		 */
		HttpPost request1 = new HttpPost("http://localhost:8092/rest/todolist");
		request1.setHeader("Content-Type", "application/json");
		request1.setHeader("Authorization", token);
		TodoListDTO dto1 = new TodoListDTO();
		dto1.setName("test002");
		dto1.setTodoItemList(Collections.emptyList());
		StringEntity reqEntity1 = new StringEntity(gson.toJson(dto1), "UTF-8");
		request1.setEntity(reqEntity1);
		HttpResponse httpResponse1 = HttpClientBuilder.create().build().execute(request1);
		Assert.assertEquals(200, httpResponse1.getStatusLine().getStatusCode());
		
		
		/**
		 * todolist get
		 */
		HttpGet request2 = new HttpGet("http://localhost:8092/rest/todolist/1");
		request2.setHeader("Content-Type", "application/json");
		request2.setHeader("Authorization",token);
		HttpResponse httpResponse2 = HttpClientBuilder.create().build().execute(request2);
		Assert.assertEquals(200, httpResponse2.getStatusLine().getStatusCode());
		String responseEntityString2 = EntityUtils.toString(httpResponse2.getEntity());
		TodoListDTO result2 = gson.fromJson(responseEntityString2, TodoListDTO.class);
		Assert.assertEquals("test002", result2.getName());
		Assert.assertEquals(new Integer(1), result2.getId());
		
		Integer targetId = result2.getId();
		
		
		/**
		 * todolist delete
		 */
		HttpDelete request3 = new HttpDelete("http://localhost:8092/rest/todolist/"+targetId);
		request3.setHeader("Content-Type", "application/json");
		request3.setHeader("Authorization", token);
		HttpResponse httpResponse3 = HttpClientBuilder.create().build().execute(request3);
		Assert.assertEquals(204, httpResponse3.getStatusLine().getStatusCode());
		
		/**
		 * todolist get again
		 */
		HttpGet request4 = new HttpGet("http://localhost:8092/rest/todolist/1");
		request4.setHeader("Content-Type", "application/json");
		request4.setHeader("Authorization",token);
		HttpResponse httpResponse4 = HttpClientBuilder.create().build().execute(request4);
		Assert.assertEquals(404, httpResponse4.getStatusLine().getStatusCode());
		String responseEntityString4 = EntityUtils.toString(httpResponse4.getEntity());
		WebApplicationErrorMessage result4 = gson.fromJson(responseEntityString4, WebApplicationErrorMessage.class);
		Assert.assertEquals("Cannot find TodoList", result4.getMessage());
	}

}
