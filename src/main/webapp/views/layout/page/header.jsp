<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%--    Header-body    --%>
    <nav class="navbar navbar-expand px-4 py-3 bg-white">
        <div class="navbar-collapse collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <a href="#" data-bs-toggle="dropdown" class="nav-icon pe-md-0" style="display: flex">
                        <p class="me-3 mt-2" name="tenNV">${loggedInUser.ten}</p>
                        <img src="/views/scss/js/img_2.png" class="avatar img-fluid" alt="">
                    </a>
                    <div class="dropdown-menu dropdown-menu-end rounded">
                    </div>
                </li>
            </ul>
        </div>
    </nav>

