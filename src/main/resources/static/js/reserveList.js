/**
 * @author 김민혜
 */

/*$(document).ready(function() {
	loadReserveList();

	function loadReserveList() {
		$.ajax({
			url: "/mypage/reservation",
			type: "GET",
			dataType: "html",
			success: function(data) {
				$('reserveTableBody').html(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.error('Error loading reserveList:', textStatus, errorThrown);
			}
		});
	}

	function updateReserveList(reserveList) {
		$('#reserveTable tbody').empty();

		$.each(reserveList, function(index, reserve) {
			var statusText = reserve.checkout == 1 ? '이용 완료' : '이용 대기';

			$('#reserveTable tbody').append('<tr>' +
				'<td>' + reserve.reserveId + '</td>' +
				'<td><a href="/mypage/reservation/' + reserve.reserveId + '">' + reserve.rsrcNm + '</a></td>' +
				'<td>' + reserve.useDate + '</td>' +
				'<td>' + statusText + '</td>' +
				'</tr>');
		});
	}
});*/