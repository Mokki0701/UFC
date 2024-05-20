// alert("연결!")

/* ===== 별점 후기 ==================================== */

//호버 상태에 따른 이미지 (빈별, 가득찬 별만 사용)
const starImageSourceMap = {
  empty : '/images/icon_empty_star.png',
  full : '/images/icon_star.png',
}

//const starContentElement = document.querySelector(".content-star");
const starBackground = document.querySelector(".star_background");
const starImages = document.querySelectorAll(".star");
const starPointResetButton = document.querySelector(".less_remove_star");

//별점이 고정되어 있는지 아닌지 상태를 알려주는 변수
//고정되어 있으면 마우스 이동에 따른 별점 변경을 막음
let lockedStarPoint = false; 

//lockedStarPoint 함수 실행시 true로 바뀜

const lockStarPoint =()=>{
  lockedStarPoint=true;
}

const unlockStarPoint =()=>{
  lockedStarPoint=false;
}

//별점상태반환
const isLockedPoint =()=>{
  return lockedStarPoint;
}

//별점 1~5점 가져오는 변수
let reviewPoint;

starBackground.addEventListener('mousemove',e=>{

  //lockStarPoint true인 경우(별점 고정)
  if(isLockedPoint()){
    //클릭시 마우스무브 기능을 멈춤(이벤트 중지)
    return;
  }

  const target = e.target;
  const currentUserPoint = e.offsetX;

  //html data-* 속성 값 1~5 
  //data-* JS로 받으면 문자열이 됨
  const point = target.dataset.point;
  
  //이미지들을 배열로 가져왔을때 인덱스값을 맞추기 위해
  //parseInt 함수는 첫 번째 매개변수로 주어진 문자열을 정수로 변환
  const starPointIndex = parseInt(point,10)-1 ;

  //요소의 좌표와 크기에 대한 정보를 반환
  //getClientRects() = 대상 요소에 대한 DOMRect 객체의 컬렉션을 반환
  // DOMRect 객체는 요소의 크기와 뷰포트에 대한 상대적인 위치 정보를 포함
  //top, right, bottom, left, width, height 같은 속성들이 포함
  const [starImageClientRect] = target.getClientRects();
  //별모양 넚이값
  const starImageWidth = starImageClientRect.width;

  //마우스 포인터의 위치가 별 중간을 넘어서면 true , false
  const isOverHalf = starImageWidth/2 < currentUserPoint 

  renderStarPointImages({drawableLimitIndex : starPointIndex, isOverHalf})

  //별점 데이터 전송
  reviewPoint = point;

});


//select 태그 가져오기
const lessonSelect = document.querySelector("#lectureSelect");

// 등록한 별점 불러오기 
lessonSelect.addEventListener("change",()=>{
  const lessonCode = lessonSelect.options[lessonSelect.selectedIndex].value;

  const lessonText = lessonSelect.options[lessonSelect.selectedIndex].text;

  const label = document.querySelector("[for='lectureSelect']");
  label.innerText = lessonText;

  if(lessonSelect.value == "강의선택"){
    resetStarPointImages();
    return;
  } 

  //console.log(lessonCode); //코드 나오는거 확인
  fetch('/lesson/dashboard/star?lessonNo='+lessonCode)
  .then(res => res.text())
  .then(result =>{
  
    if(result>0){
      //별점 UI 함수
      setStarRating(result)
    }else{
      //초기화
      resetStarPointImages();
    }
  })
})


//별점 UI 함수
const setStarRating= (result)=>{
  renderStarPointImages({drawableLimitIndex: result - 1, isOverHalf: false});

  //별점 고정
  lockStarPoint();
  
}


//평점제출 reviewBtn
const reviewBtn = document.querySelector("#reviewBtn");
//평점제출 클릭시 search 함수 실행
reviewBtn.addEventListener("click",search);

//수업 코드 가져오고 별점 등록
function search(){
  const lessonCode = lessonSelect.options[lessonSelect.selectedIndex].value;

  if(lessonSelect.value == "강의선택"){
    alert("강의선택을 해주세요");
    return;
  } 
  //console.log(lessonCode);
  //평점 제출 클릭 시 별점이랑 option value 값(수업코드) DB에 보내기 
  const obj = {
  "lessonStar" : reviewPoint,
  "lessonNo" : lessonCode
  };

  fetch("/lesson/dashboard",{
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(resp => resp.text())
  .then(result =>{
    if(result == 0){
      alert("후기 등록 실패");
      return;
    }
    alert("별점이 등록 되었습니다");
    
    //별점 고정 해제
    unlockStarPoint();

    //별점 초기화
    //resetStarPointImages();

    setStarRating();
    
    setDefaultLecture();

  })
}

//별 이미지 full/empty 결정
function renderStarPointImages(payload={}){

  let drawableLimitIndex = payload.drawableLimitIndex;
  if (drawableLimitIndex === undefined) {
      drawableLimitIndex = -1;
  }

  let isOverHalf = payload.isOverHalf;
  if (isOverHalf === undefined) {
      isOverHalf = false;
  }

  //노드리스트에서 forEach 가능하게 (다른 브라우저에서도)
  //NodeList는 배열과 유사하지만, 배열의 모든 메서드를 지원하지 않음
  //NodeList !== Araay. call을 통해서 함수를 호출하는 객체를 
  // Array 에서 NodeList 객체로 
  //starImages == img 태그들
  Array.prototype.forEach.call(starImages,(starimage,index)=>{
    let imageSource = index < drawableLimitIndex? starImageSourceMap.full : starImageSourceMap.empty;

    if(drawableLimitIndex === index){
      imageSource = isOverHalf? starImageSourceMap.full : starImageSourceMap.full;
    }
    starimage.src = imageSource;
  });

}


//마우스 클릭시 별점 고정(lockStarPoint)
starBackground.addEventListener("click",()=>{
  lockStarPoint();
})

//별점제거 
starPointResetButton.addEventListener("click",()=>{
  unlockStarPoint();
  resetStarPointImages();
});

//별점 제거 함수
const resetStarPointImages = ()=>{
  renderStarPointImages();
}

//마우스 아웃일 경우 별점이 고정 상태가 아닌 경우 별점 초기화
starBackground.addEventListener("mouseout",()=>{
  //&& 연산자는 첫번째 연산자가 true인경우 다음 연산자를 반환
  //별점 상태가 고정이 아닌 경우 resetStarPointImages 실행
  !isLockedPoint() && resetStarPointImages();
})


//select의 옵션 1 선택 
const setDefaultLecture = ()=>{

  const selectElement = document.getElementById('lectureSelect');

  //selectElement.length
  //<select> 요소 내에 포함된 <option> 요소의 개수
    if (selectElement.length > 1) { // 옵션이 두 개 이상 있는지 확인 (기본 옵션 포함)
      //select option 요소의 인덱스
      selectElement.selectedIndex = 0; 
    }
}
/* ===== 별점 후기 끝 ==================================== */
/** 즐겨찾기 리스트 ********************* */






/**============================================== */
/* 강사 출석부 리스트 팝업 */
const popupCloseBtn = document.querySelector(".popup_close_btn");
const popupContainer = document.querySelector("#less_attendancePopup");
//const date = document.querySelector("#less_attendanceForm > input");

popupCloseBtn.addEventListener("click",()=>{
  popupContainer.style.display = 'none';
})


// 강의리스트들 가져오기
const lectureLinks = document.querySelectorAll(".lecture-link");

//console.log(lectureLinks);

document.addEventListener('DOMContentLoaded', () => {
  // 처음에 팝업 창 숨기기
  popupContainer.style.display = 'none';

  // 강의 링크에 이벤트 리스너 추가
  const lectureLinks = document.querySelectorAll('.lecture-link');
  lectureLinks.forEach(link => {
      link.addEventListener('click', event => {
          event.preventDefault(); // 기본 링크 동작 방지
          popupContainer.style.display = 'flex'; // 팝업 창 보이기
          const lessonId = link.getAttribute('data-lesson-id'); // 강의 ID 가져오기
          popupContainer.dataset.lessonId = lessonId; // 팝업 컨테이너에 lessonId 저장
      });
  });

  // 닫기 버튼에 이벤트 리스너 추가하여 팝업 창 숨기기
  const closeBtn = document.querySelector('.popup_close_btn');
  closeBtn.addEventListener('click', () => {
      popupContainer.style.display = 'none';
  });

  // 검색 버튼에 이벤트 리스너 추가
  const searchButton = document.getElementById('less_student_list');
  searchButton.addEventListener('click', () => {
      const lessonId = popupContainer.dataset.lessonId; // 팝업 컨테이너에서 lessonId 가져오기
      const attendanceDateInput = document.getElementById('attendanceDateInput');
      const attendanceDate = attendanceDateInput.value; // 선택한 날짜 가져오기
      console.log('lessonId:', lessonId);
      console.log('attendanceDate:', attendanceDate);
      fetchAttendance(lessonId, attendanceDate); // 강의 ID와 날짜로 출석부 데이터 가져오기
  });

  document.getElementById('less_attendanceForm').addEventListener('submit', event => {
      event.preventDefault(); // 기본 폼 제출 동작 방지
      //submitAttendance(); // 폼 제출 처리 함수 호출
  });
});
//===============================================================


function fetchAttendance(lessonId, attendanceDate) {
  fetch(`/lesson/dashboard/attendance?lessonNo=${lessonId}&date=${attendanceDate}`)
      .then(response => response.json())
      .then(data => {
        console.log("data >>>>>>>>> ", JSON.stringify(data))
          // 테이블에 출석부 데이터 표시
          const attendanceTable = document.getElementById('less_attendanceTable');
          attendanceTable.innerHTML = ''; // 기존 테이블 내용 지우기
          // data.forEach(student => {
          //     const row = document.createElement('tr');
          //     const nameCell = document.createElement('td');
          //     nameCell.textContent = student.name; // 데이터 구조에 맞게 조정
          //     const attendanceCell = document.createElement('td');
          //     const attendanceInput = document.createElement('input');
          //     attendanceInput.type = 'checkbox';
          //     attendanceInput.checked = student.isPresent; // 데이터 구조에 맞게 조정
          //     attendanceCell.appendChild(attendanceInput);
          //     row.appendChild(nameCell);
          //     row.appendChild(attendanceCell);
          //     attendanceTable.appendChild(row);
          // });
      })
      .catch(error => console.error('Error fetching attendance data:', error));
}

