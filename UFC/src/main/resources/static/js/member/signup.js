document.addEventListener('DOMContentLoaded', () => {
  const domainListEl = document.querySelector('#domain-list')
  const domainInputEl = document.querySelector('#memberEmailDomain')
  const tabTitle = document.querySelector("tabTitle")

    // select 옵션 변경 시
    domainListEl.addEventListener('change', (event) => {
    // option에 있는 도메인 선택 시
    if(event.target.value !== "type") {
        // 선택한 도메인을 수정된 도메인 입력란에 입력하고 readOnly 
        // disabled로 하면 값이 넘어가질 못한다
        domainInputEl.value = event.target.value
        domainInputEl.readOnly = true
    } else { // 직접 입력 시
        // 수정: 기존 input 요소의 value를 초기화
        document.querySelector('#memberEmailDomain').value = ""
        // 수정: 직접 입력 가능하도록 변경
        domainInputEl.readOnly = false
    }
    })


function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
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




/* 회원가입 유효성 검사 */
const checkObj = {
    "memberEmail"      : false,
    "memberPw"         : false,
    "memberPwConfirm"  : false,
    "memberLastName"   : false,
    "memberFirestName" : false,
    "memberGender"     : false,
    "memberBirthday"   : false,
    "memberPhone"      : false
};



// 아이디 유효성 검사
const memberEmailId = document.querySelector("#memberEmailId")
const memberEmailDomain = document.querySelector("#memberEmailDomain")
const emailMessage = document.querySelector("#emailMessage")

memberEmailId.addEventListener("input", e =>{

    const inputEmailId = e.target.value
    const inputEmailDomain = e.target.value

    if(inputEmailId.trim().length === 0){
        emailMessage.innerText = "앞에 아이디는 영어,숫자 4~20글자로 입력해주세요.";
        emailMessage.classList.remove("confirm","error");
        checkObj.memberEmail = false;
        return;
    }

    if(inputEmailDomain.trim().length === 0){
        emailMessage.innerText = "이메일을 입력해 주세요.";
        emailMessage.classList.remove("confirm", "error");
        checkObj.memberEmail = false;
        return;
    }

    const regExp = /^[a-zA-Z0-9]+$/
    const domain = ['naver.com', 'gmail.com', 'hanmail.net', 'nate.com', 'kakao.com']

    if(!regExp.test(inputEmailId)){
        emailMessage.innerHTML = "알맞은 아이디 형식을 작성해주세요.";
        emailMessage.remove("confirm");
        emailMessage.add("error");
        checkObj.memberEmail = false;
        return;
    }


    fetch("/member/checkEmail?memberEmail=" + inputEmailId + "@"+ inputEmailDomain) 
    .then(response => response.text())
    .then( count => {

        if(count == 1){ // 중복 O
            emailMessage.innerText = "이미 사용중인 이메일 입니다.";
            emailMessage.classList.add('error');
            emailMessage.classList.remove('confirm');
            checkObj.memberEmail = false; // 중복은 유효하지 않음
            return;
          }
      
          // 중복 x 인 경우
          emailMessage.innerText = "사용 가능한 이메일 입니다.";
          emailMessage.classList.add('confirm');
          emailMessage.classList.remove('error');
          checkObj.memberEmail = true; // 유요한 이메일
      
        })
        .catch( e => {
          // fetch() 수행 중 예외 발생 시 처리
          console.log(e); // 발생한 예외(e) 출력 
        })
})





//비밀번호 유효성 검사
const memberPw = document.querySelector("#memberPw")
const memberPwConfirm = document.querySelector("#memberPwConfirm")
const pwMessage = document.querySelector("#pwMessage")


const checkPw = () => {

    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText = "비밀번호가 일치합니다"
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPwConfirm = true;
        return;
    }

    pwMessage.innerText = "비밀번호가 일치하지 않습니다"
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    checkObj.memberPwConfirm = false;
}


    memberPw.addEventListener("input", e =>{

        const inputPw = e.target.value;

        if(inputPw.trim().length === 0){
            pwMessage.innerText =  "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
            pwMessage.classList.remove("confirm", "error");
            checkObj.memberPw = false;
            pwMessage.value = "";
            return;
        }

        /* 비밀번호 정규 표현식 */
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

        // test : 맞으면 ture 아니면 false
       if(!regExp.test(inputPw)){
          pwMessage.innerText = "비밀번호가 유효하지 않습니다"
          pwMessage.classList.add("error");
          pwMessage.classList.remove("confirm");
          checkObj.memberPw = false;
          return;
       }

        // 유효한 경우
        pwMessage.innerText = " 유효한 비밀번호 형식입니다";
        pwMessage.classList.add("confirm");
        pwMessage.classList.remove("error");
        checkObj.memberPw = true; 

        // 7) 비밀번호 입력 시에도 확인이랑 비교하는 코드 추가

        // 비밀번호 확인에 값이 작성되어 있을 때
        if(memberPwConfirm.value.length > 0){
        checkPw();
        }
    });


    memberPwConfirm.addEventListener("input", () => {

        if(checkObj.memberPw){
            checkPw();
            return;
        }

    
        checkObj.memberPwConfirm = false;
    });


    const memberLastName = document.querySelector("#memberLastName")
    const lnameMessage = document.querySelector("#lnameMessage")

    // 성 입력 조건
    memberLastName.addEventListener("input" , e =>{

        const inputName = e.target.value;

        if(inputName.trim().length === 0){
            lnameMessage.innerText = "한글 1~5글자 사이로 입력해 주세요"
            lnameMessage.classList.remove("confirm", "error");
            checkObj.memberFirestName = false;
            memberLastName.value = "";
            return;
        }

        const regExp = /^[가-힣]{1,5}$/

        if( !regExp.test(inputName)) {
            lnameMessage.innerText = "성이 유효하지 않습니다";
            lnameMessage.classList.remove("confirm");
            lnameMessage.classList.add("error");
            checkObj.memberFirestName = false;
            
            return;
        }

        lnameMessage.innerText = "유효한 작성 형식입니다";
        lnameMessage.classList.remove("error");
        lnameMessage.classList.add("confirm");
        checkObj.memberFirestName = true;
        
    })


    const memberFirstName = document.querySelector("#memberFirstName")
    const fnameMessage = document.querySelector("#fnameMessage");
    
    // 이름 입력 조건
    memberFirstName.addEventListener("input" , e =>{

        const nameinput = e.target.value;

        
        if(nameinput.trim().length === 0){
            fnameMessage.innerText = "한글 1~7글자 사이로 입력해 주세요."
            fnameMessage.classList.remove("confirm", "error");
            checkObj.memberLastName = false; 
            memberLastName.value = "";
            return;
        }

        const regExp = /^[가-힣]{1,5}$/

        if( !regExp.test(nameinput)) {
            fnameMessage.innerText = "이름이 유효하지 않습니다";
            fnameMessage.classList.add("error");
            fnameMessage.classList.remove("confirm");
            checkObj.memberLastName = false;   
            return;
        }

        fnameMessage.innerText = "유효한 작성 형식입니다";
        fnameMessage.classList.add("confirm");
        fnameMessage.classList.remove("error");
        checkObj.memberLastName = true;
    });



    const memberPhone = document.querySelector("#memberPhone")
    const telMessage = document.querySelector("#telMessage")

    memberPhone.addEventListener("input", e =>{

        const inputTel = e.target.value;
          
            if(inputTel.trim().length === 0 ){
              telMessage.innerText = "전화번호를 입력해주세요.(010으로 시작만 가능)(- 제외)";
              telMessage.classList.remove("confirm", "error");
              checkObj.value= "";
              return;
            }
          
            const regExp = /^01[0-9][0-9]{3,4}[0-9]{4}$/;
          
            if( !regExp.test(inputTel)) {
              telMessage.innerText = "유효하지 않은 전화번호 형식입니다."
              telMessage.classList.add("error");
              telMessage.classList.remove("confirm");
              checkObj.memberPhone = false;
              return;
            }
          
            telMessage.innerText = "유효한 전화번호 형식입니다."
              telMessage.classList.add("confirm");
              telMessage.classList.remove("error");
              checkObj.memberPhone = true;
          });
          
    
});