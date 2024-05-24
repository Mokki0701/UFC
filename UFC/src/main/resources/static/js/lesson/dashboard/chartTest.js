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

  new Chart(document.getElementById("bar-chart-horizontal"), {
    type: 'horizontalBar',
    data: {
      // labels: ["강의1", "강의2", "강의3", "강의4", "강의5"],
      labels: labels,
      datasets: [
        {
          label: "attendance rate (%)",
          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
          data: [10,25,50,75,100]
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



