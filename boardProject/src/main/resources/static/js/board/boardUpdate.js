
/* 선택된 이미지 미리보기 */
// const previewList = document.getElementsByClassName("preview"); // img 태그 5개
const inputImageList = document.getElementsByClassName("inputImage"); // input 태그 5개
const deleteImageList = document.getElementsByClassName("delete-image"); // x버튼 5개

// x버튼이 눌러져 삭제된 이미지의 순서를 저장
// * Set : 중복 저장 X, 순서 유지 X
const deleteOrder = new Set();


// 이미지 선택 이후 취소를 누를 경우를 대비한 백업 이미지
// (백업 원리 -> 복제품으로 기존 요소를 대체함)
const backupInputList = new Array(inputImageList.length);


/* ***** input 태그 값 변경 시(파일 선택 시) 실행할 함수 ***** */
/**
 * @param inputImage : 파일이 선택된 input 태그
 * @param order : 이미지 순서
 */
const changeImageFn = (inputImage, order) => {

  // byte단위로 10MB 지정
  const maxSzie = 1024 * 1024 * 10;

  // 업로드된 파일 정보가 담긴 객체를 얻어와 변수에 저장
  const file = inputImage.files[0];


  // ------------- 파일 선택 -> 취소 해서 파일이 없는 경우 ----------------
  if(file == undefined){
    console.log("파일 선택 취소됨");

    // 같은 순서(order)번째 backupInputList 요소를 얻어와 대체하기

    /* 한 번 화면에 추가된 요소는 재사용(다른 곳에 또 추가) 불가능 */

    // 백업본을 한 번 더 복제
    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp); // 백업본을 다음 요소로 추가
    inputImage.remove();    // 원본을 삭제
    inputImage = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입

    // 백업본에 없는 이벤트 리스너를 다시 추가
    inputImage.addEventListener("change", e => {
      changeImageFn(e.target, order);
    })

    return;
  }


  // ---------- 선택된 파일의 크기가 최대 크기(maxSize) 초과 ---------

  if(file.size > maxSzie){
    alert("10MB 이하의 이미지를 선택해주세요");

    // 해당 순서의 backup 요소가 없거나, 
    // 요소는 있는데 값이 없는 경우 == 아무 파일도 선택된적 없을 때
    if(backupInputList[order] == undefined
        || backupInputList[order].value == ''){

      inputImage.value = ""; // 잘못 업로드된 파일 값 삭제
      return;
    }

    // 이전에 정상 선택 -> 다음 선택에서 이미지 크기 초과한 경우
    // 백업본을 한 번 더 복제
    const temp = backupInputList[order].cloneNode(true);

    inputImage.after(temp); // 백업본을 다음 요소로 추가
    inputImage.remove();    // 원본을 삭제
    inputImage = temp;      // 원본 변수에 백업본을 참조할 수 있게 대입
 
    // 백업본에 없는 이벤트 리스너를 다시 추가
    inputImage.addEventListener("change", e => {
     changeImageFn(e.target, order);
    })

    return;
  }


  // ------------ 선택된 이미지 미리보기 --------------

  const reader = new FileReader(); // JS에서 파일을 읽고 저장하는 객체

  // 선택된 파일을 JS로 읽어오기 -> reader.result 변수에 저장됨
  reader.readAsDataURL(file);

  reader.addEventListener("load", e => {
    const url = e.target.result;

    // img 태그(.preview)에 src 속성으로 url 값을 대입
    previewList[order].src = url;

    // 같은 순서 backupInputList에 input태그를 복제해서 대입
    backupInputList[order] = inputImage.cloneNode(true);

    // 이미지가 성공적으로 읽어진 경우
    // deleteOrder에서 해당 순서를 삭제
    deleteOrder.delete(order);
  });
}


for(let i=0 ; i<inputImageList.length ; i++){

  // **** input태그에 이미지가 선택된 경우(값이 변경된 경우) ****
  inputImageList[i].addEventListener("change", e => {
    changeImageFn(e.target, i);
  })


  // **** x 버튼 클릭 시 ****
  deleteImageList[i].addEventListener("click", () => {

    // img, input, backup의 인덱스가 모두 일치한다는 특징을 이용

    // 삭제된 이미지 순서를 deleteOrder에 기록

    // 미리보기 이미지가 있을 때에만
    if(previewList[i].getAttribute("src") != null 
        &&  previewList[i].getAttribute("src") != ""  ){

      // 기존에 이미지가 존재하고 있을 경우에만
      if( orderList.includes(i) ){
        deleteOrder.add(i);
      }
    }

    previewList[i].src       = ""; // 미리보기 이미지 제거
    inputImageList[i].value  = ""; // input에 선택된 파일 제거
    backupInputList[i]       = undefined; // 백업본 제거

  });
}


// -------------------------------------------

// 제출 시 유효성 검사
const boardUpdateFrm = document.querySelector("#boardUpdateFrm");

boardUpdateFrm.addEventListener("submit", e => {

  const boardTitle = document.querySelector("[name='boardTitle']");
  const boardContent = document.querySelector("[name='boardContent']");

  if(boardTitle.value.trim().length == 0){
    alert("제목을 작성해 주세요");
    boardTitle.focus();
    e.preventDefault();
    return;
  }

  if(boardContent.value.trim().length == 0){
    alert("내용을 작성해 주세요");
    boardContent.focus();
    e.preventDefault();
    return;
  }

  // input 태그에 삭제할 이미지 순서(Set)를 배열로 만든 후 대입
  // -> value(문자열) 저장 시 배열은 toString()호출되서 양쪽 []가 사라짐
  document.querySelector("[name='deleteOrder']").value
    = Array.from( deleteOrder );


  document.querySelector("[name='querystring']").value = location.search;
});