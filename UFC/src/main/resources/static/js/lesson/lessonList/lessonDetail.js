const signupBtn = document.querySelector("#signupBtn");

if (signupBtn != null) { // 신청 버튼 존재 시
    signupBtn.addEventListener("click", () => {
      // 현재 URL
      const urlPath = window.location.pathname;
      // 새로운 링크 생성
      const newUrl = `${urlPath}/signup`

      location.href = newUrl;

    });
}