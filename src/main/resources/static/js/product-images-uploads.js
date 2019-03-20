$(function () {

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

// ================================================================ P E R F O R M A N C E

    testIt();
    uploadImage('singleFileUploadSubmit', 'singleFileUploadInput',
        'singleFileUploadSuccess', 'singleFileUploadError', 'uploadFileImg');

});