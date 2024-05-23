const drawingArea = document.getElementById('drawingArea');
const editButton = document.getElementById('editButton');
const assignButton = document.getElementById('assignButton');
const sizeForm = document.getElementById('sizeForm');
const widthInput = document.getElementById('width');
const heightInput = document.getElementById('height');
const applySizeButton = document.getElementById('applySize');
const createButton = document.getElementById('createButton');
const saveData = document.getElementById('saveData');
const loadDataBtn = document.querySelector("#loadData");
const SNAP_DISTANCE = 10;

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

    if (newLeft < 0) newLeft = 0;
    if (newTop < 0) newTop = 0;
    if (newLeft + rect.width > parentRect.width) newLeft = parentRect.width - rect.width;
    if (newTop + rect.height > parentRect.height) newTop = parentRect.height - rect.height;

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
                newDiv.addEventListener('mousedown', startDrag); // 불러온 div에도 이벤트 리스너 추가
                drawingArea.appendChild(newDiv);
            });
        })
        .catch(error => console.error('불러오기 실패', error));
}

editButton.addEventListener('click', toggleEditing);
applySizeButton.addEventListener('click', applySize);
assignButton.addEventListener('click', assignNumbers);
createButton.addEventListener('click', createDiv);
saveData.addEventListener('click', sendDivInfo);
loadDataBtn.addEventListener('click', loadSpaces);

drawingArea.addEventListener('click', (e) => {
    if (e.target.classList.contains('drawnDiv') && !isEditing) {
        selectDiv(e);
    }
});

function collectDivInfo() {
    const divs = Array.from(drawingArea.getElementsByClassName('drawnDiv'));
    const divInfo = divs.map(div => {
        return {
            spaceNo: parseInt(div.innerText.match(/\d+/)[0]), // 숫자 부분만 추출
            left: parseInt(div.style.left, 10),
            top: parseInt(div.style.top, 10),
            width: parseInt(div.style.width, 10),
            height: parseInt(div.style.height, 10)
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

const clearAllButton = document.querySelector("#clearAll");

// clearAllButton 클릭 시 모든 div 요소 지우기
clearAllButton.addEventListener('click', () => {
    drawingArea.innerHTML = '';
});