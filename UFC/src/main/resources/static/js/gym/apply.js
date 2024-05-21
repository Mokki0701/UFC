const submit = document.querySelector("#submit");
submit.addEventListener("click", e => {
    e.preventDefault(); // 기본 제출 동작을 막음

    const form = document.querySelector("#applicationForm");
    const formData = new FormData(form);

    fetch("apply", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('제출되었습니다');
            location.href = '/gym/gymMain'; // 확인 버튼을 누르면 메인 페이지로 이동
        } else {
            alert('제출에 실패했습니다. 다시 시도해주세요.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('제출에 실패했습니다. 다시 시도해주세요.');
    });
});
