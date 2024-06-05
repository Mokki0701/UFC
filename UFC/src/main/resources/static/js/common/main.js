const tabColors = {
	'main': '#A67E33',
	'library': '#BF9D5E',
	'lesson': '#D9BD89',
	'health': '#ffffdd',
	'community': '#F2DDB6',
	'myPage': '#BF7B54'
};

const openTab = (tabName) => {
	let tabContent = document.getElementsByClassName("tab-content");
	for (let i = 0; i < tabContent.length; i++) {
		tabContent[i].style.display = "none";
		tabContent[i].classList.remove("active");
	}

	let tabLinks = document.getElementsByClassName("tab");
	for (let i = 0; i < tabLinks.length; i++) {
		tabLinks[i].classList.remove("active");
	}

	const activeTab = document.getElementById(tabName);
	activeTab.style.display = "block";
	activeTab.classList.add("active");
	const activeTabLink = document.querySelector(`.tab[data-tab="${tabName}"]`);
	activeTabLink.classList.add("active");

	// Swiper 초기화 ===================================

	const swiperContainers = activeTab.querySelectorAll('.swiper');
	//선택된 탭의 내용에서 모든 .swiper 요소를 찾아서 각각 Swiper 인스턴스 초기화
	swiperContainers.forEach(swiperContainer => {
		new Swiper(swiperContainer, {
			effect: "cube",
			speed: 900,
			grabCursor: false,
			cubeEffect: {
				shadow: false,
				slideShadows: true,
			},
			direction: "vertical",
			pagination: false,
			mousewheel: {
				invert: false,
			},
			//드래그 막기 (마우스로 화면전환 x -> 필요시 지우기)
			touchRatio: 0,
			loop: false,
			// on 옵션 : Swiper.js에서 특정 이벤트가 발생할 때 실행할 콜백 함수를 설정할 수 있게 해주는 기능
			/*  swiper.isEnd는 Swiper.js에서 제공하는 속성 -> 현재 활성화된 슬라이드가 마지막 슬라이드인지 여부를 확인 */
			on: {
				//swiper -> 라이브러리에서 제공하는 스와이퍼 인스턴스 
				// --> 슬라이더 속성에 접근 가능
				slideChange: function (swiper) {
					const arrowDown = activeTab.querySelector('.arrow-down');
					if (swiper.isEnd) {
						arrowDown.innerHTML = '&#8593;'; // 화살표를 위로 변경
					} else if (swiper.isBeginning) {
						arrowDown.innerHTML = '&#8595;'; // 화살표를 아래로 변경
					}
				}
			},
			//반응형 추가시 breakpoints 옵션 사용하면 됨 ==================

		});
	});

	// 필요한 데이터 서버로 보내는거 필요하면 사용//팀원들한테 물어보기 ===========
	// fetch(`/tab-content?tabName=${tabName}`)
	// 		.then(response => response.json())
	// 		.then(data => {
	// 				console.log(data); // 서버로부터 받은 데이터를 콘솔에 출력
	// 		})
	// 		.catch(error => console.error('Error fetching tab content:', error));
};




document.addEventListener("DOMContentLoaded", () => {
	const tabs = document.querySelectorAll('.tab');
	tabs.forEach(tab => {
		tab.addEventListener('click', () => {
			const tabName = tab.dataset.tab;
			openTab(tabName);

		});
	});
	openTab('main'); // 기본적으로 첫 번째 탭 열기
});



// ------------------ 도서관 스와이퍼 내부 이동 ------------------- //

// 도서관 스와이퍼에서 각 버튼 클릭 시 해당 인덱스 슬라이드로 이동
// 도서관 스와이퍼에서 각 버튼 클릭 시 해당 인덱스 슬라이드로 이동
document.addEventListener('DOMContentLoaded', function () {
	const libToZeroSlide = document.querySelector("#libToZeroSlide");
	const libToFirstSlide = document.querySelector("#libToFirstSlide");
	const libToSecondSlide = document.querySelector("#libToSecondSlide");
	const libToThirdSlide = document.querySelector("#libToThirdSlide");

	libToZeroSlide.addEventListener('click', () => {
		const swiperContainerElement = document.querySelector('.libSwiper').swiper;
		if (swiperContainerElement) {
			swiperContainerElement.slideTo(1); // 첫 번째 슬라이드로 이동
		}
	});

	libToFirstSlide.addEventListener('click', () => {
		const swiperContainerElement = document.querySelector('.libSwiper').swiper;
		if (swiperContainerElement) {
			swiperContainerElement.slideTo(2); // 두 번째 슬라이드로 이동
		}
	});

	libToSecondSlide.addEventListener('click', () => {
		const swiperContainerElement = document.querySelector('.libSwiper').swiper;
		if (swiperContainerElement) {
			swiperContainerElement.slideTo(3); // 세 번째 슬라이드로 이동
		}
	});

	libToThirdSlide.addEventListener('click', () => {
		const swiperContainerElement = document.querySelector('.libSwiper').swiper;
		if (swiperContainerElement) {
			swiperContainerElement.slideTo(4); // 네 번째 슬라이드로 이동
		}
	});

});

