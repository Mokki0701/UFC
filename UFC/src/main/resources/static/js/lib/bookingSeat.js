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
        const seatStatus = seat.classList.contains('availSeat') ? '사용 가능' :
                           seat.classList.contains('disavailSeat') ? '사용 불가능' :
                           seat.classList.contains('nowUsing') ? '사용 중' : '없음';
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

  useSeatBtn.addEventListener("click", () => {
    if (selectedSeat && selectedSeat.classList.contains('availSeat') && !selectedSeat.classList.contains('nowUsing') && !selectedSeat.classList.contains('disavailSeat')) {
      const dbSeatNo = document.getElementById('dbSeatNo').textContent;
      if (dbSeatNo !== '') {
        fetch(`/lib/seat/useSeat?seatNo=${dbSeatNo}`, {
          method: 'POST'
        })
        .then(response => response.json())
        .then(data => {
          console.log('Seat usage response:', data);
          alert("좌석 사용 상태가 업데이트되었습니다.");
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
