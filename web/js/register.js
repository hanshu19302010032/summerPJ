$(function () {
    let registerPageClass = new RegisterClass();
    registerPageClass.setUsernameInputOnChange();
    registerPageClass.setEmailInputOnChange();
    registerPageClass.setSubmitButtonOnClick();
    registerPageClass.setPasswordOnChange();
    registerPageClass.setCaptchaAreaOnClick();
});
class RegisterClass {
    constructor() {
        this.userNameInput = $("#userNameInput");
        this.emailInput = $("#emailInput");
        this.password1Input = $("#password1Input");
        this.password2Input = $("#password2Input");
        this.userNameInfoArea = $("#usernameInfoArea");
        this.emailInfoArea = $("#emailInfArea");
        this.passwordProgressArea = $("#password1ProgressArea");
        this.password2InfoArea = $("#password2InfoArea");
        this.submitButton = $("#submitButton");
        this.form = $("#form");
        this.usernameOK = false;
        this.emailOK = false;
        this.passwordOK = false;
        this.captchaArea = $("#captchaArea");


    }

    setUsernameInputOnChange() {
        let that = this;
        this.userNameInput.on("input", function (target) {
            let username = that.userNameInput.val();
            let usernameOK = username.length >= 4 && username.length <= 15;
            that.usernameOK = usernameOK;
            that.userNameInfoArea.empty();
            if (!usernameOK) {
                that.userNameInfoArea.append($("<div class=\"alert alert-danger\" role=\"alert\">\n" +
                    "the length of username must be 4-15" +
                    "</div>"))
            } else {
                that.userNameInfoArea.empty();
            }

        })
    }


    setEmailInputOnChange() {
        let that = this;
        this.emailInput.on("input", function () {
            let email = that.emailInput.val();
            let emailOK = /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,4})$/.test(email);
            that.emailOK = emailOK;
            that.emailInfoArea.empty();
            if (!emailOK) {
                that.emailInfoArea.append($("<div class=\"alert alert-danger\" role=\"alert\">\n" +
                    "wrong email address" +
                    "</div>"));
            } else {
                that.emailInfoArea.empty()
            }

        })
    }


    setPasswordOnChange() {
        let that = this;
        that.password1Input.on("input", function () {
                let password1 = that.password1Input.val();
                let getSecurityIndex = function (s) {
                    if (s.length < 6 || s.length > 15) {
                        return 0
                    }
                    let hasNumber = false;
                    let hasUpperCaseLetter = false;
                    let hasLowerCaseLetter = false;
                    let hasSpecCharacter = false;
                    let count = 0;
                    for (let i = 0; i <= s.length - 1; i++) {
                        if (!hasNumber && isNumber(s[i])) {
                            count++;
                            hasNumber = true;
                        } else if (!hasLowerCaseLetter && isLowerCaseLetter(s[i])) {
                            count++;
                            hasLowerCaseLetter = true;
                        } else if (!hasUpperCaseLetter && isUpperCaseLetter(s[i])) {
                            count++;
                            hasUpperCaseLetter = true;
                        } else if (!hasSpecCharacter && isSpecialCharacter(s[i])) {
                            count++;
                            hasSpecCharacter = true;
                        }
                    }
                    return count * 25;


                    function isNumber(s) {
                        return /^[0-9]$/.test(s)
                    }

                    function isUpperCaseLetter(s) {
                        return /^[A-Z]$/.test(s)
                    }

                    function isLowerCaseLetter(s) {
                        return /^[a-z]$/.test(s)
                    }

                    function isSpecialCharacter(s) {
                        return !isNumber(s) && !isUpperCaseLetter(s) && !isLowerCaseLetter(s);
                    }

                };
                let securityIndex = getSecurityIndex(password1);
                that.passwordProgressArea.empty();
                that.passwordProgressArea.append($(
                    `<div class="progress">
                    <div class="progress-bar" role="progressbar" style="width: ${securityIndex}%" aria-valuenow="${securityIndex}" aria-valuemin="0" aria-valuemax="100"></div>
                </div>`
                ));
            }
        );
        let checkPassword1AndPassword2 = function () {
            let password1 = that.password1Input.val();
            let password2 = that.password2Input.val();
            that.password2InfoArea.empty();
            if (password1 !== password2) {
                that.password2InfoArea.append(`<div class="alert alert-danger" role="alert">
                                                    password confirm failed!
                                               </div>`);
                that.passwordOK = false;
                return


            }
            if (password1.length < 6 || password1.length > 15) {
                that.password2InfoArea.append(`<div class="alert alert-danger" role="alert">
                                                    the length of password must be 6-15!
                                               </div>`);
                that.passwordOK = false;
                return;
            }
            that.passwordOK = true;


        };
        that.password1Input.on("input", checkPassword1AndPassword2);
        that.password2Input.on("input", checkPassword1AndPassword2);
    }


    setSubmitButtonOnClick() {
        let that = this;
        this.submitButton.click(function (e) {
            if (!that.usernameOK) {
                syalert.syopen("alertUsername");
                e.preventDefault();
                return;
            }
            if (!that.emailOK) {
                syalert.syopen("alertEmail");
                e.preventDefault();
                return;
            }
            if (!that.passwordOK) {
                syalert.syopen("alertPassword");
                e.preventDefault();
                return;
            }

            let arrayToBeSubmitted = that.form.serializeArray();
            arrayToBeSubmitted[2]['value'] = md5(arrayToBeSubmitted[2]['value']);
            arrayToBeSubmitted[3]['value'] = md5(arrayToBeSubmitted[3]['value']);


            $.post("UserServlet?method=register", arrayToBeSubmitted)
                .done(function (data) {
                    let object = JSON.parse(data);
                    alert(object['info']);
                    if (object['success']) {
                        location.assign("index");
                    }
                    that.changeCaptcha();
                })


        })

    }

    changeCaptcha() {
        this.captchaArea.empty();
        this.captchaArea.append($(`<img src="getCaptcha?ver=${Math.random()}" alt="captcha">`));
    }

    setCaptchaAreaOnClick() {
        let that = this;
        this.captchaArea.click(function () {
            that.changeCaptcha();
        })
    }

}