<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document    : Product.jsp
    Modified on : October 28, 2017
    Author      : Joseph T. Parsons
    Description : This displays a form that lists a product's information and, depending on context, allows them to add, edit, or delete that product.
--%>
          
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- Define the source of the product information based on the action,
  -- as well as text describing the action. -->
<c:choose>
    <c:when test='${param.action == "add"}'>
        <c:set var="actionText" value="Add New Product"/>
    </c:when>

    <c:when test='${param.action == "edit"}'>
        <!-- Disable the form if no product available. -->
        <c:if test="${empty product}">
            <c:set var="disableForm" value="true" />
        </c:if>
        
        <c:set var="actionText" value="Edit Product"/>
    </c:when>

    <c:when test='${param.action == "delete"}'>
        <!-- Disable the form if no product available. -->
        <c:if test="${empty product}">
            <c:set var="disableForm" value="true" />
        </c:if>
        
        <c:set var="actionText" value="Delete Product"/>
    </c:when>
    
    <c:otherwise>
        <c:set var="disableForm" value="true" />
        <c:set var="actionText" value="Unknown Action"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Manager: ${actionText}</title>
        <link rel="stylesheet" href="css/productList.css" type="text/css"/>
        
        <!-- Used for basic bootstrap styling. If not available, everything will
          -- still be functional, but ugly. -->
        <link
            rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
            integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb"
            crossorigin="anonymous">
    </head>
    <body>
        <!-- Display the form, with fields populated based on previous failed
          -- submission (if adding), or with current information, if editing
          -- or deleting. -->

        <!-- (Note: Following a REST-like convention, the action and, if possible,
          -- code should be stored in GET parameters. To keep things simple, I am
          -- only storing the action in GET here, but we do accept the action from GET.) -->
        <form action="<c:url value="./product">
                        <c:param name="action" value="${param.action}" />
                     </c:url>" method="post">
            <div class="card">

                <h1 class="card-header">${actionText}</h1>

                <div class="card-content p-3">
                    <!-- List all of the errors encountered, if applicable. -->
                    <c:if test="${!empty errMsgs}">
                        <h2>Errors</h2>

                        <div class="errMsg text-danger">
                            <ul>
                                <c:forEach var="errMsg" items="${errMsgs}">
                                    <li>${errMsg}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                        
                    <div class="input-group p-2">
                        <span class="input-group-addon">Code</span>
                        <!-- When editing or deleting, this field is made read-only (as
                          -- it identifies the item being edited/deleted.) Manipulation
                          -- of the field is possible on older browsers (which don't
                          -- support the attribute), and could be prevented by copying
                          -- the named field as a hidden input. -->
                        <input
                            type="text"
                            class="form-control"
                            name="code"
                            value="${!empty param.code ? param.code : product.code}"
                            size="30"
                            <c:if test='${param.action != "delete"}'>
                                required
                            </c:if>
                            <c:if test='${param.action == "delete" || param.action == "edit"}'>
                                readonly="readonly"
                            </c:if>
                            />
                    </div>
                            
                    <div class="input-group px-2 pb-2">
                        <span class="input-group-addon">Description</span>
                        <!-- When deleting, this field is omitted from the form submission,
                            -- and made read-only. -->
                        <input
                            type="text"
                            class="form-control"
                            <%-- A user could, in theory, change the description in the URL,
                              -- but it wouldn't really cause any problems. --%>
                            value="${!empty param.description ? param.description : product.description}"
                            size="50"
                            <c:if test='${param.action != "delete"}'>
                                name="description"
                                required
                            </c:if>
                            <c:if test='${param.action == "delete"}'>
                                readonly
                            </c:if>
                            />
                    </div>
                            
                    <div class="input-group px-2 pb-2">
                        <span class="input-group-addon">Price</span>
                        
                        <!-- When deleting, this field is omitted from the form submission,
                          -- and made read-only. -->
                        <input
                            type="text"
                            class="form-control"
                            <%-- A user could, in theory, change the price in the URL,
                              -- but it wouldn't really cause any problems. --%>
                            value="${!empty param.price ? param.price : product.price}"
                            size="30"
                            <c:if test='${param.action != "delete"}'>
                                name="price"
                                required
                                pattern="^\d+(\.\d+|)$"
                            </c:if>
                            <c:if test='${param.action == "delete"}'>
                                readonly
                            </c:if>
                            />
                    </div>
                            
                    <div class="input-group px-2 pb-2">
                        <span class="input-group-addon">Release Date<br /><small>(yyyy-mm-dd)</small></span>

                        <!-- When deleting, this field is omitted from the form submission,
                          -- and made read-only. -->
                        <input
                            type="text"
                            class="form-control"
                            <%-- A user could, in theory, change the release date in the URL, but it wouldn't
                              -- really cause any problems. --%>
                            value="${!empty param.releaseDate ? param.releaseDate : product.releaseDate}"
                            size="30"
                            <c:if test='${param.action != "delete"}'>
                                name="releaseDate"
                            </c:if>
                            <c:if test='${param.action == "delete"}'>
                                readonly
                            </c:if>
                            />
                    </div>

                    <!-- Indicate that a form submission event has occured,
                      -- and that the servlet should process the submission. -->
                    <input type="hidden" name="formSubmitted" />
                </div>

                <div class="card-footer text-center">                
                    <input
                        type="submit"
                        value="${actionText}"
                        class="btn btn-lg btn-success"
                        <c:if test='${disableForm == true}'>disabled="disabled"</c:if>
                        />

                    <%-- Easiest way of doing cancel would be to send the user back to
                      -- the catalog page. In some cases, however, it may be better to
                      -- go back a page in the browser history, since the user can
                      -- come from any page. --%>
                    <a href="./catalog" class="btn btn-lg btn-danger">Cancel</a>

                    <!-- Include button to reset the form, unless action is delete, in
                      -- which case it is not applicable. -->
                    <c:if test='${param.action != "delete"}'>
                        <input
                            type="reset"
                            value="Reset"
                            class="btn btn-lg btn-secondary"    />
                    </c:if>
                </div>
            </div>        
        </form>
    </body>
</html>
