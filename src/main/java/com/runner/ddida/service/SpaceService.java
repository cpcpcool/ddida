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
	
	public Map<String, Object> findSpaceList(int page, int pageSize) {
		String clientSecretKey = "00d4e0aa728e31deb3ee7293fcda8670";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		Map<String, Object> spcaeList = new HashMap<String, Object>();
		
		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);
			
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
						 filteredData.add(apiVo);
				 }
				 
				 int totaldata = filteredData.size();
				 int totalPages = (int) Math.ceil((double) totaldata / pageSize);
				 
					int fromIndex = (page - 1) * pageSize;
					int toIndex = Math.min(page * pageSize, totaldata);
					List<ApiVo> dataPage = filteredData.subList(fromIndex, toIndex);
					
					
					spcaeList.put("dataPage", dataPage);
					spcaeList.put("currentPage", page);
					spcaeList.put("totalPages", totalPages);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return spcaeList;
	}
	
	public Map<String, Object> findSeachList(int page, int pageSize, String search) {
		String clientSecretKey = "00d4e0aa728e31deb3ee7293fcda8670";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		Map<String, Object> spcaeList = new HashMap<String, Object>();
		
		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);
			
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
					 if(apiVo.getRsrcNm().contains(search)) {
						 filteredData.add(apiVo);
					 }
				 }
				 int totaldata = filteredData.size();
				 int totalPages = (int) Math.ceil((double) totaldata / pageSize);
				 
					int fromIndex = (page - 1) * pageSize;
					int toIndex = Math.min(page * pageSize, totaldata);
					List<ApiVo> dataPage = filteredData.subList(fromIndex, toIndex);
					
					System.out.println("totaldata : " + totaldata);
					System.out.println("totalPages : " + totalPages);
					System.out.println("fromIndex : " + fromIndex);
					System.out.println("toIndex : " + toIndex);
					
					spcaeList.put("dataPage", dataPage);
					spcaeList.put("currentPage", page);
					spcaeList.put("totalPages", totalPages);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return spcaeList;
	}
	
	public List<String> findApiVoIdList(int page, int pageSize) {
		String clientSecretKey = "00d4e0aa728e31deb3ee7293fcda8670";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		List<String> ApiVoIdList = new ArrayList<String>();

		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);
			
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
				 
				 ApiVoIdList = new ArrayList<String>();
				 for (ApiVo Api : data) {
					 ApiVoIdList.add(Api.getRsrcNo());
				 }
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ApiVoIdList;
	}
	
	
	public List<SpaceDetailVo> findSports(List<String> ApiVoIdList, String sportsNm) {
		String clientSecretKey = "00d4e0aa728e31deb3ee7293fcda8670";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = new ArrayList<SpaceDetailVo>();
		
		try {
			JSONObject obj = new JSONObject(); // Request parameter
			List<String> rsrc = ApiVoIdList;
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
				
				for(SpaceDetailVo list : spaceDetaiMetaVo.getData()) {
					if(list.getRsrcClsNm().equals(sportsNm)) {
						data.add(list);
					}
				}
				
			} else {
				System.out.println("Response Error:");
				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return data;
	}
	
	
	public List<SpaceDetailVo> findDetail(String spaceNo) {
		String clientSecretKey = "00d4e0aa728e31deb3ee7293fcda8670";
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
