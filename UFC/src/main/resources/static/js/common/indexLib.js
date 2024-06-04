const swiper = new Swiper('.swiper', {
  pagination: {
    el: '.swiper-pagination',
  },
});

// Anchor link handling
document.querySelectorAll('a[href^="#libSpace"]').forEach(anchor => {
  anchor.addEventListener('click', function (e) {
    e.preventDefault();
    swiper.slideTo(2); // Slide to the third slide (index starts from 0)
  });
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