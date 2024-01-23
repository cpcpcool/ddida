/**
 * @author : 박재용
 */

$(function(){
	var depth1 = $(".menu-box > li");
	var userNav = $(".user-nav > .menu-box");
	var subBack = $(".sub-back")
	
	depth1.mouseenter(function(){
		userNav.stop().animate({height:"250px"});
		subBack.stop().animate({height:"180px"});
	}).mouseleave(function(){
		userNav.stop().animate({height:"80px"});
		subBack.stop().animate({height:"0px"});
	})
	
});
