class DetailsPageClass extends PageWithPagination {
    constructor() {
        super();
        this.form = $("#form");
        this.submitButton = $("#submitButton");
        this.commentInput = $("#commentInput");
        this.commentArea = $("#commentArea");
        this.pagesize = 6;
        this.currentPage = 1;
        this.imageID = getAllUrlParams()['imageID'];
        this.howToOrder = "popularity";

        this.timeOrderButton = $("#timeOrderButton");
        this.popularityOrderButton = $("#popularityOrderButton");
    }

    //提交评论后
    setSubmitButtonOnClick() {
        let that = this;

        this.submitButton.click(function () {
            if (!that.commentInput.val()) {
                alert("write sth first");
                return;
            }

            $.post(`UserServlet?method=comment&imageID=${that.imageID}`, that.form.serializeArray())
                .done(function (data) {
                    let commentResult = JSON.parse(data);
                    alert(commentResult['info']);
                    that.search(that.currentPage);
                })
        })

    }

   //搜索评论
    search(requestedPage) {
        let that = this;
        let serializedArray = [
            {"name": "requestedPage", "value": requestedPage},
            {"name": "pageSize", "value": this.pagesize},
            {"name": "howToOrder", "value": this.howToOrder}
        ];
        $.post(`CommentServlet?method=getComment&imageID=${that.imageID}`, serializedArray)
            .done(function (data) {
                let searchResult = JSON.parse(data);
                that.displayComments(searchResult);
                that.setPagination(searchResult);

            })

    }


   //排序
    setOrderButtonOnClick() {
        let that = this;
        this.popularityOrderButton.click(function () {
            that.howToOrder = "popularity";
            that.search(that.currentPage);
        })
        this.timeOrderButton.click(function () {
            that.howToOrder = "time";
            that.search(that.currentPage);
        })

    }

   //显示评论
    displayComments(searchResult) {
        let that = this;
        that.commentArea.empty();
        for (let i = 0; i <= searchResult['commentList'].length - 1; i++) {
            let element;



            if (searchResult['commentList'][i]['haveFavoredByMe']) {
                element = $(
                    `<div class="alert alert-primary" role="alert">
                    <h4 class="alert-heading">${searchResult['commentList'][i]['commenterName']} says this at ${new Date(searchResult['commentList'][i]['dateReleased'])}:</h4>
               
                    <p>${searchResult['commentList'][i]['text']}</p>
                    <hr>
                    <p class="mb-0">
                        favor number：${searchResult['commentList'][i]['favorCount']}
                        <span class="badge badge-danger">cancel favor</span>
                    </p>
                </div>`
                );

                let cancelFavorButton = $(element.get(0).querySelector(".badge-danger"));

                cancelFavorButton.click(function () {
                    that.cancelFavorButton(searchResult['commentList'][i]['commentID'])
                })

            } else {
                element = $(
                    `<div class="alert alert-primary" role="alert">
                    <h4 class="alert-heading">${searchResult['commentList'][i]['commenterName']} says this at ${new Date(searchResult['commentList'][i]['dateReleased'])}:</h4>
               
                    <p>${searchResult['commentList'][i]['text']}</p>
                    <hr>
                    <p class="mb-0">
                        favor number：${searchResult['commentList'][i]['favorCount']}
                        <span class="badge badge-success">favor</span>
                    </p>
                </div>`
                );

                let favorButton = $(element.get(0).querySelector(".badge-success"));

                favorButton.click(function () {
                    that.favorComment(searchResult['commentList'][i]['commentID'])

                })


            }


            this.commentArea.append(element);


        }


    }


    favorComment(commentID) {
        let that = this;
        $.post(`UserServlet?method=favorComment&commentID=${commentID}`)
            .done(function (data) {
                let favorCommentResult = JSON.parse(data);
                alert(favorCommentResult['info']);
                that.search(that.currentPage);

            })
    }

    cancelFavorButton(commentID) {
        let that = this;
        $.post(`UserServlet?method=cancelFavorComment&commentID=${commentID}`)
            .done(function (data) {
                let cancelFavorCommentResult = JSON.parse(data);
                alert(cancelFavorCommentResult['info']);
                that.search(that.currentPage);
            })
    }
}