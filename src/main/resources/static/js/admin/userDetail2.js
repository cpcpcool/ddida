/*
function showPlaceholder(input) {
    input.setAttribute('type', 'text');
    input.value = '선택';
    input.onclick = function() {
        input.setAttribute('type', 'date');
        input.value = '';
        input.onclick = null;
    };
}
*/

var showmoreButtons = document.querySelectorAll('.showmore');
showmoreButtons.forEach(function (showmore) {
    showmore.addEventListener('click', function () {
        console.log('클릭됨');

        // 추가될 행의 tbody 가져오기
        var tableId = this.getAttribute('data-table');
        var tbody = document.getElementById(tableId);

        // 행을 2번씩 복제하고 추가
        for (var i = 0; i < 2; i++) {
            // 추가될 행의 첫 번째 행을 복제
            var templateRow = tbody.firstElementChild.cloneNode(true);

            // tbody에 복제된 행 추가
            tbody.appendChild(templateRow);

            // 복제된 행을 보이게 하기
            templateRow.style.display = '';
        }

        // 추가 행의 갯수가 예상보다 많을 경우, 더보기 버튼 숨기고 완료 버튼 보이기
        if (tbody.children.length > 2) {
            showmore.style.display = 'none';

            // tableId가 addRowQna일 때
            if (tableId === 'addRowQna') {
                var showAllQna = document.createElement('button');
                showAllQna.className = 'showAllQna';
                showAllQna.innerText = '모든 문의글 내역';
                showAllQna.addEventListener('click', function () {
                    console.log('모든 문의글 내역 클릭됨');
                    
                    // 여기에 URL과 함께 전달할 값을 설정
				    var destinationUrl = 'http://localhost:8080/admin/users';
				    var parameterName = 'username';
				    var parameterValue = '홍길동';
				
				    // URL에 쿼리 매개변수 추가
				    var urlWithParameters = destinationUrl + '?' + parameterName + '=' + encodeURIComponent(parameterValue);
				
				    // 페이지 이동
				    window.location.href = urlWithParameters;
                });
                showmore.parentNode.appendChild(showAllQna);
            }

            // tableId가 addRowReview일 때
            else if (tableId === 'addRowReview') {
                var showAllReview = document.createElement('button');
                showAllReview.className = 'showAllReview';
                showAllReview.innerText = '모든 후기 내역';
                showAllReview.addEventListener('click', function () {
                    console.log('모든 후기 내역 클릭됨');
                    
                    // 여기에 URL과 함께 전달할 값을 설정
				    var destinationUrl = 'http://localhost:8080/admin/users';
				    var parameterName = 'username';
				    var parameterValue = '홍길동';
				
				    // URL에 쿼리 매개변수 추가
				    var urlWithParameters = destinationUrl + '?' + parameterName + '=' + encodeURIComponent(parameterValue);
				
				    // 페이지 이동
				    window.location.href = urlWithParameters;
                });
                showmore.parentNode.appendChild(showAllReview);
            }

            // tableId가 addRowUsage일 때
            else if (tableId === 'addRowUsage') {
                var showAllUsage = document.createElement('button');
                showAllUsage.className = 'showAllUsage';
                showAllUsage.innerText = '모든 이용 내역';
                showAllUsage.addEventListener('click', function () {
                    console.log('모든 이용 내역 클릭됨');
                    
                    // 여기에 URL과 함께 전달할 값을 설정
				    var destinationUrl = 'http://localhost:8080/admin/users';
				    var parameterName = 'username';
				    var parameterValue = '홍길동';
				
				    // URL에 쿼리 매개변수 추가
				    var urlWithParameters = destinationUrl + '?' + parameterName + '=' + encodeURIComponent(parameterValue);
				
				    // 페이지 이동
				    window.location.href = urlWithParameters;
                });
                showmore.parentNode.appendChild(showAllUsage);
            }
        }
    });
});
