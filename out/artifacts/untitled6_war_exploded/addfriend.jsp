<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>friend list</title>
    <link rel="stylesheet" href="libraries/bootstrap-4.5.0-dist/css/bootstrap.css">
    <link rel="stylesheet" href="libraries/FlexHelper/FlexHelper.css">
    <link rel="stylesheet" href="css/universal.css">
    <link rel="stylesheet" href="css/friendList.css">
    <script src="libraries/jQuery/jquery-3.5.1.js"></script>
    <script src="libraries/bootstrap-4.5.0-dist/js/bootstrap.js"></script>
    <script src="js/class/PageWithPagination.js"></script>
    <script src="js/class/AddFriendPage.class.js"></script>
    <script src="js/addfriend.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">
       Image Gallery
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="index">home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="search">search</a>
            </li>
            <li class="nav-item dropdown">
                <c:if test="${requestScope.user!=null}">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${requestScope.user.username}'s personal centre
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="myfavor">my favorite</a>
                        <a class="dropdown-item" href="upload">upload</a>
                        <a class="dropdown-item" href="myphoto">my photo</a>
                        <a class="dropdown-item" href=addfriend>befriend</a>
                        <a class="dropdown-item" href="myfriend">my friend</a>
                        <a class="dropdown-item" href="logout">log out</a>
                    </div>
                </c:if>
                <c:if test="${requestScope.user==null}">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                        no login
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="login">login</a>
                    </div>
                </c:if>
            </li>
        </ul>
    </div>
</nav>

<main class="flex-container">
    <div class="card flex-24-24">
        <div class="card-header">
            search by username
        </div>
        <div class="card-body flex-container">
            <form id="form" class="flex-1">
                <input placeholder="search by username" name="username" type="text" class="flex-16-24 form-control">
                <button type="button" class="btn-info btn" id="submitButton">search</button>
            </form>
            <div id="userDisplayArea" class="flex-1">

            </div>


        </div>
    </div>
    <nav aria-label="Page navigation example">
        <ul class="pagination" id="pagination">

        </ul>
    </nav>


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