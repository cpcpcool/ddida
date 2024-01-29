
// 기본 지도 ==========================================================================
var mapContainer = document.getElementById('map');

var options = {
  center: new kakao.maps.LatLng(37.566826, 126.978656),
  level: 3
};

var map = new kakao.maps.Map(mapContainer, options);


// 키워드 검색 수행 ====================================================================
function searchKeyword() {
  // 사용자가 입력한 키워드 가져오기
  var keyword = document.getElementById('spaceName').value;

  // 카카오 Maps API 초기화
  kakao.maps.load(function () {
    var mapContainer = document.getElementById('map');
    var options = {
      center: new kakao.maps.LatLng(37.566826, 126.978656),
      level: 5
    };
    var map = new kakao.maps.Map(mapContainer, options);

    // Places 객체 생성
    var places = new kakao.maps.services.Places();

    places.keywordSearch(keyword,
      function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
          // 검색 결과가 있을 경우, 각 장소에 대한 마커 생성 및 지도에 추가
          for (var i = 0; i < result.length; i++) {
            var place = result[i];
            var coords = new kakao.maps.LatLng(place.y,
              place.x);

            // 마커 생성
            var marker = new kakao.maps.Marker({
              position: coords
            });

            // 마커를 지도에 추가
            marker.setMap(map);

            // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
            var infowindow = new kakao.maps.InfoWindow(
              {
                zIndex: 1
              });
            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'click', function () {
              // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
              infowindow
                .setContent('<div style="padding:5px;font-size:12px;">'
                  + place.place_name + '</div>');
              infowindow.open(map, marker);
            });
          }
          // 검색 결과가 있을 경우, 첫 번째 장소의 좌표로 지도 이동
          if (result.length > 0) {
            var place = result[0];
            var firstPlace = new kakao.maps.LatLng(place.y, place.x);
            map.setCenter(firstPlace);
            map.setLevel(7);
          }

        } else {
          // 검색 결과가 없을 경우, 사용자에게 알림 등을 처리할 수 있음
          alert('검색 결과가 없습니다.');
        }
      });
  });
}
// 키워드 검색 완료 시 호출되는 콜백함수 입니다 ===================================
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
    // LatLngBounds 객체에 좌표를 추가합니다
    var bounds = new kakao.maps.LatLngBounds();

    for (var i = 0; i < data.length; i++) {
      displayMarker(data[i]);
      bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
    }

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
  }
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {

  // 마커를 생성하고 지도에 표시합니다
  var marker = new kakao.maps.Marker({
    map: map,
    position: new kakao.maps.LatLng(place.y, place.x)
  });
}



// // API 받아오기
// $(document).ready(function () {
//   var uri = 'https://www.eshare.go.kr/eshare-openapi/rsrc/list/010500/';
//   var clientKey = 'f52a97aeb3db33508088d414ae36a7b9';
//   var url = uri + clientKey;
//   var data = {
//     'pageNo': 1,
//     'numOfRows': 20,
//     'updBgngYmd': '20201001',
//     'updEndYmd': '20201231'
//   };
//   $.ajax({
//     url: url,
//     type: 'post',
//     data: data,
//     dataType: 'json',
//     contentType: 'application/json; charset=utf-8',
//     success: function (result) {
//       // 여기서 result는 체육시설 목록에 대한 데이터입니다.
//       // 이 데이터에서 위도와 경도 정보를 추출하여 사용할 수 있습니다.
//       if (result && result.response && result.response.body && result.response.body.items) {
//         var facilities = result.response.body.items.item;

//         // 예시: 첫 번째 체육시설의 위도와 경도 출력
//         if (facilities.length > 0) {
//           var firstFacility = facilities[0];
//           var latitude = firstFacility.lat;
//           var longitude = firstFacility.lot;

//           // 좌표를 이용하여 지도에 마커 표시
//           showMapWithMarker(latitude, longitude);
//         }
//       }
//     },
//     error: function (request, status, error) {
//       alert(error);
//       alert(status);
//       alert(request.status);
//     }
//   });
// });

// 지도에 마커 표시 함수
function showMapWithMarker(latitude, longitude) {
  // 카카오 Maps API 초기화
  kakao.maps.load(function () {
    var mapContainer = document.getElementById('map');

    var options = {
      center: new kakao.maps.LatLng(latitude, longitude),
      level: 3
    };

    var map = new kakao.maps.Map(mapContainer, options);

    // 마커를 생성하고 지도에 표시
    var markerPosition = new kakao.maps.LatLng(latitude, longitude);
    var marker = new kakao.maps.Marker({
      position: markerPosition,
      map: map
    });

    // 마커에 클릭 이벤트 추가
    kakao.maps.event.addListener(marker, 'click', function () {
      // 마커를 클릭했을 때의 동작을 여기에 추가
      alert('마커를 클릭했습니다!');
    });
  });
}