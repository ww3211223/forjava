<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div id="msgFromPush"></div>
</body>

<script src="assets/js/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    if (!!window.EventSource) {
        var source = new EventSource('push');
        var s = '';
        source.addEventListener("message", function (e) {
            s += e.data + "<br/>";
            $("#msgFromPush").html(s);
        });

        source.addEventListener("open", function (e) {
            console.log("连接打开.");
        });

        source.addEventListener("error", function (e) {
            if (e.readyState == EventSource.CLOSED) {
                console.log("连接关闭");
            } else {
                console.log(e.readyState);
            }
        });
    }
    else {
        console.log("你的浏览器不支持SSE.");
    }

</script>

</html>