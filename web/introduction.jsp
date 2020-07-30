<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>introduction</title>
    <link rel="stylesheet" href="libraries/bootstrap-4.5.0-dist/css/bootstrap.css">
    <link rel="stylesheet" href="libraries/FlexHelper/FlexHelper.css">
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/introduction.css">
    <link rel="stylesheet" href="libraries/thdoan-magnify-cca1561/css/magnify.css">
    <script src="libraries/jQuery/jquery-3.5.1.js"></script>
    <script src="libraries/bootstrap-4.5.0-dist/js/bootstrap.js"></script>
    <script src="js/util.js"></script>
    <script src="js/fenye.js"></script>
    <script src="js/introduction.js"></script>

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
                        <a class="dropdown-item" href="addfriend">befriend</a>
                        <a class="dropdown-item" href="myfriend">my firend</a>
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


<main class="flex-container-center" style="margin-bottom: 20px">
    <div class="card flex-24-24">
        <div class="card-header">
            introduction
        </div>
        <div class="card-body flex-container-center">
            <div id="leftColumn" class="flex-14-24 flex-container">
                <img src="photos/large/${requestScope.image.path}" class="zoom flex-1"
                     data-magnify-src="photos/large/${requestScope.image.path}">
                <a href="photos/large/${requestScope.image.path}" class="flex-1">get detailed picture</a>
            </div>
            <div id="rightColumn" class="flex-10-24 flex-container-center">
                <ul class="list-group flex-1">
                    <li class="list-group-item">title：<c:out value="${requestScope.image.title}"></c:out></li>
                    <li class="list-group-item">cameraman：<c:out value="${requestScope.image.username}"></c:out></li>
                    <li class="list-group-item">content：<c:out value="${requestScope.image.content}"></c:out></li>
                    <li class="list-group-item">description：<c:out value="${requestScope.image.description}"></c:out></li>
                    <li class="list-group-item">heat：<c:out value="${requestScope.image.favorCount}"></c:out></li>
                    <li class="list-group-item">country：<c:out
                            value="${requestScope.image.country_RegionName}"></c:out></li>
                    <li class="list-group-item">city：<c:out value="${requestScope.image.asciiName}"></c:out></li>
                    <li class="list-group-item">time：<c:out value="${requestScope.image.dateReleased}"></c:out></li>
                </ul>
                <c:choose>
                    <c:when test="${requestScope.user==null}">
                        <button class="flex-1 btn btn-info" onclick="location.assign('login')">login</button>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${requestScope.hasFavoredImage}">
                            <button class="flex-1 btn btn-info"
                                    onclick="location.assign('details?imageID=${requestScope.image.imageID}&action=unfavor')">
                                abolish
                            </button>
                        </c:if>
                        <c:if test="${!requestScope.hasFavoredImage}">
                            <button class="flex-1 btn btn-info"
                                    onclick="location.assign('details?imageID=${requestScope.image.imageID}&action=favor')">
                                collect
                            </button>
                        </c:if>
                    </c:otherwise>
                </c:choose>

                <c:if test="${requestScope.actionResult!=null}">
                    <div class="flex-1 alert alert-primary">
                        <c:out value="${requestScope.actionResult.info}"></c:out>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <div class="card flex-1">
        <div class="card-header">
            comment
        </div>
        <form class="card-body flex-container" id="form">
            <input type="text" name="comment" class="flex-16-24 form-control" id="commentInput">
            <c:choose>
                <c:when test="${requestScope.user!=null}">
                    <button class="flex-8-24 btn btn-info" type="button" id="submitButton">publish</button>
                </c:when>
                <c:otherwise>
                    <button class="flex-8-24 btn btn-info" type="button">login</button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>


    <div class="card flex-1">
        <div class="card-header">
            <c:if test="${requestScope.user!=null}">
                Comments
                <span class="badge badge-success" id="timeOrderButton">order by time</span>
                <span class="badge badge-danger" id="popularityOrderButton">order by heat</span>
            </c:if>
            <c:if test="${requestScope.user==null}">
               login
            </c:if>
        </div>
        <div class="card-body" id="commentArea">



        </div>
    </div>

    <c:if test="${requestScope.user!=null}">
        <nav aria-label="Page navigation example">
            <ul class="pagination" id="pagination">

            </ul>
        </nav>
    </c:if>



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



<script src="libraries/thdoan-magnify-cca1561/js/jquery.magnify.js"></script>
<script src="libraries/thdoan-magnify-cca1561/js/jquery.magnify-mobile.js"></script>
<script>
    $(document).ready(function () {
        $('.zoom').magnify();
    });
</script>

</body>
</html>