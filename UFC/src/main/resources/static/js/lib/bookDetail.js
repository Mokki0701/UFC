const reserveBtn = document.querySelector("#reserveBtn");

var swiper = new Swiper('.swiper-container', {
    slidesPerView: 1,
    spaceBetween: 30,
    loop: true,
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
});

const divisionBtn = document.querySelectorAll(".divisionBtn");

for(let i of divisionBtn){
    i.addEventListener("click", e=>{
        updateChart(i.value);
    })
}

function updateChart(type) {
    const data = type === 'age' ? ageLoanData : yearLoanDate;

    data.forEach((loanCount, index) => {
        
        const bar = document.getElementById(`bar-${index}`);
        
        let heightPercent = 0;
        
        if(type === 'age'){
            document.getElementsByClassName(`bar-label`)[index].innerText = 10*index + "대";
            document.getElementsByClassName(`loan-label`)[index].innerText = 10*index + "대";
            heightPercent = (loanCount / ageStatisticsMax) * 100; 
        }
        else{
            document.getElementsByClassName(`bar-label`)[index].innerText = 2015 + index;
            document.getElementsByClassName(`loan-label`)[index].innerText = 2015 + index;
            heightPercent = (loanCount / yearStatisticsMax) * 100; 
        }

        bar.style.height = `${heightPercent}%`;

        const loan = document.getElementById(`loan-${index}`);
        loan.innerText = loanCount; 
    });
}

document.addEventListener('DOMContentLoaded', () => {
    updateChart('age');
});

reserveBtn.addEventListener("click", e=>{

    const bookNo = bookNo2;

    location.href= "/reservation/check?bookNo=" + bookNo;


});



