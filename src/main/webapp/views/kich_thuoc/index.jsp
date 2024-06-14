
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kích thước</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <jsp:include page="../layout/page/taglibViewsAdmin.jsp"></jsp:include>
</head>
<body>
<div class="wrapper">
    <%--Menu--%>
    <jsp:include page="../layout/page/menu.jsp"></jsp:include>
    <div class="main">
        <%--Header--%>
        <jsp:include page="../layout/page/header.jsp"></jsp:include>
        <%--Body--%>
        <div class="box container my-2">
            <h2>Danh sách kích thước</h2>
            <div class="d-flex justify-content-between">
                <form action="/kich-thuoc/index" method="get" class="d-flex align-items-center">
                    <div class="row mx-2">
                        <div class="col-md-15">
                            <input type="text" class="form-control" placeholder="Tìm kiếm theo mã và tên"
                                   name="timKiem" id="timKiem">
                        </div>
                    </div>
                    <button class="btn btn-dark" type="submit">Lọc</button>
                </form>
                <div class="my-3">
                    <a class="btn btn-primary" href="/kich-thuoc/create">Tạo mới</a>
                    <%--        <a class="btn btn-primary" href="/nhan-vien/export">Xuất file excel</a>--%>
                </div>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Mã kích thước</th>
                    <th scope="col">Tên kích thước</th>
                    <th scope="col">Trạng thái</th>
                    <th scope="col">Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listKT}" var="ms">
                    <tr>
                        <td>${ms.id}</td>
                        <td>${ms.ma}</td>
                        <td>${ms.ten}</td>
                        <td>${ms.trangThai==0 ? "Hoạt Động":"Ngừng Hoạt Động"}</td>
                        <td>
                            <a class="btn btn-warning" href="/kich-thuoc/edit/${ms.id}">Update</a>
                            <a class="btn btn-danger" href="/kich-thuoc/delete/${ms.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- Điều khiển phân trang -->
            <nav>
                <ul class="pagination d-flex justify-content-center">
                    <!-- Previous Button -->
                    <c:choose>
                        <c:when test="${currentPage == 1}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">Previous</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="/kich-thuoc/index?page=${currentPage - 1}&size=${pageSize}">Previous</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <!-- Page Numbers -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="/kich-thuoc/index?page=${i}&size=${pageSize}">${i}</a>
                        </li>
                    </c:forEach>
                    <!-- Next Button -->
                    <c:choose>
                        <c:when test="${currentPage == totalPages}">
                            <li class="page-item disabled">
                                <a class="page-link" href="#">Next</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="/kich-thuoc/index?page=${currentPage + 1}&size=${pageSize}">Next</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
        <%--Footer--%>
        <jsp:include page="../layout/page/footer.jsp"></jsp:include>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script>
    const hamBurger = document.querySelector(".toggle-btn");

    hamBurger.addEventListener("click", function () {
        document.querySelector("#sidebar").classList.toggle("expand");
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

    ::after,
    ::before {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

    a {
        text-decoration: none;
    }

    li {
        list-style: none;
    }

    body {
        font-family: 'Poppins', sans-serif;
    }

    .wrapper {
        display: flex;
    }

    .main {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        width: 100%;
        overflow: hidden;
        transition: all 0.35s ease-in-out;
        background-color: #fff;
        min-width: 0;
    }

    #sidebar {
        width: 70px;
        min-width: 70px;
        z-index: 1000;
        transition: all .25s ease-in-out;
        background-color: #0e2238;
        display: flex;
        flex-direction: column;
    }

    #sidebar.expand {
        width: 260px;
        min-width: 260px;
    }

    .toggle-btn {
        background-color: transparent;
        cursor: pointer;
        border: 0;
        padding: 1rem 1.5rem;
    }

    .toggle-btn i {
        font-size: 1.5rem;
        color: #FFF;
    }

    .sidebar-logo {
        margin: auto 0;
    }

    .sidebar-logo a {
        color: #FFF;
        font-size: 1.15rem;
        font-weight: 600;
    }

    #sidebar:not(.expand) .sidebar-logo,
    #sidebar:not(.expand) a.sidebar-link span {
        display: none;
    }

    #sidebar.expand .sidebar-logo,
    #sidebar.expand a.sidebar-link span {
        animation: fadeIn .25s ease;
    }

    @keyframes fadeIn {
        0% {
            opacity: 0;
        }

        100% {
            opacity: 1;
        }
    }

    .sidebar-nav {
        padding: 2rem 0;
        flex: 1 1 auto;
    }

    a.sidebar-link {
        padding: .625rem 1.625rem;
        color: #FFF;
        display: block;
        font-size: 0.9rem;
        white-space: nowrap;
        border-left: 3px solid transparent;
    }

    .sidebar-link i,
    .dropdown-item i {
        font-size: 1.1rem;
        margin-right: .75rem;
    }

    a.sidebar-link:hover {
        background-color: rgba(255, 255, 255, .075);
        border-left: 3px solid #3b7ddd;
    }

    .sidebar-item {
        position: relative;
    }

    #sidebar:not(.expand) .sidebar-item .sidebar-dropdown {
        position: absolute;
        top: 0;
        left: 70px;
        background-color: #0e2238;
        padding: 0;
        min-width: 15rem;
        display: none;
    }

    #sidebar:not(.expand) .sidebar-item:hover .has-dropdown + .sidebar-dropdown {
        display: block;
        max-height: 15em;
        width: 100%;
        opacity: 1;
    }

    #sidebar.expand .sidebar-link[data-bs-toggle="collapse"]::after {
        border: solid;
        border-width: 0 .075rem .075rem 0;
        content: "";
        display: inline-block;
        padding: 2px;
        position: absolute;
        right: 1.5rem;
        top: 1.4rem;
        transform: rotate(-135deg);
        transition: all .2s ease-out;
    }

    #sidebar.expand .sidebar-link[data-bs-toggle="collapse"].collapsed::after {
        transform: rotate(45deg);
        transition: all .2s ease-out;
    }

    .navbar {
        background-color: #f5f5f5;
        box-shadow: 0 0 2rem 0 rgba(33, 37, 41, .1);
    }

    .navbar-expand .navbar-collapse {
        min-width: 200px;
    }

    .avatar {
        height: 40px;
        width: 40px;
    }

    @media (min-width: 768px) {
    }
</style>
</body>
</html>


