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

  // 버튼 클릭 시 동작
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
        // 페이지를 리다이렉트
        window.location.href = '/';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('이용 종료 중 오류가 발생했습니다.');
    });
});

// 모달 닫는 기능
function closeModal(){
 
  const bookingModal = document.getElementById('bookingModal');
  bookingModal.style.display = "none";

  const checkMySpaceDataModal = document.querySelector('.checkMySpaceDataModal');
  checkMySpaceDataModal.style.display = "none";

  const checkMySpaceReservationModal = document.querySelector('.checkMySpaceReservationModal');
  checkMySpaceReservationModal.style.display = "none";
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

  const seatNo = document.getElementById('currentSelectSeat').innerText;
  if (seatNo === null) {
    alert("예약하고자 하는 열람실 좌석을 선택해주세요.");
    return;
  }

  const bookingModal = document.getElementById('bookingModal');
  const currentTimeSpan = document.getElementById('currentTime');

  bookingModal.style.display = "block";
  const now = new Date();
  currentTimeSpan.textContent = now.toLocaleTimeString();

}



function realBookingSeat() {
  const seatNo = document.getElementById('currentSelectSeat').innerText;
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
        alert('예약이 성공적으로 완료되었습니다.\n' + result.message);
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

