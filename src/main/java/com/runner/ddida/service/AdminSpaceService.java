package com.runner.ddida.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
public class AdminSpaceService {

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
	
	@Value("${api.key}")
	private String clientSecretKey;
	
	public List<String> getRsrcNoList() {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		List<String> rsrcNoList = new ArrayList<String>();
		
		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 98);
//			obj.put("pageNo", 1);
			obj.put("ctpvCd", "11");
			
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
				
				try{
					apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				List<ApiVo> data = apiMetaVo.getData();
				
				List<ApiVo> filteredData = new ArrayList<>();
				for(ApiVo apiVo : data) {
					filteredData.add(apiVo);
				}
				
				for(int i=0; i<filteredData.size(); i++) {
					rsrcNoList.add(i, filteredData.get(i).getRsrcNo());
				}
				
			}
			
		} catch (Exception e) {
//			System.err.println(e.getMessage());
		}
		System.out.println("getRsrcNoList() 실행 - rsrcNoList.size() : " + rsrcNoList.size());
		return rsrcNoList;
	}
	
	
	
	
	public Map<String, Object> show(List<String> rsrcNoGroup, int page, int pageSize) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = new ArrayList<SpaceDetailVo>();
		Map<String, Object> spaceList = new HashMap<String, Object>();
		
		try {
			JSONObject obj = new JSONObject(); // Request parameter
			
			ArrayList<String> rsrcNoList = new ArrayList<String>();
			obj.put("rsrcNoList", rsrcNoGroup);
			
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
				
				// 주소필터링 없이 데이터저장
				data = spaceDetaiMetaVo.getData();
				
//				List<SpaceDetailVo> each = spaceDetaiMetaVo.getData();
//				for(int i=0; i<each.size(); i++) {
//					if(each.get(i).getAddr() != null && !each.get(i).getAddr().isBlank()) {
//						data.add(each.get(i));
//					}
//				}
//				System.out.println("[show] 필터후 data.size() : " + data.size());
				
			} else {
//				System.out.println("Response Error:");
//				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}
//			System.out.println(result); // 결과 출력
			
//			System.out.println(data.isEmpty());
			
			int totaldata = data.size();
			int totalPages = (int) Math.ceil((double) totaldata / pageSize);
			
			int fromIndex = (page - 1) * pageSize;
			int toIndex = Math.min(page * pageSize, totaldata);
			List<SpaceDetailVo> dataPage = data.subList(fromIndex, toIndex);
			
			spaceList.put("dataPage", dataPage);
			spaceList.put("currentPage", page);
			spaceList.put("totalPages", totalPages);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return spaceList;
	}
	
	
	
	public Map<String, Object> showFilterdResult(List<String> rsrcNoGroup, String searchType, String searchWord, int page, int pageSize) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = new ArrayList<SpaceDetailVo>();
		Map<String, Object> spaceList = new HashMap<String, Object>();
		
		try {
			JSONObject obj = new JSONObject(); // Request parameter
			
			ArrayList<String> rsrcNoList = new ArrayList<String>();
			obj.put("rsrcNoList", rsrcNoGroup);
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); // get method 생성
			getRequest.setHeader("Content-Type", "application/json");// type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			// response
			CloseableHttpResponse response = client.execute(getRequest);
			if(response.getStatusLine().getStatusCode() == 200) {
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
				
				System.out.println("[showFilteredResult] 필터전 data.size() : " + data.size());
				
				List<SpaceDetailVo> filteredData = new ArrayList<>();

				outer:
				for(SpaceDetailVo spaceDetail : data) {
				    String valueToCheck = null;

				    switch(searchType) {
			        case "rsrcNo":
			            valueToCheck = spaceDetail.getRsrcNo();
			            break;
			        case "rsrcClsNm":
			            valueToCheck = spaceDetail.getRsrcClsNm();
			            break;
			        case "rsrcNm":
			            valueToCheck = spaceDetail.getRsrcNm();
			            break;
			        case "addr":
			            valueToCheck = spaceDetail.getAddr();
			            break;
			        case "rsrcInstNm":
			            valueToCheck = spaceDetail.getRsrcInstNm();
			            break;
				    }

				    if (valueToCheck != null && valueToCheck.contains(searchWord)) {
				        filteredData.add(spaceDetail);
				        continue outer;
				    }
				}

				data = filteredData;

				System.out.println("[showFilteredResult] 필터후 data.size() : " + data.size());
				
			} else {
//				System.out.println("Response Error:");
//				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}
//			System.out.println(result); // 결과 출력
			
//			System.out.println(data.isEmpty());
			
			int totaldata = data.size();
			int totalPages = (int) Math.ceil((double) totaldata / pageSize);
			
			int fromIndex = (page - 1) * pageSize;
			int toIndex = Math.min(page * pageSize, totaldata);
			List<SpaceDetailVo> dataPage = data.subList(fromIndex, toIndex);
			
			spaceList.put("dataPage", dataPage);
			spaceList.put("currentPage", page);
			spaceList.put("totalPages", totalPages);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return spaceList;
	}
	
	
	public List<SpaceDetailVo> findDetail(String spaceNo) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = null;

		try {
			JSONObject obj = new JSONObject(); // Request parameter

			ArrayList<String> rsrcNoList = new ArrayList<String>();
			rsrcNoList.add(spaceNo);
			obj.put("rsrcNoList", rsrcNoList);

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
				System.out.println(response.getStatusLine().getStatusCode());
			}
//			System.out.println(result); // 결과 출력
			
//			System.out.println(data.isEmpty());
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return data;
	}
	
}
