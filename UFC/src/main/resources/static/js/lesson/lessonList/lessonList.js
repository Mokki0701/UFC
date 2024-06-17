// DOMContentLoaded 이벤트 리스너는 페이지가 로드될 때
document.addEventListener("DOMContentLoaded", function () {

    /* 페이지가 로드될 때 마다 URL에 있는 태그 값들 다시 clicked class 부여하기 시작 */

    // 현재 페이지의 URL을 가져옴
    const currentUrl = window.location.href;

    // URL 객체를 사용해서 현재 URL을 파싱함
    const url = new URL(currentUrl);

    // URLSearchParams 객체를 생성해서 쿼리 파라미터를 파싱함
    const params = new URLSearchParams(url.search);

    // 'tags' 파라미터의 값을 가져옴
    const tags = params.get('tags');

    // 'tags' 값이 있는지 확인함
    if (tags) {
        // 'tags' 값을 ','로 분리해서 배열로 만듦
        const tagIds = tags.split(',');

        // 각 tagId에 대해 해당 ID를 가진 요소에 'clicked' 클래스를 추가함
        tagIds.forEach(tagId => {
            const element = document.getElementById(tagId);
            if (element) {
                element.classList.add('clicked');
            }
        });
    }
    /* 페이지가 로드될 때 마다 URL에 있는 태그 값들 다시 clicked class 부여하기 끝 */

    /* 상세 검색에 clicked 클래스를 가진 요소가 있다면 display:none에서 block으로 바꾸는 코드 시작 */

    // 'advanced-search-options' ID를 가진 div 요소 선언
    const advancedSearchOptions = document.getElementById('advanced-search-options');

    if (advancedSearchOptions) {

        // 'advanced-search-options' div 내에 'clicked' 클래스를 가진 요소를 찾음
        const clickedElement = advancedSearchOptions.querySelector('.clicked');

        // 'checked' 클래스를 가진 요소가 있는지 확인함
        if (clickedElement) {
            // 'advanced-search-options' div의 display 속성을 block으로 변경함
            advancedSearchOptions.style.display = 'block';
        }
    }

    /* 상세 검색에 clicked 클래스를 가진 요소가 있다면 display:none에서 block으로 바꾸는 코드 끝 */

    // 검색 버튼을 변수로 저장함
    const searchButton = document.querySelector('.search-bar button[type="submit"]');
    // 상세 검색 버튼들을 변수로 저장함
    const advancedSearchButtons = document.querySelectorAll('.advanced-search-options button:not(.search-button)');
    // 상세 검색 토글 버튼을 변수로 저장함
    const advancedSearchTgglBtn = document.querySelector('#advanced-search-toggle');
    // 정렬 버튼과 숨겨진 필드를 변수로 저장함
    const orderBtn = document.getElementById('orderBtn');
    const orderInput = document.getElementById('orderInput');

    // 검색 버튼 클릭 이벤트 리스너를 추가함
    searchButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 동작(폼 제출)을 막음
        const query = document.getElementById('searchQuery').value; // 검색어를 가져옴
        const order = orderInput.value; // 정렬 순서를 가져옴
        fetchSearchResults(query, null, 1, true, order); // 검색 결과를 가져오는 함수를 호출함
    });

    // 각 상세 검색 버튼에 클릭 이벤트 리스너를 추가함
    advancedSearchButtons.forEach(button => {
        button.addEventListener('click', function () {
            this.classList.toggle('clicked'); // 클릭된 상태를 토글함
            const query = document.getElementById('searchQuery').value; // 검색어를 가져옴
            const order = orderInput.value; // 정렬 순서를 가져옴
            const tag = this.id; // 클릭된 버튼의 ID를 가져옴
            let currentTags = new URLSearchParams(window.location.search).get('tags') || ''; // 현재 URL의 태그를 가져옴
            let tags = currentTags.split(',').filter(Boolean); // 태그를 배열로 만듦

            // 클릭된 태그가 포함되어 있으면 제거하고, 포함되어 있지 않으면 추가함
            if (tags.includes(tag)) {
                tags = tags.filter(t => t !== tag);
            } else {
                tags.push(tag);
            }

            const tagString = tags.join(','); // 태그를 다시 문자열로 만듦
            fetchSearchResults(query, tagString, 1, true, order); // 검색 결과를 다시 가져옴
        });
    });

    // 정렬 버튼 클릭 이벤트 리스너를 추가함
    orderBtn.addEventListener('click', function () {
        const currentOrder = this.getAttribute('data-order');
        const newOrder = currentOrder === 'asc' ? 'desc' : 'asc';
        this.setAttribute('data-order', newOrder);
        this.innerHTML = `등록일순 ${newOrder === 'asc' ? '&#9650;' : '&#9660;'}`;
        orderInput.value = newOrder; // 숨겨진 필드의 값을 업데이트
        const query = document.getElementById('searchQuery').value; // 검색어를 가져옴
        const tags = new URLSearchParams(window.location.search).get('tags') || null; // 현재 URL의 태그를 가져옴
        fetchSearchResults(query, tags, 1, true, newOrder); // 검색 결과를 다시 가져옴
    });

    // 페이지네이션 버튼 클릭 이벤트 리스너를 추가함
    document.addEventListener('click', function (event) {
        const target = event.target.closest('a'); // 클릭된 요소가 링크인지 확인함
        if (target && target.hasAttribute('data-page')) { // 링크가 페이지 속성을 가지고 있는지 확인함
            event.preventDefault(); // 기본 동작(링크 이동)을 막음
            const page = target.getAttribute('data-page'); // 페이지 번호를 가져옴
            const query = document.getElementById('searchQuery').value; // 검색어를 가져옴
            const tags = new URLSearchParams(window.location.search).get('tags') || null; // 현재 URL의 태그를 가져옴
            const order = orderInput.value; // 정렬 순서를 가져옴
            fetchSearchResults(query, tags, page, true, order); // 검색 결과를 다시 가져옴
        }
    });
});

// 검색 결과를 가져오는 함수
function fetchSearchResults(query, tags = null, page = 1, pushState = true, order = 'desc') {
    // 요청할 URL을 만듦
    let requestUrl = `/lesson/list/search2?query=${encodeURIComponent(query)}&cp=${page}&order=${order}`;
    // 히스토리에 추가할 URL을 만듦
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}&cp=${page}&order=${order}`;
    if (tags) {
        requestUrl += `&tags=${encodeURIComponent(tags)}`; // 태그가 있으면 URL에 추가
        url += `&tags=${encodeURIComponent(tags)}`; // 히스토리 URL에도 추가
    }

    // 요청을 보냄
    fetch(requestUrl)
        .then(response => response.text()) // 응답을 텍스트로 변환
        .then(html => {
            const parser = new DOMParser(); // DOM 파서를 생성
            const doc = parser.parseFromString(html, 'text/html'); // 응답 HTML을 파싱

            // 새로운 프로그램 리스트와 페이지네이션 영역을 가져옴
            const newPrograms = doc.querySelector('.programs');
            const newPagination = doc.querySelector('.pagination-area');

            // 기존 프로그램 리스트와 페이지네이션 영역을 업데이트함
            document.querySelector('.programs').replaceWith(newPrograms);
            document.querySelector('.pagination-area').replaceWith(newPagination);

            // 히스토리를 업데이트함
            if (pushState) {
                window.history.pushState({ query: query, tags: tags, page: page, order: order }, '', url);
            }

            // 페이지네이션에서 현재 페이지를 강조함
            const activePageLink = document.querySelector(`.pagination a.page-number[data-page="${page}"]`);
            if (activePageLink) {
                document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));
                activePageLink.classList.add('active');
            }
        })
        .catch(error => console.error('검색 결과 fetch 중 오류 발생', error)); // 오류를 처리함
}

// popstate 이벤트 리스너를 추가함
window.addEventListener('popstate', function (event) {
    if (event.state) {
        const query = event.state.query; // 히스토리 상태에서 검색어를 가져옴
        const tags = event.state.tags; // 히스토리 상태에서 태그를 가져옴
        const page = event.state.page; // 히스토리 상태에서 페이지 번호를 가져옴
        const order = event.state.order; // 히스토리 상태에서 정렬 순서를 가져옴
        fetchSearchResults(query, tags, page, false, order); // 검색 결과를 가져옴
    }
});

/* 상세 검색 드롭 다운 메뉴 시작 */
// 상세 검색 토글 버튼 클릭 이벤트 리스너를 추가함
document.getElementById('advanced-search-toggle').addEventListener('click', function () {
    var options = document.getElementById('advanced-search-options'); // 상세 검색 옵션을 가져옴
    if (options.style.display === 'none' || options.style.display === '') { // 상세 검색 옵션의 표시 상태를 확인
        options.style.display = 'block'; // 표시 상태를 변경
        this.innerHTML = '상세검색 &#9650;'; // 위쪽 화살표로 변경
    } else {
        options.style.display = 'none'; // 표시 상태를 변경
        this.innerHTML = '상세검색 &#9660;'; // 아래쪽 화살표로 변경
    }
});
/* 상세 검색 드롭 다운 메뉴 끝 */

/* 즐겨찾기 버튼 시작 */
function addWish(button) {
    // 즐찾 버튼에서 가장 가까운 부모 클래스 tags-container 선택하고 해당 요소 ID 얻어오기
    const parentDiv = button.closest('.tags-container');
    const lessonNo = parentDiv.id;
    
    fetch(`/lesson/list/wishlistAdd?lessonNo=${lessonNo}`)
        .then(response => {
            if (response.ok) {
                // 요청이 성공하면 아이콘의 클래스를 변경
                const icon = button.querySelector('i');
                if (icon.classList.contains('fa-solid')) {
                    icon.classList.remove('fa-solid');
                    icon.classList.add('fa-regular');
                } else {
                    icon.classList.remove('fa-regular');
                    icon.classList.add('fa-solid');
                }
            } else {
                console.error('Request failed:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}

/* 즐겨찾기 버튼 끝 */

