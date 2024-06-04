const gymWriteFrm = document.querySelector("#gymWriteFrm");
        gymWriteFrm.addEventListener("submit", e =>{

        const gymTtitle = document.querySelector("input[name = gymTitle]")
        const gymContent = document.querySelector("textarea[name = gymContent]")


        if(gymTtitle.value.trim().length == 0 ){
            alert("제목을 작성해 주세요")
            e.preventDefault();
            return;
        }

        if(gymContent.value.trim().length == 0 ){
            alert("내용을 작성해 주세요")
            e.preventDefault();
            return;
        }
        });