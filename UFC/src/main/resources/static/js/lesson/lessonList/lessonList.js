document.addEventListener("DOMContentLoaded", function() {
    const searchButton = document.querySelector('.search-bar button[type="submit"]');
    const advancedSearchButtons = document.querySelectorAll('.advanced-search-options button:not(.search-button)');

    // 검색 버튼 클릭 이벤트 리스너
    searchButton.addEventListener('click', function(event) {
        event.preventDefault();
        const query = document.getElementById('searchQuery').value;
        fetchSearchResults(query);
    });

    // 상세 검색 버튼 클릭 이벤트 리스너
    document.addEventListener('click', function(event) {

        console.log("확인");
        const target = event.target;
        if (target.closest('.advanced-search-options button:not(.search-button)')) {
            const button = target.closest('.advanced-search-options button:not(.search-button)');
            button.classList.toggle('clicked');
            const query = document.getElementById('searchQuery').value;
            const tag = button.id;
            let currentTags = new URLSearchParams(window.location.search).get('tags') || '';
            let tags = currentTags.split(',').filter(Boolean);

            if (tags.includes(tag)) {
                tags = tags.filter(t => t !== tag);
            } else {
                tags.push(tag);
            }

            const tagString = tags.join(',');
            fetchSearchResults(query, tagString);
        }
    });

    // 페이지네이션 버튼 클릭 이벤트 리스너
    document.addEventListener('click', function(event) {
        const target = event.target.closest('a');
        if (target && target.hasAttribute('data-page')) {
            event.preventDefault();
            const page = target.getAttribute('data-page');
            const query = document.getElementById('searchQuery').value;
            const tag = new URLSearchParams(window.location.search).get('tags') || null;
            fetchSearchResults(query, tag, page);
        }
    });
});

function fetchSearchResults(query, tags = null, page = 1, pushState = true) {
    let requestUrl = `/lesson/list/search2?query=${encodeURIComponent(query)}&cp=${page}`;
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}&cp=${page}`;
    if (tags) {
        requestUrl += `&tags=${encodeURIComponent(tags)}`;
        url += `&tags=${encodeURIComponent(tags)}`;
    }

    fetch(requestUrl)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');

            // 새로운 프로그램 리스트와 페이지네이션 영역을 가져옵니다.
            const newPrograms = doc.querySelector('.programs');
            const newPagination = doc.querySelector('.pagination-area');

            // 기존 프로그램 리스트와 페이지네이션 영역을 업데이트합니다.
            document.querySelector('.programs').replaceWith(newPrograms);
            document.querySelector('.pagination-area').replaceWith(newPagination);

            if (pushState) {
                window.history.pushState({ query: query, tags: tags, page: page }, '', url);
            }

            // 페이지네이션에서 현재 페이지를 강조
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
