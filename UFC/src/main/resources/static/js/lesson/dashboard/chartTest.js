//출석률, 수업타이틀 넣어서 수정하기

fetch('/lesson/api/attendance?memberNo='+loginMemberNo)
.then(res => res.json())
.then(data=>{

  //console.log(data);
  //출석률 구하고 
  //const attendanceData = data.map(item => item.attendance);
  //개수 제한
  const limitedData = data.slice(0, 4);
  const labels = limitedData.map(item => item.lessonTitle);
  const rates = limitedData.map(item=> item.attendanceRate);

  new Chart(document.getElementById("bar-chart-horizontal"), {
    type: 'horizontalBar',
    data: {
      labels: labels,
      datasets: [
        {
          label: "attendance rate (%)",
          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
          data: rates
        }
      ]
    },
    options: {
      legend: { display: false },
      title: {
        display: true,
        text: '수강률 또는 출석률'
      }
    }
  });
  
})



// ---------- 풀캘린더 ------------------

    document.addEventListener('DOMContentLoaded', function () {
      var calendarEl = document.getElementById('calendar');

      var calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'ko', // 한국어로 설정
        initialView: 'dayGridMonth',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: function(fetchInfo, successCallback, failureCallback) {
          fetch('/lesson/calendar')
            .then(response => response.json())
            .then(data => {
              console.log('Fetched data:', data); // 데이터 확인을 위한 로그 출력
              if (Array.isArray(data)) {
                var events = data.map(lesson => {
                  let backgroundColor = getRandomColor();
                  return {
                    title: lesson.lessonTitle,
                    start: lesson.lessonStartDate,
                    end: lesson.lessonEndDate,
                    backgroundColor: backgroundColor // 색상 설정
                  };
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

//이벤트 랜덤 색상
function getRandomColor() {
  let colors = ["#D4A5A5", "#6A8EAE", "#8BA590", "#9F7A93", "#D1B055"];
  return colors[Math.floor(Math.random() * colors.length)];
}

// document.addEventListener('DOMContentLoaded', function() {
//   var calendarEl = document.getElementById('calendar');
//   var calendar = new FullCalendar.Calendar(calendarEl, {
//     initialView: 'resourceTimelineWeek'
//   });
//   calendar.render();

//   var calendarEl = document.getElementById('calendar');

//   var calendar = new FullCalendar.Calendar(calendarEl, {

//     eventClick: function (info) {
//           alert('Event: ' + info.event.title);

//           // change the border color just for fun
//           info.el.style.borderColor = 'red';
//         },

//     locale: 'ko', // 한국어로 설정
//     initialView: 'dayGridMonth',
//     //초기날짜 설정하지 않으면 오늘 날짜로 
//     //initialDate: '2024-03-07',
//     headerToolbar: {
//       left: 'prev,next today',
//       center: 'title',
//       right: 'dayGridMonth,timeGridWeek,timeGridDay'
//     },
//     //events: '/api/events'
//     // events: [
//     //   {
//     //     title: 'All Day Event',
//     //     start: '2024-03-01'
//     //   },
//     //   {
//     //     title: 'Long Event',
//     //     start: '2024-03-07',
//     //     end: '2024-03-10'
//     //   },

//   });

//   calendar.render();
//   //console.log(calendar);

// });

