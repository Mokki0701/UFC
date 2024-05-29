const searchBtn = document.querySelector("#searchBtn");
const approveBtns = document.querySelectorAll(".approveBtn");
const searchBar = document.querySelector("#search-bar");

let checkMod = 1;

searchBtn.addEventListener("click", e=>{

    if(checkMod === 1){

        fetch("/loan/querySelect?query=" + searchBar.value)
        .then(resp=> resp.text())
        .then(html=>{
            document.querySelector('.loanBook').outerHTML = html;
    
        });

    }
    else{

        fetch("/loan/returnSelect?query=" + searchBar.value)
        .then(resp=> resp.text())
        .then(html=>{
            document.querySelector('.returnBook').outerHTML = html;
        })

    }


})

function handleApprove(button){

    const actionButtonsTd = button.parentElement;
    const row = actionButtonsTd.parentElement;

    const bookNoCell = row.children[1];
    const memberNoCell = row.children[3];

    const bookNo = bookNoCell.innerText.trim();
    const memberNo = memberNoCell.innerText.trim();

    console.log(bookNo);
    console.log(memberNo);

    fetch("/loan/approveLoan?bookNo=" + bookNo + "&query=" + searchBar.value + "&memberNo=" + memberNo)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.loanBook').outerHTML = html;
    })

}

function handleDelete(button){

    const actionButtonsTd = button.parentElement;
    const row = actionButtonsTd.parentElement;

    const bookNoCell = row.children[1];
    const memberNoCell = row.children[3];

    const bookNo = bookNoCell.innerText.trim();
    const memberNo = memberNoCell.innerText.trim();

    console.log(bookNo);
    console.log(memberNo);

    fetch("/loan/deleteLoan?bookNo=" + bookNo + "&query=" + searchBar.value + "&memberNo=" + memberNo)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.loanBook').outerHTML = html;
    })

}

function showLoanStatus() {
    document.getElementById('loanBookContainer').style.display = 'block';
    document.getElementById('returnBookContainer').style.display = 'none';
    checkMod = 1;
}

function showReturnStatus() {
    document.getElementById('loanBookContainer').style.display = 'none';
    document.getElementById('returnBookContainer').style.display = 'block';
    checkMod = 0;
}

function handlePagination(cp){

    fetch("/loan/returnSelect?cp=" + cp)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.returnBook').outerHTML = html;
    });

}


function handleComplete(button){

    const actionButtonsTd = button.parentElement;
    const row = actionButtonsTd.parentElement;

    const loanNoCell = row.children[0];
    const bookNoCell = row.children[1];

    const loanNo = loanNoCell.innerText.trim();
    const bookNo = bookNoCell.innerText.trim();

    fetch("/loan/completeSelect?loanBookNo=" + loanNo + "&query=" + searchBar.value + "&bookNo=" + bookNo)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.returnBook').outerHTML = html;
    });

}


function handleExtend(button){

    const actionButtonsTd = button.parentElement;
    const row = actionButtonsTd.parentElement;

    const loanNoCell = row.children[0];
    const returnDateCell = row.children[6];

    const loanNo = loanNoCell.innerText.trim();
    const returnDate = returnDateCell.innerText

    fetch("/loan/extend?loanBookNo=" + loanNo)

    const date = new Date(returnDate);

    date.setDate(date.getDate() + 7);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    const newDateString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

    returnDateCell.innerText = newDateString;
}


