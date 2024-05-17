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
            const tag = this.innerText;
            fetchSearchResults(query, tag);
        });
    });

    // 페이지네이션 버튼 클릭 이벤트 리스너
    document.querySelector('.pagination-area').addEventListener('click', function(event) {
        const target = event.target.closest('a');
        if (target && target.hasAttribute('data-page')) {
            event.preventDefault();
            const page = target.getAttribute('data-page');
            const query = document.getElementById('searchQuery').value;
            const tag = document.querySelector('.advanced-search-options button.active')?.innerText || null;
            fetchSearchResults(query, tag, page);

            // active 클래스 추가 및 제거
            document.querySelectorAll('.pagination a').forEach(a => a.classList.remove('active'));
            target.classList.add('active');

        }
    });
});

function fetchSearchResults(query, tag = null, page = 1, pushState = true) {
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}&cp=${page}`;
    if (tag) {
        url += `&tag=${encodeURIComponent(tag)}`;
    }

    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.programs').innerHTML = html;
            if (pushState) {
                window.history.pushState({ query: query, tag: tag, page: page }, '', url);
            }
        })
        .catch(error => console.error('Error fetching search results:', error));
}

window.addEventListener('popstate', function(event) {
    if (event.state) {
        const query = event.state.query;
        const tag = event.state.tag;
        const page = event.state.page;
        fetchSearchResults(query, tag, page, false);
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
