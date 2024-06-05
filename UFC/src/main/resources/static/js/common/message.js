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

function openMessagePopup(element) {
    var messageNo = element.previousElementSibling.textContent;
    var url = "/message/detail?messageNo=" + messageNo;
    window.open(url, 'messagePopup', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');
}

function openSendMessagePopup(){

    var url = "/message/send";
    window.open(url, 'messageSend', 'width=450,height=400,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no');

}


function showReceivedMessages() {
    document.getElementById('messageSelect').classList.remove('hidden');
    document.getElementById('blockMemberList').classList.add('hidden');
}

function showSentMessages() {
    document.getElementById('messageSelect').classList.remove('hidden');
    document.getElementById('blockMemberList').classList.add('hidden');
}

function showBlockedMembers() {

    fetch("/message/blockMember")
    .then(resp=>resp.text())
    .then(html=>{
        console.log(html);
        console.log(document.getElementById('blockMemberList'));
        document.getElementById('blockMemberList').outerHTML = html;

        document.getElementById('messageSelect').classList.add('hidden');
        document.getElementById('blockMemberList').classList.remove('hidden');
    })

}

function unblockMember(element) {
    const memberEmail = element.previousElementSibling.textContent;

    fetch("/message/unblockMember?memberEmail=" + memberEmail)
        .then(response => response.text())
        .then(result => {
            if (result > 0) {
                showBlockedMembers();
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}