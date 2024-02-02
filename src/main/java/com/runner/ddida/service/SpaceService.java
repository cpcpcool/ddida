package com.runner.ddida.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.runner.ddida.model.Reserve;
import com.runner.ddida.model.ReserveTime;
import com.runner.ddida.repository.ReserveRepository;
import com.runner.ddida.repository.ReserveTimeRepository;
import com.runner.ddida.vo.ApiMetaVo;
import com.runner.ddida.vo.ApiVo;
import com.runner.ddida.vo.SpaceDetaiMetaVo;
import com.runner.ddida.vo.SpaceDetailVo;
import com.runner.ddida.vo.SpaceListMetaVo;
import com.runner.ddida.vo.SpaceListVo;

import jakarta.transaction.Transactional;
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

	@Value("${api.key}")
	private String clientSecretKey;
<<<<<<< HEAD

	// =======================================================================================================================

	// 체육시설 기본 정보 api (서울 + 경기 + 인천)
	public List<ApiVo> findDefault() {
		List<ApiVo> spaceDefault = new ArrayList<>();

		List<ApiVo> seoulList = getSpaceList("11");
		List<ApiVo> gyeonggiList = getSpaceList("41");
		List<ApiVo> incheonList = getSpaceList("28");

		spaceDefault.addAll(seoulList);
		spaceDefault.addAll(gyeonggiList);
		spaceDefault.addAll(incheonList);

		return spaceDefault;

	}

	public List<ApiVo> getSpaceList(String ctpvCd) {
=======
	
	private final ReserveRepository reserveRepository;
	private final ReserveTimeRepository reserveTimeRepository;
	
	
	public Map<String, Object> recommendSpaceList() {
		
>>>>>>> origin/3-kbk
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<ApiVo> spaceDefault = null;
		try {
			// req
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 100);
			obj.put("ctpvCd", ctpvCd);

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

				spaceDefault = apiMetaVo.getData();

				int totaldata = spaceDefault.size();
				System.out.println("totaldata : " + totaldata);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return spaceDefault;
	}

	public List<SpaceDetailVo> findDetailList(List<ApiVo> api) {

		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		List<SpaceDetailVo> data = null;

		try {
			JSONObject obj = new JSONObject(); // Request parameter

			ArrayList<String> rsrc = new ArrayList<String>();
			for (int i = 0; i < api.size(); i++) {
				rsrc.add(api.get(i).getRsrcNo());
			}

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

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return data;
	}

	// 자원 분류 api
	public List<SpaceListVo> findClass(List<SpaceDetailVo> detailList) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/class/list/" + clientSecretKey;
		String result = "";

		List<SpaceListVo> classList = new ArrayList<>();

		try {
			JSONObject obj = new JSONObject(); // Request parameter
			obj.put("numOfRows", 100);

			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGetEntity getRequest = new HttpGetEntity(apiURI); // get method 생성
			getRequest.setHeader("Content-Type", "application/json");// type(json/xml)
			getRequest.setHeader("Accept-Charset", "UTF-8");
			getRequest.setEntity(new StringEntity(obj.toString()));
			// res

			// response
			CloseableHttpResponse response = client.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				org.apache.http.HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);

				ObjectMapper objectMapper = new ObjectMapper();
				SpaceListMetaVo spaceListMetaVo = objectMapper.readValue(result.getBytes(), SpaceListMetaVo.class);

				classList = spaceListMetaVo.getData();

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		int totaldata = classList.size();
		System.out.println("totaldata : " + totaldata);

		return classList;

	}

	// 검색 기준필터
	public List<ApiVo> searchByCriteria(String type, String pay, String region, String spaceNm) {
		List<ApiVo> allData = findDefault();

	    List<ApiVo> filteredList = allData.stream()
	            .filter(apiVo -> (type == null || type.isEmpty()) || apiVo.getRsrcNm().contains(type))
	            .filter(apiVo -> (region == null || region.isEmpty()) || apiVo.getAddr().contains(region))
	            .filter(apiVo -> (spaceNm == null || spaceNm.isEmpty()) || apiVo.getRsrcNm().contains(spaceNm))
	            .collect(Collectors.toList());

	    if ("N".equals(pay) || "Y".equals(pay)) {
	        List<SpaceDetailVo> detailList = findDetailList(filteredList);

	        filteredList = filteredList.stream()
	                .filter(apiVo -> detailList.stream()
	                        .anyMatch(detail -> apiVo.getRsrcNo().equals(detail.getRsrcNo()) && pay.equals(detail.getFreeYn())))
	                .collect(Collectors.toList());
	    }

		return filteredList;
	}

	// =======================================================================================================================

	public List<String> findrsrcNoList() {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<String> rsrcNoList = new ArrayList<String>();

		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);

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
				ApiMetaVo apiMetaVo = null;

				try {
					apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				List<ApiVo> data = apiMetaVo.getData();

				rsrcNoList = new ArrayList<String>();
				for (ApiVo api : data)
					rsrcNoList.add(api.getRsrcNo());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return rsrcNoList;
	}

	// 체육시설 세부정보 api
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
<<<<<<< HEAD

	public List<ApiVo> recommendSpaceList() {

		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<ApiVo> recmdSpaceList = new ArrayList<>();
		try {
			// req
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 100);
			obj.put("pageNo", 1);
			obj.put("ctpvCd", 11);

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
				List<ApiVo> filteredData = data.stream().filter(apiVO -> !apiVO.getRsrcNm().contains("테스트"))
						.filter(apiVO -> !apiVO.getImgFileUrlAddr().isEmpty())
						.filter(apiVO -> !apiVO.getInstUrlAddr().isEmpty())
						.filter(apiVO -> apiVO.getRsrcNm().length() <= 13).limit(12).collect(Collectors.toList());

				int totaldata = filteredData.size();
				System.out.println("totaldata : " + totaldata);

				recmdSpaceList = filteredData;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return recmdSpaceList;
	}

	public List<ApiVo> mapSpaceList() {

		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<ApiVo> recmdSpaceList = new ArrayList<>();
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
				List<ApiVo> filteredData = data.stream().filter(apiVO -> !apiVO.getRsrcNm().contains("테스트"))
						.filter(apiVO -> !apiVO.getImgFileUrlAddr().isEmpty())
						.filter(apiVO -> !apiVO.getInstUrlAddr().isEmpty())
						.filter(apiVO -> apiVO.getRsrcNm().length() <= 13).collect(Collectors.toList());

				int totaldata = filteredData.size();
				System.out.println("totaldata : " + totaldata);

				recmdSpaceList = filteredData;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return recmdSpaceList;
	}

	public Map<String, Object> findSpaceList(int page, int pageSize) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		System.out.println(apiURI);

		Map<String, Object> spcaeList = new HashMap<String, Object>();

		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);

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
				ApiMetaVo apiMetaVo = null;

				try {
					apiMetaVo = objectMapper.readValue(result.getBytes(), ApiMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				List<ApiVo> data = apiMetaVo.getData();

				List<ApiVo> filteredData = new ArrayList<>();
				for (ApiVo apiVo : data) {
					if (apiVo.getRsrcNm().contains(search)) {
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
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<String> ApiVoIdList = new ArrayList<String>();

		try {
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
			obj.put("pageNo", 1);

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

				for (SpaceDetailVo list : spaceDetaiMetaVo.getData()) {
					if (list.getRsrcClsNm().equals(sportsNm)) {
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

=======
	
	
	@Transactional
    public void saveReserve(Reserve reserve) {
        reserveRepository.save(reserve);
    }
	
	// 예약 중복 막기
	@Transactional
    public List<Reserve> findReserve() {
        return reserveRepository.findAll();
    }
	
	@Transactional
    public List<ReserveTime> findReserveTime() {
		return reserveTimeRepository.findAll();
    }
	
	
	
	
>>>>>>> origin/3-kbk
}
