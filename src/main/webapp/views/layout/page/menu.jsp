<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Sidebar</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        #sidebar {
            height: 100vh;
            width: 350px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #6c757d; /* Màu xám */
            color: darkgrey;
            padding-top: 20px;
        }
        #sidebar .sidebar-logo a {
            color: #fff;
            font-size: 1.5em;
            text-align: center;
            display: block;
            margin-bottom: 1.5em;
        }
        #sidebar .sidebar-nav {
            list-style-type: none;
            padding: 0;
        }
        #sidebar .sidebar-item {
            margin: 0;
        }
        #sidebar .sidebar-link {
            color: #adb5bd;
            padding: 10px 15px;
            display: flex;
            align-items: center;
            text-decoration: none;
        }
        #sidebar .sidebar-link:hover, #sidebar .sidebar-link.active {
            color: #fff;
            background-color: #495057;
        }
        #sidebar .sidebar-link i {
            margin-right: 10px;
        }
        #sidebar .sidebar-dropdown {
            padding-left: 20px;
        }
        #sidebar .sidebar-footer {
            position: absolute;
            bottom: 20px;
            width: 100%;
        }
        #sidebar .toggle-btn {
            color: #fff;
            background-color: transparent;
            border: none;
            font-size: 1.2em;
            margin-left: 15px;
        }
    </style>
</head>
<body>
<aside id="sidebar">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <button class="toggle-btn" type="button">
            <i class="fa-solid fa-bars"></i>
        </button>
        <div class="sidebar-logo text-left">
            <a href="/admin/trang-chu">HaBits</a>
        </div>
    </div>
    <ul class="sidebar-nav">
        <li class="sidebar-item">
            <a href="/ban-hang/index" class="sidebar-link">
                <i class="fa-solid fa-bag-shopping"></i>
                <span>Bán Hàng</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="#auth" class="sidebar-link collapsed" data-toggle="collapse" aria-expanded="false" aria-controls="auth">
                <i class="fa-solid fa-money-bill"></i>
                <span>Quản Lý Đơn Hàng</span>
            </a>
            <ul id="auth" class="sidebar-dropdown collapse">
                <li class="sidebar-item">
                    <a href="/hoa-don/index" class="sidebar-link">Quản Lý Hóa Đơn</a>
                </li>
                <li class="sidebar-item">
                    <a href="/hdct/index" class="sidebar-link">Quản Lý Hóa Đơn Chi Tiết</a>
                </li>
            </ul>
        </li>
        <li class="sidebar-item">
            <a href="#multi" class="sidebar-link collapsed" data-toggle="collapse" aria-expanded="false" aria-controls="multi">
                <i class="fa-solid fa-cart-shopping"></i>
                <span>Quản Lý Sản Phẩm</span>
            </a>
            <ul id="multi" class="sidebar-dropdown collapse">
                <li class="sidebar-item">
                    <a href="#multi-two" class="sidebar-link collapsed" data-toggle="collapse" aria-expanded="false" aria-controls="multi-two">
                        <i class="fa-solid fa-caret-down"></i>
                    </a>
                    <ul id="multi-two" class="sidebar-dropdown collapse">
                        <li class="sidebar-item">
                            <a href="/spct/index" class="sidebar-link"><i class="fa-solid fa-file-lines"></i> Quản Lý CTSP</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/san-pham/index" class="sidebar-link"><i class="fa-solid fa-book"></i> Quản Lý Sản Phẩm</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/mau-sac/index" class="sidebar-link"><i class="fa-solid fa-palette"></i>Quản Lý Màu Sắc</a>
                        </li>
                        <li class="sidebar-item">
                            <a href="/kich-thuoc/index" class="sidebar-link"><i class="fa-solid fa-expand"></i> Quản Lý Kích Cỡ</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="sidebar-item">
            <a href="/nhan-vien/index" class="sidebar-link">
                <i class="fa-solid fa-user-tie"></i>
                <span>Quản Lý Nhân Viên</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="/khach-hang/index" class="sidebar-link">
                <i class="fa-solid fa-user-secret"></i>
                <span>Quản Lý Khách Hàng</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="#" class="sidebar-link">
                <i class="fas fa-cog"></i>
                <span>Setting</span>
            </a>
        </li>
    </ul>
    <div class="sidebar-footer">
        <a href="/login" class="sidebar-link">
            <i class="fas fa-sign-out-alt"></i>
            <span>Logout</span>
        </a>
    </div>
</aside>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
