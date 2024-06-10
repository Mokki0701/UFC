document.addEventListener("DOMContentLoaded", () => {
    const approve = document.querySelector("#approve");
    const refuse = document.querySelector("#refuse");
    const trainerChange = document.querySelector("#trainerChange");

    if (approve && refuse && trainerChange) {
        trainerChange.textContent = "트레이너 변경하기";
        trainerChange.style.display = "none"; // 처음에 버튼 숨기기

        approve.addEventListener("click", () => {
            const memberNo = document.querySelector("#memberNo").textContent;
            const memberEmail = document.querySelector("#memberEmail").textContent;

            // 비동기로 서버 메일 보내기
            alert("승인 이메일이 전송되었습니다.");
            
            fetch("/management/approveApplication", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    memberNo: memberNo,
                    email: memberEmail
                })
            })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    // 승인 및 거절 버튼 숨기기
                    approve.style.display = "none";
                    refuse.style.display = "none";
                    // 트레이너 변경하기 버튼 표시
                    trainerChange.style.display = "block";
                } else {
                    alert("이메일 전송에 실패했습니다.");
                }
            })
            .catch(error => {
                alert("작업 중 오류가 발생했습니다.");
                console.error('Error:', error);
            });
        });

        refuse.addEventListener("click", () => {
            const memberNo = document.querySelector("#memberNo").textContent;
            const memberEmail = document.querySelector("#memberEmail").textContent;

            // 비동기로 서버 메일 보내기
            alert("거절 이메일이 전송되었습니다.");
            
            fetch("/management/refuseApplication", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    memberNo: memberNo,
                    email: memberEmail
                })
            })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert("거절되었습니다");
                    location.href = "/management/applicationList";
                } else {
                    alert("이메일 전송에 실패했습니다.");
                }
            })
            .catch(error => {
                alert("작업 중 오류가 발생했습니다.");
                console.error('Error:', error);
            });
        });

        trainerChange.addEventListener("click", () => {
            const memberNo = document.querySelector("#memberNo").textContent;

            // 비동기로 서버에 memberAuthority 업데이트 요청 보내기
            fetch("/management/updateMemberAuthority", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    memberNo: memberNo
                })
            })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert(result.message);
                    location.href = result.redirect;
                } else {
                    alert(result.message);
                }
            })
            .catch(error => {
                alert("작업 중 오류가 발생했습니다.");
                console.error('Error:', error);
            });
        });
    } else {
        console.error('One or more elements are not found on the page.');
    }
});
