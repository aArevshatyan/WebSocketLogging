let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

function init(sessionId) {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/message/' + sessionId, function (data) {
            if (data.body === 'done') {
                $('#btnMigrate').prop('disabled', false);
            } else {
                $('#percent').text(data.body + '%');
            }
        });
    });
}

function migrate() {
    $('#btnMigrate').prop('disabled', true);
    $.ajax('/migrate', {
        method: 'POST'
    });
}