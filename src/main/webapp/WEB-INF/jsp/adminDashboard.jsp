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
                            <a class="nav-link active" href="/dashboard">Voters</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/candidatelist">Candidates</a>
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
                <table id="voters-table" class="table table-bordered">
                    <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Has Voted</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${voters}" var="voter">
                            <tr>
                                <td>${voter.firstName}</td>
                                <td>${voter.lastName}</td>
                                <td>${voter.hasVoted}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    <%@include file="/WEB-INF/jspf/scripts.jspf" %>
    <script type="text/javascript">
        $(document).ready(() => {
            $('#voters-table').DataTable();
        });
    </script>
    </body>

</html>