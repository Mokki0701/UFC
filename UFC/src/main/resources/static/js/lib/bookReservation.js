const removeBtn = document.querySelectorAll(".remove-btn");

for(let i of removeBtn){
    i.addEventListener("click", e => {
        const value = i.previousElementSibling.innerText;
        
        console.log(value);

        fetch("/reservation/delete?bookNo=" + value)
        .then(resp => resp.text())
        .then(html=>{
            document.querySelector('.reservationBook').outerHTML = html;
        })
        .catch(error => {
            console.error('Error during fetch operation:', error);
        });
    });
}
