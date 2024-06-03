const reserveCheck = document.querySelectorAll(".reserveCheck");
const reserveBtn = document.querySelector("#reserveBtn");


function bookReserveDelete(){

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

}

function reserveBook(){
    
    const checkBoxs = {}
    let num = 0;

    for(let i of reserveCheck){

        if(i.checked){
            const bookNo = i.nextElementSibling.innerText;
            checkBoxs[num] = bookNo;
        }
        else{
            checkBoxs[num] = 0;
        }
        num++;
    }

    console.log(num);
    console.log(checkBoxs);

    fetch("/reservation/reserve", {
        method : 'POST',
        headers : {'Content-Type': 'application/json'},
        body : JSON.stringify(checkBoxs)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);

        window.location.href = "/main/libMain";
    })
    .catch((error) => {

        console.error('Error:', error);
    });

}
