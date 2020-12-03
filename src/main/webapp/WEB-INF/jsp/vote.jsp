<!DOCTYPE html>
<html lang="en">
  <%@include file="/WEB-INF/jspf/header.jspf" %>
  <body>
    <div class="container">
            <nav class="navbar navbar-light bg-light text-center">
                <span class="navbar-brand mb-0 h1">E-Vote</span>
                <div>
                    <a class="btn btn-primary" href="/">Logout</a>
                </div>
            </nav>
            <div class="row vh-100 justify-content-center" style="height: 80vh;">
                <div class="card col-12 col-md-8 shadow-lg border border-dark align-self-center">
                    <div class="card-header text-center">
                        <h4>Vote</h4>
                    </div>
                    <div class="card-body">
                        <form id="vote-form">
                            <div class="form-group d-none">
                                <input class="form-control" type="text" id="voter-id" value="${voterId}">
                            </div>
                            <div class="form-group">
                                <label for="election">Election:</label>
                                <select class="custom-select" id="election" name="election">
                                    <option selected disabled value="0">Select a category</option>
                                    <c:forEach items="${elections}" var="election">
                                        <option value="${election.id}">${election.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="candidate">Candidate:</label>
                                <select class="custom-select" id="candidate" name="candidate">
                                    <option selected disabled value="0">Select a candidate</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <div class="w-100 d-flex justify-content-center">
                                    <button class="btn btn-outline-primary col-12 col-md-8 col-lg-4" type="submit">Cast Vote</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    <%@include file="/WEB-INF/jspf/scripts.jspf" %>
    <script type="text/javascript">
        $("#election").change(() => {
            $.ajax({
                url: "/getCandidates/" + $("#election").val(),
                method: 'GET',
                success: (response) => {
                    $("#candidate").empty().append(new Option("Select a category", "0", true, true));
                    response.forEach(candidate => {
                        $("#candidate").append(new Option(candidate.firstName + " " + candidate.lastName, candidate.id, false, false));
                    });
                },
                error: (response) => {
                    console.error(response.responseText);
                }
            });
        });
        $("#vote-form").validate({
            rules: {
                election: {
                    required: true,
                    min: 1
                },
                candidate: {
                    required: true,
                    min: 1
                }
            },
            messages: {
                election: {
                    required: "Please select an election category.",
                    min: "Please select an election category."
                },
                candidate: {
                    required: "Please select a candidate.",
                    min: "Please select a candidate."
                }
            },
            errorClass: "error text-danger",
            validClass: "valid is-valid",
            submitHandler: () => {
                try {
                    $.ajax({
                        beforeSend: (xhr) => {
                            Swal.fire({
                                title: "Casting Vote",
                                html: '<div class="spinner-border text-success" style="width: 4rem; height: 4rem;" role="status"><span class="sr-only">Loading...</span></div>',
                                showConfirmButton: false
                            });
                        },
                        url: "/vote",
                        method: "POST",
                        data: JSON.stringify({
                            electionId: $("#election").val(),
                            candidateId: $("#candidate").val(),
                            voterId: $("#voter-id").val()
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
                                Swal.fire({
                                    title: 'Vote Submitted',
                                    icon: 'success',
                                    text: 'Thank you for casting your vote. Have a good day.'
                                }).then(choice => {
                                    if (choice.isConfirmed) {
                                        window.location.reload();
                                    }
                                });
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