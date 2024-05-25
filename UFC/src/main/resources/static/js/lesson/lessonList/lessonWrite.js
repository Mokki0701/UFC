// 이미지 프리뷰 시작
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
// 이미지 프리뷰 끝

// 폼 유효성 검사 시작
document.getElementById('lessonPostFrm').addEventListener('submit', function (event) {
  var isValid = true;
  var missingFields = [];
  var fields = [
    { id: 'lessonTitle', name: '수업명' },
    { id: 'lessonDetail', name: '수업 개요' },
    { id: 'lessonRoom', name: '강의실' },
    { id: 'lessonScheduleDay', name: '수업 요일' },
    { id: 'lessonSchedule', name: '수업 시간' },
    { id: 'lessonStartDate', name: '수업 시작 날짜' },
    { id: 'lessonEndDate', name: '수업 종료 날짜' },
    { id: 'lessonCapacity', name: '수업 정원' },
    { id: 'lessonTuitionFee', name: '수업 수강료' },
    { id: 'lessonMaterialFee', name: '수업 재료비' },
    { id: 'lessonTargetAudience', name: '수업 대상' },
    { id: 'lessonRegisterStart', name: '수업 등록 시작 날짜' },
    { id: 'lessonRegisterEnd', name: '수업 등록 종료 날짜' }
  ];
  
  fields.forEach(function (field) {
    var input = document.getElementById(field.id);
    if (input && !input.value.trim()) {
      isValid = false;
      missingFields.push(field.name);
      input.style.borderColor = 'red';
    } else {
      input.style.borderColor = '';
    }
  });

  // 이미지 파일 선택 여부 확인
  var inputImg = document.getElementById('inputImg');
  if (inputImg && inputImg.files.length === 0) {
    isValid = false;
    missingFields.push('썸네일 이미지');
    inputImg.style.borderColor = 'red';
  }

  if (!isValid) {
    alert('다음 필드를 채워주세요: ' + missingFields.join(', '));
    event.preventDefault();
  }
});
// 폼 유효성 검사 끝