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


const gymPrices = document.querySelectorAll(".gym_price");

gymPrices.forEach(btn => {
    btn.addEventListener("click", () => {
        const ptCount =  btn.dataset.ptCount; // PT 횟수
        const memberGender = btn.dataset.memberGender; // 이름
        

        // form 태그 만들기
        const form = document.createElement("form");
        form.action = '/trainerSelect/trainerPrice';
        form.method = 'POST';
        
        // 횟수 입력된 input
        const input1 = document.createElement("input");
        input1.type = "hidden";
        input1.name = "ptCount";
        input1.value = ptCount;
        
        const input2 = document.createElement("input");
        input2.type = "hidden";
        input2.name = "trainerNo";
        input2.value = btn.parentElement.parentElement.dataset.trainerNo;
        

        // form태그를 화면에 추가
        form.append(input1, input2);
        document.querySelector("body").append(form);

        // form태그 제출
        form.submit();

    })
})
