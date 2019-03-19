

$(function () {

// ======================================================================================= page index.html

    function hackButtonBehavior() {
         let hackButton = $("#hack-button");
        let getPassword = $("#hack-password");
        hackButton.click(function (event) {
            event.preventDefault();
            console.log('hackButton was pushed!')
            hackRequest(getPassword.val());
            getPassword.val('');
        });
    }
// ------------------------------------------------

    // send "#hack-password" input content (see index.html) to check, and if the password matched return true
    function hackRequest(password) {
        $.ajax({
            type: 'get',
            url: 'index?pswd=' + password,
            dataType: 'json',
            success: function (data, status, jqXHR) {
                console.log('hackRequest completed, data = ' + data);
//                if (data) hackerRedirect();
                if (data) window.location.href = 'terriblemistakeofyourlife.html';
                else hackMessage(password);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("hackRequest() error");
            }
        });
    }

// ------------------------------------------------

    function hackMessage(password) {
        $("#hack-message").html('No dear, <span style="color: blue; font-weight: bold">' + password + '</span> does not fit. Come again!');
        $('#promo-picture').css('visibility', 'visible');
    }

// ------------------------------------------------

    function unlockButtonBehavior() {
        let unlockBtn = $('#unlock-btn');
        unlockBtn.click(function (event) {
            event.preventDefault();
            console.log('unlock-btn pushed');
            unlockRequest();
        })

    }

// ------------------------------------------------

// ========================================================================= page terriblemistakeofyourlife.html

    function unlockRequest() {
        $.ajax({
            type: 'post',
            url: 'unlock',
//            dataType: 'json',
            success: function (data, status, jqXHR) {
                console.log('unlockRequest completed');
                let image = $('#hack-img');
                image.attr("src","images/unlocked.png");
                setTimeout(function () {
                    window.location.href = '/store';
                }, 2000)
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("unlockRequest() error");
            }
        })
    }

// ------------------------------------------------
// ================================================================================== page product-images-uploads.html

    function testIt() {
        $('#testForm').click(function (event) {
            event.preventDefault();
            console.log('testIt submitted');
            $.ajax({
                type: 'get',
                url: 'product-images-uploads/test',
                dataType: 'text',
                success: function (data, status, jqXHR) {
                    console.log('test success');
                    $('#testMessage').html(data);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log('test error');
                    $('#testMessage').html('test error');
                }
            })
        })
    }

// ------------------------------------------------

    function uploadImage(submitId, inputId, successTxtId, errorTxtId, uploadFileImgId) {
        let submit = $('#' + submitId);
        let input = $('#' + inputId);
        let successTxt = $('#' + successTxtId);
        let errorTxt = $('#' + errorTxtId);
        let uploadFileImg = $('#' + uploadFileImgId);

        submit.click(function (event) {
            event.preventDefault();
            console.log(submitId + ' clicked');
            let files = input.prop('files');
            if (!files[0]) {
                errorTxt.html('Please select a file');
            } else {
                let formData = new FormData();
                formData.append('file', files[0]);
                $.ajax({
                    type: 'POST',
                    url: window.location.href, // "http://localhost:8081/store/product-images-uploads" ( + '?file=14.jpeg')
//                    sending data type - need to be false for multipart file
                    contentType: false,
                    cache: false,
                    processData: false,
//                    sending data
                    data: formData,
//                    The type of data that you're expecting back from the server (i.e. 'obtainedData' type).
//                    dataType: 'application/json',
                    success: function (obtainedData, status, jqXHR) {
                    console.log('file ' + obtainedData.fileName + ' stored in db');
                        errorTxt.html('');
//                        errorTxt.css('visibility', 'hidden');
                        let fileHref = obtainedData.fileDownloadUri;
                        successTxt.html('<a href="' + fileHref + '" download>' + fileHref + '</a>');
                        uploadFileImg.attr("src", fileHref);
                    }, error: function (jqXHR, textStatus, errorThrown) {

                    }
                });
            }
        })
    }

// ------------------------------------------------

    function removeHackerBlockButtonBehavior() {
        let rHBButton = $('#remove-hacker-block');
        rHBButton.click(function (event) {
            event.preventDefault();
            $('#hacker-block').html('');
            rHBButton.display('visible', 'false');
            createLogInput();
        })
    }

// ------------------------------------------------

    function createLogInput() {

    }

// ------------------------------------------------
// ------------------------------------------------
// ------------------------------------------------

// ================================================================ P E R F O R M A N C E
    hackButtonBehavior();
    unlockButtonBehavior();
    testIt();
    uploadImage('singleFileUploadSubmit', 'singleFileUploadInput',
        'singleFileUploadSuccess', 'singleFileUploadError', 'uploadFileImg');
    removeHackerBlockButtonBehavior();

});