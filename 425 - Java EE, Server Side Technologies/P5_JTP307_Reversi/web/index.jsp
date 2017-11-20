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
        
        <!-- Used for basic bootstrap styling. If not available, everything will
          -- still be functional, but ugly. -->
        <link
            rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
            integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
            crossorigin="anonymous">
    </head>
    <body class="card">
        <h1 class="card-header">Index</h1>
        
        <div class="card-body">
            <ul>
                <li><a href="./Reversi">Reversi</a></li>
            </ul>
        </div>
    </body>
</html>