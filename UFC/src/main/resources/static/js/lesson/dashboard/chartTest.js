new Chart(document.getElementById("bar-chart-horizontal"), {
  type: 'horizontalBar',
  data: {
    labels: ["강의1", "강의2", "강의3", "강의4", "강의5"],
    datasets: [
      {
        label: "Population (millions)",
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