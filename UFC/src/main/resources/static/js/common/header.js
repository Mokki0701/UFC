
function checkDoneTime() {
  console.log("Sending fetch request to /lib/space/doneTimeCheck");

  // 현재 시간 분 단위 까지만 구하기
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');

  // yyyy-mm-dd-hh-mi 형식으로 조합
  const formattedDateTime = `${year}-${month}-${day}${hours}:${minutes}`;

  console.log("Current Date and Time: ", formattedDateTime);

  // 매 분마다 요청만 보내니까 뭐 받아서 처리 할 필요 없음
  fetch('/lib/space/doneTimeCheck', {
    method: 'POST', // POST 요청으로 변경
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ dateTime: formattedDateTime }) // 요청 본문에 데이터 포함
  })
}


document.addEventListener('DOMContentLoaded', function () {
  console.log('DOMContentLoaded event fired'); // 추가된 로그

  var loginButton = document.getElementById('login-button');

  if (loginButton) {
    loginButton.addEventListener('click', function () {
      if (loginButton.textContent === '로그인') {
        loginButton.textContent = '로그아웃';
      } else {
        loginButton.textContent = '로그인';
      }
    });
  } else {
    console.error('login-button not found');
  }

  // 매 분 00초마다 대여 공간 이용 종료 확인
  
  // 현재 시간을 기준으로 다음 00초까지 남은 시간을 계산하여 처음 실행 시간 설정
  const now = new Date();
  console.log('Current time:', now); // 추가된 로그
  const delay = 60000 - (now.getSeconds() * 1000 + now.getMilliseconds());
  console.log('Initial delay:', delay); // 추가된 로그

  setTimeout(function () {
    console.log('Initial timeout fired'); // 추가된 로그
    checkDoneTime(); // 처음 실행
    // 이후 매 1분마다 실행
    setInterval(checkDoneTime, 60000);
    console.log('매 분 00초에 실행함');
  }, delay);


});

// ----------------------------------------------------------
// 웹소켓 기능

let notificationSock;       // 알림 웹소켓 객체
let sendNotificationFn;     // 웹소켓을 이용해 알림을 보내는 함수

let selectnNotificationFn;  // 비동기로 알림을 조회하는 함수
let  notReadCheckFn;         // 비동기로 읽지 않은 알림 개수 체크하는 함수

if(notificationLoginCheck){

    notificationSock = new SockJS("/notification/send");

    sendNotificationFn = (type, url, messageNo) =>{
        
        const notification = {

            "notificationType": type,
            "notificationUrl": url,
            "messageNo": messageNo

        }

        notificationSock.send(JSON.stringify(notification));

    }

    notificationSock.addEventListener("message", e=>{
        
        notReadCheckFn().then(notReadCount => {
                                
            const notificationBtn = document.querySelector(".header-notification-btn");
            const notificationCount = document.querySelector(".header-notification-num");

            notificationCount.innerText= notReadCount;

            if(notReadCount > 0){
                notificationBtn.classList.remove("fa-regular");
                notificationBtn.classList.add("fa-solid");
            }
            else{
                notificationBtn.classList.add("fa-regular");
                notificationBtn.classList.remove("fa-solid");
            }
        })

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

                console.log(selectList);
                const notificationList = document.querySelector(".header-notification-list");
                notificationList.innerHTML = "";

                for(let data of selectList){

                    const notiItem = document.createElement("li");
                    notiItem.className = 'header-notification-item';

                    if(data.notificationCheck === 0){
                        notiItem.classList.add("not-read");
                    }

                    const notiText = document.createElement("div");
                    notiText.className = 'header-notification-text';

                    notiText.addEventListener("click", ()=>{

                        if(data.notificationCheck === 0){
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
                                const notificationCount = document.querySelector(".header-notification-num");

                                notificationCount.innerText= notReadCount;

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

                    notificationList.append(notiItem);
                    notiItem.append(notiText, notiDelete);
                    notiText.append( contentContainer);
                    contentContainer.append(notiDate, notiContent);

                }
            })
    }

    document.addEventListener("DOMContentLoaded", ()=>{

        const notificationBtn = document.querySelector(".header-notification-btn");

        notReadCheckFn().then(notReadCount => {

            const notificationCount = document.querySelector(".header-notification-num");

            notificationCount.innerText= notReadCount;

            if(notReadCount > 0){
                notificationBtn.classList.remove("fa-regular");
                notificationBtn.classList.add("fa-solid");
            }
        })

        notificationBtn.addEventListener("click", ()=>{

            var notificationList = document.getElementById('notification-list');

            if (notificationList.style.display === 'block') {
                notificationList.style.display = 'none';
                return;
            } 

            selectnNotificationFn();
            notificationList.style.display = 'block';
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

