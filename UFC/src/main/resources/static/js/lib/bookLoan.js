const searchBtn = document.querySelector("#searchBtn");
const approveBtns = document.querySelectorAll(".approveBtn");
const searchBar = document.querySelector("#search-bar");

searchBtn.addEventListener("click", e=>{


    fetch("/loan/querySelect?query=" + searchBar.value)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.loanBook').outerHTML = html;

    })

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
}

function showReturnStatus() {
    document.getElementById('loanBookContainer').style.display = 'none';
    document.getElementById('returnBookContainer').style.display = 'block';
}





