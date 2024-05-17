
document.addEventListener("DOMContentLoaded", function () {
  const memberOnly = document.querySelectorAll(".memberOnly");

    memberOnly.forEach(button => {
      button.addEventListener("click", event => {
        event.preventDefault();
        alert("로그인 후 이용해 주세요");
        return;
      });
    });
});