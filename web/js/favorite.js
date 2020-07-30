$(function () {
    let myFavorPageClass = new MyFavorPageClass();
    myFavorPageClass.search(1);
    myFavorPageClass.setMyFootage();
    myFavorPageClass.setConfigButtonOnClick();
});
class MyFavorPageClass extends Fenye {
    constructor() {
        super();
        this.pagesize = 6;
        this.imageArea = $("#imageArea");
        this.footageArea = $("#footageArea");
        this.clearFootageButton = $("#clearFootageButton");
        this.form = $("#form");
        this.configButton = $("#configButton");
    }

    search(requestedPage) {
        let that = this;
        let serializedArray = [
            {"name": "requestedPage", "value": requestedPage},
            {"name": "pageSize", "value": this.pagesize},
        ];
        that.imageArea.empty();
        $.post("ImageServlet?method=myfavor", serializedArray)
            .done(function (data) {
                let searchResult = JSON.parse(data);
                that.setImage(searchResult);
                that.setPagination(searchResult);

            })
            .fail(function () {
                alert("please try again")
            })

    }

    setImage(searchResult) {
        let that = this;
        let imageList = searchResult['imageList'];
        for (let i = 0; i < imageList.length; i++) {
            let element = $(
                `<div class="media">
                <a href="details?imageID=${imageList[i]['imageID']}">
                 <img class="mr-3 img-thumbnail myThumbnail" src="photos/small/${imageList[i]['path']}" alt="Generic placeholder image">
                </a>
                <div class="media-body">
                    <h5 class="mt-0">${imageList[i]['title']}</h5>
                    <p>${imageList[i]['description']}</p>
                    <div>
                        <button class="btn-danger btn">abolish</button>
                    </div>
                </div>
            </div>`
            );
            let unfavorButton = $(element.get(0).querySelector(".btn-danger"));
            unfavorButton.click(function () {
                that.unfavor(imageList[i]['imageID'])
            });
            that.imageArea.append(element);
        }

    }


    unfavor(imageID) {
        let that = this;
        let c = confirm("sure to abolish？");
        if (!c) return;
        $.post(`UserServlet?method=unfavor&imageID=${imageID}`)
            .done(function (data) {
                let actionResult = JSON.parse(data);
                alert(actionResult['info']);
                if (actionResult['success']) {
                    that.search(that.currentPage);
                }
            })
            .fail(function () {
                alert("please try again");
            })
    }

    setMyFootage() {
        let that = this;
        $.post("UserServlet?method=getUid")
            .done(function (data) {
                let uid = JSON.parse(data)['uid'];
                let cookies = document.cookie.split(";");

                for (let i = 0; i < cookies.length; i++) {


                    if (cookies[i].split("=")[0] === uid.toString()) {
                        let json = decodeURIComponent(cookies[i].split("=")[1]);

                        let browseRecord = JSON.parse(json);
                        that.footageArea.empty();
                        for (let i = 0; i <= browseRecord['records'].length - 1; i++) {
                            let element = $(
                                `<li class="list-group-item flex-6-24">
                                    <span class="footageTitle">${browseRecord['records'][i]['title']}</span>
                                </li>`
                            );
                            that.footageArea.append(element);



                            element.click(function () {
                                location.assign(`details?imageID=${browseRecord['records'][i]['imageID']}`)
                            })

                        }


                        that.clearFootageButton.click(function () {
                            document.cookie = `${uid}=;expires=Thu, 01 Jan 1970 00:00:00 GMT`;
                            alert("clear successfully！");
                            that.footageArea.empty();
                        })

                    }
                }


            })

    }


    setConfigButtonOnClick() {
        let that = this;

        that.configButton.click(function () {
            $.post("UserServlet?method=setCanBeSeenFavor", that.form.serializeArray())
                .done(function (data) {
                    let result = JSON.parse(data);
                    alert(result['info'])

                })
                .fail(function () {
                    alert("please try later");
                })
        })

    }
}

