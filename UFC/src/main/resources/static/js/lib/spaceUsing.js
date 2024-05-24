document.addEventListener('DOMContentLoaded', () => {
  const drawingArea = document.getElementById('drawingArea');

  // db에서 공간 정보 불러오는 기능
  function loadSpaces() {
    fetch('/lib/space/getSpaces')
      .then(response => response.json())
      .then(data => {
        drawingArea.innerHTML = ''; // 기존 내용을 지웁니다.

        data.forEach(space => {
          const newDiv = document.createElement('div');
          newDiv.className = 'drawnDiv';
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
            newDiv.classList.toggle('chosen');

            // 공간의 innerText에서 숫자만 추출
            const spaceNo = parseInt(newDiv.innerText.match(/\d+/)[0])
            const currentSelectSpace = document.querySelector("#currentSelectSpace");
            currentSelectSpace.textContent = spaceNo;
          });
          drawingArea.appendChild(newDiv);
        });
      })
      .catch(error => console.error('불러오기 실패', error));
  }

  // 화면이 로딩되면 db에서 공간 정보 불러오는 기능 실행
  loadSpaces();
});


// 공간 이용하기 버튼을 눌렀을 때, 선택지 제시
function wannaUseSpace() {
  let userConfirmed = confirm("이용하시겠습니까?");
  if (userConfirmed) {
    document.getElementById('actionInput').value = 'use';
    document.getElementById('actionForm').action = "/lib/space/wannaUseSpace"; // 절대 경로로 설정
    document.getElementById('actionForm').submit();
  }
}

function stopUsingSpace() {
  let userConfirmed = confirm("그만 사용하시겠습니까?");
  if (userConfirmed) {
    document.getElementById('actionInput').value = 'stop';
    document.getElementById('actionForm').action = "/lib/space/stopUsingSpace"; // 예시로 설정
    document.getElementById('actionForm').submit();
  }
}