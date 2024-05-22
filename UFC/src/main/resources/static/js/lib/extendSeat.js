// 자리 연장하는 페이지에서 사용되는 js

// 페이지가 로딩 되자 마자 회원의 열람실 이용 정보 불러오기
document.addEventListener('DOMContentLoaded', function () {
  fetch('/lib/seats/seatUsage')
    .then(response => response.json())
    .then(data => {
      if (data) {
        document.getElementById('readingStart').textContent = data.readingStart;
        document.getElementById('readingDone').textContent = data.readingDone;
        document.getElementById('remainingExtensions').textContent = data.readingExtend;
      } else {
        document.getElementById('seatUsageInfo').textContent = '이용 정보가 없습니다.';
      }
    })
    .catch(error => console.error('Error fetching seat usage:', error));


  // 현재시간 실시간으로 업뎃 기능
  function updateTime() {
    const now = new Date();
    const year = String(now.getFullYear()).substring(2);
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    document.getElementById('currentTime').textContent = formattedTime;
  }

  // 기능 실행
  updateTime();
  // 매초마다 업뎃
  setInterval(updateTime, 1000);

});

// 버튼 모음
const extendSeat = document.querySelector("#extendSeat");
const stopUsing = document.querySelector("#stopUsing");
const moveSeat = document.querySelector("#moveSeat");


// 자리 연장하기
document.getElementById('extendSeat').addEventListener('click', function () {
  fetch('/lib/seats/extend', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.text())
    .then(data => {
      if (data === 'success') {
        alert('연장되었습니다.');
        location.reload();
      } else {
        alert('연장할 수 없습니다.');
      }
    })
    .catch(error => console.error('Error extending seat:', error));
});


// 자리 그만 이용하기
stopUsing.addEventListener("click", () => {
  fetch('/lib/seats/stopUsingSeat', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(data => {
      // 서버에서 반환된 메시지를 alert 창으로 띄우기
      alert(data.message);

      // 메시지에 따라 페이지를 리다이렉트
      if (data.result === 'success') {
        // 현재 회원이 이용 중인 좌석의 좌표를 사용하여 nowUsing 클래스를 제거
        const seatNo = data.seatNo;
        const seatDiv = document.querySelector(`[data-seat-no="${seatNo}"]`);
        if (seatDiv) {
          console.log(`Removing 'nowUsing' class from seat with seatNo: ${seatNo}`);
          seatDiv.classList.remove('nowUsing');
        } else {
          console.warn(`Seat with seatNo ${seatNo} not found`);
        }
        // 페이지를 리다이렉트
        window.location.href = '/';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('이용 종료 중 오류가 발생했습니다.');
    });
});

