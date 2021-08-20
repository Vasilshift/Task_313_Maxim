function createTableRow(u) {                                                    //table creating
    let userRole = "[";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += u.roles[i].name.substring(5);
        if (i != (u.roles.length - 1)) {
            userRole += ", ";
        }
    }
    userRole += "]";
    return `<tr id="user_table_row">
        <td>${u.id}</td>
        <td>${u.name}</td>
        <td>${u.lastname}</td>
        <td>${u.age}</td>
        <td>${u.work}</td>
        <td>${u.username}</td>
        <td>${userRole}</td>
        <td>
        <a href="/api/users/${u.id}" class="btn btn-info openEdit" >Edit</a>
        </td>
        <td>
        <a href="/api/users/${u.id}" class="btn btn-danger openDelete">Delete</a>
        </td>
</tr>`;
}

function restartAllUser() {                                                     //table refreshing
    let UserTableBody = $("#user_table_body")
    UserTableBody.children().remove();

    fetch("api/users")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);
            }));
        }).catch(error => {
        console.log(error);
    });
}

function getRole(address) {                                                     //get roles from form
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), name: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}

$(document).ready(function () {
    restartAllUser();
    $('.addBtn').on('click', function (event) {                                  //adding user
        let user = {
            name: $("#nameNew").val(),
            lastname: $("#lastnameNew").val(),
            age: $("#ageNew").val(),
            work: $("#workNew").val(),
            username: $("#usernameNew").val(),
            password: $("#passwordNew").val(),
            roles: getRole("#selectRoleNew")
        }
        console.log(user);
        fetch("api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => $('input').val('')) //fields cleaning
            .then(() => $('select').val(false))
            .then(() => openTabById('nav-home'))
            .then(() => restartAllUser());

    });
});

document.addEventListener('click', function (event) {
    event.preventDefault()

    if ($(event.target).hasClass('openEdit')) {                                     //open edit page
        let href = $(event.target).attr("href");
        $(".editUser #editModal").modal();
        $.get(href, function (user) {
            $(".editUser #idEd").val(user.id);
            $(".editUser #nameEd").val(user.name);
            $(".editUser #lastnameEd").val(user.lastname);
            $(".editUser #ageEd").val(user.age);
            $(".editUser #workEd").val(user.work);
            $(".editUser #usernameEd").val(user.username);
            $(".editUser #passwordEd").val(user.password);
            $(".editUser #selectRoleEd").val(user.roles);

            formRoleSelect(user, 'selectRoleEd')
        });
    }

    if ($(event.target).hasClass('editBtn')) {                                 //save changes
        let user = {
            id: $('#idEd').val(),
            name: $('#nameEd').val(),
            lastname: $('#lastnameEd').val(),
            age: $('#ageEd').val(),
            work: $('#workEd').val(),
            username: $('#usernameEd').val(),
            password: $('#passwordEd').val(),
            roles: getRole("#selectRoleEd")
        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('openDelete')) {
        let href = $(event.target).attr("href");
        $(".deleteUser #deleteModal").modal();
        $.get(href, function (user) {
            $(".deleteUser #idDel").val(user.id);
            $(".deleteUser #nameDel").val(user.name);
            $(".deleteUser #lastnameDel").val(user.lastname);
            $(".deleteUser #ageDel").val(user.age);
            $(".deleteUser #workDel").val(user.work);
            $(".deleteUser #usernameDel").val(user.username);
            $(".deleteUser #selectRoleDel").val(user.roles);

            formRoleSelect(user, 'selectRoleDel')
        });
    }

    if ($(event.target).hasClass('deleteBtn')) {                                 //submit delete
        let deleteId = $('#idDel').val()
        delModalButton(deleteId)
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }

    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }
});

function formRoleSelect(user, adress) {                                         //selet roles on form
    const select = document.getElementById(adress)
        .getElementsByTagName('option')

    for (let i of user.roles) {
        if (i.name === 'ROLE_ADMIN') {
            select[0].selected = true
        }
        if (i.name === 'ROLE_USER') {
            select[1].selected = true
        }
    }
}

function editModalButton(user) {
    fetch("api/users", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #editModal').modal('hide');
        restartAllUser();
    })
}

function delModalButton(deleteId) {
    fetch("api/users/" + deleteId, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then($('.deleteUser #deleteModal').modal('hide'))
        .then(() => restartAllUser());
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function userTable() {
    document.location.replace("/user");
}

function logout() {
    document.location.replace("/logout");
}

