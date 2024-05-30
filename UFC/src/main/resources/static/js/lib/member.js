function LoanModal(title) {
    document.getElementById("loanTitle").innerText = title;
    document.getElementById("loanModal").style.display = "block";
}

function closeLoanModal() {
    document.getElementById("loanModal").style.display = "none";
}

function RentModal(title) {
    document.getElementById("rentTitle").innerText = title;
    document.getElementById("rentModal").style.display = "block";
}

function closeRentModal() {
    document.getElementById("rentModal").style.display = "none";
}

function hopeModal(title) {
    document.getElementById("hopeTitle").innerText = title;
    document.getElementById("hopeModal").style.display = "block";
}

function closeHopeModal() {
    document.getElementById("hopeModal").style.display = "none";
}

// 클릭하여 모달 외부를 클릭하면 모달 닫기
window.onclick = function(event) {
    const modal = document.querySelectorAll(".modal");

    for(let i of modal){
        if (event.target == i) {
            i.style.display = "none";
        }
    }
}

function mySeat(){
    location.href = "/libMain/toUseSeat";
}

function mySpace(){
    location.href = "/libMain/toSpace";
}

function extendApply(button){
    const parentTd = button.closest('td');
    const bookNo = parentTd.getAttribute('value');

    fetch("/libMember/extendBook?bookNo=" + bookNo)
    .then(resp => resp.text())
    .then(result => {
        if (result > 0) {
            alert("연장 신청 되었습니다.");
            button.disabled = true;
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function submitHope(memberNo){

    const param = {
        "memberNo" : memberNo,
        "bookTitle" : document.getElementById("bookTitle").value,
        "authorLastName" : document.getElementById("authorLastName").value,
        "authorFirstName" : document.getElementById("authorFirstName").value,
        "publisher" : document.getElementById("publisher").value
    }

    fetch("/libMember/insertHope", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(param)
    })
    .then(resp=>resp.text())
    .then(result=>{
        if(result > 0){
            alert("신청 완료!");
            closeHopeModal();
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });

}