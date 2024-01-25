$(document).ready(function() {
	$('.slide-card').slick({
		infinite: true,
		slidesToShow: 3,
		slidesToScroll: 3,
		dots: true,	
		prevArrow: '<a href="#" class="slick-prev"><img src="/img/left-arrow-button.svg" alt="Previous" style="width:55px; position:relative; left:-40px; bottom: 50px;"></a>',
		nextArrow: '<a href="#" class="slick-next"><img src="/img/right-arrow-button.svg" alt="Next" style="width:55px; position:relative; bottom: 50px;"></a>'
		// 슬라이드 버튼을 사용할지 여부
		/*prevArrow: '<a class="col-1" href="" style="display: inline-block;"><img
					src="/img/left-arrow-button.svg" alt="왼쪽 버튼" style="width: 55px;"></a>', // 이전 버튼 지정
		nextArrow: '<a class="col-1" href="#" style="display: inline-block;"><img
					src="/img/right-arrow-button.svg" alt="오른쪽 버튼" style="width: 55px;"></a>' // 다음 버튼 지정*/
	});
});