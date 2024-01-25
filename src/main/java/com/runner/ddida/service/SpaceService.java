package com.runner.ddida.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.ddida.vo.ApiMetaVo;
import com.runner.ddida.vo.ApiVo;
import com.runner.ddida.vo.SpaceDetaiMetaVo;
import com.runner.ddida.vo.SpaceDetailVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpaceService {

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
	
	public Map<String, Object> findSpaceList() {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		int page = 1; // 1 페이지당
		int pageSize = 12; // 12개의 데이터
		Map<String, Object> spcaeList = new HashMap<String, Object>();
		
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
					
					spcaeList.put("dataPage", dataPage);
					spcaeList.put("currentPage", page);
					spcaeList.put("totalPages", currentPage);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return spcaeList;
	}
	
	
	
	
	public Map<String, Object> findSpaceDetail() {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";

		try {
			JSONObject obj = new JSONObject(); // Request parameter

			ArrayList<String> rsrc = new ArrayList<String>();
			rsrc.add("BA10A0001104");
			obj.put("rsrcNoList", rsrc);

			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); // get method 생성
			getRequest.setHeader("Content-Type", "application/json");// type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			// response
			CloseableHttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				org.apache.http.HttpEntity entity = response.getEntity(); // Use org.apache.http.HttpEntity
				result = EntityUtils.toString(entity); // 정상 호출
				ObjectMapper objectMapper = new ObjectMapper();
				SpaceDetaiMetaVo spaceDetaiMetaVo = null;

				try {
					spaceDetaiMetaVo = objectMapper.readValue(result.getBytes(), SpaceDetaiMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				List<SpaceDetailVo> data = spaceDetaiMetaVo.getData();
				System.out.println(data);
				
			} else {
				System.out.println("Response Error:");
				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}
			System.out.println(result); // 결과 출력
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		return null;
	}
	
	
	public List<SpaceDetailVo> findDetail(String spaceNo) {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = null;

		try {
			JSONObject obj = new JSONObject(); // Request parameter

			ArrayList<String> rsrc = new ArrayList<String>();
			rsrc.add(spaceNo);
			obj.put("rsrcNoList", rsrc);

			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); // get method 생성
			getRequest.setHeader("Content-Type", "application/json");// type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			// response
			CloseableHttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				org.apache.http.HttpEntity entity = response.getEntity(); // Use org.apache.http.HttpEntity
				result = EntityUtils.toString(entity); // 정상 호출
				ObjectMapper objectMapper = new ObjectMapper();
				SpaceDetaiMetaVo spaceDetaiMetaVo = null;

				try {
					spaceDetaiMetaVo = objectMapper.readValue(result.getBytes(), SpaceDetaiMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				data = spaceDetaiMetaVo.getData();
				System.out.println(data);
				
			} else {
				System.out.println("Response Error:");
				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}
			System.out.println(result); // 결과 출력
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return data;
	}
	
}
