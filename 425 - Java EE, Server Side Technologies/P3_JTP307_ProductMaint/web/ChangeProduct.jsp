<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : Product.jsp
    Modified on : Sept 16, 2017
    Author      : Joseph T. Parsons
    Description : This displays a form that lists a product's information and, depending on context, allows them to add, edit, or delete that product.
--%>
          
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- Define the source of the product information based on the action,
  -- as well as text describing the action.
  --
  -- This does not maintain the form submission data after a failed edit;
  -- that will be fixed in part B. -->
<c:choose>
    <c:when test='${param.action == "add"}'>
        <c:set var="product" value="${param}"/>
        <c:set var="actionText" value="Add New Product"/>
    </c:when>

    <c:when test='${param.action == "edit"}'>
        <c:set var="actionText" value="Edit Product"/>
    </c:when>

    <c:when test='${param.action == "delete"}'>
        <c:set var="actionText" value="Delete Product"/>
    </c:when>
    
    <c:otherwise>
        <c:set var="actionText" value="Unknown Action"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Manager: ${actionText}</title>
        <link rel="stylesheet" href="css/productList.css" type="text/css"/>
    </head>
    <body>
        
        <h1>${actionText}</h1>
        
        <!-- List all of the errors encountered, if applicable. -->
        <c:if test="${!empty errMsgs}">
            <h2>Errors</h2>
            
            <div class="errMsg">
                <ul>
                    <c:forEach var="errMsg" items="${errMsgs}">
                        <li>${errMsg}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
            
            
        <!-- Display the form, with fields populated based on previous failed
          -- submission (if adding), or with current information, if editing
          -- or deleting. -->
        
        <!-- (Note: Following a REST-like convention, the action and, if possible,
          -- code should be stored in GET parameters. To keep things simple, I am
          -- only storing the action in GET here, but we do accept the action from GET.) -->
        <form action="<c:url value="./product">
                        <c:param name="action" value="${param.action}" />
                     </c:url>" method="post">
            <table cellpadding="10" border="1">
                <tr>
                    <th><label>Code:</label></td>
                        
                    <!-- When editing or deleting, this field is made read-only (as
                      -- it identifies the item being edited/deleted.) Manipulation
                      -- of the field is possible on older browsers (which don't
                      -- support the attribute), and could be prevented by copying
                      -- the named field as a hidden input. -->
                    <td><input
                            type="text"
                            name="code"
                            value="${product.code}"
                            size="30"
                            <c:if test='${param.action == "delete" || param.action == "edit"}'>
                                readonly="readonly"
                            </c:if>
                            /></td>
                </tr>
                
                <tr>
                    <th><label>Description:</label></td>
                        
                    <!-- When deleting, this field is omitted from the form submission,
                      -- and made read-only. -->
                    <td><input
                            type="text"
                            <c:if test='${param.action != "delete"}'>name="description"</c:if>
                            value="${product.description}"
                            size="50"
                            <c:if test='${param.action == "delete"}'>readonly="readonly"</c:if>
                            /></td>
                </tr>
                
                <tr>
                    <th><label>Price:</label></td>
                        
                    <!-- When deleting, this field is omitted from the form submission,
                      -- and made read-only. -->
                    <td><input
                            type="text"
                            <c:if test='${param.action != "delete"}'>name="price"</c:if>
                            value="${product.price}"
                            size="30"
                            <c:if test='${param.action == "delete"}'>readonly="readonly"</c:if>
                            /></td>
                </tr>
                
                <tr>
                    <th><label>Release Date<br />(yyyy-mm-dd):</label></td>
                        
                    <!-- When deleting, this field is omitted from the form submission,
                      -- and made read-only. -->
                    <td><input
                            type="text"
                            <c:if test='${param.action != "delete"}'>name="releaseDate"</c:if>
                            value="${product.releaseDate}"
                            size="30"
                            <c:if test='${param.action == "delete"}'>readonly="readonly"</c:if>
                            /></td>
                </tr>
                
                <tr>
                    <td colspan="2">
                        <input
                            type="submit"
                            value="${actionText}"
                            style="zoom: 1.5;"
                            <c:if test='${disableForm == true}'>disabled="disabled"</c:if>
                            />
                        
                        <!-- Easiest way of doing cancel would be to send the user back to
                          -- the catalog page. In some cases, however, it may be better to
                          -- go back a page in the browser history. -->
                        <input
                            type="button"
                            onclick="window.location='./catalog'"
                            value="Cancel"
                            style="zoom: 1.5;"
                            />
                        
                        <!-- Include button to reset the form, unless action is delete, in
                          -- which case it is not applicable. -->
                        <c:if test='${param.action != "delete"}'>
                            <input type="reset" value="Reset" style="zoom: 1.5;" />
                        </c:if>
                    </td>
                </tr>
            </table>
                        
            <!-- Indicate that a form submission event has occured,
              -- and that the servlet should process the submission. -->
            <input type="hidden" name="formSubmitted" />
        </form>
    </body>
</html>
