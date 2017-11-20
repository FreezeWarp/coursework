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
        <link rel="stylesheet" href="<c:url value='/css/productList.css' />" type="text/css"/>
        
        <!-- Used for basic bootstrap styling. If not available, everything will
          -- still be functional, but ugly. -->
        <link
            rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
            integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
            crossorigin="anonymous">
        <script
            src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>

    </head>
    <body class="card">
        <h1 class="card-header">Reversi</h1>
        
        <div class="card-body">
            <!-- Consume any catalog notification placed in the user session. -->
            <c:if test='${!empty errMsgs}'>
                    <ul>
                    <c:forEach var="errMsg" items="${errMsgs}">
                        <li class="text-danger">${errMsg}</li>
                    </c:forEach>
                </ul>
            </c:if>
        
                
            <!-- Display Reversi Board -->
            <table class="reversiBoard table table-dark table-striped table-bordered" style="table-layout: fixed">
                <tbody>
                    <c:forEach var="row" items="${sessionScope.reversiInstance.pieces}" varStatus="rowMeta">
                        <tr>
                            <c:forEach var="column" items="${row}" varStatus="columnMeta">
                                <td
                                    onmouseover="
                                        $(this).attr('data-oldClass', $('div', this).attr('class'));
                                        $('div', this).attr('class', '${sessionScope.reversiInstance.lastPiece.opposite().name().toLowerCase()}Piece')
                                    "
                                    onmouseout="$('div', this).attr('class', $(this).attr('data-oldClass'));">

                                    <a href="<c:url value='/Reversi/Move'>
                                           <c:param name='yPos' value='${rowMeta.index}' />
                                           <c:param name='xPos' value='${columnMeta.index}' />
                                       </c:url>" style="display: block">

                                            <div class="${column.name().toLowerCase()}Piece"></div>
                                    </a>
                                    
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>