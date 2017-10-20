<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : Product.jsp
    Modified on : Sept 16, 2017
    Author      : Joseph T. Parsons (based on P1 .jsp from rfoy)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Bean Demo</title>
        <link rel="stylesheet" href="css/productList.css" type="text/css"/>
    </head>
    <body>
        <h1>Product Bean</h1>
        <c:if test="${!empty errMsgs}">
            <h2>Errors</h2>
            <div class="errMsg">
                <ul>
                    <c:forEach var="errMsg" items="${errMsgs}">
                        <li>
                            ${errMsg}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        <h2>Add New Product</h2>
        <form action="product" method="post">
            <label>Code: </label>
            <input type="text" name="code" value="${param.code}" size="10" /><br />
            <label>Description: </label>
            <input type="text" name="description" value="${param.description}" size="50"/><br />
            <label>Price: </label>
            <input type="text" name="price" value="${param.price}" size="10"/><br />
            <label>Release Date (yyyy-mm-dd): </label>
            <input type="text" name="releaseDate" value="${param.releaseDate}" size="30"/><br />
            <input type="hidden" name="doAdd" value="true" />
            <input type="submit" value="Add Product" name="showProduct" />
        </form>
            
        <h2>Current Products</h2>
        <c:if test="${!empty sessionScope.products}">
            <table class="productList">
                <tr>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Release Date</th>
                    <th>Years Released</th>
                    <th>Hash Code</th>
                    <th>String</th>
                </tr>

                <c:forEach var="product" items="${sessionScope.products}">
                    <tr>
                        <td>${product.code}</td>
                        <td>${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.releaseDate}</td>
                        <td>${product.yearsReleased}</td>
                        <td>${product.hashCode()}</td>
                        <td>${product.toString()}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
