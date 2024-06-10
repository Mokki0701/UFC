document.addEventListener('DOMContentLoaded', () => {
    const domainListEl = document.querySelector('#domain-list')
    const domainInputEl = document.querySelector('#memberEmailDomain')
    const tabTitle = document.querySelector("tabTitle")

    // select 옵션 변경 시
    domainListEl.addEventListener('change', (event) => {
        // option에 있는 도메인 선택 시
        if (event.target.value !== "type") {
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




    /* 회원가입 유효성 검사 */
    const checkObj = {
        "memberEmail": false,
        "memberPw": false,
        "memberPwConfirm": false,
        "memberLastName": false,
        "memberFirstName": false,
        "memberGender": false,
        "memberBirthday": false,
        "memberPhone": false,
        "authKey": false
    };



    // 아이디 유효성 검사
    const memberEmailId = document.querySelector("#memberEmailId")
    const memberEmailDomain = document.querySelector("#memberEmailDomain")
    const emailMessage = document.querySelector("#emailMessage")

    const emailDulicateCheck = e => {
        const inputEmailId = memberEmailId.value;
        const inputEmailDomain = memberEmailDomain.value;

        if (inputEmailId.trim().length === 0) {
            emailMessage.innerText = "앞에 아이디는 영어,숫자 4~20글자로 입력해주세요.";
            emailMessage.classList.remove("confirm", "error");
            checkObj.memberEmail = false;
            return;
        }



        const regExp = /^[a-zA-Z0-9]{4,20}$/


        if (!regExp.test(inputEmailId)) {
            emailMessage.innerHTML = "알맞은 아이디 형식을 작성해주세요.";
            emailMessage.classList.remove("confirm");
            emailMessage.classList.add("error");
            checkObj.memberEmail = false;
            return;
        }


        fetch("/member/checkEmail?memberEmail=" + inputEmailId + "@" + inputEmailDomain)
            .then(response => response.text())
            .then(count => {
                if (count == 1) { // 중복 O
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
            .catch(e => {
                // fetch() 수행 중 예외 발생 시 처리
                console.log(e); // 발생한 예외(e) 출력 
            })
    }

    memberEmailId.addEventListener("input", emailDulicateCheck);
    memberEmailDomain.addEventListener("input", emailDulicateCheck);
    document.querySelector("#domain-list").addEventListener("change", emailDulicateCheck);





    //비밀번호 유효성 검사
    const memberPw = document.querySelector("#memberPw")
    const memberPwConfirm = document.querySelector("#memberPwConfirm")
    const pwMessage = document.querySelector("#pwMessage")


    const checkPw = () => {

        if (memberPw.value === memberPwConfirm.value) {
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


    memberPw.addEventListener("input", e => {

        const inputPw = e.target.value;

        if (inputPw.trim().length === 0) {
            pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
            pwMessage.classList.remove("confirm", "error");
            checkObj.memberPw = false;
            pwMessage.value = "";
            return;
        }

        /* 비밀번호 정규 표현식 */
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

        // test : 맞으면 ture 아니면 false
        if (!regExp.test(inputPw)) {
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
        if (memberPwConfirm.value.length > 0) {
            checkPw();
        }
    });


    memberPwConfirm.addEventListener("input", () => {

        if (checkObj.memberPw) {
            checkPw();
            return;
        }


        checkObj.memberPwConfirm = false;
    });


    // 성 입력 조건
    const memberLastName = document.querySelector("#memberLastName")
    const lnameMessage = document.querySelector("#lnameMessage")

    memberLastName.addEventListener("input", e => {

        const inputName = e.target.value;

        if (inputName.trim().length === 0) {
            lnameMessage.innerText = "한글 1~5글자 사이로 입력해 주세요"
            lnameMessage.classList.remove("confirm", "error");
            checkObj.memberLastName = false;
            memberLastName.value = "";
            return;
        }

        const regExp = /^[가-힣]{1,5}$/

        if (!regExp.test(inputName)) {
            lnameMessage.innerText = "성이 유효하지 않습니다";
            lnameMessage.classList.remove("confirm");
            lnameMessage.classList.add("error");
            checkObj.memberLastName = false;

            return;
        }

        lnameMessage.innerText = "유효한 작성 형식입니다";
        lnameMessage.classList.remove("error");
        lnameMessage.classList.add("confirm");
        checkObj.memberLastName = true;

    })


    // 이름 입력 조건
    const memberFirstName = document.querySelector("#memberFirstName");
    const fnameMessage = document.querySelector("#fnameMessage");

    memberFirstName.addEventListener("input", e => {

        const nameinput = e.target.value;


        if (nameinput.trim().length === 0) {
            fnameMessage.innerText = "한글 1~7글자 사이로 입력해 주세요."
            fnameMessage.classList.remove("confirm", "error");
            checkObj.memberFirstName = false;
            memberFirstName.value = "";
            return;
        }

        const regExp = /^[가-힣]{1,5}$/

        if (!regExp.test(nameinput)) {
            fnameMessage.innerText = "이름이 유효하지 않습니다";
            fnameMessage.classList.remove("confirm");
            fnameMessage.classList.add("error");
            checkObj.memberFirstName = false;
            return;
        }

        fnameMessage.innerText = "유효한 작성 형식입니다";
        fnameMessage.classList.remove("error");
        fnameMessage.classList.add("confirm");
        checkObj.memberFirstName = true;
    });


    // 성별 입력
    const genderMessage = document.querySelector("#genderMessage");

    // 모든 라디오 버튼 요소들을 가져옵니다.
    const genderInputs = document.querySelectorAll('input[name="memberGender"]');

    // 각 라디오 버튼에 change 이벤트 리스너를 추가합니다.
    genderInputs.forEach(input => {
        input.addEventListener('change', e => {
            // 선택된 라디오 버튼의 값을 가져옵니다.
            const selectedGender = e.target.value;

            if (selectedGender) {
                // 선택된 값이 있으면 메시지를 업데이트합니다.
                genderMessage.textContent = `선택된 성별: ${selectedGender === 'M' ? '남자' : '여자'}`;
                checkObj.memberGender = true;
            } else {
                // 선택된 값이 없으면 기본 메시지를 유지합니다.
                genderMessage.textContent = '성별을 선택해 주세요.';
                checkObj.memberGender = false;
            }
        });
    });

    // 생년월일 입력
    const memberBirthday = document.querySelector("#memberBirthday");
    const birthdateMessage = document.querySelector("#birthdateMessage");

    memberBirthday.addEventListener("change", e => {

        const inputday = e.target.value

        if (inputday) {
            birthdateMessage.textContent = `선택된 생년월일: ${inputday}`;
            checkObj.memberBirthday = true;
        } else {
            birthdateMessage.textContent = "생년월일을 선택해 주세요."
            checkObj.memberBirthday = false;
        }
    });




    // 전화번호 입력 조건
    const memberPhone = document.querySelector("#memberPhone")
    const telMessage = document.querySelector("#telMessage")

    memberPhone.addEventListener("input", e => {

        const inputTel = e.target.value;

        if (inputTel.trim().length === 0) {
            telMessage.innerText = "전화번호를 입력해주세요.(010으로 시작만 가능)(- 제외)";
            telMessage.classList.remove("confirm", "error");
            checkObj.value = "";
            return;
        }

        const regExp = /^01[0-9][0-9]{3,4}[0-9]{4}$/;

        if (!regExp.test(inputTel)) {
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


    // 회원가입 제출 버튼 클릭 시 유효성 검사
    const signupForm = document.querySelector("#signUpForm");

    // 회원가입 제출 버튼 클릭 시 유효성 검사
    signupForm.addEventListener("submit", e => {

        for (let key in checkObj) {

            if (!checkObj[key]) {

                let str;

                switch (key) {

                    case "memberEmail": str = "이메일이 유효하지 않습니다"; break;

                    case "memberPw": str = "비밀번호가 유효하지 않습니다"; break;

                    case "memberPwConfirm": str = "비밀번호가 일치하지 않습니다"; break;

                    case "memberLastName": str = "성이 유효하지 않습니다"; break;

                    case "memberFirstName": str = "이름이 유효하지 않습니다"; break;

                    case "memberGender": str = "성별을 선택하지 않았습니다"; break;

                    case "memberBirthday": str = "생년월일을 선택하지 않습니다"; break;

                    case "memberPhone": str = "핸드폰 번호가 유효하지 않습니다"; break;
                }

                alert(str);



                e.preventDefault(); // 폼 제출 중단
                return;
            }
        }
    });

    // ---------------------------- 이메일 인증 --------------------------

    // 인증번호 받기 버튼
    const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

    // 인증번호 입력 input
    const authKey = document.querySelector("#authKey");

    // 인증번호 입력 후 확인하는 버튼
    const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

    // 인정번호 관련 메세지 출력 span
    const authKeyMessage = document.querySelector("#authKeyMessage");

    let authTimer; // 타이머 역활

    const initMin = 2;
    const initSec = 59;
    const initTitme = "03:00";

    let min = initMin;
    let sec = initSec;

    sendAuthKeyBtn.addEventListener("click", () => {

        // 클릭되었을 때의 값을 얻기 위해 click된 순간의 값을 반환해야함
        const memberEmailId = document.querySelector("#memberEmailId");
        const memberEmailDomain = document.querySelector("#memberEmailDomain");
        const fullEmail = `${memberEmailId.value}@${memberEmailDomain.value}`;

        checkObj.authKey = false;
        document.querySelector("#authKeyMessage").innerText = "";

        if (!checkObj.memberEmail) {
            alert("유효한 이메일 작성 후 클릭해 주세요");
            return;
        }

        min = initMin;
        sec = initSec;

        clearInterval(authTimer);

        checkObj.authKey = false;

        fetch("/email/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: fullEmail
        })
            .then(response => response.text())
            .then(result => {
                if (result == 1) {
                    console.log("인증 번호 발송 성공");
                } else {
                    console.log("인증 번호 발송 실패");
                }

            })

    });




    // ---------------------- 마이페이지 -------------------------------
    const changePw = document.querySelector("#changePw");

    if (changePw != null) {

        changePw.addEventListener("submit", e => {

            const currentPw = document.querySelector("#currentPw");
            const newPw = document.querySelector("#newPw");
            const newPwConfirm = document.querySelector("#newPwConfirm");

            let str;

            if (currentPw.value.trim().length == 0) str = "현재 비밀번호를 입력해 주세요.";
            else if (newPw.value.trim().length == 0) str = "새 비밀번호를 입력해 주세요.";
            else if (newPwConfirm.value.trim().length == 0) str = "새 비밀번호 확인을 입력 해주세요.";

            if (str != undefined) {
                alert(str);
                e.preventDefault();
                return;
            }

            //- 새 비밀번호 정규식
            const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

            if (!regExp.test(newPw.value)) {
                alert("새 비밀번호가 유요하지 않습니다");
                e.preventDefault();
                return;
            }

            if (newPw.value != newPwConfirm.value) {
                alert("새 비밀번호가 일치하지 않습니다");
                e.preventDefault();
                return;
            }


        });
    }





});


