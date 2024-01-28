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
	
//	@Value("${api.key}")h
	private String clientSecretKey;

	public Map<String, Object> recommendSpaceList() {
		
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
	
	public Map<String, Object> findSpaceList(int page, int pageSize) {
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
