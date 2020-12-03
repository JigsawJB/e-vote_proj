<!DOCTYPE html>
<html lang="en">
    <%@include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <div class="container">
            <nav class="navbar navbar-light bg-light" style="justify-content: normal;">
                <span class="navbar-brand mb-0 h1"><a href="/dashboard" class="text-dark" style="text-decoration: none;">E-Vote</a></span>
                <div>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a class="nav-link" href="/dashboard">Voters</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="/candidatelist">Candidates</a>
                        </li>
                    </ul>
                </div>
                <div class="d-flex justify-content-end" style="width: 70%;">
                    <div>
                        <a class="btn btn-primary" href="/admin">Logout</a>
                    </div>
                </div>
            </nav>
            <div class="container-fluid mt-4">
                <table id="candidate-table" class="table table-bordered">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Election Category</th>
                            <th>Vote Tally</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${candidates}" var="candidate">
                            <tr>
                                <td>${candidate.firstName}</td>
                                <td>${candidate.lastName}</td>
                                <td>${candidate.electionName}</td>
                                <td>${candidate.voteTally}</td>
                                <td><button data-candidate-id="${candidate.candidateId}" type="button" class="btn btn-primary update" data-toggle="modal" data-target="#update-candidate-model">Update Candidate</button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="modal" tabindex="-1" id="update-candidate-model">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title">Update Candidate</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <form id="update-modal">
                        <input id="candidate-id" class="d-none" hidden disabled value="">
                        <div class="form-group">
                            <label for="first-name">First Name:</label>
                            <input type="text" class="form-control" name="firstName" id="first-name">
                        </div>
                        <div class="form-group">
                            <label for="last-name">Last Name:</label>
                            <input type="text" class="form-control" name="lastName" id="last-name">
                        </div>
                        <div class="form-group">
                            <div class="d-flex justify-content-between">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button id="update-candidate-button" type="submit" class="btn btn-primary">Update</button>
                              </div>
                        </div>
                    </form>
                  </div>
                </div>
        </div>
    <%@include file="/WEB-INF/jspf/scripts.jspf" %>
    <script type="text/javascript">
        let currentCandidateId = 0;
        $(document).ready(() => {
            $('#candidate-table').DataTable();
        });

        $(document).on("click",".update", function(){
            currentCandidateId = parseInt($(this).data("candidate-id"), 10);
        });
        $("#update-modal").validate({
            rules: {
                firstName: "required",
                lastName: "required"
            },
            messages: {
                firstName: "First name is required.",
                lastName: "Last name is required"
            },
            errorClass: "error text-danger",
            validClass: "valid is-valid",
            submitHandler: () => {
                try {
                    $.ajax({
                        beforeSend: (xhr) => {
                            Swal.fire({
                                title: "Updating...",
                                html: '<div class="spinner-border text-success" style="width: 4rem; height: 4rem;" role="status"><span class="sr-only">Loading...</span></div>',
                                showConfirmButton: false
                            });
                        },
                        url: "/updateCandidate",
                        method: "POST",
                        data: JSON.stringify({
                            firstName: $("#first-name").val(),
                            lastName: $("#last-name").val(),
                            candidateId: currentCandidateId
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
                                    title: 'Canidate Updated',
                                    icon: 'success',
                                    text: 'Update successful.'
                                });
                            }, 1500);
                        }
                    });
                } catch (e) {
                    console.error(e);
                }
            }
        });
        $('#update-candidate-model').on('shown.bs.modal', function () {
            $("")
        })
    </script>
    </body>

</html>