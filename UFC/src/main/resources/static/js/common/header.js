function checkDoneTime() {
  console.log("Sending fetch request to /lib/space/doneTimeCheck");

  // 현재 시간 분 단위 까지만 구하기
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');

  // yyyy-mm-dd-hh-mi 형식으로 조합
  const formattedDateTime = `${year}-${month}-${day}${hours}:${minutes}`;

  console.log("Current Date and Time: ", formattedDateTime);

  // 매 분마다 요청만 보내니까 뭐 받아서 처리 할 필요 없음
  fetch('/lib/space/doneTimeCheck', {
    method: 'POST', // POST 요청으로 변경
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ dateTime: formattedDateTime }) // 요청 본문에 데이터 포함
  })
}


document.addEventListener('DOMContentLoaded', function () {
  console.log('DOMContentLoaded event fired'); // 추가된 로그

  var loginButton = document.getElementById('login-button');

  if (loginButton) {
    loginButton.addEventListener('click', function () {
      if (loginButton.textContent === '로그인') {
        loginButton.textContent = '로그아웃';
      } else {
        loginButton.textContent = '로그인';
      }
    });
  } else {
    console.error('login-button not found');
  }

  // 매 분 00초마다 대여 공간 이용 종료 확인
  
  // 현재 시간을 기준으로 다음 00초까지 남은 시간을 계산하여 처음 실행 시간 설정
  const now = new Date();
  console.log('Current time:', now); // 추가된 로그
  const delay = 60000 - (now.getSeconds() * 1000 + now.getMilliseconds());
  console.log('Initial delay:', delay); // 추가된 로그

  setTimeout(function () {
    console.log('Initial timeout fired'); // 추가된 로그
    checkDoneTime(); // 처음 실행
    // 이후 매 1분마다 실행
    setInterval(checkDoneTime, 60000);
    console.log('매 분 00초에 실행함');
  }, delay);


});
