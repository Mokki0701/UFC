const categoryCheckbox = document.querySelectorAll(".category_checkbox");

for(let i of categoryCheckbox){

    
    i.addEventListener("change", e=>{
        
        const storageName = i.nextElementSibling.innerText;
        const storage = i.nextElementSibling;
        const catContainer = document.createElement("div");
        
        if(e.target.checked){

            fetch("/book/catList?storageName=" + storageName)
            .then(resp => resp.json())
            .then(catList => {
    
                for(let catName of catList){
       
                    const div = document.createElement("div");
                    const checkbox = document.createElement("input");
                    
                    checkbox.setAttribute("type", "checkbox");

                    /* 여기서 이벤트 리스너를  */
                    checkbox.addEventListener("change", f=>{

                        /* 카테고리 체크박스가 체크 됬을 때 */
                        if(f.target.checked){
                            addCatList(1, catName);
                        }
                        /* 카테고리 체크박스가 해제 됬을 때 */
                        else{
                            addCatList(0, catName);
                        }

                    })

                    div.innerText = catName;
                    
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

function addCatList(check, catName){
    
    /* session에 보내서 계속 저장해야하나? */
    fetch("/book/search?catName=" + catName + "&check=" + check)
    .then(resp=> resp.text())
    .then(html=>{
        document.querySelector('.searchBook').outerHTML = html;
    })
    
}









