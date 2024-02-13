 var reply = document.querySelector('.answer-btn');
 var ansArea = document.querySelector('.ans-area');
 var innerContainer = document.querySelector('.inner-container');
 var saveBtn = document.querySelector('.save');
 var answerDiv = document.createElement('div');
 var answerContent = document.getElementById('answer').getAttribute('data-qna-answer');

 if (answerContent) {
     var answerDiv = document.createElement('div');
     answerDiv.className = 'answerDiv';
     answerDiv.innerHTML = answerContent;
     ansArea.appendChild(answerDiv);
     
     reply.style.display = "none";
     saveBtn.style.display = "none";
 } else {
     reply.addEventListener('click', function() {
         console.log('클릭됨');
         const replySection = document.createElement('textarea');
         replySection.className = 'replySection';
         replySection.rows = 3;
         replySection.maxLength = 250;

         replySection.style.width = innerContainer.clientWidth + 'px';

         ansArea.appendChild(replySection);
         ansArea.style.width = innerContainer.clientWidth + 'px';
         reply.style.display = "none";
         saveBtn.style.display = "block";
     });
 }

 var qnaNo = document.getElementById('qnaDetail').getAttribute('data-qna-no');
 saveBtn.addEventListener('click', function() {
     var answerText = document.querySelector('.ans-area textarea').value;
     $.ajax({
         type: 'POST',
         url: '/admin/qna/' + qnaNo,
         data: { answer: answerText },
         success: function(response) {
             console.log('저장 성공', response);
             ansArea.style.display = "none";
             answerDiv.className = 'answerDiv';
 		    answerDiv.innerHTML = answerText;
 		    innerContainer.appendChild(answerDiv);
         },
         error: function(error) {
             console.error('저장 실패', error);
         }
     });
     saveBtn.style.display = "none";
 });