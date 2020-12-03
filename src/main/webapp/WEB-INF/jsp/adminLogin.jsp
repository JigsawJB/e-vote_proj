<!DOCTYPE html>
<html lang="en">
    <%@include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <div class="container">
            <nav class="navbar navbar-light bg-light text-center">
                <span class="navbar-brand mb-0 h1"><a href="/admin" class="text-dark" style="text-decoration: none;">E-Vote</a></span>
            </nav>
            <div class="row justify-content-center" style="height: 80vh;">
                <div class="card col-12 col-md-8 shadow-lg border border-dark align-self-center">
                    <div class="card-header text-center">
                        <h4>Admin Login</h4>
                    </div>
                    <div class="card-body">
                        <form id="login-form">
                            <div class="form-group">
                                <label for="username">Username:</label>
                                <input type="text" class="form-control" id="username" name="adminUserName">
                            </div>
                            <div class="form-group">
                                <label for="password">Password:</label>
                                <input type="password" class="form-control" id="password" name="adminPassword">
                            </div>
                            <div class="form-group">
                                <div class="w-100 d-flex justify-content-center">
                                    <button class="btn btn-outline-primary col-12 col-md-8 col-lg-4" type="submit">Login</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    <%@include file="/WEB-INF/jspf/scripts.jspf" %>
    <script type="text/javascript">
        $("#login-form").validate({
            rules: {
                adminUserName: "required",
                adminPassword: "required"
            },
            messages: {
                adminUserName: "Username is required.",
                adminPassword: "Password is required."
            },
            errorClass: "error text-danger",
            validClass: "valid is-valid",
            submitHandler: () => {
                try {
                    $.ajax({
                        beforeSend: (xhr) => {
                            Swal.fire({
                                title: "Logging In",
                                html: '<div class="spinner-border text-success" style="width: 4rem; height: 4rem;" role="status"><span class="sr-only">Loading...</span></div>',
                                showConfirmButton: false
                            });
                        },
                        url: "/login",
                        data: JSON.stringify({
                            userName: $("#username").val(),
                            password: $("#password").val(),
                            isAdmin: true
                        }),
                        method: "POST",
                        contentType: "application/json",
                        error: (response) => {
                            setTimeout(() => {
                                Swal.close();
                                Swal.fire({
                                    title: 'Error',
                                    icon: 'error',
                                    text: response.responseText
                                });
                            }, 1500);
                        },
                        success: (response) => {
                            setTimeout(() => {
                                Swal.close();
                                window.location.href = `${window.location.href}\${response}`;
                            }, 1500);
                        }
                    });
                } catch (e) {
                    console.error(e);
                }
            }
        });
    </script>
    </body>

</html>