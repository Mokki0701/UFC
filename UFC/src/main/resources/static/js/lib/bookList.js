const categoryCheckbox = document.querySelectorAll(".category_checkbox");
const searchBookBtn= document.querySelector("#searchBookBtn");
const searchQuery = document.querySelector("#searchQuery");

for(let i of categoryCheckbox){

    
    i.addEventListener("change", e=>{
        
        const storageName = i.nextElementSibling.innerText;
        const storage = i.nextElementSibling;
        const catContainer = document.createElement("div");
        
        if(e.target.checked){

            fetch("/book/catList?storageName=" + storageName)
            .then(resp => resp.json())
            .then(catList => {

                for(let cat of catList){    

                    const div = document.createElement("div");
                    const checkbox = document.createElement("input");
                    
                    checkbox.setAttribute("type", "checkbox");

                    /* 여기서 이벤트 리스너를  */
                    checkbox.addEventListener("change", f=>{

                        /* 아래 있는거 삭제하고, 
                            체크되면 catNames에 값 추가해서 쿼리스트링으로 넣고,
                            체크 해제되면 catNames에 값이 있는지 확인하고, 있으면 제거한다.
                        */

                        console.log(f.target.checked);

                        if (f.target.checked) {
                            // 체크된 경우 catNames에 추가
                            if (!catNumbers.includes(cat.catNo)) {
                                catNumbers.push(cat.catNo);
                            }

                            addCatList(catNumbers);

                        } else {
                            // 체크 해제된 경우 catNames에서 제거
                            const index = catNumbers.indexOf(cat.catNo);
                            if (index !== -1) {
                                catNumbers.splice(index, 1);
                            }

                            addCatList(catNumbers);
                        }


                    })

                    div.innerText = cat.catName;
                    
                    catContainer.appendChild(div);
                    catContainer.appendChild(checkbox);
                    
                }
            });

            storage.appendChild(catContainer);

        }

        else{
            
            storage.children[0].remove();

        }




    })



}

function checkCategory(){

    let catParams = [];

    for (let i of categoryCheckbox) {
        if (i.checked) {
            catParams.push(encodeURIComponent(i.nextElementSibling.innerText));
        }
    }
    
    // 쿼리 스트링 형태로 변환
    let queryString = catParams.map((param, index) => `cat${index + 1}=${param}`).join('&');
    
}


function addCatList(catNumbers){
    
    console.log(catNumbers);

    /* session에 보내서 계속 저장해야하나? */
    fetch("/book/category?catNumbers=" + catNumbers)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.searchBook').outerHTML = html;
    })
    
}

searchBookBtn.addEventListener("click", e=>{

    
    const searchQuery2 = document.querySelector("#searchQuery");
    console.log(searchQuery2.value);
    fetch("/book/search?query="+ searchQuery2.value + "&catNumbers=" + catNumbers)
    .then(resp=>resp.text())
    .then(html=>{

        document.querySelector('.searchBook').outerHTML = html;

    })
})

function bookDetailSelect(){
    
    document.querySelectorAll('.book-detail').forEach(bookDetail => {
        bookDetail.addEventListener('click', () => {
    
            const bookNo = bookDetail.getAttribute('data-book-no');
            
            location.href = "/book/bookDetail?bookNo=" + bookNo;
    
        });
    });

}
 
function paginationSelect(cp){

    fetch("/book/category?cp=" + cp + "&catNumbers=" + catNumbers)
    .then(resp => resp.text())
    .then(html => {
        document.querySelector('.searchBook').outerHTML = html;
    })

}

function bookInput(){
    
    var url = "/loan/store";
    window.open(url, 'messagePopup', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');
    
}