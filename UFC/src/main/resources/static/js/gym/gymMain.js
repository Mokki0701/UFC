document.addEventListener('DOMContentLoaded', () => {
  const modal = document.querySelector("#modal");
  const modalTitle = document.querySelector("#modal-title");
  const modalContent = document.querySelector("#modal-content");
  const closeModal = document.querySelector(".close");

  document.querySelectorAll('.gym_events ul li, .gym_notices ul li').forEach(item => {
    item.addEventListener('click', event => {
      const target = event.currentTarget;
      const title = target.getAttribute('data-title');
      const content = target.getAttribute('data-content');
      modalTitle.textContent = title;
      modalContent.textContent = content;
      modal.style.display = "block";
    });
  });

  closeModal.addEventListener('click', () => {
    modal.style.display = "none";
  });

  window.addEventListener('click', event => {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  });
});

