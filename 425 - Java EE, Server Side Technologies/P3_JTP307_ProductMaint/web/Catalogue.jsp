<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : Product.jsp
    Modified on : October 28, 2017
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
        <link
            rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
            integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
            crossorigin="anonymous">
    </head>
    <body class="card">
        <h1 class="card-header">Product List</h1>
        
        <div class="card-body">
            <!-- Consume any catalog notification placed in the user session. -->
            <c:if test='${!empty sessionScope.catalogueNotification}'>
                <p class="text-success">${sessionScope.catalogueNotification}</p>

                <c:set var="catalogueNotification" scope="session" value="${null}" />
            </c:if>
        
                
            <!-- Display Product List -->
            <table class="productList table table-striped">
                <!-- Don't show the headers if the catalogue is empty. -->
                <c:if test="${!empty products}">
                    <thead class="thead-light">
                        <tr>
                            <th>Code</th>
                            <th>Description</th>
                            <th>Release Date</th>
                            <th>Years Released</th>
                            <th>Price</th>
                            <th colspan="2">Actions</th>
                        </tr>
                    </thead>

                    <tbody>
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

                                <td>
                                    <c:if test='${!empty product.price}'>\$${product.price}</c:if>
                                </td>

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
                    </tbody>
                </c:if>
            </table>
        </div>
        
        <div class="card-footer text-center">            
            <!-- Add Product Form
              -- The action is intentionally displayed in the request parameters,
              -- to make the action obvious from the URL alone. -->
            <form action="<c:url value="./product" />" method="get">
                <input class="btn btn-lg btn-primary" type="submit" value="Add Product" />
                <input type="hidden" name="action" value="add" />
            </form>
        </div>
    </body>
</html>