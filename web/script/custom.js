var chatServer = new function () {
    
    // set address to broadcaster server
    this.serverAddress = "ws://localhost:8084/JavaChatServer/chatws";
    
    this.nicknameTextId = "nicknameText";
    this.nicknameButtonId = "nicknameButton";
    this.nicknameModalId = "setNickname";
    this.messageTextId = "messageText";

    var chatThreadId = "mainThread";
    
    var nickname,
        ws,
        message = {
            content: "",
            nickname: ""
        };
    
    /**
    * Set nickname. Will be used in following communication.
    */
    this.setNickname = function () {
        nickname = document.getElementById(this.nicknameTextId).value;
        if (nickname !== undefined && nickname !== "") {
            $('#' + this.nicknameModalId).modal('hide');
            document.getElementById(this.messageTextId).focus();
            document.getElementById(this.nicknameTextId).value = "";
        }
        
    };
    
    /**
    * Send message to server. Username is appended automatically.
    *
    * text: Message to send
    */
    this.sendMessage = function (text) {
        if (ws !== undefined && text !== undefined && text !== "") {
            var msg = message;
            msg.content = text;
            msg.nickname = nickname;
            msg = JSON.stringify(msg);
            
            document.getElementById(this.messageTextId).value = '';
            
            ws.send(msg);
        } else {
            console.log("No websocket connection initialized or message is empty.");
        }
    };
    
    /**
    * Initialize WebSocket stream.
    */
    this.init = function () {
        ws = new WebSocket(this.serverAddress);
        ws.onmessage = onMessage;
        ws.onclose = onClose;
        ws.onopen = onOpen;
    };
    
    var appendMessageToThread = function (message, username) {
        var str =
            '<p>'
            + '    <b>' + username + ':</b> ' + message
            + '</p>',
            div = document.getElementById(chatThreadId);
        div.insertAdjacentHTML("beforeend", str);
        window.scrollTo(0, document.body.scrollHeight);
    },
    
        onMessage = function(event) {
        var msg = JSON.parse(event.data);
        console.log("Message content: " + msg.content + ", Nickname: " + msg.nickname);
        appendMessageToThread(msg.content, msg.nickname);
    },
    
        onOpen = function(event) {
        console.log("Connection established.");
        
    },
    
        onClose = function () {
            appendMessageToThread("<span class=\"text-danger\">Connection is closed.</span>", "System message");
            console.log("WS connection closed.");
            ws = undefined;
        };
};