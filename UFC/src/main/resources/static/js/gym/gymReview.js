const gymReviewWrite = document.querySelector("gymReviewWrite");

gymReviewWrite.addEventListener("click", () => {
    

    location.href = `/gymWrite/${gymNo}/insert`;
})