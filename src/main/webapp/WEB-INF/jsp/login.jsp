<!DOCTYPE html>
<html lang="en">
  <%@include file="/WEB-INF/jspf/header.jspf" %>
  <body>
    <div class="container">
            <nav class="navbar navbar-light bg-light text-center">
                <span class="navbar-brand mb-0 h1"><a href="/" class="text-dark" style="text-decoration: none;">E-Vote</a></span>
            </nav>
            <div class="row justify-content-center" style="height: 80vh;">
                <div class="card col-12 col-md-8 shadow-lg border border-dark align-self-center">
                    <div class="card-header text-center">
                        <h4>Login</h4>
                    </div>
                    <div class="card-body">
                        <form id="login-form">
                            <div class="form-group">
                                <label for="student-id">Student ID:</label>
                                <input type="number" class="form-control" id="student-id" name="studentId">
                            </div>
                            <div class="form-group">
                                <label for="student-password">Password:</label>
                                <input type="password" class="form-control" id="student-password" name="studentPassword">
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
                studentId: "required",
                studentPassword: "required"
            },
            messages: {
                studentId: "Student ID is required.",
                studentPassword: "Password is required."
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
                        method: "POST",
                        data: JSON.stringify({
                            userName: $("#student-id").val(),
                            password: $("#student-password").val(),
                            isAdmin: false
                        }),
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