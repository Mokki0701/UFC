// const gymPrices = document.querySelectorAll(".gym_price");

// gymPrices.forEach(btn => {
//     btn.addEventListener("click", () => {
//         const ptCount =  btn.dataset.ptCount; // PT 횟수
//         const memberGender = btn.dataset.memberGender; // 이름
        

//         // form 태그 만들기
//         const form = document.createElement("form");
//         form.action = '/trainerSelect/trainerPrice';
//         form.method = 'POST';
        
//         // 횟수 입력된 input
//         const input1 = document.createElement("input");
//         input1.type = "hidden";
//         input1.name = "ptCount";
//         input1.value = ptCount;
        
//         const input2 = document.createElement("input");
//         input2.type = "hidden";
//         input2.name = "trainerNo";
//         input2.value = btn.parentElement.parentElement.dataset.trainerNo;
        
        
//         // form태그를 화면에 추가
//         form.append(input1, input2);
//         document.querySelector("body").append(form);

//         // form태그 제출
//         form.submit();

//     })
// });




document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".gym_price");

    console.log(ptPrices);
    
    buttons.forEach(button => {
        button.addEventListener("click", e => {
            const endDate = new Date(ptPrices.ptStrdate);
            endDate.setDate(endDate.getDate() + ptPrices.ptYn);
            const currentDate = new Date();
            
            if (endDate > currentDate) {  
                console.log(endDate)
                console.log(currentDate)  
                alert("PT 진행 중입니다. PT기간이 끝난후 신청 하실수있습니다");
                return;
              
            } else {
                console.log(endDate)
            console.log(currentDate)
            const ptCount = button.dataset.ptCount; // PT 횟수
            const trainerNo = button.closest('.trainer-container').dataset.trainerNo; // 트레이너 번호

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
            input2.value = trainerNo;

            // form태그를 화면에 추가
            form.append(input1, input2);
            document.querySelector("body").append(form);

            // form태그 제출
            form.submit();
            }
        });
    });
});










