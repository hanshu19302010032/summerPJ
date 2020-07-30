class OthersFavorPageClass extends PageWithPagination {
    constructor(othersusername) {
        super();
        this.imageArea = $("#imageArea");
        this.pagination = $("#pagination");
        this.cardTitle = $("#cardTitle");
        this.othersusername = othersusername;
        this.pagesize = 6;
    }


    setCardTitle() {
        this.cardTitle.empty();
        this.cardTitle.text(`${this.othersusername}'s favorite`);

    }

    search(requestedPage) {
        let that = this;
        let serializedArray = [
            {"name": "requestedPage", "value": requestedPage},
            {"name": "pageSize", "value": this.pagesize},
        ];

        $.post(`UserServlet?method=othersfavor&username=${this.othersusername}`, serializedArray)
            .done(function (data) {
                let searchResult = JSON.parse(data);
                if (searchResult['info']) {
                    alert(searchResult['info']);
                    return;
                }

                that.imageArea.empty();

                that.setImage(searchResult);
                that.setPagination(searchResult);


            })

    }

    setImage(searchResult) {
        let that = this;
        let imageList = searchResult['imageList'];
        that.imageArea.empty();
        for (let i = 0; i < imageList.length; i++) {
            let element = $(
                `<div class="media">
                <a href="details?imageID=${imageList[i]['imageID']}">
                 <img class="mr-3 img-thumbnail myThumbnail" src="photos/small/${imageList[i]['path']}" alt="Generic placeholder image">
                </a>
                <div class="media-body">
                    <h5 class="mt-0">${imageList[i]['title']}</h5>
                    <p>${imageList[i]['description']}</p>
                </div>
            </div>`
            );
            that.imageArea.append(element);
        }
    }


}