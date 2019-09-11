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

    /**
     * render table with results of ajax request of appropriate ProductImage instance
     * @param imgId - id of the ProductImage instance
     * @param containerId - here used default value of appropriate <div> element
     */
    function showProductImageById(imgId, containerId = 'find-image-container') {
        let container = document.getElementById(containerId);
        container.innerHTML = '';
        let errorTxt = document.createElement('div');
        errorTxt.classList.add('error-text');
        container.append(errorTxt);
        imgId = Number.parseInt(imgId, 10);
        // parseInt() returns number or NaN to be coerced to boolean true or false with unambiguous way:
        if (!imgId) {
            errorTxt.innerHTML = 'Please type number id';
            return;
        }
        // create table with headers row:
        let table = createProductImageShowTable(containerId);
        // send request:
        $.ajax({
            type: 'GET',
            url: 'product-images-uploads/product-images/' + imgId,
            dataType: 'json',
            success: function (obtainedData, status, jqXHR) {
                // append table row with image and image details by the following function:
                appendTableRow(table, obtainedData);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errorTxt.innerHTML = jqXHR.responseText;
            }
        });
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
     * @param containerId - here used default value of appropriate <div> element
     * @return {HTMLElement}
     */
    function createProductImageShowTable(containerId = 'find-image-container') {
        /**
         * obtain and create table elements
         * @type {HTMLElement}
         */
        let container = document.getElementById(containerId);
        let table = document.createElement('table'),
            thead = document.createElement('thead'),
            tbody = document.createElement('tbody');
        table.id = 'show-product-images-table';
        /**
         * create and append table headers
         * @type {string[]}
         */
        // let headers = ['<th>Id</th>', '<th>fileName</th>', '<th>fileType</th>', '<th>image</th>'];
        let headers = ['id', 'fileName', 'fileType', 'image'];
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

    function appendTableRow(table, obtainedData) {
        let tbody = table.children[1];
        let tr = document.createElement('tr'),
        img = document.createElement('img');
        let properties = ['id', 'fileName', 'fileType']; // , 'data'
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
         let td = document.createElement('td');
         td.append(img);
         tr.append(td);
        tbody.append(tr);
    }


// ================================================================ P E R F O R M A N C E

    testIt();
//    createProductImageShowTable();
    showProductImageById('11');
    uploadImage('singleFileUploadSubmit', 'singleFileUploadInput',
        'singleFileUploadSuccess', 'singleFileUploadError', 'uploadFileImg');

});

// =========================================================================================================== CODE END
// ====================================================================================================================

// рабочий блок:
// thead.insertAdjacentHTML('afterbegin',
//     '<th>Id</th>' +
//     '<th>fileName</th>' +
//     '<th>fileType</th>' +
//     '<th>image</th>');

// рабочий блок:
//        table.classList.add("show-product-images-table");
// рабочий блок:
//        document.getElementById('show-product-images-table').firstChild.childNodes[1].innerHTML = 'changed child 1';
//        document.getElementById('show-product-images-table').firstElementChild.children[1].innerHTML = 'changed child 2';
//        document.getElementById('show-product-images-table').firstElementChild.children[1].insertAdjacentHTML('afterbegin', 'added to child 2 <br>');