$(function () {

// ==================================================================================

// HTML PAGE "product-images-uploads.html"
// SERVER: ProductImageUploadsController: uploadsTest(),
// GET url: "product-images-uploads/test"
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
// ---------------------------------------------------------------------------------------------------------
    /**
     * @param submitId
     * @param inputId
     * @param successTxtId
     * @param errorTxtId
     * @param uploadFileImgId
     * HTML PAGE "product-images-uploads.html" {@link singleUploadForm}
     * SERVER: ProductImageUploadsController: uploadFile(@RequestParam("file") MultipartFile file),
     * POST url: "product-images-uploads")
     */
    function uploadImage(submitId = 'singleFileUploadSubmit', inputId = 'singleFileUploadInput',
                         successTxtId = 'singleFileUploadSuccess', errorTxtId = 'singleFileUploadError',
                         uploadFileImgId = 'uploadFileImg') {
        let submit = $('#' + submitId);
        let input = $('#' + inputId);
        let successTxt = $('#' + successTxtId);
        let errorTxt = $('#' + errorTxtId);
        let uploadFileImg = $('#' + uploadFileImgId);

        submit.click(function (event) {
            event.preventDefault();
            let files = input.prop('files');
            if (!files[0]) {
                errorTxt.html('Please select a file');
            } else {
                let formData = new FormData();
                formData.append('file', files[0]);
                $.ajax({
                    type: 'POST',
                    url: window.location.href, // "http://localhost:8081/store/product-images-uploads"

//                  contentType (i.e. the type of data sent to the server) needs to be false for multipart file:
                    contentType: false,
                    cache: false,
                    processData: false,
//                  data sent to the server (a plain object or string):
                    data: formData,
// dataType is the type of data expected back from the server (that is the one I'm ready to accept and intend
// to work with)  (it's 'obtainedData' in success function).
// Some mess is here, I don't know why: I won't be able read the obtainedData properties, if I explicitly indicated the dataType here.
// Though actually the image is successfully saves in the db and I obtain json object from server.
// Everything goes well without dataType indication.
//                    dataType: 'application/json',
                    success: function (obtainedData, status, jqXHR) {
                        console.log('file ' + obtainedData.fileName + ' stored in db');
                        errorTxt.html('');
                        let fileHref = obtainedData.fileDownloadUri; // "http://localhost:8081/store/product-images-uploads/download/40"
// SERVER: ProductImageUploadsController:
// downloadFile(@PathVariable(value = "resourceId") Long resourceId, @Autowired HttpServletRequest request),
// GET url: 'product-images-uploads/download/{resourceId}'
                        successTxt.html('<a href="' + fileHref + '" download>' + fileHref + '</a>');
                        uploadFileImg.attr("src", fileHref);
                    }, error: function (jqXHR, textStatus, errorThrown) {

                    }
                });
            }
        })
    }

// ---------------------------------------------------------------------------------------------------------

    function findProductImageById(input = 'find-image-by-id-input') {
        let inputElem = document.querySelector(input);
        let id = Number.parseInt(inputElem.value, 10);
        let parent = inputElem.getParent();
        let errorTxt = document.createElement('div');
        errorTxt.classList.add('error-text');
        if (!imgId) {
            errorTxt.innerHTML = 'Please type number id';
            parent.prepend(errorTxt);
            return;
        }
        return null;
    }
// ------------------------------------------------------------------------------------------------------------

    /**
     * render table with results of ajax request of appropriate ProductImage instance
     * @param imgId - id of the ProductImage instance
     * @param errorTxtId - id of the error message block
     * @param imgDataTableId - id of the image data table block
     * @param formId - id of the find-image form
     */
    function showProductImageById(imgId,
                                  errorTxtId = 'image-id-number-parse-error',
                                  imgDataTableId = 'show-product-images-table',
                                  formId = 'find-image-by-id-form') {
        let form = document.querySelector(`#${formId}`);
        let numberParseErrorTxt = document.querySelector('#' + errorTxtId);
        imgId = Number.parseInt(imgId, 10);
        // parseInt() returns number or NaN to be coerced to boolean true or false with unambiguous way:
        if (!imgId) {
            numberParseErrorTxt.innerHTML = 'Please type number id.  ';
            numberParseErrorTxt.style.display = 'block';
// after error text appeared, move the form field to the bottom position (arg false)
            form.scrollIntoView(false);
            return;
        }
        numberParseErrorTxt.innerHTML = '';
        numberParseErrorTxt.style.display = 'none';
        // create table with headers row:
        let table = document.querySelector('#' + imgDataTableId);
        // send request:
        $.ajax({
            type: 'GET',
            url: 'product-images-uploads/product-images/' + imgId,
            dataType: 'json',
            success: function (obtainedData, status, jqXHR) {
                // append table row with image and image details by the following function:
                if (jqXHR.status >= 200 && jqXHR.status < 300) appendProductImageShowTableRow(table, obtainedData);
// FIXME: why else clause do not work?
                else if (jqXHR.status == 404) {
                    numberParseErrorTxt.innerHTML += '404: image not found';
                    numberParseErrorTxt.style.display = 'block';
                } else {
                    numberParseErrorTxt.innerHTML += `Error occured: ${jqXHR.status}: ${jqXHR.statusText}`;
                    numberParseErrorTxt.style.display = 'block';
                }
// after table row appended or error text displayed, move the form field to the bottom position (arg false)
                form.scrollIntoView(false);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                numberParseErrorTxt.innerHTML += jqXHR.responseText;
            }
        });
    }
// ---------------------------------------------------------------------------------------------------------

    function showProductsByName(productName, containerId = 'find-product-container') {
        let container = document.createElement(containerId);

    }
// ---------------------------------------------------------------------------------------------------------

    // function showProductImagesByPage(page) {
    //     let container = document.getElementById('find-image-container');
    //     let table = document.createElement('table');
    //     // table.classList.add('');
    //
    // }

// ==================================================================================================== U T I L

    /**
     * create table with headers row to render results of ajax request of a ProductImage instance
     * @param containerId
     * @param imgDataTableId
     * @return {HTMLElement}
     */
    function createProductImageShowTable(containerId = 'find-image-container',
                                         imgDataTableId = 'show-product-images-table') {
        /**
         * obtain and create table elements
         * @type {HTMLElement}
         */
        let container = document.getElementById(containerId);
        let table = document.createElement('table'),
            thead = document.createElement('thead'),
            tbody = document.createElement('tbody');
        table.id = imgDataTableId;
// create and append table headers
        let headers = ['id', 'fileName', 'fileType', 'image', 'hide'];
        for (let i = 0; i < headers.length; i++) {
            let th = document.createElement('th');
            th.innerHTML = headers[i];
            thead.append(th);
        }
        table.append(thead);
        table.append(tbody);
        container.append(table);
        return table;
    }

// ------------------------------------------------------------------------------------------------------------
    /**
     * display results of request for ProductImage data
     * @param table - table to display results
     * @param obtainedData - an object containing ProductImage data (id, fileName, fileType and image data byte array)
     * @param formId - id of the searching form
     */
    function appendProductImageShowTableRow(table, obtainedData, formId = '#find-image-by-id-form') {
        let tbody = table.children[1];
        let tr = document.createElement('tr'),
        img = document.createElement('img');
        // an array with obtainedData properties names to convenient adding table data
        let properties = ['id', 'fileName', 'fileType'];
        for (let i = 0; i < properties.length; i++) {
            let td = document.createElement('td');
            td.innerHTML = obtainedData[properties[i]];
            tr.append(td);
        }
// https://stackoverflow.com/questions/20756042/javascript-how-to-display-image-from-byte-array-using-javascript-or-servlet
//        img.src = "data:image/jpeg;base64," + obtainedData['data'];
        // get image data not from database src, but directly from obtained byte array (that is 'data' property
        // of obtained ProductImage JSON object)
        img.src = "data:" + obtainedData['fileType'] + ";base64," + obtainedData['data'];
        img.alt = obtainedData.fileName;
        img.classList.add('img-table-cell');
         let imgTd = document.createElement('td');
         imgTd.append(img);
         tr.append(imgTd);
 // create 'hide' button to remove the current table row
        let hideBtn = document.createElement('input');
        hideBtn.type = 'submit';
        hideBtn.value = 'hide';
        // hideBtn.classList.add('hide-product-img-row');
        hideBtn.onclick = (event) => {
            event.preventDefault();
            hideBtn.parentElement.parentElement.innerHTML = '';
        };
        let hideTd = document.createElement('td');
        hideTd.append(hideBtn);
        tr.append(hideTd);
        tbody.append(tr);
    }
// ------------------------------------------------------------------------------------------------------------

    function doSubmits() {
// find-image-by-id-form submit:
        document.querySelector('#find-image-by-id-form').onsubmit =
            (event) => {
                event.preventDefault();
                let findImageByIdInput = document.querySelector('#find-image-by-id-input');
                showProductImageById(findImageByIdInput.value);
                findImageByIdInput.value = '';
            };
    }
// ------------------------------------------------------------------------------------------------------------

    function initializePage() {
        testIt();
        uploadImage('singleFileUploadSubmit', 'singleFileUploadInput',
            'singleFileUploadSuccess', 'singleFileUploadError', 'uploadFileImg');
        createProductImageShowTable('find-image-container', 'show-product-images-table');
        doSubmits();
    }
// ------------------------------------------------------------------------------------------------------------



// ================================================================ P E R F O R M A N C E

    // testIt();
    // showProductImageById('289');
    // uploadImage('singleFileUploadSubmit', 'singleFileUploadInput',
    //     'singleFileUploadSuccess', 'singleFileUploadError', 'uploadFileImg');
    initializePage();

});

// =========================================================================================================== CODE END
// ====================================================================================================================
