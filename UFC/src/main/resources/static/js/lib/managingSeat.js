

const seatChart = document.querySelector('.seat-chart');
const rows = 20;
const cols = 20;
let seats = {};

for (let i = 0; i < rows; i++) {
  for (let j = 0; j < cols; j++) {
    const seat = document.createElement('div');
    seat.className = 'seat aisle';
    seat.dataset.coords = `seat-${i}-${j}`;  // data-coords 속성으로 좌표 저장
    seatChart.appendChild(seat);
    seats[`seat-${i}-${j}`] = {
      element: seat,
      condition: 'aisle'
    };
  }
}

// 드래그로 div 선택 시작
document.addEventListener('DOMContentLoaded', function () {
  const seatChart = document.querySelector('.seat-chart');
  let isDragging = false;
  let startX, startY, currentX, currentY;
  let selectionBox = null;

  seatChart.addEventListener('mousedown', function (event) {
    if (event.target.classList.contains('seat')) {
      isDragging = true;
      startX = event.clientX + window.scrollX;
      startY = event.clientY + window.scrollY;

      // 선택 박스 생성 및 초기 위치 설정
      selectionBox = document.createElement('div');
      selectionBox.className = 'selection-box';
      selectionBox.style.position = 'absolute';
      selectionBox.style.left = `${startX}px`;
      selectionBox.style.top = `${startY}px`;
      document.body.appendChild(selectionBox);

      event.preventDefault();
    }
  });

  document.addEventListener('mousemove', function (event) {
    if (isDragging) {
      currentX = event.clientX + window.scrollX;
      currentY = event.clientY + window.scrollY;

      // 선택 박스 크기와 위치 업데이트
      const width = Math.abs(currentX - startX);
      const height = Math.abs(currentY - startY);
      const left = Math.min(startX, currentX);
      const top = Math.min(startY, currentY);

      selectionBox.style.width = `${width}px`;
      selectionBox.style.height = `${height}px`;
      selectionBox.style.left = `${left}px`;
      selectionBox.style.top = `${top}px`;

      highlightSeatsInSelectionBox(left, top, width, height);
    }
  });

  document.addEventListener('mouseup', function (event) {
    if (isDragging) {
      isDragging = false;

      // 선택 박스 제거
      if (selectionBox) {
        selectionBox.remove();
        selectionBox = null;
      }

      // 선택한 좌석 상태 업데이트
      const highlightedSeats = document.querySelectorAll('.seat.dragging');
      highlightedSeats.forEach(seat => {
        toggleSeatCondition(seat);
        seat.classList.remove('dragging');
      });
    }
  });

  function highlightSeatsInSelectionBox(left, top, width, height) {
    const seats = document.querySelectorAll('.seat');
    seats.forEach(seat => {
      const rect = seat.getBoundingClientRect();
      const seatLeft = rect.left + window.scrollX;
      const seatTop = rect.top + window.scrollY;
      const seatRight = rect.right + window.scrollX;
      const seatBottom = rect.bottom + window.scrollY;

      // 좌석이 선택 박스 영역에 있는지 확인
      if (seatLeft < left + width && seatRight > left &&
        seatTop < top + height && seatBottom > top) {
        seat.classList.add('dragging');
      } else {
        seat.classList.remove('dragging');
      }
    });
  }

  function toggleSeatCondition(seat) {
    if (seat.classList.contains('aisle')) {
      seat.classList.remove('aisle');
      seat.classList.add('availSeat');
    } else if (seat.classList.contains('availSeat')) {
      seat.classList.remove('availSeat');
      seat.classList.add('disavailSeat');
    } else if (seat.classList.contains('disavailSeat')) {
      seat.classList.remove('disavailSeat');
      seat.classList.add('aisle');
    }
  }
});





// 드래그로 div 선택 끝


// 하단 버튼 클릭 시 동작

const clearSeat = document.querySelector("#clearSeat");
const doneSeat = document.querySelector("#doneSeat");
const saveSeat = document.querySelector("#saveSeat");
const loadSeat = document.querySelector("#loadSeat");

// 좌석 클리어
clearSeat.addEventListener("click", () => {
  const seatsElements = document.querySelectorAll(".seat");
  seatsElements.forEach(seat => {
    // 클래스 초기화
    seat.classList.remove("availSeat", "disavailSeat", "aisle");
    seat.classList.add("aisle");
    seat.textContent = "";

    // 좌표를 통해 객체에서 해당 좌석 정보 가져오기
    const coords = seat.dataset.coords;
    if (seats[coords]) {
      seats[coords].condition = 'aisle';  // seats 객체 상태도 업데이트
    }
  });
});

// 좌석에 번호 부여
doneSeat.addEventListener('click', () => {
  const availableSeats = document.querySelectorAll(".availSeat, .disavailSeat");
  availableSeats.forEach((seat, index) => {
    seat.textContent = "";
    seat.textContent = index + 1;
  });
});

// 로딩창 모달




// 좌석 편집 현황 저장
document.getElementById('saveSeat').addEventListener('click', () => {

  // 로딩 모달 창 열기
  const loadingModal = document.querySelector("#loadingModal");
  loadingModal.style.display = "flex";

  const seatData = [];
  const seats = document.querySelectorAll('.seat');
  seats.forEach((seat, index) => {  // index를 사용하여 SEAT_NO 고유하게 설정
    const coords = seat.dataset.coords.split('-').map(Number);
    console.log(`Coords: ${coords}`); // 디버깅용 로그
    seatData.push({
      seatNo: index + 1,  // index를 SEAT_NO로 사용
      coordiX: coords[1],
      coordiY: coords[2],
      condition: seat.classList.contains('availSeat') ? 1 : seat.classList.contains('disavailSeat') ? 2 : 0
    });
  });

  console.log('Sending seat data:', seatData);

  fetch('/lib/seats/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(seatData)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log('Save successful', data);
      alert("변경 사항 저장 완료");
      loadingModal.style.display = "none";
    })
    .catch(error => {
      console.error('There was a problem with the fetch operation:', error);
      loadingModal.style.display = "none";
    });
});


// 좌석 정보 불러오기
// 좌석 초기화 후 condition에 맞게 클래스 부여
loadSeat.addEventListener("click", () => {

  const seatChart = document.querySelector('.seat-chart');

  // 모든 좌석 초기화
  seatChart.querySelectorAll('.seat').forEach(seat => {
    seat.className = 'seat aisle';
    seat.textContent = '';
  });
  
  fetch('/lib/seats/data')
    .then(response => response.json())
    .then(data => {
      // console.log('Loaded seat data:', data); // 디버깅용 로그 추가

      // 서버에서 가져온 좌석 데이터로 업데이트
      data.forEach(seat => {
        const seatDiv = document.querySelector(`[data-coords="seat-${seat.coordiX}-${seat.coordiY}"]`);
        if (seatDiv) {
          // console.log('Updating seat:', seat); // 디버깅용 로그 추가
          seatDiv.className = 'seat ' + (seat.condition === 1 ? 'availSeat' : seat.condition === 2 ? 'disavailSeat' : 'aisle');
          seatDiv.dataset.seatNo = seat.seatNo;
        } else {
          console.warn('Seat not found for coords:', seat.coordiX, seat.coordiY); // 디버깅용 로그 추가
        }
      });
    })
    .catch(error => console.error('Error loading seats:', error));
});

function banAllSeatUsers() {
  if (confirm("정말 모든 회원의 좌석 이용을 종료시키시겠습니까?")) {
    fetch('/lib/seats/banAllSeatUsers', {  
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text();  // 서버로부터 응답 메시지를 텍스트로 받음
    })
    .then(message => {
      alert(message);  // 받은 메시지를 alert로 출력
    })
    .catch(error => {
      console.error('Error:', error);
      alert("서버와 통신 중 오류가 발생했습니다.");
    });
  }
}
