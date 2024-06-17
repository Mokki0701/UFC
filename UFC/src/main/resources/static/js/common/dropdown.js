function clickDropDown(element) {
    // 드롭다운 메뉴 생성
    const dropdownContentId = 'dropdown-content';
    let dropdownContent = document.getElementById(dropdownContentId);

    if (!dropdownContent) {
        dropdownContent = document.createElement('div');
        dropdownContent.id = dropdownContentId;
        dropdownContent.className = 'dropdown-content';

        const infoLink = document.createElement('a');
        infoLink.textContent = '쪽지쓰기';
        infoLink.onclick = function() {
            const memberName = element.textContent.trim();
            var url = "/message/send?memberName=" + memberName;
            window.open(url, 'messagePopup', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');
        };

        const logoutLink = document.createElement('a');
        logoutLink.textContent = '회원차단';
        logoutLink.onclick = function() {
            if (confirm('정말 회원을 차단하시겠습니까?')) {
                let memberName = element.textContent.trim();
                fetch("/message/block?memberName=" + memberName)
                    .then(response => response.text())
                    .then(result => {
                        if (result > 0) {
                            alert("해당 회원을 차단했습니다.");
                        } else {
                            alert("이미 해당 회원을 차단하였습니다.");
                        }
                        // 차단 후 드롭다운 메뉴 삭제
                        dropdownContent.remove();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert("회원 차단 중 오류가 발생했습니다.");
                        // 오류 발생 시에도 드롭다운 메뉴 삭제
                        dropdownContent.remove();
                    });
            }
        };

        dropdownContent.appendChild(infoLink);
        dropdownContent.appendChild(logoutLink);

        document.body.appendChild(dropdownContent);
    }

    // 클릭한 h2 태그 바로 밑에 드롭다운 메뉴 위치 설정
    const rect = element.getBoundingClientRect();
    const scrollTop = document.documentElement.scrollTop;
    const scrollLeft = document.documentElement.scrollLeft;

    dropdownContent.style.position = 'absolute';
    dropdownContent.style.top = (rect.bottom + scrollTop) + 'px';
    dropdownContent.style.left = (rect.left + scrollLeft) + 'px';

    // 드롭다운 메뉴 표시/숨기기 토글
    dropdownContent.classList.toggle('show');
}

// 드롭다운 외부를 클릭하면 닫기 및 삭제
window.onclick = function(event) {
    if (!event.target.matches('.messageDropdown')) {
        const dropdowns = document.getElementsByClassName('dropdown-content');
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.remove(); // 드롭다운 메뉴 삭제
            }
        }
    }
}

