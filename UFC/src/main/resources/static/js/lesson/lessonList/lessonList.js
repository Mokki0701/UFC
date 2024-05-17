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
            const tag = this.id;
            let currentTags = new URLSearchParams(window.location.search).get('tags') || '';
            let tags = currentTags.split(',').filter(Boolean);

            if (tags.includes(tag)) {
                tags = tags.filter(t => t !== tag);
            } else {
                tags.push(tag);
            }

            const tagString = tags.join(',');
            console.log(tags);
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
            const tag = new URLSearchParams(window.location.search).get('tags') || null;
            fetchSearchResults(query, tag, page);

            if (target.classList.contains('page-arrow')) {
                const url = window.location.href;
                const urlObj = new URL(url);
                const params = new URLSearchParams(urlObj.search);
                const cp = params.get('cp');
                document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));
                const activePageLink = document.querySelector(`.pagination a.page-number[data-page="${cp}"]`);
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
    let requestUrl = `/lesson/list/search2?query=${encodeURIComponent(query)}&cp=${page}`;
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}&cp=${page}`;
    if (tags) {
        requestUrl += `&tags=${encodeURIComponent(tags)}`;
        url += `&tags=${encodeURIComponent(tags)}`;
    }

    fetch(requestUrl)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.programs').outerHTML = html;
            if (pushState) {
                window.history.pushState({ query: query, tags: tags, page: page }, '', url);
            }
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
