const quickLoginBtn = document.querySelectorAll(".quickLoginBtn");  
if (quickLoginBtn != null) {
  quickLoginBtn.forEach((item) => {
    item.addEventListener("click", () => {
      const email = item.innerText;
      location.href = "/member/quickLogin?memberEmail=" + email;
    })
  });
}
