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

const buttons = document.querySelectorAll(".gym_price");
console.log(`ptYn: ${ptYn}, ptStrdate: ${ptStrdate}`);


buttons.forEach(button => {
    button.addEventListener("click", () => {
        const ptYn = button.getAttribute("data-pt-yn");
        const ptStrdate = button.getAttribute("data-pt-strdate");
        const ptCount = button.getAttribute("data-pt-count");

        // PT 세션 정보가 있을 때 확인
        if (ptYn > 0 && ptStrdate !== null) {
            const confirmation = confirm(`PT가 있습니다. ${ptStrdate}부터 ${ptYn}일 동안 PT가 진행 중입니다. 결제를 계속 하시겠습니까?`);
            if (confirmation) {
                // 사용자가 확인을 선택한 경우 결제 페이지로 이동
                window.location.href = `/trainerSelect/trainerSelect`;
            }
        } else {
            // PT 세션 정보가 없는 경우 바로 결제 페이지로 이동
            window.location.href = `/trainerSelect/trainerPrice`;
        }
    });
});








