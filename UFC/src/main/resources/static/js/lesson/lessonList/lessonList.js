document.addEventListener("DOMContentLoaded", function() {
    const searchButton = document.querySelector('.search-bar button[type="submit"]');
    const advancedSearchButtons = document.querySelectorAll('.advanced-search-options button');

    // 검색 버튼 클릭 이벤트 리스너
    searchButton.addEventListener('click', function(event) {
        event.preventDefault();
        const query = document.getElementById('searchQuery').value;
        fetchSearchResults(query);
    });

    // 상세 검색 버튼 클릭 이벤트 리스너
    advancedSearchButtons.forEach(button => {
        button.addEventListener('click', function() {
            const query = document.getElementById('searchQuery').value;

            // 클릭한 태그의 id 값을 얻어 tag에 저장
            const tag = this.id;

            //**** 기존 태그들을 가져오고 현재 태그를 추가 ****
            let currentTags = new URLSearchParams(window.location.search).get('tags') || '';
            const tags = currentTags.split(',').filter(Boolean);
            if (!tags.includes(tag)) {
                tags.push(tag);
            }
            const tagString = tags.join(',');

            console.log(tagString);
            fetchSearchResults(query, tagString);
        });
    });

    // 페이지네이션 버튼 클릭 이벤트 리스너
    document.querySelector('.pagination-area').addEventListener('click', function(event) {
        const target = event.target.closest('a');
        if (target && target.hasAttribute('data-page')) {
            event.preventDefault();
            const page = target.getAttribute('data-page');
            const query = document.getElementById('searchQuery').value;
            //**** 쿼리 스트링에서 태그들 가져오기 ****
            const tag = new URLSearchParams(window.location.search).get('tags') || null;
            fetchSearchResults(query, tag, page);

            // active 클래스 추가 및 제거
            if (target.classList.contains('page-arrow')) {

            // 현재 URL 저장
            const url = window.location.href;

            // URL 객체를 생성
            const urlObj = new URL(url);

            // URLSearchParams 객체를 사용하여 쿼리 파라미터 가져오기
            const params = new URLSearchParams(urlObj.search);

            // 'cp' 파라미터 값 얻어오기
            const cp = params.get('cp');

            // 모든 active 삭제
            document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));


            // 지정한 조건에 맞는 번호 지정
            const activePageLink = document.querySelector(`.pagination a.page-number[data-page="${cp}"]`);


            // active 추가하기
            if (activePageLink) {
                activePageLink.classList.add('active');
            }
            } else {
                document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));
                target.classList.add('active');
            }
        }
    });
});

function fetchSearchResults(query, tags = null, page = 1, pushState = true) {

    //**** tags를 쿼리 파라미터로 추가 ****
    let requestUrl = `/lesson/list/search2?query=${encodeURIComponent(query)}&cp=${page}`;
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}&cp=${page}`;
    if (tags) {
        requestUrl += `&tags=${encodeURIComponent(tags)}`;
    }

        // 검색 결과를 가져오기 위해 fetch API를 사용
        fetch(requestUrl)
        // 응답을 텍스트 형태로 변환
        .then(response => response.text())
         // 변환된 텍스트(HTML)를 처리
        .then(html => {
            // .programs 요소의 innerHTML을 새로운 검색 결과 HTML로 대체
            document.querySelector('.programs').outerHTML = html;
            if (pushState) {
                window.history.pushState({ query: query, tags: tags, page: page }, '', url);
            }

            // 새로운 결과 반환 시 active 클래스 최신화
            const activePageLink = document.querySelector(`.pagination a.page-number[data-page="${page}"]`);
            if (activePageLink) {
                document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));
                activePageLink.classList.add('active');
            }
        })
        .catch(error => console.error('검색 결과 fetch 중 오류 발생', error));
}

window.addEventListener('popstate', function(event) {
    if (event.state) {
        const query = event.state.query;
        const tags = event.state.tags;
        const page = event.state.page;
        fetchSearchResults(query, tags, page, false);
    }
});

/* 상세 검색 드롭 다운 메뉴 시작 */
document.getElementById('advanced-search-toggle').addEventListener('click', function() {
    var options = document.getElementById('advanced-search-options');
    if (options.style.display === 'none' || options.style.display === '') {
        options.style.display = 'block';
        this.innerHTML = '상세검색 &#9650;'; // 위쪽 화살표로 변경
    } else {
        options.style.display = 'none';
        this.innerHTML = '상세검색 &#9660;'; // 아래쪽 화살표로 변경
    }
});
/* 상세 검색 드롭 다운 메뉴 끝 */
