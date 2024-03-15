<%--
  Created by IntelliJ IDEA.
  User: Garnik-PC
  Date: 16.03.2024
  Time: 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Bill from Order ID</h1>
    <div>Order ID - ${billing.order.orderId}</div>
    <div>Order Time - ${billing.order.orderTime}</div>
    <div>Billing ID - ${billing.billId}</div>
    <div>Service fee -  ${billing.serviceFee}</div>
    <div>Service tax - ${billing.tax}</div>
    <div>Service tip - ${billing.tip}</div>
    <div>Total - ${billing.total}</div>
</body>
</html>
