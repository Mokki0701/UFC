// 레슨 신청 버튼
const signupBtn = document.querySelector("#signupBtn");

// 목록으로 버튼
const gobackBtn = document.querySelector("#gobackBtn");

if (signupBtn != null) { // 신청 버튼 존재 시
    signupBtn.addEventListener("click", () => {
      // 현재 URL
      const urlPath = window.location.pathname;
      // 새로운 링크 생성
      const newUrl = `${urlPath}/signup`

      location.href = newUrl;

    });
}



gobackBtn.addEventListener("click", () => {
  // 목표 url 예시 http://localhost/lesson/list/search?query=&cp=1&order=desc&tags=19

  // 쿼리스트링 얻어오기
  const queryParams = window.location.search;  // '?query=&tags=19,20&cp=1&order=desc'

  // http://localhost/lesson/list/search

  // console.log('/lesson/list/search' + queryParams);
  location.href = '/lesson/list/search' + queryParams;
  
  
})

/* 수정 버튼 기능 */
const updateBtn = document.querySelector("#updateBtn");
if(updateBtn != null){
updateBtn.addEventListener("click", () => {

  // 현재 URL 예시 '/lesson/list/22'


  location.href = location.pathname.replace('list', 'listEdit')
  + "/update";



})

}

// 삭제 버튼
const deleteBtn = document.querySelector("#deleteBtn");
if (deleteBtn != null) {
  deleteBtn.addEventListener("click", () => {

    if (confirm("정말 수업을 삭제하시겠습니까?")) {

      // 현재 URL 예시 '/lesson/list/22'
      location.href = location.pathname.replace('list', 'listEdit')
        + "/delete";

    }

  })

}
