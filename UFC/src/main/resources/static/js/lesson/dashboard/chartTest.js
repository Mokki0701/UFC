//출석률, 수업타이틀 넣어서 수정하기

fetch('/lesson/api/attendance?memberNo=' + loginMemberNo)
  .then(res => res.json())
  .then(data => {

    //console.log(data);
    //출석률 구하고 
    //const attendanceData = data.map(item => item.attendance);
    //개수 제한
    const limitedData = data.slice(0, 4);
    const labels = limitedData.map(item => item.lessonTitle);
    const rates = limitedData.map(item => item.attendanceRate);

    new Chart(document.getElementById("bar-chart-horizontal"), {
      type: 'horizontalBar',
      data: {
        labels: labels,
        datasets: [
          {
            label: "attendance rate (%)",
            backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850"],
            data: rates
          }
        ]
      },
      options: {
        legend: { display: false },
        title: {
          display: true,
          text: '출석률'
        }
      }
    });

  })



// ---------- 풀캘린더 ------------------
document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',

    eventClick: function (info) {
      if (confirm(info.event.title + ' 강의를 수강신청페이지로 이동하시겠습니까?')) {
        location.href = 'list/' + info.event.extendedProps.lessonNo;
      }

      info.el.style.borderColor = 'red';
    },
    locale: 'ko', // 한국어로 설정
    initialView: 'dayGridMonth',
    headerToolbar: {
    left: 'prev,next today',
    center: 'title',
    right: 'dayGridMonth'
    },
    displayEventTime : false,
    events: function (fetchInfo, successCallback, failureCallback) {
      fetch('/lesson/calendar')
        .then(response => response.json())
        .then(data => {
          //console.log('Fetched data:', data); // 데이터 확인을 위한 로그 출력
          if (Array.isArray(data)) {
            const events = [];
            let colorIndex = 0;
            let colors = ["#FFD1DC", "#B3E5FC", "#C8F7C5", "#E6E6FA", "#8BA590"]; // 색상 배열
            colorIndex++; // 다음 색상을 사용하도록 인덱스 증가
            data.forEach(lesson => {
              const backgroundColor = lesson.wishYn === 'Y' ? 'Gold' : colors[colorIndex % colors.length]; // 색상 순환
              colorIndex++;
              const lessonDays = lesson.lessonSchedule.split(' '); // 수업 요일 및 시간 배열로 변환
              const lessonDay = convertDayToNumber(lessonDays[0]); // 수업 요일만 숫자로 변환
              const currentDate = new Date(lesson.lessonStartDate);
              const endDate = new Date(lesson.lessonEndDate);

              while (currentDate <= endDate) {
                if (currentDate.getDay() === lessonDay) {
                  events.push({
                    title: lesson.lessonTitle,
                    start: new Date(currentDate), // 이벤트 시작 날짜
                    end: new Date(currentDate), // 이벤트 종료 날짜
                    backgroundColor: backgroundColor, // 색상 설정
                    extendedProps: {
                    lessonNo: lesson.lessonNo // lessonNo 추가
                    }
                  });
                }
                currentDate.setDate(currentDate.getDate() + 1); // 다음 날짜로 이동
              }
            });
            successCallback(events);
          } else {
            console.error('Error: data is not an array');
            failureCallback(new Error('Data is not an array'));
          }
        })
        .catch(error => {
          console.error('Error fetching events:', error);
          failureCallback(error);
        });
    }
  });

  calendar.render();
});

// 요일을 숫자로 변환하는 함수 (0: 일요일, 1: 월요일, ..., 6: 토요일)
function convertDayToNumber(day) {
  switch(day) {
    case '일': return 0;
    case '월': return 1;
    case '화': return 2;
    case '수': return 3;
    case '목': return 4;
    case '금': return 5;
    case '토': return 6;
    default: return -1; // 유효하지 않은 요일
  }
}


