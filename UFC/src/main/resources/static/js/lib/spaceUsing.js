let selectedSpaceNo = null;

document.addEventListener('DOMContentLoaded', () => {
  const drawingArea = document.getElementById('drawingArea');
  const currentSelectSpaces = document.querySelectorAll(".currentSelectSpace");

  // db에서 공간 정보 불러오는 기능
  function loadSpaces() {
    fetch('/lib/space/getSpaces')
      .then(response => response.json())
      .then(data => {
        drawingArea.innerHTML = ''; // 기존 내용을 지웁니다.

        data.forEach(space => {
          const newDiv = document.createElement('div');

          /* 상태 설정 */
          const spaceAvail = space.spaceAvail;
          if (spaceAvail === 0) {
            newDiv.classList.add('avail');
          } else if (spaceAvail === 1) {
            newDiv.classList.add('nowUsing');
          } else if (spaceAvail === 2) {
            newDiv.classList.add('disavail');
          }

          /* 좌표 및 크기 설정 */
          newDiv.classList.add('drawnDiv'); // 기존 클래스에 추가
          newDiv.style.left = `${space.left}px`;
          newDiv.style.top = `${space.top}px`;
          newDiv.style.width = `${space.width}px`;
          newDiv.style.height = `${space.height}px`;
          newDiv.innerText = `${space.spaceNo}번 방`;
          newDiv.addEventListener('click', () => {
            document.querySelectorAll('.drawnDiv').forEach(div => {
              if (div !== newDiv) {
                div.classList.remove('chosen');
              }
            });

            // 이용 가능한 공간에만 클릭 반응
            if (!newDiv.classList.contains('nowUsing') && !newDiv.classList.contains('disavail')) {
              // 선택 가능한 div에만 클래스를 변경
              newDiv.classList.toggle('chosen');
              selectedSpaceNo = newDiv.classList.contains('chosen') ? parseInt(newDiv.innerText.match(/\d+/)[0]) : null;

              // 공간의 innerText에서 숫자만 추출
              const spaceNo = selectedSpaceNo;

              currentSelectSpaces.forEach(e => {
                e.textContent = spaceNo !== null ? spaceNo : "";
              });
            }
          });
          drawingArea.appendChild(newDiv);
        });
      })
      .catch(error => console.error('불러오기 실패', error));

    // 메세지 출력하기
    const message = document.getElementById('message').innerText;
    if (message) {
      alert(message);
    }
  }

  // 화면이 로딩되면 db에서 공간 정보 불러오는 기능 실행
  loadSpaces();

  // 현재 시간 표시용
  function updateTime() {
    const now = new Date();
    const year = String(now.getFullYear()).substring(2);
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const formattedTime = `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분 ${seconds}초`;
    document.getElementById('currentTime').textContent = formattedTime;
  }

  // 기능 실행
  updateTime();
  // 매초마다 업데이트
  setInterval(updateTime, 1000);
});



// 공간 이용하기 버튼을 눌렀을 때, 선택지 제시
function wannaUseSpace() {
  const useDiv = document.querySelector(".chosen");

  if (useDiv == null) {
    alert("해당 좌석은 이용하실 수 없습니다.");
    return;
  }

  const spaceNo = parseInt(useDiv.innerText.match(/\d+/)[0]);
  let userConfirmed = confirm(`${spaceNo}번 자리를 이용하시겠습니까?`);

  if (userConfirmed) {
    document.getElementById('actionInput').value = spaceNo;
    document.getElementById('actionForm').action = "/lib/space/wannaUseSpace"; // 절대 경로로 설정
    document.getElementById('actionForm').submit();
  }
}


// 공간 그만 사용하기
function stopUsingSpace() {
  let userConfirmed = confirm("그만 사용하시겠습니까?");
  if (userConfirmed) {
    document.getElementById('actionForm').action = "/lib/space/stopUsingSpace";
    document.getElementById('actionForm').submit();
  }
}

// 자리 연장하기 기능
/* 추가 할 것
  1. 현재 시간 ~ 예약 종료 예정 시간(이용시작 시간 부터+4시간) 내에 예약이 있으면
     연장이 불가능하다(아직 추가 안함.. 예약 기능 추가하고 추가 할 예정)
*/
function extendSpace() {
  let userConfirmed = confirm("자리를 연장하시겠습니까?");
  if (userConfirmed) {
    document.getElementById('actionForm').action = "/lib/space/extendUseSpace";
    document.getElementById('actionForm').submit();

  }
}

// 공간 예약하기 기능
/* 제약조건
  1. 회원이 이용 중인 공간이 없어야 하고
  2. 회원이 이용 중인 열람실이 없어야 하고
  3. 회원이 같은 시간에 space, seat를 동시에 예약할 수 없고,
  4. 예약 시작 시간이 해당 공간의 종료시간 이후여야하고
  5. 다른 회원과 예약이 겹쳐도 되는가? : ㅇㅇ 됨. 
     현재 공간 사용자가 연장을 못하게 하려는 목적임
*/

// 공간 예약하기 기능
function bookingSpace() {
  if (selectedSpaceNo === null) {
    alert("예약하고자 하는 공간을 선택해 주세요.");
    return;
  }

  const bookingModal = document.getElementById('bookingModal');
  const currentTimeSpan = document.getElementById('currentTime');

  // 선택한 공간 번호 표시
  document.querySelectorAll(".currentSelectSpace").forEach(element => {
    element.textContent = selectedSpaceNo;
  });

  // 모달 표시
  bookingModal.style.display = "block";

  // 현재 시간 표시
  const now = new Date();
  currentTimeSpan.textContent = now.toLocaleTimeString();
}

function closeModal() {
  const bookingModal = document.getElementById('bookingModal');
  const checkMySpaceDataModal = document.querySelector('.checkMySpaceDataModal');
  const checkMySpaceReservationModal = document.querySelector('.checkMySpaceReservationModal');
  // 모달 숨기기
  bookingModal.style.display = "none";
  checkMySpaceDataModal.style.display = "none";
  checkMySpaceReservationModal.style.display = "none";

}

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
  const bookingModal = document.getElementById('bookingModal');
  const checkMySpaceDataModal = document.querySelector('.checkMySpaceDataModal');
  const checkMySpaceReservationModal = document.querySelector('.checkMySpaceReservationModal');

  if (event.target == bookingModal) {
    bookingModal.style.display = "none";
  }

  // checkMySpaceDataModal을 클릭한 경우 처리
  if (event.target == checkMySpaceDataModal) {
    checkMySpaceDataModal.style.display = "none";
  }

  if (event.target == checkMySpaceReservationModal) {
    checkMySpaceReservationModal.style.display = "none";
  }
}

function realBookingSpace() {
  const selectedSpaceNo = document.querySelector(".currentSelectSpace").textContent;
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
    spaceNo: selectedSpaceNo,
    memberNo: parseInt(memberNo),
    startTime: selectedTime
  };

  fetch('/lib/space/bookSpace', {
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

// 나의 공간 확인하기
function checkMySpace() {
  const checkMySpaceDataModal = document.querySelector('.checkMySpaceDataModal');
  checkMySpaceDataModal.style.display = "block";

  const memberNo = document.getElementById('userInfo').getAttribute('data-member-no');


  /* 비동기식 정보 보내기 */
  fetch('/lib/space/checkMySpace', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ memberNo: memberNo })
  })
    .then(response => response.json())
    .then(result => {
      console.log(result); // 응답 결과를 콘솔에 출력

      if (result.message) {
        alert(result.message); // 메시지가 있을 경우 alert로 출력
      } else {

        document.getElementById('startTime').innerText = result.startTime || '';
        document.getElementById('endTime').innerText = result.endTime || '';
        document.getElementById('remainingExtensions').innerText = result.remainingExtensions !== undefined && result.remainingExtensions !== null ? result.remainingExtensions : '';
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
}


// 공간 예약 확인하기
function checkMySpaceReservation() {

  const checkMySpaceReservationModal = document.querySelector('.checkMySpaceReservationModal');
  checkMySpaceReservationModal.style.display = "block";

  const memberNo = document.getElementById('userInfo').getAttribute('data-member-no');
  /* 비동기식 정보 보내기 */
  fetch('/lib/space/checkMySpaceReservation', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ memberNo: memberNo })
  })
    .then(response => response.json())
    .then(result => {
      console.log(result); // 응답 결과를 콘솔에 출력

      if (result.message) {
        alert(result.message); // 메시지가 있을 경우 alert로 출력
      } else {

        document.getElementById('reservedSpaceNo').innerText = result.spaceNo || '';
        document.getElementById('startBookingTime').innerText = result.startBooking || '';
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });

}

// 공간 예약 취소하기
function cancleSpceBooking() {

  const memberNo = document.getElementById('userInfo').getAttribute('data-member-no');

  /* 비동기식 정보 보내기 */
  fetch('/lib/space/cancleSpceBooking', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ memberNo: memberNo })
  })
    .then(response => response.json())
    .then(result => {
      console.log(result); // 응답 결과를 콘솔에 출력

      const checkMySpaceReservationModal = document.querySelector('.checkMySpaceReservationModal');

      if (result == 1) {
        alert("예약이 취소되었습니다.");
        checkMySpaceReservationModal.style.display = "none";
      }

      if (result == 0) {
        alert("예약이 없습니다");
        checkMySpaceReservationModal.style.display = "none";
      }

      else {
        alert("예약 취소 중 오류가 발생했습니다. 관리자에게 문의하세요.");
        checkMySpaceReservationModal.style.display = "none";
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });

}








// 공간 정보를 가져옴
// 생성된 공간의 갯수만큼 반복
// 가져올 공간 정보 : 
// spaceNo2, spaceName -> space테이블
// spaceDone -> rentSpace 테이블


function spaceInfo() {
  fetch('/lib/space/spaceDoneTime', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' }
  })
  .then(response => response.json())
  .then(result => {
    console.log(result); // 받은 데이터를 콘솔에 출력하여 확인

    const spaceUsage = document.querySelector('.spaceUsage');
    if (result != null && result.length > 0) {
      if (spaceUsage != null) {
        // 내부 요소를 초기화
        spaceUsage.innerHTML = '';

        // 공간 갯수만큼 반복하여 요소 생성
        result.forEach(space => {
          const smallSpaceUsage = document.createElement('div');
          smallSpaceUsage.classList.add('smallSpaceUsage');
          
          // 공간 번호와 종료 예정 시간을 설정
          smallSpaceUsage.innerHTML = `
            <span>공간 번호 : <span class="sn">${space.spaceNo2}</span></span>
            <span>종료 예정 시간 : <span class="sd">${space.spaceDone}</span></span>
          `;
          
          spaceUsage.appendChild(smallSpaceUsage);
        });
      }
    } else {
      console.warn("No space information received.");
      // 빈 상태를 처리하는 로직 추가
      if (spaceUsage != null) {
        spaceUsage.innerHTML = '<p>현재 이용중인 공간이 없습니다.</p>';
      }
    }
  })
  .catch(error => console.error('Error:', error));
}






