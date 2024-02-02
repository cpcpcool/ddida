/**
 *  검색 type 필수
 */

$(document).ready(function() {
	$('#search-btn').click(function(event) {
		// 폼이 서버로 전송되기 전에 검증 수행
		if ($('#type').val() === '') {
			alert('시설 구분을 선택하세요.');
			event.preventDefault(); // 폼 전송 중지
		}
	});
});