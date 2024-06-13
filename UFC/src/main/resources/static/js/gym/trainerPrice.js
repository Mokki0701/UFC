const checkboxes = document.querySelectorAll('.agreement');
const selectAllCheckbox = document.getElementById('selectAll');
const agreementCheckboxes = document.querySelectorAll('.agreement');



// 전체 동의 체크박스를 클릭했을 때 실행되는 함수
function toggleAll(selectAllCheckbox) {
    // 각 체크박스의 체크 상태를 "전체 동의" 체크박스의 상태와 동일하게 설정합니다.
    checkboxes.forEach(checkbox => {
        checkbox.checked = selectAllCheckbox.checked;
    });
}

// 개별 약관 체크박스를 클릭했을 때 실행되는 함수
function checkSelectAll() {
    // 모든 약관 체크박스가 체크되었는지 확인합니다.
    // Array.from()을 사용하여 NodeList를 배열로 변환하고, 
    // every() 메서드를 사용하여 모든 체크박스가 체크되었는지 확인합니다.
    selectAllCheckbox.checked = Array.from(agreementCheckboxes).every(checkbox => checkbox.checked);
}





const gymPayButton = document.querySelector("#gym_pay_button");
// 락커룸 0,1 작동법 + 요구조건

    let ptLkroom  = null;

    document.getElementById("lockerYes").addEventListener("click", function() {
        ptLkroom  = 1;
        console.log("락커룸 사용 여부: " + ptLkroom);
    });

    document.getElementById("lockerNo").addEventListener("click", function() {
        ptLkroom  = 0;
        console.log("락커룸 사용 여부: " + ptLkroom);
    });



const startDateInput = document.getElementById("startDate");
let inputDate = ""; // 입력된 날짜를 저장할 변수
let selectedDate = null; // 선택된 날짜를 저장할 변수

// 운동 시작일 입력이 변경될 때마다 확인하는 함수
startDateInput.addEventListener("input", function() {
    inputDate = startDateInput.value.trim();
    selectedDate = new Date(inputDate); // 입력된 날짜 가져오기
    const today = new Date(); // 오늘 날짜 가져오기

    // 오늘 이전의 날짜인 경우
    if (selectedDate < today) {
        startDateInput.value = '';
        alert("오늘 이후의 날짜부터 가능합니다.")
        return;
    }
});


/* 버튼을 눌렀을 때 */
gymPayButton.addEventListener("click", function() {
    if (ptLkroom === null) {
        alert("락커룸 사용 여부를 확인해주세요.");
        return;
    } 
    
    // inputDate와 selectedDate는 전역 변수로 선언됨
    if (inputDate === "") {
        alert("운동 시작일을 입력해주세요");
        return;
    } 

    const today = new Date(); // 오늘 날짜 가져오기
    if (selectedDate < today) {
        alert("운동 시작일을 입력해주세요");
        return;
    }

    const requiredCheckboxes = document.querySelectorAll('.agreement[data-required="true"]');
    const allRequiredChecked = Array.from(requiredCheckboxes).every(checkbox => checkbox.checked);

    if (!allRequiredChecked) {
        alert('(필수) 약관에 모두 동의해주셔야 합니다.');
        return;
    }

    
    requestPay();
});

let number = 0;

function incrementNumber() {
    console.log(number);
    number++;
    setTimeout(incrementNumber, 1000); // 1000ms (1초) 간격으로 숫자를 증가시킴
}





// 카카오페이 api 


    IMP.init("imp50442418");
    
    function requestPay() {

      // 운동 시작일(input 태그)의 값을 가져옴
      var strDate = document.getElementById("startDate").value;
        
      // 운동 시작일 값 확인
      console.log("운동 시작일: " + strDate);

      IMP.request_pay({
        pg: "kakaopay",
        pay_method: "kakaopay",
        merchant_uid: incrementNumber() ,
        name: "PT 결제",
        amount: calcResult,
        buyer_tel: memberPhone,
      }, 
      function (rsp) {
        console.log(rsp);
        if (rsp.success){
          const msg = "결제가 완료되었습니다.";
          const result = {
            "ptCount": ptCount,
            "ptPrice": calcResult,
            "ptLkroom" : ptLkroom,
            "memberNo" : memberNo,
            "trainerNo" : trainerNo,
            "strDate" : strDate
          }
          console.log(result);

          fetch("/trainerSelect/gymPay", {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(result)
          })
          .then(resp => resp.json())
          .then(data => {

            console.log('Success', data);
            alert("결제 성공")

            window.location.href = "/trainerSelect/trainerSelect";
          })
          .catch((error) =>{
            console.error('Error:', error);
            alert("결제 실패")
          });
          
        }
      })
    }



