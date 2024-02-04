package com.runner.ddida.service;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.ddida.model.Reserve;
import com.runner.ddida.model.ReserveTime;
import com.runner.ddida.repository.ReserveRepository;
import com.runner.ddida.repository.ReserveTimeRepository;
import com.runner.ddida.vo.SpaceDetailMetaVo;
import com.runner.ddida.vo.SpaceDetailVo;
import com.runner.ddida.vo.SpaceMetaVo;
import com.runner.ddida.vo.SpaceVo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Service
@RequiredArgsConstructor
public class SpaceService {

	private final ReserveRepository reserveRepository;
	private final ReserveTimeRepository reserveTimeRepository;

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

	// 체육시설 기본 정보 api (서울 + 경기 + 인천)
	public List<SpaceVo> findDefaultList() {
		List<SpaceVo> spaceDefault = new ArrayList<>();

		List<SpaceVo> seoulList = getSpaceList("11");
		List<SpaceVo> gyeonggiList = getSpaceList("41");
		List<SpaceVo> incheonList = getSpaceList("28");

		spaceDefault.addAll(seoulList);
		spaceDefault.addAll(gyeonggiList);
		spaceDefault.addAll(incheonList);

		return spaceDefault;

	}

	public List<SpaceVo> getSpaceList(String ctpvCd) {

		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<SpaceVo> spaceDefault = new ArrayList<>();

		try {
			// req
			JSONObject obj = new JSONObject();
			obj.put("numOfRows", 1000);
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
				SpaceMetaVo apiMetaVo = objectMapper.readValue(result.getBytes(), SpaceMetaVo.class);

				spaceDefault = apiMetaVo.getData().stream()
						.filter(space -> !space.getRsrcNm().contains("테스트"))
						.filter(space -> !space.getRsrcNm().contains("야외운동기구"))
						.filter(space -> !space.getRsrcNm().contains("아차산"))
						.filter(apiVO -> !apiVO.getImgFileUrlAddr().isEmpty()).collect(Collectors.toList());

				int totaldata = spaceDefault.size();
				System.out.println("totaldata : " + totaldata);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return spaceDefault;
	}

	// 리스트 상세정보 리스트 호출
	public List<SpaceDetailVo> findDetailList(List<SpaceVo> api) {

		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/detail/" + clientSecretKey;
		String result = "";
		
		List<SpaceDetailVo> detailData = null;

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
				SpaceDetailMetaVo spaceDetaiMetaVo = null;
				try {
					spaceDetaiMetaVo = objectMapper.readValue(result.getBytes(), SpaceDetailMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				detailData = spaceDetaiMetaVo.getData();
			} else {
				System.out.println("Response Error:");
				System.out.println(response.getStatusLine().getStatusCode());// 에러 발생
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return detailData;
	}

	// 메인 추천시설
	public List<SpaceVo> recommendSpaceList() {

		// 데이터 필터링
		List<SpaceVo> recmdSpaceList = findDefaultList().stream().filter(apiVO -> !apiVO.getRsrcNm().contains("테스트"))
				.filter(apiVO -> !apiVO.getImgFileUrlAddr().isEmpty())
				.filter(apiVO -> !apiVO.getInstUrlAddr().isEmpty()).filter(apiVO -> apiVO.getRsrcNm().length() <= 13)
				.limit(12).collect(Collectors.toList());

		int totaldata = recmdSpaceList.size();
		System.out.println("totaldata : " + totaldata);

		return recmdSpaceList;
	}

	// 뛰맵 검색 필터
	public List<SpaceVo> searchMapByCriteria(String type, String pay, String region, String spaceNm) {
		List<SpaceVo> allData = findDefaultList();

		List<SpaceVo> filteredList = allData.stream()
				.filter(apiVo -> (type == null || type.isEmpty()) || apiVo.getRsrcNm().contains(type))
				.filter(apiVo -> (region == null || region.isEmpty()) || apiVo.getAddr().contains(region))
				.filter(apiVo -> (spaceNm == null || spaceNm.isEmpty()) || apiVo.getRsrcNm().contains(spaceNm))
				.collect(Collectors.toList());

		if ("N".equals(pay) || "Y".equals(pay)) {
			List<SpaceDetailVo> detailList = findDetailList(filteredList);

			filteredList = filteredList.stream()
					.filter(apiVo -> detailList.stream().anyMatch(
							detail -> apiVo.getRsrcNo().equals(detail.getRsrcNo()) && pay.equals(detail.getFreeYn())))
					.collect(Collectors.toList());
		}

		return filteredList;
	}

	// 메인 검색 필터
	public Page<SpaceVo> searchMainByCriteria(String type, String pay, String region, String spaceNm, Pageable pageable) {
		List<SpaceVo> allData = findDefaultList();

		List<SpaceVo> filteredList = allData.stream()
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

		// List를 Page로 변환
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), filteredList.size());
		Page<SpaceVo> filteredListPage = new PageImpl<>(filteredList.subList(start, end), pageable, filteredList.size());

		return filteredListPage;
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
				SpaceMetaVo apiMetaVo = null;

				try {
					apiMetaVo = objectMapper.readValue(result.getBytes(), SpaceMetaVo.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				List<SpaceVo> data = apiMetaVo.getData();

				rsrcNoList = new ArrayList<String>();
				for (SpaceVo api : data)
					rsrcNoList.add(api.getRsrcNo());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return rsrcNoList;
	}

	// 통합 검색예약 시설 리스트
	public Page<SpaceVo> findSpaceList(Pageable pageable) {
		String apiURI = "https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/" + clientSecretKey;
		String result = "";

		List<SpaceVo> spaceList = findDefaultList();

		int totalData = spaceList.size();
		System.out.println("totalData: " + totalData);

		// List를 Page로 변환
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), spaceList.size());
		Page<SpaceVo> spaceListPage = new PageImpl<>(spaceList.subList(start, end), pageable, spaceList.size());

		return spaceListPage;
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
				SpaceDetailMetaVo spaceDetaiMetaVo = null;
				try {
					spaceDetaiMetaVo = objectMapper.readValue(result.getBytes(), SpaceDetailMetaVo.class);
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
				SpaceDetailMetaVo spaceDetaiMetaVo = null;
				try {
					spaceDetaiMetaVo = objectMapper.readValue(result.getBytes(), SpaceDetailMetaVo.class);
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

	
	@Transactional
	public void saveReserve(Reserve reserve) {
		reserveRepository.save(reserve);
	}

	// 예약 중복 막기
	@Transactional
	public List<Reserve> findReserve() {
		return reserveRepository.findAll();
	}

    public List<Reserve> findByRsrcNo(String rsrcNo) {
		Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(today);
		
		List<Reserve> reserveList = reserveRepository.findByRsrcNoAndUseDateAfter(rsrcNo, dateString);
	    
        return reserveList;
    }
	
	@Transactional
	public List<ReserveTime> findReserveTime() {
		return reserveTimeRepository.findAll();
    }
	
    public List<String> getAvailableTimes(String date, String rsrcNo) {
		
		List<String> useTime = reserveRepository.findUseTimeByRsrcNoAndUseDate(rsrcNo, date);

		for(String use : useTime) {
			System.out.println(use);
		}
		
        return useTime;
    }
	
}
