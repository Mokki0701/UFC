// alert("연결!")

//호버 상태에 따른 이미지 (빈별, 가득찬 별만 사용)
// 프로젝트에 옮길 경우 경로 확인하기
//객체는 각 별의 상태(빈 별, 반 별, 가득 찬 별)에 따른 이미지 경로를 저장
const starImageSourceMap = {
  empty : '/images/icon_empty_star.png',
  half : '/images/icon_half_star.png',
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

let reviewPoint;

starBackground.addEventListener('mousemove',e=>{

  //lockStarPoint true인 경우(별점 고정)
  if(isLockedPoint()){
    //클릭시 마우스무브 기능을 멈춤(이벤트 중지)
    return;
  }

  const target = e.target;
  const currentUserPoint = e.offsetX;

  // const {point} = target.dataset; //data-point 값
  const point = target.dataset.point;
  
  //이미지들을 배열로 가져왔을때 인덱스값을 맞추기 위해
  const starPointIndex = parseInt(point,10)-1 ;
  //요소의 좌표와 크기에 대한 정보를 반환
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

//수업 코드 가져오기
function search(){
  const lessonCode = lessonSelect.options[lessonSelect.selectedIndex].value;
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
    resetStarPointImages();
  })
}

// 등록한 별점 불러오기 
lessonSelect.addEventListener("change",()=>{
  const lessonCode = lessonSelect.options[lessonSelect.selectedIndex].value;
  //console.log(lessonCode); //코드 나오는거 확인
  fetch('/lesson/dashboard/star?lessonNo='+lessonCode)
  .then(res => res.text())
  .then(result =>{
  console.log(result);
  })
})

//평점제출 reviewBtn
const reviewBtn = document.querySelector("#reviewBtn");
//평점제출 클릭시 search 함수 실행
reviewBtn.addEventListener("click",search);

function renderStarPointImages(payload={}){

  //초기값 할당
  //const {drawableLimitIndex = -1, isOverHalf = false} = payload;

  let drawableLimitIndex = payload.drawableLimitIndex;
  if (drawableLimitIndex === undefined) {
      drawableLimitIndex = -1;
  }

  let isOverHalf = payload.isOverHalf;
  if (isOverHalf === undefined) {
      isOverHalf = false;
  }

  //노드리스트에서 forEach 가능하게 (다른 브라우저에서도)
  //NodeList !== Araay. call을 통해서 함수를 호출하는 객체를 
  // Array 에서 NodeList 객체로 
  Array.prototype.forEach.call(starImages,(starimage,index)=>{
    let imageSource = index < drawableLimitIndex? starImageSourceMap.full : starImageSourceMap.empty;

    if(drawableLimitIndex === index){
      imageSource = isOverHalf? starImageSourceMap.full : starImageSourceMap.full;
    }
    starimage.src = imageSource;
  });

}






//마우스 클릭시 별점 고정(lockStarPoint).
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


