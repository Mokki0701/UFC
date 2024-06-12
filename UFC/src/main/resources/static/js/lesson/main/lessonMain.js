/* gym community 인기 강사 부분 */
document.addEventListener("DOMContentLoaded", function() {
  const tables = document.querySelectorAll('.board-table');

  tables.forEach(table => {
      const rows = table.querySelectorAll('tbody tr');
      rows.forEach((row, index) => {
          const indexCell = row.querySelector('.index');
          if (indexCell) {
              indexCell.textContent = index + 1;
          }
      });
  });
});