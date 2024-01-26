/*
// 현재 페이지를 나타내는 정보를 가져오는 함수 (예: body 태그에 data-page 속성으로 설정)
function getCurrentPage() {
    return document.body.getAttribute('data-page');
}

// 페이지가 로드될 때 실행되는 함수
window.onload = function() {
    // 현재 페이지 확인
    var currentPage = getCurrentPage();

    // 투명도를 조절할 메뉴 항목 선택
    var qnaMenu = document.querySelector('.admin-nav a[href="/admin/qna"]');
    var usersMenu = document.querySelector('.admin-nav a[href="/admin/users"]');
    var spaceMenu = document.querySelector('.admin-nav a[href="/admin/space"]');

    // 페이지에 따라 투명도 조절
    if (currentPage.includes('/admin/qna')) {
        // 현재 페이지가 Q&A 페이지인 경우
        usersMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    } else if (currentPage.includes('/admin/users')) {
        // 현재 페이지가 회원 관리 페이지인 경우
        qnaMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    }
};
*/



/*
document.addEventListener('DOMContentLoaded', function() {
// 여기에 현재 페이지 확인 및 투명도 조절 로직을 넣으세요.

// 현재 페이지를 나타내는 정보를 가져오는 함수 (예: body 태그에 data-page 속성으로 설정)
function getCurrentPage() {
    return document.body.getAttribute('data-page');
}

// 페이지가 로드될 때 실행되는 함수
    window.onload = function() {
        // 현재 페이지 확인
        var currentPage = getCurrentPage();

        // 투명도를 조절할 메뉴 항목 선택
        var qnaMenu = document.querySelector('.admin-nav a[href="/admin/qna"]');
        var usersMenu = document.querySelector('.admin-nav a[href="/admin/users"]');
        var spaceMenu = document.querySelector('.admin-nav a[href="/admin/space"]');

        // 페이지에 따라 투명도 조절
        if (currentPage.includes('/admin/qna')) {
            // 현재 페이지가 Q&A 페이지인 경우
            usersMenu.style.opacity = '0.3';
            spaceMenu.style.opacity = '0.3';
        } else if (currentPage.includes('/admin/users')) {
            // 현재 페이지가 회원 관리 페이지인 경우
            qnaMenu.style.opacity = '0.3';
            spaceMenu.style.opacity = '0.3';
        }
        // 다른 페이지에 따른 추가적인 로직도 필요할 수 있습니다.
	};
});
*/


/*
document.addEventListener('DOMContentLoaded', function() {
    // 현재 페이지 확인
    var currentPage = getCurrentPage();

    // 투명도를 조절할 메뉴 항목 선택
    var qnaMenu = document.querySelector('.admin-nav a[href="/admin/qna"]');
    var usersMenu = document.querySelector('.admin-nav a[href="/admin/users"]');
    var spaceMenu = document.querySelector('.admin-nav a[href="/admin/space"]');

    // 페이지에 따라 투명도 조절
    if (currentPage.includes('/admin/qna')) {
        // 현재 페이지가 Q&A 페이지인 경우
        usersMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    } else if (currentPage.includes('/admin/users')) {
        // 현재 페이지가 회원 관리 페이지인 경우
        qnaMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    }
    // 다른 페이지에 따른 추가적인 로직도 필요할 수 있습니다.

    // 페이지가 로드될 때 실행되는 함수
    function getCurrentPage() {
        return document.body.getAttribute('data-page');
    }
});
*/



document.addEventListener('DOMContentLoaded', function() {
    // 페이지가 로드될 때 실행되는 함수
    function getCurrentPage() {
        return document.body.getAttribute('data-page');
    }

    // 현재 페이지 확인
    var currentPage = getCurrentPage();

    // 투명도를 조절할 메뉴 항목 선택
    var qnaMenu = document.querySelector('.admin-nav a[href="/admin/qna"]');
    var usersMenu = document.querySelector('.admin-nav a[href="/admin/users"]');
    var spaceMenu = document.querySelector('.admin-nav a[href="/admin/space"]');

    // 페이지에 따라 투명도 조절
    if (currentPage.includes('/admin/qna', '/admin/qna/1')) {
        // 현재 페이지가 Q&A 페이지인 경우
        usersMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    } else if (currentPage.includes('/admin/users', '/admin/users/1')) {
        // 현재 페이지가 회원 관리 페이지인 경우
        qnaMenu.style.opacity = '0.3';
        spaceMenu.style.opacity = '0.3';
    } else if (currentPage.includes('/admin/space', '/space/detail')) {
        // 현재 페이지가 회원 관리 페이지인 경우
        qnaMenu.style.opacity = '0.3';
        usersMenu.style.opacity = '0.3';
    }
    // 다른 페이지에 따른 추가적인 로직도 필요할 수 있습니다.
});
