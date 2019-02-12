let $loading = $('#loading');

let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('/message', function (data) {
        let message = data.body;

        let $li = $('<li/>');
        let $div = $('<div/>')
            .append(message);

        $loading.append($li.append($div));
    });
});


