document.addEventListener('DOMContentLoaded', function () {
  var loginButton = document.getElementById('login-button');

  loginButton.addEventListener('click', function () {
    if (loginButton.textContent === '로그인') {
      loginButton.textContent = '로그아웃';
    } else {
      loginButton.textContent = '로그인';
    }
  });


  // 매 분 00초마다 대여 공간 이용 종료 확인

  function checkDoneTime() {
    fetch('lib/space/doneTimeCheck', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => response.json())
      .then(result => {
        console.log(result);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  }

  // 현재 시간을 기준으로 다음 00초까지 남은 시간을 계산하여 처음 실행 시간 설정
  const now = new Date();
  const delay = 60000 - (now.getSeconds() * 1000 + now.getMilliseconds());

  setTimeout(function () {
    checkDoneTime(); // 처음 실행
    // 이후 매 1분마다 실행
    setInterval(checkDoneTime, 60000);
  }, delay);




});





