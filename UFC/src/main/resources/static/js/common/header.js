// ----------------------------------------------------------
// 웹소켓 기능

let notificationSock;       // 알림 웹소켓 객체
let sendNotificationFn;     // 웹소켓을 이용해 알림을 보내는 함수

let selectnNotificationFn;  // 비동기로 알림을 조회하는 함수
let  notReadCheckFn;         // 비동기로 읽지 않은 알림 개수 체크하는 함수

if(notificationLoginCheck){

    notificationSock = new SockJS("/notification/send");

    sendNotificationFn = (type, url, messageNo) =>{
        console.log("adsfasdf", type, url, messageNo);
        const notification = {

            "notificationType": type,
            "notificationUrl": url,
            "messageNo": messageNo

        }

        notificationSock.send(JSON.stringify(notification));

    }

    notificationSock.addEventListener("message", e=>{
        
        const notificationBtn = document.querySelector(".header-notification-btn");
        notificationBtn.classList.remove("fa-regular");
        notificationBtn.classList.add("fa-solid");

        selectnNotificationFn();
    })

    notReadCheckFn = async()=>{
        const resp = await fetch("/notification/notReadCheck")
        const notReadCount = await resp.text();

        return notReadCount;
    }

    selectnNotificationFn = ()=>{

        fetch("/notification")
            .then(resp => resp.json())
            .then(selectList => {

                const notificationList = document.querySelector(".header-notification-list");
                notificationList.innerHTML = "";

                for(let data of selectList){

                    const notiItem = document.createElement("li");
                    notiItem.className = 'header-notification-item';

                    if(data.notificationCheck === "N"){
                        notiItem.classList.add("not-read");
                    }

                    const notiText = document.createElement("div");
                    notiText.className = 'header-notification-text';

                    notiText.addEventListener("click", ()=>{

                        if(data.notificationCheck === "N"){
                            fetch("/notification", {
                                method: "PUT",
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: data.notificationNo
                            })
                        }

                        location.href= data.notificationUrl;

                    })

                    const contentContainer = document.createElement("div");
                    contentContainer.className = 'header-notification-content-container';

                    const notiDate = document.createElement("p");
                    notiDate.className = 'header-notification-date';
                    notiDate.innerText = data.notificationDate;

                    const notiContent = document.createElement("p");
                    notiContent.className = 'header-notification-content';
                    notiContent.innerHTML = data.notificationContent;

                    const notiDelete = document.createElement("span");
                    notiDelete.className = 'header-notification-delete';
                    notiDelete.innerHTML = '&times;';

                    notiDelete.addEventListener("click", ()=>{

                        fetch("/notification", {
                            method: "DELETE",
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: data.notificationNo
                        })
                        .then(resp => resp.text())
                        .then(result => {
                            notiDelete.parentElement.remove();

                            notReadCheckFn().then(notReadCount => {
                                
                                const notificationBtn = document.querySelector(".header-notification-btn");

                                if(notReadCount > 0){
                                    notificationBtn.classList.remove("fa-regular");
                                    notificationBtn.classList.add("fa-solid");
                                }
                                else{
                                    notificationBtn.classList.add("fa-regular");
                                    notificationBtn.classList.remove("fa-solid");
                                }
                            })

                        })

                    })

                    notiList.append(notiItem);
                    notiItem.append(notiText, notiDelete);
                    notiText.append(senderProfile, contentContainer);
                    contentContainer.append(notiDate, notiContent);

                }
            })
    }

    document.addEventListener("DOMContentLoaded", ()=>{

        const notificationBtn = document.querySelector(".header-notification-btn");

        notReadCheckFn().then(notReadCount => {

            if(notReadCount > 0){
                notificationBtn.classList.remove("fa-regular");
                notificationBtn.classList.add("fa-solid");
            }
        })

        notificationBtn.addEventListener("click", ()=>{

            const notiList = document.querySelector(".header-notification-list");

            if(notiList.classList.contains("notification-show")){
                notiList.classList.remove("notification-show");
                return;
            }

            selectnNotificationFn();
            notiList.classList.add("notification-show");
        })

    })
}

document.addEventListener("DOMContentLoaded", ()=>{

    const params = new URLSearchParams(location.search);
    const targetId = "c" + params.get("cn");

    let targetElement = document.getElementById(targetId);

    if(targetElement){
        const scrollPosition = targetElement.offsetTop;
        window.scrollTo({top: scrollPosition - 200, behavior: 'smooth'});
    }


})

// ------------------------------------------------------------------------------------------------

document.getElementById('notification-btn').addEventListener('click', function() {
    var notificationList = document.getElementById('notification-list');
    if (notificationList.classList.contains('notification-show')) {
        notificationList.classList.remove('notification-show');
    } else {
        notificationList.classList.add('notification-show');
    }
});

document.addEventListener('click', function(event) {
    var isClickInsideButton = document.getElementById('notification-btn').contains(event.target);
    var isClickInsideList = document.getElementById('notification-list').contains(event.target);

    if (!isClickInsideButton && !isClickInsideList) {
        document.getElementById('notification-list').classList.remove('notification-show');
    }
});


