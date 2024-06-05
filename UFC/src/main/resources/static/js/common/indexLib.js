const swiper = new Swiper('.swiper', {
  pagination: {
    el: '.swiper-pagination',
  },
});


// 스와이퍼 0번 페이지에서
// 각 버튼 클릭 시 해당 정보 표시
document.addEventListener('DOMContentLoaded', function () {
  const rightContents = document.querySelectorAll('.libContentBox.right .libContent');
  const leftSliders = document.querySelectorAll('.libContentBox.left .libSlider');

  rightContents.forEach((rightContent, index) => {
    rightContent.addEventListener('click', () => {
      leftSliders.forEach(slider => slider.classList.remove('curShow'));
      leftSliders[index].classList.add('curShow');

      rightContents.forEach(content => content.classList.remove('libSelectedContent'));
      rightContents[index].classList.add('libSelectedContent');
    });
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const buttons = document.querySelectorAll('.libColBtn button');
  const infoDivs = document.querySelectorAll('#infoContainer > div');

  buttons.forEach(button => {
    button.addEventListener('click', () => {
      const floor = button.getAttribute('data-floor');

      // 모든 정보 div를 숨기고
      infoDivs.forEach(div => {
        div.classList.remove('active');
        // 애니메이션을 위해 잠시 지연
        setTimeout(() => {
          div.style.opacity = 0;
          div.style.transform = 'translateY(100%)';
        }, 200);
      });

      // 해당하는 정보 div를 표시
      const activeDiv = document.querySelector(`#infoContainer > div[data-floor="${floor}"]`);
      if (activeDiv) {
        setTimeout(() => {
          activeDiv.classList.add('active');
          activeDiv.style.opacity = 1;
          activeDiv.style.transform = 'translateY(0)';
        }, 200);
      }
    });
  });

  // 초기 설정: 지상 1층 정보 표시
  const firstDiv = infoDivs[1];
  firstDiv.classList.add('active');
  firstDiv.style.opacity = 1;
  firstDiv.style.transform = 'translateY(0)';
  
});
