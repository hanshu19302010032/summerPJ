<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="libraries/bootstrap-4.5.0-dist/css/bootstrap.css">
    <link rel="stylesheet" href="libraries/FlexHelper/FlexHelper.css">
    <link rel="stylesheet" href="css/universal.css">
    <link rel="stylesheet" href="css/register_login.css">
    <script src="libraries/jQuery/jquery-3.5.1.js"></script>
    <script src="libraries/bootstrap-4.5.0-dist/js/bootstrap.js"></script>
    <script src="libraries/JavaScriptMD5/md5.js"></script>
    <script src="js/login.js"></script>
</head>
<body>

<main class="card">
    <h3 class="card-header" style="text-align: center;color: #005cbf">login</h3>
    <form class="card-body flex-container" id="form">
        <label class="flex-1"><span style="color: #7abaff;font-size: large">username</span></label>
        <input type="text" name="username" class="flex-1 form-control">
        <label class="flex-1"><span style="color: #7abaff;font-size: large">password</span></label>
        <input type="password" name="password" class="flex-1 form-control">
        <label class="flex-1"><span style="color: #7abaff;font-size: large">code</span></label>
        <div class="flex-1" id="captchaArea">
            <img src="getCaptcha" alt="captcha">
        </div>
        <input type="text" name="captcha" class="flex-1 form-control">
        <button type="button" class="btn btn-info flex-1" id="loginButton">login</button>
        <div style="text-align: center"><a href="register">register</a></div>
    </form>
</main>
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