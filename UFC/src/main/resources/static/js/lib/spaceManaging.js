/* session.loginMember == null && session.loginMember.getMemberAuthority != 3 */




const drawingArea = document.getElementById('drawingArea');
const editButton = document.getElementById('editButton');
const assignButton = document.getElementById('assignButton');
const sizeForm = document.getElementById('sizeForm');
const widthInput = document.getElementById('width');
const heightInput = document.getElementById('height');
const applySizeButton = document.getElementById('applySize');
let isDrawing = false;
let startX, startY, currentDiv;
let roomCounter = 1;
let isEditing = false;
let selectedDiv = null;
let isDragging = false;
let offsetX, offsetY;

function startDrawing(e) {
  isDrawing = true;
  startX = e.clientX - drawingArea.offsetLeft;
  startY = e.clientY - drawingArea.offsetTop;
  currentDiv = document.createElement('div');
  currentDiv.className = 'drawnDiv';
  currentDiv.style.left = `${startX}px`;
  currentDiv.style.top = `${startY}px`;
  currentDiv.style.width = '0px';
  currentDiv.style.height = '0px';
  currentDiv.addEventListener('mousedown', startDrag);
  drawingArea.appendChild(currentDiv);
}

function draw(e) {
  if (!isDrawing) return;
  const currentX = e.clientX - drawingArea.offsetLeft;
  const currentY = e.clientY - drawingArea.offsetTop;
  const width = currentX - startX;
  const height = currentY - startY;

  if (width < 0) {
    currentDiv.style.left = `${currentX}px`;
    currentDiv.style.width = `${-width}px`;
  } else {
    currentDiv.style.width = `${width}px`;
  }

  if (height < 0) {
    currentDiv.style.top = `${currentY}px`;
    currentDiv.style.height = `${-height}px`;
  } else {
    currentDiv.style.height = `${height}px`;
  }
}

function stopDrawing() {
  isDrawing = false;
  roomCounter++;
}

function toggleEditing() {
  isEditing = !isEditing;
  if (isEditing) {
    drawingArea.addEventListener('mousedown', startDrawing);
    drawingArea.addEventListener('mousemove', draw);
    drawingArea.addEventListener('mouseup', stopDrawing);
    drawingArea.addEventListener('mouseleave', stopDrawing);
    editButton.textContent = "편집 모드 종료";
  } else {
    drawingArea.removeEventListener('mousedown', startDrawing);
    drawingArea.removeEventListener('mousemove', draw);
    drawingArea.removeEventListener('mouseup', stopDrawing);
    drawingArea.removeEventListener('mouseleave', stopDrawing);
    editButton.textContent = "공간 편집하기";
    sizeForm.style.display = 'none';
  }
}

function selectDiv(e) {
  if (isEditing) return; // 편집 모드에서는 선택 불가
  selectedDiv = e.target;
  widthInput.value = parseInt(selectedDiv.style.width);
  heightInput.value = parseInt(selectedDiv.style.height);
  sizeForm.style.display = 'block';
}

function applySize() {
  if (selectedDiv) {
    const newWidth = widthInput.value;
    const newHeight = heightInput.value;
    selectedDiv.style.width = `${newWidth}px`;
    selectedDiv.style.height = `${newHeight}px`;
    sizeForm.style.display = 'none';
    selectedDiv = null;
  }
}

function startDrag(e) {
  if (isEditing) return; // 편집 모드에서는 드래그 불가
  isDragging = true;
  selectedDiv = e.target;
  offsetX = e.clientX - selectedDiv.offsetLeft;
  offsetY = e.clientY - selectedDiv.offsetTop;
  window.addEventListener('mousemove', drag);
  window.addEventListener('mouseup', stopDrag);
}

function drag(e) {
  if (!isDragging) return;
  let newLeft = e.clientX - offsetX;
  let newTop = e.clientY - offsetY;

  // Ensure the div stays within the drawingArea bounds
  const rect = selectedDiv.getBoundingClientRect();
  const parentRect = drawingArea.getBoundingClientRect();

  // 테두리에 가까워지면 달라붙는 기능
  if (newLeft < SNAP_DISTANCE) newLeft = 0;
  if (newTop < SNAP_DISTANCE) newTop = 0;
  if (newLeft + rect.width > parentRect.width - SNAP_DISTANCE) newLeft = parentRect.width - rect.width;
  if (newTop + rect.height > parentRect.height - SNAP_DISTANCE) newTop = parentRect.height - rect.height;

  selectedDiv.style.left = `${newLeft}px`;
  selectedDiv.style.top = `${newTop}px`;
}

function stopDrag() {
  isDragging = false;
  window.removeEventListener('mousemove', drag);
  window.removeEventListener('mouseup', stopDrag);
}

function assignNumbers() {
  const divs = Array.from(drawingArea.getElementsByClassName('drawnDiv'));
  divs.sort((a, b) => {
    const aRect = a.getBoundingClientRect();
    const bRect = b.getBoundingClientRect();
    return (aRect.top - bRect.top) || (aRect.left - bRect.left);
  });

  divs.forEach((div, index) => {
    div.innerText = `${index + 1}번 방`;
  });
}

editButton.addEventListener('click', toggleEditing);
applySizeButton.addEventListener('click', applySize);
assignButton.addEventListener('click', assignNumbers);
drawingArea.addEventListener('click', (e) => {
  if (e.target.classList.contains('drawnDiv') && !isEditing) {
    selectDiv(e);
  }
});

const createButton = document.getElementById('createButton');

function createDiv() {
  const newDiv = document.createElement('div');
  newDiv.className = 'drawnDiv';
  newDiv.style.left = '50px';
  newDiv.style.top = '50px';
  newDiv.style.width = '100px';
  newDiv.style.height = '100px';
  newDiv.innerText = `${roomCounter}번 방`;
  newDiv.addEventListener('mousedown', startDrag);
  drawingArea.appendChild(newDiv);
  roomCounter++;
}

createButton.addEventListener('click', createDiv);

function drag(e) {
  if (!isDragging) return;
  let newLeft = e.clientX - offsetX;
  let newTop = e.clientY - offsetY;

  // Ensure the div stays within the drawingArea bounds
  const rect = selectedDiv.getBoundingClientRect();
  const parentRect = drawingArea.getBoundingClientRect();

  if (newLeft < 0) newLeft = 0;
  if (newTop < 0) newTop = 0;
  if (newLeft + rect.width > parentRect.width) newLeft = parentRect.width - rect.width;
  if (newTop + rect.height > parentRect.height) newTop = parentRect.height - rect.height;

  selectedDiv.style.left = `${newLeft}px`;
  selectedDiv.style.top = `${newTop}px`;
}


/* 저장하기 버튼 클릭 시 보낼 정보*/


// if (width < 0) {
//   currentDiv.style.left = `${currentX}px`;
//   currentDiv.style.width = `${-width}px`;
// } else {
//   currentDiv.style.width = `${width}px`;
// }

// if (height < 0) {
//   currentDiv.style.top = `${currentY}px`;
//   currentDiv.style.height = `${-height}px`;
// } else {
//   currentDiv.style.height = `${height}px`;
// }

// 1. 방의 번호 ${roomCounter}
// 2. 방의 좌표(div박스의 시작 지점)
// 2.1. 좌표 X : ${currentX} 
// 2.2. 좌표 Y : ${currentY}
// 3. 방의 크기
// 3.1. 가로 너비 : ${width}
// 3.2. 세로 높이 : ${height}




const saveData = document.getElementById('saveData');

// 만들어진 div의 정보 수집하기
function collectDivInfo() {
    const divs = Array.from(drawingArea.getElementsByClassName('drawnDiv'));
    const divInfo = divs.map(div => {
        return {
          spaceNo: parseInt(div.innerText.match(/\d+/)[0]),
            left: parseInt(div.style.left),
            top: parseInt(div.style.top),
            width: parseInt(div.style.width),
            height: parseInt(div.style.height)
        };
    });
    return divInfo;
}

function sendDivInfo() {
    const divInfo = collectDivInfo();
    fetch('/lib/space/saveSpaceManagement', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(divInfo)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

saveData.addEventListener('click', sendDivInfo);


/* 불러오기 버튼 누를 시 받을 정보 */
// fetch 형식으로 가져오기
const loadData = document.querySelector("#loadData")

function getDivFromDb(){
  
}