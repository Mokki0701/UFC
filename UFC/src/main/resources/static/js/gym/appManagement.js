// 승인 버튼
const approve = document.querySelector("#approve");
const refuse = document.querySelector("#refuse");
const trainerChange = document.querySelector("#trainerChange")

trainerChange.textContent = "트레이너 변경하기";
trainerChange.style.display = "none";
document.body.appendChild(trainerChange);

approve.addEventListener("click", () => {
    const memberNo = document.querySelector("#memberNo").textContent;
    const memberEmail = document.querySelector("#memberEmail").textContent;

    // 비동기로 서버 메일 보내기
    fetch("/management/sendEmail", {
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
            alert("승인 이메일이 전송되었습니다.");

            // 승인 및 거절 버튼 숨기기
            approve.style.display = "none";
            refuse.style.display = "none";

            // 트레이너 변경하기 버튼 표시
            trainerChange.style.display = "block";

            // 페이지 나가기 방지
            window.onbeforeunload = function() {
                return "이 페이지를 떠나면 변경 사항이 저장되지 않을 수 있습니다.";
            };
        } else {
            alert("이메일 전송에 실패했습니다.");
        }
    })
    .catch(error => {
        alert("작업 중 오류가 발생했습니다.");
        console.error('Error:', error);
    });
});

// 거절 버튼
refuse.addEventListener("click", () => {
    alert("거절되었습니다");
});

// 트레이너 변경하기 버튼
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
            alert("멤버 권한이 성공적으로 업데이트되었습니다.");
            // 페이지 나가기 방지 해제
            window.onbeforeunload = null;
        } else {
            alert("멤버 권한 업데이트에 실패했습니다.");
        }
    })
    .catch(error => {
        alert("작업 중 오류가 발생했습니다.");
        console.error('Error:', error);
    });
});
