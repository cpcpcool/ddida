var reply = document.querySelector('.answer-btn');
var innerContainer = document.querySelector('.inner-container');
reply.addEventListener('click', function() {
	console.log('클릭됨');
	const replySection = document.createElement('textarea');
	replySection.rows = 3;
	replySection.maxLength = 150;
	replySection.name = ansForQna;
	innerContainer.appendChild(replySection);
});



/*
// '의견작성' 버튼 클릭하면 반려의견 작성 가능.
const addComment = document.querySelector('.commentButton');
const userInputZone = document.querySelector('.documentBody');

addComment.addEventListener('click', function() {
	const description = document.createElement('span');
	description.textContent = '반려 의견 : ';
	
	const commentSection = document.createElement('textarea');
	commentSection.rows = 4;
	commentSection.maxLength = 150;
	commentSection.name = "returnComment";
	
	userInputZone.appendChild(description);
	userInputZone.appendChild(commentSection);
});*/