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
	
	public Map<String, Object> recommendSpaceList() {
		String clientSecretKey = "f52a97aeb3db33508088d414ae36a7b9";
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";
		
		Map<String, Object> recmdSpaceList = new HashMap<String, Object>();
		
		try {
			// req
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 100);
			obj.put("pageNo", 1);
			obj.put("ctpvCd", 11);
			obj.put("updBgngYmd", 20230101);
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			
			// api-uri로 get요청 생성
			HttpGetEntity getRequest = new HttpGetEntity(apiURI);
			// get요청에 대한 헤더, 바디 세팅
			getRequest.setHeader("Content-Type", "application/json");
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			
			// res
			CloseableHttpResponse response = client.execute(getRequest);
			// 응답 ok 상태코드 200
			if (response.getStatusLine().getStatusCode() == 200) {
				// 응답에서 Entity 추출 후 Entity를 String으로 변환
				org.apache.http.HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				
				// jackson 라이브러리 사용 JSON데이터 자바객체로
				ObjectMapper objectMapper = new ObjectMapper();
				ApiMetaVo apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
				
				// 데이터 필터링
				List<ApiVo> data = apiMetaVo.getData();
				List<ApiVo> filteredData = 
						data.stream()
							.filter(apiVO -> !apiVO.getRsrcNm().contains("테스트"))
							.filter(apiVO -> !apiVO.getImgFileUrlAddr().isEmpty())
							.filter(apiVO -> !apiVO.getInstUrlAddr().isEmpty())
							.filter(apiVO -> apiVO.getRsrcNm().length() <= 13)
							.limit(12)
							.collect(Collectors.toList());
				
				int totaldata = filteredData.size();
				System.out.println("totaldata : " + totaldata);
				
				recmdSpaceList.put("data", filteredData); 
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return recmdSpaceList;
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
