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
});

document.addEventListener('DOMContentLoaded', () => {
    const ptPrice = document.getElementById('ptPrice'); // PT 정보를 담고 있는 요소
    const buttons = document.querySelectorAll('.gym_price'); // 가격 버튼들

    if (ptPrice) {
        const ptYn = ptPrice.dataset.ptYn; // PT 유효 여부
        const ptStrdate = ptPrice.dataset.ptStrdate; // PT 시작일

        // 현재 날짜와 PT 시작일을 비교하여 PT 유효 여부를 확인합니다
        const currentDate = new Date();
        const startDate = new Date(ptStrdate);

        // PT가 유효한 경우에만 버튼을 활성화합니다
        if (ptYn === '1') {
            // PT가 유효하고 현재 날짜가 시작일 이후인 경우 버튼을 활성화합니다
            if (currentDate >= startDate) {
                buttons.forEach(button => {
                    button.disabled = false; // 활성화
                });
            } else {
                // PT가 유효하지만 아직 시작일이 오지 않은 경우 버튼을 비활성화합니다
                buttons.forEach(button => {
                    button.disabled = true; // 비활성화
                });
            }
        } else {
            // PT가 유효하지 않은 경우 버튼을 비활성화합니다
            buttons.forEach(button => {
                button.disabled = true; // 비활성화
            });
        }
    }
});

