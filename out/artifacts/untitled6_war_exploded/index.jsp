<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>loading</title>
</head>
<body>
<%
    request.getRequestDispatcher("/index").forward(request,response);
%>
<footer style=" color: black;font-size: .70em;font-style: italic;padding: 10px;clear: both;">
    <ul style=" list-style-type: none;text-align: center;">
        <li style="display: inline;"><img src="images/footer1phone.png" width="20" height="20">
            17705826551
        </li>
        <li style="display: inline;"><img src="images/footer2qq.png" width="20" height="20">
            1726778203
        </li>
        <li style="display: inline;"><img src="images/footer3wechat.png" width="20" height="20">
            17705826551
        </li>
        <li style="display: inline;"><img src="images/footer4address.png" width="20" height="20">
            universe-earth
        </li>
        <img src="images/QR%20code.png" width="40" height="40" id="floatright">
        <p>Copyright &copy; 2020 HanShu</p>
    </ul>
    <div style="position: fixed;right: 20px;bottom: 1px;">
        <a href="javascript:location.reload();">
            <img src="images/F5.png" width="20" height="20">
        </a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="#top" target="_self">
            <img src="images/backToTop.png" width="20" height="20">
        </a>
    </div>
</footer>

</body>
</html>
