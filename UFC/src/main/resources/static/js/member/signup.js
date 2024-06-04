const domainListEl = document.querySelector('#domain-list')
const domainInputEl = document.querySelector('#memberEmailDomain')

// select 옵션 변경 시
domainListEl.addEventListener('change', (event) => {
  // option에 있는 도메인 선택 시
  if(event.target.value !== "type") {
    // 선택한 도메인을 수정된 도메인 입력란에 입력하고 readOnly 
    // disabled로 하면 값이 넘어가질 못한다
    domainInputEl.value = event.target.value
    domainInputEl.readOnly = true
  } else { // 직접 입력 시
    // 수정: 기존 input 요소의 value를 초기화
    document.querySelector('#memberEmailDomain').value = ""
    // 수정: 직접 입력 가능하도록 변경
    domainInputEl.readOnly = false
  }
})
