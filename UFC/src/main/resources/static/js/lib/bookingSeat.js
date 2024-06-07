document.addEventListener('DOMContentLoaded', function () {




  const seatChart = document.querySelector('.seat-chart');
  const rows = 20;
  const cols = 20;
  let seats = {};
  let selectedSeat = null; // 선택된 좌석을 저장할 변수

  // 좌석 초기화
  for (let i = 0; i < rows; i++) {
    for (let j = 0; j < cols; j++) {
      const seat = document.createElement('div');
      seat.className = 'seat aisle';
      seat.dataset.coords = `seat-${i}-${j}`; // data-coords 속성으로 좌표 저장
      seatChart.appendChild(seat);
      seats[`seat-${i}-${j}`] = {
        element: seat,
        condition: 'aisle'
      };

      // 좌석 클릭 이벤트 추가
      seat.addEventListener('click', function () {
        const seatNo = seat.innerText || '없음';
        document.getElementById('currentSelectSeat').textContent = seatNo;

        // 좌표 표시
        const [prefix, x, y] = seat.dataset.coords.split('-');
        document.getElementById('coordiX').textContent = x;
        document.getElementById('coordiY').textContent = y;

        // 계산된 a 값을 DB SEAT_NO에 표시
        const a = 20 * parseInt(x) + parseInt(y) + 1;
        document.getElementById('dbSeatNo').textContent = a;

        // 좌석 상태 표시
        const seatStatus = seat.classList.contains('availSeat') && seat.classList.contains('nowUsing') ? '사용 중' :
          seat.classList.contains('availSeat') ? '사용 가능' :
            seat.classList.contains('disavailSeat') ? '사용 불가능' : '없음';
        document.getElementById('dbSeatAvail').textContent = seatStatus;

        // 선택된 좌석 저장
        selectedSeat = seat;
      });
    }
  }

  // 페이지 로드 시 좌석 데이터 불러오기
  loadSeatData();

  function loadSeatData() {
    fetch('/lib/seats/data')
      .then(response => response.json())
      .then(data => {
        console.log('Loaded seat data:', data); // 디버깅용 로그 추가

        // 서버에서 가져온 좌석 데이터로 업데이트
        data.forEach(seat => {
          const seatDiv = document.querySelector(`[data-coords="seat-${seat.coordiX}-${seat.coordiY}"]`);
          if (seatDiv) {
            seatDiv.className = 'seat ' + (seat.condition === 1 ? 'availSeat' : seat.condition === 2 ? 'disavailSeat' : 'aisle');
            seatDiv.dataset.seatNo = seat.seatNo;

            // avail_seat가 1인 경우 nowUsing 클래스 추가
            if (seat.seatAvail === 1) {
              seatDiv.classList.add('nowUsing');
            }
          } else {
            console.warn('Seat not found for coords:', seat.coordiX, seat.coordiY); // 디버깅용 로그 추가
          }
        });

        // 좌석 번호 할당
        assignSeatNumbers();
      })
      .catch(error => console.error('Error loading seats:', error));
  }

  // 좌석 번호 할당 함수
  function assignSeatNumbers() {
    const availableSeats = document.querySelectorAll(".availSeat, .disavailSeat");
    availableSeats.forEach((seat, index) => {
      seat.textContent = "";
      seat.textContent = index + 1;
    });
  }

  // 이용 불가능한 좌석 클릭시 alert
  // + 선택 된 좌석임을 가시적으로 표시.
  const seatAlert = document.querySelectorAll(".seat");

  seatAlert.forEach(seat => {
    seat.addEventListener("click", function () {

      if (seat.classList.contains("disavailSeat")) {
        alert("해당 좌석은 사용 불가능합니다.");
        return;
      }

      if (seat.classList.contains("nowUsing")) {
        alert("해당 좌석은 이미 다른 사람이 사용 중 입니다.");
        return;
      }

      if (seat.classList.contains("aisle")) {
        alert("클릭하신 공간은 이동 통로로 이용이 불가능합니다.");
        return;
      }

      // 모든 seat의 nowSelected 클래스를 제거
      seatAlert.forEach(s => s.classList.remove("nowSelectedSeat"));

      // 현재 선택된 seat에 nowSelected 클래스를 추가
      seat.classList.add("nowSelectedSeat");
    });
  });



  // 이용하기 버튼 클릭 시 동작
  const useSeatBtn = document.querySelector("#useSeat");

  // 이용 가능한 좌석에만 반응하기
  useSeatBtn.addEventListener("click", () => {


    if (selectedSeat && selectedSeat.classList.contains('availSeat') && !selectedSeat.classList.contains('nowUsing') && !selectedSeat.classList.contains('disavailSeat')) {
      const dbSeatNo = document.getElementById('dbSeatNo').textContent;
      // 클릭된 좌석의 상태를 기준으로 seatCondition 설정
      const seatCondition = selectedSeat.classList.contains('availSeat') ? 1 :
        selectedSeat.classList.contains('disavailSeat') ? 2 :
          0;
      if (dbSeatNo !== '') {
        fetch('/lib/seats/useSeat', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ seatNo: dbSeatNo, condition: seatCondition })
        })
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            console.log('Seat usage response:', data);
            alert(data.message);
            loadSeatData(); // 좌석 데이터 다시 로드
          })
          .catch(error => console.error('Error using seat:', error));
      } else {
        alert("좌석을 먼저 선택해 주세요.");
      }
    } else {
      alert("선택한 좌석은 사용할 수 없습니다.");
    }


  });


});

// 좌석 이용 종료하기
const stopUsingSeat = document.querySelector("#stopUsingSeat");
stopUsingSeat.addEventListener("click", () => {
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
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('이용 종료 중 오류가 발생했습니다.');
    });
});

// 모달 닫는 기능
function closeModal() {
  const bookingModal = document.getElementById('bookingModal');
  if (bookingModal) {
    bookingModal.style.display = "none";
  }

  const checkMySeatReservationModal = document.querySelector('.checkMySeatReservationModal');
  if (checkMySeatReservationModal) {
    checkMySeatReservationModal.style.display = "none";
  }

  const checkMySeat = document.getElementById('checkMyseat');
  if (checkMySeat) {
    checkMySeat.style.display = "none";
  }
}

// 모달 외부 클릭 시 닫기
window.onclick = function (e) {
  const bookingModal = document.getElementById('bookingModal');
  const checkMySeat = document.getElementById('checkMyseat');
  const checkMySeatReservationModal = document.querySelector('.checkMySeatReservationModal');

  if (e.target === bookingModal || e.target === checkMySeat || e.target === checkMySeatReservationModal) {
    closeModal();
  }
}





// 좌석 예약하기
// how?

// 이용 가능한 좌석일 경우에만 기능 수행 --> 좌석 예약 판단 로직은 좌석 이용 로직과 다름
// 이용 가능한 좌석인지 판단, 이용 불가능한 좌석이면 : js에서 보내지 않기.
// postMapping으로 보내기

// 회원 번호를 보낸다 -> 안보내도 된다. 백엔드 세션에서 회원 번호 가져올거임.
// 좌석 번호를 보내서
// !!! -> 실제 좌석 번호, db 좌석 번호 다르다   !!!
//    --> 따라서 db용 좌석 번호를 받아와야 한다 !!!
//    --> db용 좌석 번호 : #currentSelectSeat.innerText


// 모달 표시하기
function openBookingSeatModal() {

  const seatNo = document.getElementById('currentSelectSeat').innerText.trim();
  if (!seatNo) {
    alert("예약하고자 하는 열람실 좌석을 선택해주세요.");
    closeModal();
    return;
  }

  const bookingModal = document.getElementById('bookingModal');
  const currentTimeSpan = document.getElementById('currentTime');

  bookingModal.style.display = "block";
  const now = new Date();
  currentTimeSpan.textContent = now.toLocaleTimeString();

}

function realBookingSeat() {
  const seatNo = document.getElementById('currentSelectSeat').innerText.trim();
  const amPm = document.getElementById('amPm').value;
  const hour = document.getElementById('hour').value;
  const minute = document.getElementById('minute').value;
  const memberNo = document.getElementById('userInfo').getAttribute('data-member-no');


  // 시간 형식 변환 12시간제 -> 24시간제
  let selectedHour = parseInt(hour);
  if (amPm === 'PM' && selectedHour !== 12) {
    selectedHour += 12;
  } else if (amPm === 'AM' && selectedHour === 12) {
    selectedHour = 0;
  }
  const selectedTime = `${selectedHour.toString().padStart(2, '0')}:${minute}`;

  const data = {
    seatNo: seatNo,
    memberNo: parseInt(memberNo),
    startTime: selectedTime
  };

  

  // 1. 좌석이 예약 가능한지 여부 판단하기
  fetch('/lib/seats/checkAvailReservation', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
    .then(response => response.json())
    .then(result => {
      if (result.success) {
        alert(result.message);
        if (result.redirectUrl) {
          window.location.href = result.redirectUrl;
        } else {
          closeModal(); // 예약 완료 후 모달을 닫는 함수
        }
      } else {
        alert('예약에 실패했습니다: ' + result.message);
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('예약 처리 중 오류가 발생했습니다.');
    });
}

// 이용중인 열람실 확인하는 모달 열기
function checkMySeat() {
  const checkMyseat = document.getElementById('checkMyseat');

  // 필요한 정보를 보여주는 모달 표시
  checkMyseat.style.display = "block";

  // 정보를 표시 할 span

  // 필요한 정보 비동기로 받아오기
  fetch('/lib/seats/getMySeatInfo', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(map => {
      // const seatInfo = result.seatInfo;
      if (map.message) {
        alert(map.message);
        console.log(map.message);
        // 열람실 이용중이 아니면 모달 닫기
        // 이용중이 아닐 때에만 메세지가 있다 -> 메세지가 있다면 모달을 닫는다.
        checkMyseat.style.display = "none";

      } else {
        // 내부 텍스트를 결과로 표시하기
        // 현재 시간은 24시간제, hh:mm:ss:로 표시됨.
        const startTime = document.querySelector("#startTime");
        const endTime = document.querySelector('#endTime');
        const remainingExtensions = document.querySelector('#remainingExtensions');

        startTime.innerText = map.startTime;
        endTime.innerText = map.endTime;
        remainingExtensions.innerText = map.readingExtend;
      }
    });
}

// 좌석 연장버튼 기능
// 1. 내 자리의 연장하고자 하는 시간에 예약이 있다면 연장 불가
// 필요한 테이블 : SEAT_SPACE_BOOKING ssb, RENT_SEAT rs
// 필요한 컬럼 : 전달 받은 #{seatNo}를 기준으로. 
// ssb.START_BOOKING이 rs.END_BOOKING ~ +4HH 사이에 있는지 확인하는 select문 작성.
function extendSeat() {

  // 내가 사용 중인 자리는 memberAndSeatSession에 저장되어 있다.
  let userConfirmed = confirm("자리를 연장하시겠습니까?");
  if (userConfirmed) {
    fetch('/lib/seats/extend', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then(result => {
        alert(result.message);
      })
      .catch(error => {
        console.error('Error:', error);
        alert('오류 발생, 관리자에게 문의하세요.');
      });
  }


}


// 나의 예약 확인하기
// 비동기식으로 진행.
// 1. 회원번호 : session에서 얻어옴

// -- 로직
// 1. 예약 있는지 확인
// 1.1. 없으면 집에 가라
// 1.2. 있으면 다음 기능 실행

// 2.

// 날짜 형식 변환 function
function formatDateTime(dateTimeStr) {
  const date = new Date(dateTimeStr);

  const year = date.getFullYear();
  const month = date.getMonth() + 1; // getMonth()는 0부터 시작하므로 +1 필요
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  return `${year}년 ${month}월 ${day}일 ${hours}:${minutes.toString().padStart(2, '0')}`;
}




function checkMySeatReservation() {

  // 모달 열기
  const checkMySeatReservation = document.querySelector("#checkMySeatReservation");
  checkMySeatReservation.style.display = "block";

  fetch('/lib/seats/checkMySeatReservation', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(result => {
      console.log("Response Result:", result);  // 응답 결과 로그 출력

      const reservedSeatNoElement = document.getElementById('reservedSeatNo');
      const startBookingTimeElement = document.getElementById('startBookingTime');

      console.log("reservedSeatNoElement:", reservedSeatNoElement);
      console.log("startBookingTimeElement:", startBookingTimeElement);

      if (result.SEATNO && result.STARTBOOKING) {
        if (reservedSeatNoElement && startBookingTimeElement) {
          reservedSeatNoElement.innerText = result.SEATNO;
          startBookingTimeElement.innerText = formatDateTime(result.STARTBOOKING);
        } else {
          console.error('HTML 요소를 찾을 수 없습니다.');
        }
      } else {
        alert('예약된 정보가 없습니다.');

        // 모달 닫기
        checkMySeatReservation.style.display = "none";
      }
    })
    .catch(error => {
      // 모달 닫기
      checkMySeatReservation.style.display = "none";

      console.error('Error:', error);
      alert('열람실 예약이 없습니다.');


    });
}

// 공간 예약 취소하기
function cancleSeatBooking() {

  fetch('/lib/seats/cancleSeatBooking', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(result => {
      alert(result.message);

      // 모달 내부 텍스트 지우기
      const reservedSeatNo = document.querySelector("#reservedSeatNo");
      const startBookingTime = document.querySelector("#startBookingTime");

      reservedSeatNo.innerText = '';
      startBookingTime.innerText = '';

    })

  // 모달 닫기
  const checkMySeatReservation = document.querySelector("#checkMySeatReservation");
  checkMySeatReservation.style.display = "none";
}
