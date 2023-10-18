const BASE_URL = "http://localhost:8082";
const FIRST_NAME = "firstName"
const LAST_NAME = "lastName"
const EMAIL = "email";
let isModifying = false;
let modifyingEmp = "";
let employees = [];


$(document).ready(function () {
    getEmployees();
    actions();
});


// ********************** Actions *************************************

function actions() {
    // form submission----
    $("#form_activate").submit(formSubmitHandler);
    $("#" + FIRST_NAME).click(removeInValidClass);
    $("#" + EMAIL).click(removeInValidClass);

    $("#addDiv").click(addNewEmpHandler);
    $(".delete").click(deleteEmpHandler);
    $(".edit").click(editHandler);


}

function editHandler() {
    hideSubmitButton();
    isModifying = true;
    const empId = $(this).parents("tr")[0].id;
    modifyingEmp = getEmployee(empId);
    emptyFormValues();
    $("#" + FIRST_NAME).val(modifyingEmp.firstName);
    $("#" + LAST_NAME).val(modifyingEmp.lastName);
    $("#" + EMAIL).val(modifyingEmp.email);
    enableSubmitButton();
    showFrom();

}

function hideSubmitButton(){
    $("#addDiv").hide();
    $(".btn-success").hide();
}
function showSubmitButton(){
    $("#addDiv").show();
    $(".btn-success").show();
}
function addNewEmpHandler() {
    emptyFormValues();
    enableSubmitButton();
    hideSubmitButton();
    showFrom();
}


function formSubmitHandler(e) {
    e.preventDefault();
    disableSubmitButton();

    const data = getEmpData();

    if (!isAllowable(data)) {
        enableSubmitButton();
        isModifying = false;
        modifyingEmp = "";
    } else {
        if (isModifying) {
            modifyEmp(data);
        } else {
            submitEmp(data);
        }
    }
}


// ************************** Utility Functions  ............

function getEmpData() {
    const emp = {};
    emp.firstName = $("#" + FIRST_NAME).val();
    emp.lastName = $("#" + LAST_NAME).val();
    emp.email = $("#" + EMAIL).val();
    return emp;
}

function getEmployee(id) {

    return employees.find(emp => +emp.id === +id);
}

function showFrom() {
    $("#form_activate").show();
    $("#empList").hide();
}

function hideFrom() {
    $("#form_activate").hide();
    $("#empList").show();
}

function disableSubmitButton() {
    $(".btn_submit").prop("disabled", true);
}

function emptyFormValues() {
    $("#" + FIRST_NAME).val("");
    $("#" + LAST_NAME).val("");
    $("#" + EMAIL).val("");
}


function enableSubmitButton() {
    $(".custom_btn button[type='submit']").prop("disabled", false);
}

function removeInValidClass() {
    if ($(this).hasClass("is-invalid"))
        $(this).removeClass("is-invalid");
}

function alertFields(data) {
    for (let i = 0; i < data.length; i++) {
        addError(data[i].field, data[i].message)
    }
}

function addError(id, message) {
    $("#" + id).addClass("is-invalid");
    $("." + id).text(message);
}

function isAllowable(data) {
    let isOk = true;
    if (!data.firstName) {
        $("#" + FIRST_NAME).addClass("is-invalid");
        isOk = false;
    }

    if (!data.email) {
        $("#" + EMAIL).addClass("is-invalid");
        isOk = false;
    }

    return isOk;
}

function createRows(data) {
    const body = $("#empBody");
    body.empty();
    if (data && data.length > 0) {

        for (let i = 0; i < data.length; i++) {
            const vals = Object.values(data[i]);
            const tr = createRow(vals);
            body.append(tr);
        }
        showSubmitButton();
    } else {
        body.empty();
        body.append("No employees to show!!")
        showSubmitButton();
    }

    $("#" + FIRST_NAME).click(removeInValidClass);
    $("#" + EMAIL).click(removeInValidClass);
    $(".delete").click(deleteEmpHandler);
    $(".edit").click(editHandler);
}

function createRow(values) {
    const tr = document.createElement('tr');
   $(tr).attr("id", values[0]);
    for (let i = 1; i < values.length; i++) {
        const td = document.createElement('td');
        $(td).text(values[i])
        $(tr).append(td);
    }
    const operations = `  <td>
                    <div>
                        <button class="edit btn btn-warning">edit</button>
                        <button class="delete btn btn-danger">delete</button>
                    </div>
                </td>`;
    $(tr).append(operations);
    return tr;
}


// ******************************* AJAX**********************************************************
function submitEmp(params) {

    $.ajax({
        url: BASE_URL + "/new",
        contentType: "application/json",
        data: JSON.stringify(params),
        type: "POST",
        dataType: "json"
    }).done(function (xhr, status, response) {
        console.log(response);
        getEmployees();
        hideFrom();
    }).fail(function (xhr, status, response) {
        alertFields(xhr.responseJSON.errors)
        enableSubmitButton();
    });
}

function getEmployees() {

    $.ajax({
        url: BASE_URL + "/all",
        contentType: "application/json",
        type: "GET",
        dataType: "json"
    }).done(function (xhr, status, response) {
        employees = xhr;
        createRows(employees);
    }).fail(function (xhr, status, response) {

    });
}

function deleteEmpHandler() {
    const deleteEmpId = $(this).parents("tr")[0].id;
    $.ajax({
        url: BASE_URL + "/employees/" + deleteEmpId,
        contentType: "application/json",
        type: "DELETE",
        dataType: "json"
    }).done(function (xhr, status, response) {
        getEmployees();
    }).fail(function (xhr, status, errorThrown) {
        getEmployees();
    });

}

function modifyEmp(params) {

    $.ajax({
        url: BASE_URL + "/employees/" + modifyingEmp.id,
        contentType: "application/json",
        data: JSON.stringify(params),
        type: "PUT",
        dataType: "json"
    }).done(function (xhr, status, response) {
        getEmployees();
        hideFrom();

        isModifying = false;
        modifyingEmp = "";
        emptyFormValues();
    }).fail(function (xhr, status, response) {
        alertFields(xhr.responseJSON.errors)
        enableSubmitButton();
        isModifying = false;
        modifyingEmp = "";
    });
}



