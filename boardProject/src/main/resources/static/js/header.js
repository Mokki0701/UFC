let notificationSock;       // 알림 웹소켓 객체
let sendNotificationFn;     // 웹소켓을 이용해 알림을 보내는 함수

let selectnNotificationFn;  // 비동기로 알림을 조회하는 함수
let notReadCheckFn;         // 비동기로 읽지 않은 알림 개수 체크하는 함수

if (notificationLoginCheck) { // 로그인 상태일 경우만 알림 WebSocket 수행
    /* 알림 Websocket */

    //**  SockJS **
    //웹 브라우저와 웹 서버 간에 실시간 양방향 통신을 가능하게 하는 자바스크립트 라이브러리입니다.

    // 1. SockJS 라이브러리 추가
    // 2. SockJS를 이용해서 클라이언트용 웹소켓 객체 생성
    notificationSock = new SockJS("/notification/send");


    /* 웹소켓을 이용해 알림을 전달하는 함수 */
    sendNotificationFn = (type, url, pkNo) => {

        const title = document.querySelector(".board-title").innerText;

        const notification = {
            "notificationType": type,
            "notificationUrl": url,
            "pkNo": pkNo
        }

        notificationSock.send(JSON.stringify(notification));
    }


    /* 웹소켓을 통해 서버에서 전달된 메시지가 있을 경우 */
    notificationSock.addEventListener("message", e => {

        // 알람 버튼 활성화
        const notificationBtn = document.querySelector(".notification-btn");
        notificationBtn.classList.remove("fa-regular");
        notificationBtn.classList.add("fa-solid");

        selectnNotificationFn();
    })



    /* 읽지 않은 알림이 있는지 확인하는 함수 */
    notReadCheckFn = async () => {
        const resp = await fetch("/notification/notReadCheck")
        const notReadCount = await resp.text();
        // console.log(notReadCount) // 안읽은 알림 개수
        return notReadCount; // 안읽은 알림 개수가 Promise 객체에 담겨져서 반환
    }


    /* 비동기로 알림을 조회하는 함수  */
    selectnNotificationFn = () => {
        fetch("/notification")
            .then(resp => resp.json())
            .then(selectList => {

                // console.log(notificationList);

                // 이전 알림 목록 삭제
                const notiList = document.querySelector(".notification-list");
                notiList.innerHTML = '';

                for (let data of selectList) {

                    // 알림 전체를 감싸는 요소
                    const notiItem = document.createElement("li");
                    notiItem.className = 'notification-item';


                    // 알림을 읽지 않은 경우 'not-read' 추가
                    if (data.notificationCheck == 'N') notiItem.classList.add("not-read");


                    // 알림 관련 내용(프로필 이미지 + 시간 + 내용)
                    const notiText = document.createElement("div");
                    notiText.className = 'notification-text';


                    // 알림 클릭 시 동작
                    notiText.addEventListener("click", e => {

                        // 만약 읽지 않은 알람인 경우
                        if (data.notificationCheck == 'N') {
                            fetch("/notification", {
                                method: "PUT",
                                headers: { "Content-Type": "application/json" },
                                body: data.notificationNo
                            })

                            // 컨트롤러 메서드 반환값이 없으므로 then 작성 X
                        }

                        // 클릭 시 알림에 기록된 경로로 이동
                        location.href = data.notificationUrl;
                    })


                    // 알림 보낸 회원 프로필 이미지
                    const senderProfile = document.createElement("img");
                    if (data.sendMemberProfileImg == null) senderProfile.src = notificationDefaultImage;  // 기본 이미지
                    else senderProfile.src = data.sendMemberProfileImg; // 프로필 이미지


                    // 알림 내용 영역
                    const contentContainer = document.createElement("div");
                    contentContainer.className = 'notification-content-container';

                    // 알림 보내진 시간
                    const notiDate = document.createElement("p");
                    notiDate.className = 'notification-date';
                    notiDate.innerText = data.notificationDate;

                    // 알림 내용
                    const notiContent = document.createElement("p");
                    notiContent.className = 'notification-content';
                    notiContent.innerHTML = data.notificationContent; // 태그가 해석 될 수 있도록 innerHTML

                    // 삭제 버튼
                    const notiDelete = document.createElement("span");
                    notiDelete.className = 'notidication-delete';
                    notiDelete.innerHTML = '&times;';


                    /* 삭제 버튼 클릭 시 비동기로 해당 알림 지움 */
                    notiDelete.addEventListener("click", e => {

                        fetch("/notification", {
                            method: "DELETE",
                            headers: { "Content-Type": "application/json" },
                            body: data.notificationNo
                        })
                            .then(resp => resp.text())
                            .then(result => {
                                // 클릭된 x버튼이 포함된 알림 삭제
                                notiDelete.parentElement.remove();

                                // 남은 알림 개수를 확인하여
                                notReadCheckFn().then(notReadCount => {
                                    const notificationBtn = document.querySelector(".notification-btn");

                                    // 있으면 활성화
                                    if (notReadCount > 0) {
                                        notificationBtn.classList.remove("fa-regular");
                                        notificationBtn.classList.add("fa-solid");
                                    } else { // 없으면 비활성화
                                        notificationBtn.classList.add("fa-regular");
                                        notificationBtn.classList.remove("fa-solid");
                                    }
                                })
                            })
                    })





                    // 조립
                    notiList.append(notiItem);
                    notiItem.append(notiText, notiDelete);
                    notiText.append(senderProfile, contentContainer);
                    contentContainer.append(notiDate, notiContent);

                }
            })
    }



    /* 
        ajax (fetch() api)는 비동기로 동작하기 때문에
        일반 함수 () => {} 내부에 
        fetch() 성공 시 결과 result 출력,
        fetch() 다음 에 "함수 끝"  출력을 작성하면
        result -> "함수 끝" 순서 로 출력되는 것이 아닌
        "함수 끝" -> result 순서로 
        fetch() 수행 결과를 기다리지 않고 함수 내 다음 코드가 먼저 수행됨
    
        // ------------ 비동기 통신 순서 예시 코드 -------------
        notReadCheckFn = () => {
            fetch("/notification/notReadCheck")
            .then(resp => resp.text())
            .then(result => {
                console.log("result",result); // 서버와 비동기 통신이 끝난 후 출력!!
            });

            console.log("함수 끝"); // 먼저 출력!
        }


        하지만 fetch()의 결과를 기다렸다 다음 코드가 수행 할 수 있는 방법이 존재함

        - Promise 객체 
            > fetch() 같은 비동기 요청 후에 값을 가져올거라 약속하는 객체
            > 요청 상태/결과를 then() 메서드를 이용해서 얻어올 수 있음

        - async 함수
            > 작업결과를 기다려야되는 비동기 코드를 포함한 함수
            > 함수명 앞에 작성
            > 함수 결과로 Promise 객체를 반환

        - await 키워드
            > 비동기 작업이 완료 될때 까지 코드 수행을 멈추게함
            > async 함수 내부에 비동기 코드(fetch, Promise) 앞에 작성
            > 비동기 작업 결과를 반환함
    */





    /* 페이지 DOM 요소 내용이 모두 로딩된 후(화면 렌더링이 끝난 후) */
    document.addEventListener("DOMContentLoaded", () => {

        // 알람 버튼
        const notificationBtn = document.querySelector(".notification-btn");

        /* 읽지 않은 알림이 있으면 알림 버튼 활성화 하기 */
        notReadCheckFn().then(notReadCount => {
            if (notReadCount > 0) {
                notificationBtn.classList.remove("fa-regular");
                notificationBtn.classList.add("fa-solid");
            }
        })


        /* 알림 버튼(종) 클릭 시*/
        notificationBtn.addEventListener("click", e => {
            const notiList = document.querySelector(".notification-list");

            // 보이는 상태일 때
            if (notiList.classList.contains("notification-show")) {
                notiList.classList.remove("notification-show");
                return;
            }

            /* 로그인 상태인 경우 알림 목록을 바로 비동기로 조회 */
            selectnNotificationFn();
            notiList.classList.add("notification-show");
        })

    })

}








/* 페이지 DOM 요소 내용이 모두 로딩된 후(화면 렌더링이 끝난 후) */
document.addEventListener("DOMContentLoaded", () => {
    // 주소에 #아이디속성명 이 작성되어 있으면서
    // 해당 아이디를 가진 요소가 존재하는 경우
    // 해당 요소의 위치로 스크롤 옮기기
    // const targetId = location.href.substring(location.href.indexOf("#") + 1);

    // 쿼리스트링 파라미터 중 cn 값을 얻어와 같은 아이디를 가지는 요소로 이동
    const params = new URLSearchParams(location.search)
    const targetId = "c" + params.get("cn");

    let targetElement = document.getElementById(targetId);

    if (targetElement) {
        const scrollPosition = targetElement.offsetTop;
        window.scrollTo({
            top: scrollPosition - 200,
            behavior: 'smooth'
        });
    }
})

