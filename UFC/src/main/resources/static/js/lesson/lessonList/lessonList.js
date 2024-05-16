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
});

function fetchSearchResults(query, tag = null) {
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}`;
    if (tag) {
        url += `&tag=${encodeURIComponent(tag)}`;
    }

    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.programs').innerHTML = html;
            window.history.pushState({ query: query, tag: tag }, '', url);
        })
        .catch(error => console.error('Error fetching search results:', error));
}

window.addEventListener('popstate', function(event) {
    if (event.state) {
        const query = event.state.query;
        const tag = event.state.tag;
        fetchSearchResults(query, tag, false);
    }
});

function fetchSearchResults(query, tag = null, pushState = true) {
    let url = `/lesson/list/search?query=${encodeURIComponent(query)}`;
    if (tag) {
        url += `&tag=${encodeURIComponent(tag)}`;
    }

    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.programs').innerHTML = html;
            if (pushState) {
                window.history.pushState({ query: query, tag: tag }, '', url);
            }
        })
        .catch(error => console.error('Error fetching search results:', error));
}
