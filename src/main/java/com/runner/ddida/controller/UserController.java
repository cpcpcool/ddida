package com.runner.ddida.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.ddida.vo.ApiMetaVo;
import com.runner.ddida.vo.ApiVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @author 박재용
* @editDate 24.01.22 ~
*/

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	class HttpGetEntity extends HttpEntityEnclosingRequestBase {
		public final static String HTTP_METHOD_GET = "GET";
		public HttpGetEntity(String uri) {
		super();
		setURI(URI.create(uri));
		}
		@Override
		public String getMethod() {
		return HTTP_METHOD_GET;
		}
	}
	
	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@GetMapping("/sports")
	public String spaceList(Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "12") int pageSize) {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);
			obj.put("ctpvCd", 11);
			
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); //get method 생성
			getRequest.setHeader("Content-Type", "application/json");//type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			//response 
			CloseableHttpResponse response = client.execute(getRequest);
			if(response.getStatusLine().getStatusCode() == 200) {
			    org.apache.http.HttpEntity entity = response.getEntity(); // Use org.apache.http.HttpEntity
				result = EntityUtils.toString(entity); //정상 호출
				 
				 ObjectMapper objectMapper = new ObjectMapper();
				 ApiMetaVo apiMetaVo = null;

				 try {
					 apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				 
				 
				 List<ApiVo> data = apiMetaVo.getData();
				 
				 List<ApiVo> filteredData = new ArrayList<>();
				 for (ApiVo apiVo : data) {
				     if (apiVo.getRsrcNm().contains("축구장")) {
				         filteredData.add(apiVo);
				     }
				 }
				 
				 
				 int totaldata = filteredData.size();
				 int currentPage = (int) Math.ceil((double) totaldata / pageSize);
				 
					int fromIndex = (page - 1) * pageSize;
					int toIndex = Math.min(page * pageSize, totaldata);
					List<ApiVo> dataPage = filteredData.subList(fromIndex, toIndex);
					
					System.out.println("totaldata : " + totaldata);
					System.out.println("currentPage : " + currentPage);
					System.out.println("fromIndex : " + fromIndex);
					System.out.println("toIndex : " + toIndex);
					
					model.addAttribute("data", dataPage);
					model.addAttribute("currentPage", page);
					model.addAttribute("totalPages", currentPage);	 
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return "user/sports/spaceList";
	}
	
	
	@GetMapping("/sports2")
	public String spaceList2(Model model) {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		try {
			  JSONObject obj = new JSONObject(); // Request parameter

		     obj.put("rsrcNoList", new String[]{"BA10A0001104", "BA10A0002980", "BA10A0003043"});

			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); //get method 생성
			getRequest.setHeader("Content-Type", "application/json");//type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			//response 
			CloseableHttpResponse response = client.execute(getRequest);
			if(response.getStatusLine().getStatusCode() == 200) {
			    org.apache.http.HttpEntity entity = response.getEntity(); // Use org.apache.http.HttpEntity
				result = EntityUtils.toString(entity); //정상 호출
				 
				 ObjectMapper objectMapper = new ObjectMapper();
				 ApiMetaVo apiMetaVo = null;

				 try {
					 apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				 List<ApiVo> data = apiMetaVo.getData();
				 System.out.println("테2스트");
				 System.out.println(data);
				 model.addAttribute("data", data);
				 
			} else {	
				System.out.println("Response Error:");
				System.out.println(response.getStatusLine().getStatusCode());//에러 발생
			}
			System.out.println(result); //결과 출력
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "user/sports/spaceList2";
	}
	
	
	
	
	
	
	@GetMapping("/sports/1")
	public String spaceDetail() {

		return "user/sports/spaceDetail";
	}
	
	@GetMapping("/sports/1/reserve")
	public String reserveForm() {

		return "user/sports/reserveForm";
	}
	
	@PostMapping("/sports/1/reserve/check")
	public String checkForm() {

		return "user/sports/checkForm";
	}
	
	@PostMapping("/sports/1/complete")
	public String complete() {

		return "user/sports/complete";
	}
	
	
	
	
}