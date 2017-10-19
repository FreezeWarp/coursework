<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : Product.jsp
    Modified on : Sept 16, 2017
    Author      : Joseph T. Parsons
    Description : This displays the list of products in the catalog.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Bean Manager</title>
        <link rel="stylesheet" href="css/productList.css" type="text/css"/>
    </head>
    <body>
        <h1>Product List</h1>                
        <table class="productList" border="1" cellpadding="10">
            <!-- Don't show the headers if the catalogue is empty. -->
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
                        
                        <!-- Per requirements, the negative semaphore
                          -- values are not displayed. -->
                        <td>
                            <c:if test="${product.yearsReleased > -1}">
                                ${product.yearsReleased}
                            </c:if>
                        </td>
                        
                        <td>${product.price}</td>
                        
                        <!-- While there are other ways of doing this,
                          -- including the product code in the URL seems the most logical. -->
                        <td><a href="<c:url value="./product">
                                   <c:param name="action" value="edit" />
                                   <c:param name="code" value="${product.code}" />
                               </c:url>">View/Edit</a></td>
                        <td><a href="<c:url value="./product">
                                   <c:param name="action" value="delete" />
                                   <c:param name="code" value="${product.code}" />
                               </c:url>">Delete</a></td>
                    </tr>
                </c:forEach>
            </c:if>
                    
            <tr>
                <td colspan="7">
                    <!-- Add Product Form
                      -- The action is intentionally displayed in the request parameters,
                      -- to make the action obvious from the URL alone. -->
                    <form action="<c:url value="./product" />" method="get" style="zoom: 1.5;">
                        <input type="submit" value="Add Product" />
                        <input type="hidden" name="action" value="add" />
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>