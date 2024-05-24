/* 이미지 프리뷰 시작 */

// 가짜 버튼 클릭 시 파일 인풋 선택으로 연결
document.getElementById('customButton').addEventListener('click', function() {
  // 'inputImg' 요소를 클릭하여 파일 선택 대화 상자를 엶
  document.getElementById('inputImg').click();
});


// 'inputImg' 요소에 이벤트 리스너를 추가하여 사용자가 파일을 선택할 때 실행되도록 설정
document.getElementById('inputImg').addEventListener('change', function (event) {
  // 선택한 파일을 가져옴
  const file = event.target.files[0];
  // 파일이 존재하는 경우
  if (file) {
    // FileReader 객체를 생성
    const reader = new FileReader();
    // 파일을 다 읽었을 때 실행될 함수 설정
    reader.onload = function (e) {
      // 'previewImg' 요소를 가져옴
      const previewImg = document.getElementById('previewImg');
      // 읽은 파일의 데이터 URL을 이미지의 src 속성에 설정
      previewImg.src = e.target.result;
      // 이미지를 화면에 표시
      previewImg.style.display = 'block';
    };
    // 파일을 데이터 URL로 읽기 시작
    reader.readAsDataURL(file);
  }
});
/* 이미지 프리뷰 끝 */

