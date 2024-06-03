document.addEventListener('DOMContentLoaded', function() {
    var loginButton = document.getElementById('login-button');

    loginButton.addEventListener('click', function() {
        if (loginButton.textContent === '로그인') {
            loginButton.textContent = '로그아웃';
        } else {
            loginButton.textContent = '로그인';
        }
    });
});