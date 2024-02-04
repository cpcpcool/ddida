function handleDateChange(useDate) {
    var selectedDate = document.getElementById("useStartDate").value;

    if ('${useDate}' == selectedDate) {
        alert('해당 일 예약이 마감되었습니다.');
    }
}

  // 필수값 체크
function submitForm() {
  var requiredInputs = [
    document.getElementById('useTime'),
    document.getElementById('userName'),
    document.getElementById('userPhone'),
    document.getElementById('userEmail'),
  ];

  for (var i = 0; i < requiredInputs.length; i++) {
    if (requiredInputs[i].value.trim() === '') {
      alert('필수값을 입력하세요!');
      return;
    }
  }
      document.getElementById('reserveForm').submit();
}


const textarea = document.getElementById('useTime');

   function autoResize(textarea) {
            textarea.style.height = '45px';
            textarea.style.height = (textarea.scrollHeight) + 'px';
        }

	function getToday(){
	    var date = new Date();
	    var year = date.getFullYear();
	    var month = ("0" + (1 + date.getMonth())).slice(-2);
	    var day = ("0" + date.getDate()).slice(-2);

	    return year + "-" + month + "-" + day;
	}
	
	var todayDate = getToday();
	
	var selectDate = document.getElementById('useDate').value;
	var selectTime = document.getElementById('reserveTime').value;
	
	const table = document.getElementById('reserveTime');
	const cells = table.getElementsByTagName('td');

 // 시간 누르면 버튼 눌림
  for (let i = 0; i < cells.length; i++) {
    cells[i].addEventListener('click', function() {
    	if(selectDate.length !== 0) {
			if (!this.classList.contains('deadline')) {
    			this.classList.toggle('selected');
    	      	getSelectedCells();
    	      	autoResize(textarea);
    	      } 
    	} else {
			alert('날짜를 먼저 선택해주세요');
		}
    });
  }
  
  // 날짜 바꾸면 초기화
  function cancelSelection() {
    const cells = table.getElementsByTagName('td');
    for (let i = 0; i < cells.length; i++) {
        cells[i].classList.remove('selected');
        cells[i].classList.remove('deadline');
    }
}


  // 선택된 셀을 확인하는 함수
  function getSelectedCells() {
	  
	const selectedCells = Array.from(table.getElementsByClassName('selected'));
	const selectedCellTexts = selectedCells.map(cell => cell.innerText.trim());
	
	    document.getElementById("useTime").value = selectedCellTexts.join('\n');

  }
  
		document.addEventListener('DOMContentLoaded', function () {
			 var useDate = /*[[${useDate}]]*/ '';
			
			var calendarEl = document.getElementById('calendar');
			
			
			 var events = [];
            var today = new Date();
            today.setHours(0, 0, 0, 0); // 오늘 날짜의 자정으로 설정
            
             // 오늘 날짜에 대한 이벤트 추가
             events.push({
                title : '당일 예약 불가',
                start: moment(today).format('YYYY-MM-DD'),
                backgroundColor: "rgba(0, 185, 186, 0)",
                borderColor : "rgba(0, 185, 186, 0)",
                textColor : "#000000"
            });

            for (var i = 1; i <= 31; i++) { // 예시로 30일 동안의 날짜를 생성
                var currentDate = new Date(today);
                currentDate.setDate(today.getDate() - i);
                var formattedDate = moment(currentDate).format('YYYY-MM-DD');

                events.push({
                    start: formattedDate,
                    description: '예약 불가 ' + formattedDate,
                     display: 'background'
                });
            }
			
			var calendar = new FullCalendar.Calendar(calendarEl, {
				headerToolbar: {
					start: 'today',
					center: 'title',
					end: 'next'
				},
				selectable: true,
				dragScroll: false,
				editable: false,
				fixedWeekCount: false,
				height: "100%",
				locale: 'ko', // 한국어 설정 
				select: function (selectionInfo) {
					var startDate = selectionInfo.startStr;
					if(todayDate < startDate) {
						document.getElementById("useDate").value = startDate;
						textarea.value = ""; 
   						 autoResize(textarea);
		                selectDate = startDate;
						cancelSelection();
						loadAvailableTimes(startDate);
					} 
				},
				eventBackgroundColor: "#d6d4d4",
				events: events,
			});
			calendar.render();
		});
		
		function fetchReservationData(selectedDate) {
        $.ajax({
            url: '/api/getReservations',
            method: 'GET',
            data: { date: selectedDate },
            success: function (data) {
                markReservedSlots(data.reservedSlots);
            },
            error: function (error) {
                console.error('예약 데이터를 가져오는 중 에러 발생:', error);
            }
        });
    }
    
    function markReservedSlots(reservedSlots) {
        // 예약된 슬롯을 'deadline'으로 표시합니다.
        reservedSlots.forEach(function (slot) {
            var element = document.getElementById('time' + slot); // 슬롯이 숫자라고 가정합니다.
            if (element) {
                element.classList.add('deadline');
            }
        });
    }
