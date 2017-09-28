<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : AreaCalculator.jsp
    Modified on : Aug 30, 2017
    Author      : Joseph T. Parsons (modified from rfoy)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area Calculator</title>
        <link rel="stylesheet" href="css/calculator.css" type="text/css"/>
    </head>
    <body>
        <h1>Area Calculator</h1>
        <c:if test="${!empty errMsgs}">
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
        <form action="calculateArea">
            <label>Height:</label>
            <input type="text" name="height" value="${param.height}" size="3" />
            <label>Width:</label>
            <input type="text" name="width" value="${param.width}" size="3"/>
            <input type="submit" value="Calculate Area" name="calculateBtn" class="calcBtn"/>
        </form>
        <c:if test="${!empty areaCalculator}">
            <div class="areaResults">
                <label>Height:</label>
                <div class="heightVal">
                    ${areaCalculator.height}
                </div>
                <label>Width</label>
                <div class="widthVal">${areaCalculator.width}</div>
                <label>Area:</label>
                <div class="areaVal">
                    ${areaCalculator.area}
                </div>
            </c:if>

    </body>
</html>
