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
            }

            // 공간의 innerText에서 숫자만 추출
            const spaceNo = parseInt(newDiv.innerText.match(/\d+/)[0]);
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

  // 사용중인, 사용불가 공간 클릭 막기




});





// 공간 이용하기 버튼을 눌렀을 때, 선택지 제시
function wannaUseSpace() {
  let userConfirmed = confirm("이용하시겠습니까?");
  if (userConfirmed) {
    const useDiv = document.querySelector(".chosen");
    const spaceNo = parseInt(useDiv.innerText.match(/\d+/)[0]);
    document.getElementById('actionInput').value = spaceNo;
    document.getElementById('actionForm').action = "/lib/space/wannaUseSpace"; // 절대 경로로 설정
    document.getElementById('actionForm').submit();
  }
}

function stopUsingSpace() {
  let userConfirmed = confirm("그만 사용하시겠습니까?");
  if (userConfirmed) {


    document.getElementById('actionForm').action = "/lib/space/stopUsingSpace";
    document.getElementById('actionForm').submit();
  }
}

// 자리 연장하기 기능
function extendSpace() {
  let userConfirmed = confirm("자리를 연장하시겠습니까?");
  if (userConfirmed) {
    document.getElementById('actionForm').action = "/lib/space/extendUseSpace";
    document.getElementById('actionForm').submit();

  }
}