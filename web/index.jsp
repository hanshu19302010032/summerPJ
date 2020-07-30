<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>home</title>
    <link rel="stylesheet" href="libraries/bootstrap-4.5.0-dist/css/bootstrap.css">
    <link rel="stylesheet" href="libraries/FlexHelper/FlexHelper.css">
    <link rel="stylesheet" href="css/all.css">
    <link rel="stylesheet" href="css/index.css">
    <script src="libraries/jQuery/jquery-3.5.1.js"></script>
    <script src="libraries/bootstrap-4.5.0-dist/js/bootstrap.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light" >
    <a class="navbar-brand" href="#">
        Image Gallery
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
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


<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <a href="details?imageID=${requestScope.popularImageList.get(0).imageID}">
                <img src="photos/medium/${requestScope.popularImageList.get(0).path}" class="d-block w-100"
                     alt="${requestScope.popularImageList.get(0).title}">
            </a>
        </div>
        <c:forEach items="${requestScope.popularImageList}" var="image" begin="1">
            <div class="carousel-item">
                <a href="details?imageID=${image.imageID}">
                    <img src="photos/medium/${image.path}" class="d-block w-100" alt="${image.title}">
                </a>
            </div>
        </c:forEach>
    </div>
    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>


<!--This is hot photos display area-->
<div id="hotPhotosDisplayArea" class="flex-container">
    <c:forEach var="image" items="${requestScope.freshImageList}">
        <div class="card flex-8-24">
            <a href="details?imageID=${image.imageID}">
                <img src="photos/small/${image.path}" class="card-img-top img-thumbnail" alt="${image.title}">
            </a>
            <div class="card-body" style="text-align: center">
                <h5 class="card-title" style="color: #005cbf">${image.title}</h5>
                <p class="card-text"><span style="color: #7abaff;font-size: large">cameraman:</span>${image.username}</p>
                <p class="card-text"><span style="color: #7abaff;font-size: large">content:</span>${image.content}</p>
                <p class="card-text"><span style="color: #7abaff;font-size: large">time:</span>${image.dateReleased}</p>
            </div>
        </div>
    </c:forEach>
</div>

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