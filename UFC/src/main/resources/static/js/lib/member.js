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


