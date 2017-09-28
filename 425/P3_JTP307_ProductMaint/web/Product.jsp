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
            
        <c:choose>
            <c:when test='${param.action == "add" || param.action == "edit" || param.action == "delete"}'>
                <c:if test='${param.action == "add"}'>
                    <c:set var="product" value="${param}"/>
                    <c:set var="actionText" value="Add New Product"/>
                </c:if>
                
                <c:if test='${param.action == "edit"}'>
                    <c:set var="actionText" value="Edit Product"/>
                </c:if>
                
                <c:if test='${param.action == "delete"}'>
                    <c:set var="actionText" value="Delete Product"/>
                </c:if>
                
                <h2>${actionText}</h2>
                <form action="./catalog?action=${param.action}" method="post">
                    <table cellpadding="10" border="1">
                        <tr>
                            <th><label>Code:</label></td>
                            <td><input
                                    type="text"
                                    name="code"
                                    value="${product.code}"
                                    size="30"
                                    ${param.action == "edit" || param.action == "delete" ? "readonly=readonly" : ""}
                                    /></td>
                        </tr>
                        <tr>
                            <th><label>Description:</label></td>
                            <td><input
                                    type="text"
                                    ${param.action != "delete" ? "name=\"description\"" : ""}
                                    value="${product.description}"
                                    size="50"
                                    ${param.action == "delete" ? "readonly=readonly" : ""}
                                    /></td>
                        </tr>
                        <tr>
                            <th><label>Price:</label></td>
                            <td><input
                                    type="text"
                                    ${param.action != "delete" ? "name=\"price\"" : ""}
                                    value="${product.price}"
                                    size="30"
                                    ${param.action == "delete" ? "readonly=readonly" : ""}
                                    /></td>
                        </tr>
                        <tr>
                            <th><label>Release Date<br />(yyyy-mm-dd):</label></td>
                            <td><input
                                    type="text"
                                    ${param.action != "delete" ? "name=\"releaseDate\"" : ""}
                                    value="${product.releaseDate}"
                                    size="30"
                                    ${param.action == "delete" ? "readonly=readonly" : ""}
                                    /></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="${actionText}" />
                                <input
                                    type="button"
                                    onclick="window.location='./catalog'"
                                    value="Cancel"
                                    />
                                <c:if test='${param.action != "delete"}'>
                                    <input type="reset" value="Reset" />
                                </c:if>
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" name="formSubmitted" />
                </form>       
            </c:when>
            <c:otherwise>
                <h2>Products</h2>
                
                <table class="productList" border="1" cellpadding="10">
                    <c:if test="${!empty products}">
                        <tr>
                            <th>Code</th>
                            <th>Description</th>
                            <th>Release Date</th>
                            <th>Years Released</th>
                            <th>Price</th>
                            <th colspan="2">Actions</th>
                        </tr>

                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>${product.code}</td>
                                <td>${product.description}</td>
                                <td>${product.releaseDate}</td>
                                <td>${product.yearsReleased}</td>
                                <td>${product.price}</td>
                                <td><a href="./catalog?action=edit&code=${product.code}">Edit</a></td>
                                <td><a href="./catalog?action=delete&code=${product.code}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <tr>
                        <td colspan="7">
                            <form action="./catalog" method="get" style="zoom: 1.5;">
                                <input type="submit" value="Add Product" />
                                <input type="hidden" name="action" value="add" />
                            </form>
                        </td>
                    </tr>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
