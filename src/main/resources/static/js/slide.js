/**
 * @author 박재용
 */

$(document).ready(function() {
	$('.slide-card').slick({
		infinite: true,
		slidesToShow: 3,
		slidesToScroll: 3,
		dots: true,	
		prevArrow: '<a href="#" class="slick-prev"><img src="/img/left-arrow-button.svg" alt="Previous" style="width:55px; position:relative; left:-40px; bottom: 50px;"></a>',
		nextArrow: '<a href="#" class="slick-next"><img src="/img/right-arrow-button.svg" alt="Next" style="width:55px; position:relative; bottom: 50px;"></a>'
	});
});

	