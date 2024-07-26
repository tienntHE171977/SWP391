<%-- 
    Document   : order
    Created on : Oct 19, 2021, 11:23:09 PM
    Author     : Khuong Hung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh sách đơn hàng | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="admin/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="dashboard"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="admin/images/user.png" width="50px"
                                                alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>${sessionScope.user.user_name}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item" href="dashboard"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý khách hàng</span></a></li>
                <li><a class="app-menu__item" href="productmanager"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                </li>
                <li><a class="app-menu__item" href="ordermanager"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Quản lý đơn hàng</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách đơn hàng</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button"
                                 <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                            class="fas fa-print"></i> In dữ liệu</a>
                                </div>
                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                            <c:if test="${listOrderCart.size() == 0}">
                            <h3><strong class="exit">NO ORDER EXITS</strong></h3>
                        </c:if>

                        <c:if test="${listOrderCart.size() != 0}">
                            <thead>
                                <tr>


                                    <th>STT</th>
                                    <th>Name</th>
                                    <th>LocationOrder</th>
                                    <th>Total</th>

                                    <th>Status</th>
                                    <th>Comment</th>
                                    <th>Detail Order</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var = "stt" value = "${1}"/>
                                <c:forEach items="${listOrderCart}" var="i">
                                    <tr>

                                        <td>${stt}</td>
                                        <c:set var = "stt" value = "${stt+1}"/>
                                        <td>${i.nameUser}</td>
                                        <td>${i.locationOrder}</td>
                                        <td>${i.total}</td>
                                        <td>
                                            <c:if test="${i.status == 2}">
                                                Approved
                                            </c:if>

                                            <c:if test="${i.status == 0}">
                                                Reject
                                            </c:if>

                                            <c:if test="${i.status == 1}">

                                                <button onclick="chanageStatus(this,${i.userID}, ${i.orderDeitalID}, 2)">Approved</button>
                                                <button onclick="chanageStatus(this,${i.userID}, ${i.orderDeitalID}, 0)">Reject</button>

                                            </c:if> 
                                        </td>
                                        <td>
                                            <c:if test="${i.comment == null || i.comment == ''}">
                                                NO COMMENT
                                            </c:if>
                                            <c:if test="${i.comment != null && i.comment != ''}">
                                                <p>${i.comment}</p>
                                            </c:if>
                                        </td>
                                        <td><a href="detailBill?orderDetailID=${i.orderDeitalID}" class="view">View</a></td>
                                        <td>
                                            <a href="#editEmployeeModal" 
                                               onclick="editOrder('${i.orderDeitalID}', '${i.nameUser}', '${i.locationOrder}', '${i.total}', '${i.comment}')" class="edit"
                                               data-toggle="modal"><i class="material-icons" data-toggle="tooltip"
                                                                   title="Edit">&#xE254;</i></a>

<!--                                            <a href="#deleteEmployeeModal" onclick="deleteOrder(${i.orderDeitalID})" class="delete"
                                               data-toggle="modal"><i class="material-icons" data-toggle="tooltip"
                                                                   title="Delete">&#xE872;</i></a>-->
                                        </td>


                                    </tr>
                                </c:forEach>
                            </tbody>
                             </c:if>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- Essential javascripts for application to work-->
        <script src="admin/js/jquery-3.2.1.min.js"></script>
        <script src="admin/js/popper.min.js"></script>
        <script src="admin/js/bootstrap.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="admin/js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="admin/js/plugins/pace.min.js"></script>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="admin/js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="admin/js/plugins/dataTables.bootstrap.min.js"></script>
        <script type="text/javascript">$('#sampleTable').DataTable();</script>
        <script>
            //Thời Gian
            function time() {
                var today = new Date();
                var weekday = new Array(7);
                weekday[0] = "Chủ Nhật";
                weekday[1] = "Thứ Hai";
                weekday[2] = "Thứ Ba";
                weekday[3] = "Thứ Tư";
                weekday[4] = "Thứ Năm";
                weekday[5] = "Thứ Sáu";
                weekday[6] = "Thứ Bảy";
                var day = weekday[today.getDay()];
                var dd = today.getDate();
                var mm = today.getMonth() + 1;
                var yyyy = today.getFullYear();
                var h = today.getHours();
                var m = today.getMinutes();
                var s = today.getSeconds();
                m = checkTime(m);
                s = checkTime(s);
                nowTime = h + " giờ " + m + " phút " + s + " giây";
                if (dd < 10) {
                    dd = '0' + dd
                }
                if (mm < 10) {
                    mm = '0' + mm
                }
                today = day + ', ' + dd + '/' + mm + '/' + yyyy;
                tmp = '<span class="date"> ' + today + ' - ' + nowTime +
                        '</span>';
                document.getElementById("clock").innerHTML = tmp;
                clocktime = setTimeout("time()", "1000", "Javascript");

                function checkTime(i) {
                    if (i < 10) {
                        i = "0" + i;
                    }
                    return i;
                }
            }
            //In dữ liệu
            var myApp = new function () {
                this.printTable = function () {
                    var tab = document.getElementById('sampleTable');
                    var win = window.open('', '', 'height=700,width=700');
                    win.document.write(tab.outerHTML);
                    win.document.close();
                    win.print();
                }
            }
        </script>
        <script>
        function editOrder(orderDeitalID, name, location, total, comment) {

            document.getElementById('orderID').value = orderDeitalID;

            document.getElementById('name').value = name;

            document.getElementById('location').value = location;

            document.getElementById('total').value = total;

            document.getElementById('comment').value = comment;
        }

        function deleteOrder(orderDeitalID) {
            document.getElementById('orderDetailID').value = orderDeitalID;

        }
        
       
        
        function chanageStatus(btn,userName, eid, s) {
            var text = btn.textContent;//get text cua button(chu trong btn)
            btn.parentElement.innerHTML = text;//cho td = text cua minh la duoc
            //su ajax toi con servlet change
            $.ajax({
                url: '/myProject_2/orderManagement?action=changeSatus',
                type: 'POST',
                data: {//truyen id va status
                    orderDetailID: eid,
                    status: s,
                    userName : userName
                },

                success: function (response) {
                    //do something
                    alert('update status sucessfully');
                }

            });

        }

    </script>
    </body>

</html>
