// 승낙/거절 버튼 클릭 시
function accept(memberNo, button) {
  fetch('/lesson/inst/accept', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(memberNo)
  })
    .then(response => {
      if (response.ok) {
        alert(memberNo + "번 회원의 요청이 승낙되었습니다.");
        const row = button.closest('tr');
        row.remove();
      } else {
        alert('승낙 요청에 실패했습니다.');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('승낙 요청 중 오류가 발생했습니다.');
    });
}

function reject(memberNo, button) {
  fetch('/lesson/inst/reject', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(memberNo)
  })
    .then(response => {
      if (response.ok) {
        alert(memberNo + "번 회원의 요청이 거절되었습니다.");
        const row = button.closest('tr');
        row.remove();
      } else {
        alert('거절 요청에 실패했습니다.');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('거절 요청 중 오류가 발생했습니다.');
    });
}
// 승낙/거절 버튼 클릭 시 끝