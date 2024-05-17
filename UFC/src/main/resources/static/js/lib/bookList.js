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











