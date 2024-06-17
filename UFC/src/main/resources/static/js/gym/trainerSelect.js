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

    buttons.forEach(button => {
        button.addEventListener("click", e => {

            if (ptPrice && ptPrice.ptYn > 0 && ptPrice.ptStrdate) {
                const endDate = new Date(ptPrice.ptStrdate);
                endDate.setDate(endDate.getDate() + ptPrice.ptYn); // PT 가 끝나는 날짜

                const currentDate = new Date(); // 현재 날짜
                console.log(endDate);
                console.log(currentDate);
                console.log(endDate.getTime() > currentDate.getTime());

                
                if (endDate.getTime() > currentDate.getTime()) {    
                   
                    const formattedEndDate = `${endDate.getFullYear()}-${endDate.getMonth() + 1}-${endDate.getDate()}`;
                    const confirmation = confirm(`${ptPrice.ptStrdate} 부터 ${formattedEndDate}까지 PT 진행 중입니다. 결제를 계속 하시겠습니까?`);
<<<<<<< HEAD
                    if (confirmation) {
                        const ptCount =  button.dataset.ptCount; // PT 횟수
                        const memberGender = button.dataset.memberGender; // 이름
                        
                
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
                        input2.value = button.parentElement.parentElement.dataset.trainerNo;
                        
                        
                        // form태그를 화면에 추가
                        form.append(input1, input2);
                        document.querySelector("body").append(form);
                
                        // form태그 제출
                        form.submit();

                       
                    }else {
                        console.log('PT 정보가 없거나, PT 기간이 만료되었습니다.');
                        return;
                    }
                }
            } else {
                console.log(2);
                const ptCount =  button.dataset.ptCount; // PT 횟수
                const memberGender = button.dataset.memberGender; // 이름
                
        
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
                input2.value = button.parentElement.parentElement.dataset.trainerNo;
                
                
                // form태그를 화면에 추가
                form.append(input1, input2);
                document.querySelector("body").append(form);
        
                // form태그 제출
                form.submit();
=======
                    if (!confirmation) {
                        return;
                    }
                }
>>>>>>> a9ddec46e5acd2f311d603f0b4ec01c2dbbbaee0
            }

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
        });
    });
});










