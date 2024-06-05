document.addEventListener('DOMContentLoaded', () => {
    const modal = document.querySelector("#modal");
    const modalTitle = document.querySelector("#modal-title");
    const modalContent = document.querySelector("#modal-content");
    const closeModal = document.querySelector(".close");
  
    document.querySelectorAll('.gym_events ul li, .gym_notices ul li').forEach(item => {
      item.addEventListener('click', e => {
        const title = e.target.getAttribute('data-title');
        const content = e.target.getAttribute('data-content');
        modalTitle.textContent = title;
        modalContent.textContent = content;
        modal.style.display = "block";
      });
    });
  
    closeModal.addEventListener('click', () => {
      modal.style.display = "none";
    });
  
    window.addEventListener('click', e => {
      if (e.target == modal) {
        modal.style.display = "none";
      }
    });
});