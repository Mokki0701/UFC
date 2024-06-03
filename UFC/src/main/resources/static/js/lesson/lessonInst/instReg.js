    /* 업로드한 파일이 PDF 파일인지 검사하는 코드 시작 */
    document.getElementById('instPostFrm').addEventListener('submit', function(event) {
      const fileInput = document.getElementById('pdfFile');
      const file = fileInput.files[0];
      if (file && file.type !== 'application/pdf') {
        alert('PDF 파일만 업로드 가능합니다.');
        event.preventDefault();
      }
    });

    // 파일명 띄우기 코드
    document.getElementById('pdfFile').addEventListener('change', function() {
      const fileName = this.files[0] ? this.files[0].name : '선택된 파일 없음';
      document.getElementById('file-name').textContent = fileName;
    });
    
    /* 업로드한 파일이 PDF 파일인지 검사하는 코드 끝 */