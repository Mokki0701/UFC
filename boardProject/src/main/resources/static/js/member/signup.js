/* 다음 주소 API 활용 */
function execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
     

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById("address").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    }
  }).open();
}


/* 주소 검색 버튼 클릭 시 */
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);




// --------------------------------------------------------------------

/* ***** 회원 가입 유효성 검사 ***** */

// 필수 입력 항목의 유효성 검사 여부를 체크하기위한 객체(체크리스트)
// - true   == 해당 항목은 유효한 형식으로 작성됨
// - false  == 해당 항목은 유효하지 않은 형식으로 작성됨
const checkObj = {
  "memberEmail"     : false,
  "memberPw"        : false,
  "memberPwConfirm" : false,
  "memberNickname"  : false,
  "memberTel"       : false,
  "authKey"         : false
};

// ----------------------

/* 이메일 유효성 검사 */

// 1) 이메일 유효성 검사에 사용될 요소 얻어오기
const memberEmail  = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일이 입력(input 이벤트) 될 때 마다 유효성 검사 수행
memberEmail.addEventListener("input", e => {

  // 이메일 인증 후 이메일이 변경된 경우
  checkObj.authKey = false;
  document.querySelector("#authKeyMessage").innerText = "";
  
  // 작성된 이메일 값 얻어오기
  const inputEmail = e.target.value; 

  // 3) 입력된 이메일이 없을 경우
  if(inputEmail.trim().length === 0){

    emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

    // 메시지에 색상을 추가하는 클래스 모두 제거
    emailMessage.classList.remove('confirm', 'error');

    // 이메일 유효성 검사 여부를 false 변경
    checkObj.memberEmail = false;

    // 잘못 입력한 띄어쓰기가 있을 경우 없앰
    memberEmail.value = "";
    
    return;
  }


  // 4) 입력된 이메일이 있을 경우 정규식 검사
  //    (알맞은 형태로 작성 했는가 검사)
  const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 입력 받은 이메일이 정규식과 일치하지 않는 경우
  // (알맞은 이메일 형태가 아닌 경우)
  if( !regExp.test(inputEmail) ){
    emailMessage.innerText = "알맞은 이메일 형식으로 작성해 주세요.";
    emailMessage.classList.add('error'); // 글자 빨간색으로 변경
    emailMessage.classList.remove('confirm'); // 초록색 제거
    checkObj.memberEmail = false; // 유효하지 않은 이메일임을 기록
    return;
  }


  // 5) 유효한 이메일 형식인 경우 중복 검사 수행
  // 비동기(ajax)
  fetch("/member/checkEmail?memberEmail=" + inputEmail)
  .then( response => response.text() )
  .then( count => {
    // count : 1이면 중복, 0이면 중복 아님

    if(count == 1){ // 중복 O
      emailMessage.innerText = "이미 사용중인 이메일 입니다.";
      emailMessage.classList.add('error');
      emailMessage.classList.remove('confirm');
      checkObj.memberEmail = false; // 중복은 유효하지 않음
      return;
    }

    // 중복 X 인 경우
    emailMessage.innerText = "사용 가능한 이메일 입니다";
    emailMessage.classList.add('confirm');
    emailMessage.classList.remove('error');
    checkObj.memberEmail = true; // 유효한 이메일 
  
  })

  .catch( e => {
    // fetch() 수행 중 예외 발생 시 처리
    console.log(e); // 발생한 예외(e) 출력
  })
});


// ---------------------------------


/* 비밀번호 / 비밀번호 확인 유효성 검사 */

// 1) 비밀번호 관련 요소 얻어오기
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");


// 5) 비밀번호, 비밀번호확인이 같은지 검사하는 함수
const checkPw = () => {

  // 같을 경우
  if(memberPw.value === memberPwConfirm.value){
    pwMessage.innerText = "비밀번호가 일치합니다";
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPwConfirm = true; // 비밀번호확인 true
    return;
  }

  pwMessage.innerText = "비밀번호가 일치하지 않습니다";
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  checkObj.memberPwConfirm = false; // 비밀번호확인 false
}



// 2) 비밀번호 유효성 검사
memberPw.addEventListener("input", e => {

  // 입력 받은 비밀번호 값
  const inputPw = e.target.value;

  // 3) 입력되지 않은 경우
  if(inputPw.trim().length === 0){
    pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
    pwMessage.classList.remove("confirm", "error"); // 검은 글씨
    checkObj.memberPw = false; // 비밀번호가 유효하지 않다고 표시
    memberPw.value = ""; // 처음 띄어쓰기 입력 못하게 하기
    return;
  }

  // 4) 입력 받은 비밀번호 정규식 검사
  const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

  if( !regExp.test(inputPw) ){ // 유효하지 않으면
    pwMessage.innerText = "비밀번호가 유효하지 않습니다";
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    checkObj.memberPw = false; 
    return;
  }

  // 유효한 경우
  pwMessage.innerText = "유효한 비밀번호 형식입니다";
  pwMessage.classList.add("confirm");
  pwMessage.classList.remove("error");
  checkObj.memberPw = true;

  // 7) 비밀번호 입력 시에도 확인이랑 비교하는 코드 추가
  
  // 비밀번호 확인에 값이 작성되어 있을 때
  if(memberPwConfirm.value.length > 0){
    checkPw();
  }
});



// 6) 비밀번호 확인 유효성 검사
// 단, 비밀번호(memberPw)가 유효할 때만 검사 수행
memberPwConfirm.addEventListener("input", () => {

  if(checkObj.memberPw){ // memberPw가 유효한 경우
    checkPw(); // 비교하는 함수 수행
    return;
  }

  // memberPw가 유효하지 않은 경우
  // memberPwConfirm 검사 X
  checkObj.memberPwConfirm = false;
});



// ------------------------------

/* 닉네임 유효성 검사 */

const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

memberNickname.addEventListener("input", e => {

  inputNickname = e.target.value;

  // 1) 입력 안한 경우
  if(inputNickname.trim().length === 0){
    nickMessage.innerText = "한글,영어,숫자로만 2~10글자";
    nickMessage.classList.remove("confirm", "error");
    checkObj.memberNickname = false;
    memberNickname.value = "";
    return;
  }


  // 2) 정규식 검사
  const regExp = /^[가-힣\w\d]{2,10}$/;

  if( !regExp.test(inputNickname) ){ // 유효 X
    nickMessage.innerText = "유효하지 않은 닉네임 형식입니다.";
    nickMessage.classList.add("error");
    nickMessage.classList.remove("confirm");
    checkObj.memberNickname = false;
    return;
  }

  // 3) 중복 검사(유효한 경우)
  fetch("/member/checkNickname?memberNickname=" + inputNickname)
  .then(response => response.text())
  .then(count => {

    if(count == 1){ // 중복 O
      nickMessage.innerText = "이미 사용중인 닉네임 입니다.";
      nickMessage.classList.add("error");
      nickMessage.classList.remove("confirm");
      checkObj.memberNickname = false;
      return;
    }

    nickMessage.innerText = "사용 가능한 닉네임 입니다.";
    nickMessage.classList.add("confirm");
    nickMessage.classList.remove("error");
    checkObj.memberNickname = true;

  })

  .catch(e => console.log(e));
  
});


// -----------------------------------------


//  휴대폰번호 정규 표현식
//  /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

/* 전화번호(휴대폰번호) 유효성 검사 */
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberTel.addEventListener("input", e => {

  const inputTel = e.target.value;

  if(inputTel.trim().length === 0){
    telMessage.innerText = "전화번호를 입력해주세요.(- 제외)";
    telMessage.classList.remove("confirm", "error");
    checkObj.memberTel = false;
    memberTel.value = "";
    return;
  }

  const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

  if( !regExp.test(inputTel) ){
    telMessage.innerText = "유효하지 않은 전화번호 형식입니다";
    telMessage.classList.add("error");
    telMessage.classList.remove("confirm");
    checkObj.memberTel = false;
    return;
  }

  telMessage.innerText = "유효한 전화번호 형식입니다";
  telMessage.classList.add("confirm");
  telMessage.classList.remove("error");
  checkObj.memberTel = true;
});



// ----------------------------------------
/* 이메일 인증 */

// 인증번호 받기 버튼
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn"); 

// 인증번호 입력 input
const authKey = document.querySelector("#authKey");

// 인증번호 입력 후 확인하는 버튼
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

// 인증번호 관련 메시지 출력 span
const authKeyMessage = document.querySelector("#authKeyMessage");

let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수

const initMin = 4;  // 타이머 초기 값(분)
const initSec = 59; // 타이머 초기 값(초)
const initTime = "05:00"; 

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;


// 인증번호 받기 버튼 클릭 시
sendAuthKeyBtn.addEventListener("click", () => {

  checkObj.authKey = false;
  document.querySelector("#authKeyMessage").innerText = "";

  // 중복되지 않은 유효한 이메일을 입력한 경우가 아니면
  if ( !checkObj.memberEmail ){
    alert("유효한 이메일 작성 후 클릭해 주세요");
    return;
  }

  // 클릭 시 타이머 숫자 초기화
  min = initMin;
  sec = initSec;

  // 이전 동작중인 인터벌 클리어
  clearInterval(authTimer);

  checkObj.authKey = false; // 인증 유효성 검사 여부 false

  // ***************************************
  // 비동기로 서버에서 메일 보내기

  fetch("/email/signup", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : memberEmail.value
  })
  .then(response => response.text())
  .then(result => {
    if(result == 1){
      console.log("인증 번호 발송 성공");
    }else{
      console.log("인증 번호 발송 실패");
    }
  })


  // ***************************************

  // 메일은 비동기로 서버에서 보내라고 하고
  // 화면에서는 타이머 시작하기

  authKeyMessage.innerText = initTime; // 05:00 세팅
  authKeyMessage.classList.remove("confirm", "error"); // 검정 글씨
  

  alert("인증번호가 발송되었습니다.");

  // setInterval(함수, 지연시간(ms))
  // - 지연시간(ms)만큼 시간이 지날 때 마다 함수 수행

  // clearInterval(Interval이 저장된 변수)
  // - 매개변수로 전달 받은 interval을 멈춤

  // 인증 시간 출력(1초 마다 동작)
  authTimer = setInterval( () => {

    authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    // 0분 0초인 경우("00:00"초 출력 후)
    if(min == 0 && sec == 0){
      checkObj.authKey = false; // 인증 못함
      clearInterval(authTimer); // interval 멈춤
      authKeyMessage.classList.add("error");
      authKeyMessage.classList.remove("confirm");
      return;
    }

    // 0초인 경우(0초를 출력한 후)
    if(sec == 0){
      sec = 60;
      min--; 
    }

    sec--; // 1초 감소

  }, 1000);

});


// 전달 받은 숫자가 10 미만인 경우(한자리) 앞에 0 붙여서 반환
function addZero(number){
  if( number < 10 ) return  "0" + number;
  else              return number;
}






// ----------------------------------------
/* 회원 가입 버튼 클릭 시 전체 유효성 검사 여부 확인 */

const signUpForm = document.querySelector("#signUpForm");

// 회원 가입 폼 제출 시
signUpForm.addEventListener("submit", e => {

  // checkObj의 저장된 값(value)중 
  // 하나라도 false가 있으면 제출 X

  // for ~ in (객체 전용 향상된 for문)

  for(let key in checkObj){ // checkObj 요소의 key 값을 순서대로 꺼내옴

    if( !checkObj[key] ) { // false인 경우 (유효하지 않음)

      let str; // 출력할 메시지를 저장할 변수

      switch(key){

        case "memberEmail": 
          str = "이메일이 유효하지 않습니다"; break;
          
        case "authKey" :
          str = "이메일이 인증되지 않았습니다"; break;

        case "memberPw": 
          str = "비밀번호가 유효하지 않습니다"; break;

        case "memberPwConfirm": 
          str = "비밀번호가 일치하지 않습니다"; break;

        case "memberNickname": 
          str = "닉네임이 유효하지 않습니다"; break;

        case "memberTel": 
          str = "전화번호가 유효하지 않습니다"; break;
      }
      
      alert(str); // 경고창 출력

      document.getElementById(key).focus(); // 초점 이동

      e.preventDefault(); // form 태그 기본 이벤트(제출) 막기
      return;
    }
  }

});


// --------------------------------------------------------

// 인증하기 버튼 클릭 시
// 입력된 인증번호를 비동기로 서버에 전달
// -> 입력된 인증번호와 발급된 인증번호가 같은지 비교
//    같은면 1, 아니면 0 반환
// 단, 타이머가 00:00초가 아닐 경우에만 수행
checkAuthKeyBtn.addEventListener("click", () => {

  if( min === 0 && sec === 0){ // 타이머가 00:00 인 경우
    alert("인증번호 입력 제한시간을 초과하였습니다.");
    return;
  }

  if(authKey.value.length < 6){ // 인증번호가 제대로 입력 안된 경우
    alert("인증번호를 정확히 입력해 주세요");
    return;
  }

  // 입력 받은 이메일, 인증번호로 객체 생성
  const obj = {
    "email"   : memberEmail.value,
    "authKey" : authKey.value
  };

  fetch("/email/checkAuthKey", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj) // obj를 JSON 변경
  })
  .then(resp => resp.text())
  .then(result => {

    // ==   : 값만 비교
    // ===  : 값 + 타입 비교

    if(result == 0){
      alert("인증번호가 일치하지 않습니다");
      checkObj.authKey = false;
      return;
    }

    clearInterval(authTimer); // 타이머 멈춤

    authKeyMessage.innerText = "인증 되었습니다";
    authKeyMessage.classList.remove("error");
    authKeyMessage.classList.add("confirm");

    checkObj.authKey = true; // 인증번호 검사여부 true

  })


});