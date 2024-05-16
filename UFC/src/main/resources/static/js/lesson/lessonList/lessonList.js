/* 검색창 드롭다운 기능 시작 */
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
/* 검색창 드롭다운 기능 끝 */
