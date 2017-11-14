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
        <link rel="stylesheet" href="css/fizzBuzz.css" type="text/css"/>
    </head>
    <body>
        <h1>Fizz Buzz Program</h1>
        
        <hr />
        <form action="./fizzbuzz">
            <label>
                n: 
                <input type="text" name="n" autofocus value="<c:out value="${param.n}" />" />
            </label>
            <input type="submit" value="Go" />
        </form>
        
        <c:if test="${!empty errMsgs}">
            <hr />
            
            <div class="errMsgMsg">Errors Encountered!</div>
            <ul class="errMsgList">
                <c:forEach var="errMsg" items="${errMsgs}">
                    <li class="errMsg">${errMsg}</li>
                </c:forEach>
            </div>
        </c:if>
        
            <c:if test="${!empty fizzBuzzResults}">
            <hr />
            
            Fizz Buzz: n = <c:out value="${param.n}" />
            <div class="fizBuzzResultContainer">
                <c:forEach var="fizzBuzzResult" items="${fizzBuzzResults}">
                    <div class="fizzBuzzResult">${fizzBuzzResult}</div>
                </c:forEach>
            </div>
        </c:if>
            
        <!--
        <c:choose>
            <c:when test="${param.n.matches('[0-9]+')}">
                <c:if test="${param.n > 0}">
                    <hr />

                    Fizz Buzz: n = <c:out value="${param.n}" />
                    <div class="fizBuzzResultContainer">
                        <c:forEach var="i" begin="1" end="${param.n}" >
                            <div class="fizzBuzzResult">
                                <c:if test="${i % 3 == 0}">Fizz</c:if>
                                <c:if test="${i % 5 == 0}">Buzz</c:if>
                                <c:if test="${i % 3 != 0 && i % 5 != 0}">${i}</c:if>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                Make sure to enter an integer.
            </c:otherwise>
        </c:choose>
        -->
    </body>
</html>