const receivedTab = document.getElementById('receivedTab');
const sentTab = document.getElementById('sentTab');

let type = 0;

receivedTab.addEventListener('click', () => {

    messageListSelect(0);
    type = 0;

});

sentTab.addEventListener('click', () => {

    messageListSelect(1);
    type = 1;

});

function deleteMessage(button){

    const messageNo = button.previousElementSibling.textContent;
    const messageItem = button.parentElement;

    messageItem.classList.add('message-disabled');

    fetch("/message/delete?messageNo=" + messageNo)
        .then(response => response.text())
        .then(result => {
            if (result > 0) {
                messageItem.classList.add('message-disabled');
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });

}

// function paginationMessage(cp){

//     fetch("/message/select?type=" + type + "&cp=" + cp)
//         .then(response => response.text())
//         .then(html => {
//             document.querySelector('.messageSelect').innerHTML = html;
//         });

// }