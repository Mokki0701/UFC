const receivedTab = document.getElementById('receivedTab');
const sentTab = document.getElementById('sentTab');

let type = 0;

receivedTab.addEventListener('click', () => {

    messageListSelect(0);
    type = 0;

});

sentTab.addEventListener('click', () => {

    messageListSelect(1);
    type = 1;

});

function deleteMessage(button){

    const messageNo = button.previousElementSibling.textContent;
    const messageItem = button.parentElement;

    messageItem.classList.add('message-disabled');

    fetch("/message/delete?messageNo=" + messageNo)
        .then(response => response.text())
        .then(result => {
            if (result > 0) {
                messageItem.classList.add('message-disabled');
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });

}

// function paginationMessage(cp){

//     fetch("/message/select?type=" + type + "&cp=" + cp)
//         .then(response => response.text())
//         .then(html => {
//             document.querySelector('.messageSelect').innerHTML = html;
//         });

// }

function openMessagePopup(element) {
    var messageNo = element.previousElementSibling.textContent;
    var url = "/message/detail?messageNo=" + messageNo;
    window.open(url, 'messagePopup', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');
}

function openSendMessagePopup(){

    var url = "/message/send";
    window.open(url, 'messageSend', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');

}

function clickDropDown() {
    // CSS 스타일을 동적으로 추가
    const styles = `
        .messageDropdown {
            position: relative;
            display: inline-block;
        }
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: white;
            min-width: 100px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
            border: 1px solid #ccc;
        }
        .dropdown-content a {
            color: black;
            padding: 10px 12px;
            text-decoration: none;
            display: block;
        }
        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }
        .show {
            display: block;
        }
    `;
    document.getElementById('message-styles').innerHTML = styles;

    // 드롭다운 메뉴 생성
    const dropdownContentId = 'dropdown-content';
    let dropdownContent = document.getElementById(dropdownContentId);

    if (!dropdownContent) {
        dropdownContent = document.createElement('div');
        dropdownContent.id = dropdownContentId;
        dropdownContent.className = 'dropdown-content';

        const infoLink = document.createElement('a');
        infoLink.href = '#';
        infoLink.textContent = '쪽지쓰기';

        const logoutLink = document.createElement('a');
        logoutLink.href = '#';
        logoutLink.textContent = '회원차단';

        dropdownContent.appendChild(infoLink);
        dropdownContent.appendChild(logoutLink);

        document.querySelector('.messageDropdown').appendChild(dropdownContent);
    }

    // 드롭다운 메뉴 표시/숨기기 토글
    dropdownContent.classList.toggle('show');
}

// 드롭다운 외부를 클릭하면 닫기
window.onclick = function(event) {
    if (!event.target.matches('.messageDropdown')) {
        const dropdowns = document.getElementsByClassName('dropdown-content');
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}



