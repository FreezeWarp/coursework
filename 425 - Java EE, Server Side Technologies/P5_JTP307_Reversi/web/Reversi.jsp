<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
    Document    : Reversi.jsp
    Modified on : December 2, 2017
    Author      : Joseph T. Parsons
    Description : This displays a Reversi game.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reversi</title>
        
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
            
        <link rel="stylesheet" href="<c:url value='/css/reversi.css' />" type="text/css"/>

    </head>
    <body>
        <!-- Display Reversi Game -->
        <div class="reversiBoardContainer" style="position: relative;">
            <div style="position: absolute; width: 100%;">

                <!-- If the game is over, display an overlay indicating as much, and prompt for a new game. -->
                <c:if test='${reversiInstance.isGameOver()}'>
                    <div style="position: absolute; background-color: rgba(255, 255, 255, .75); min-height: 100%; width: 100%; text-align: center; vertical-align: middle;">
                        <div class="jumbotron container" style="position: absolute; background: transparent; min-height: 100%; min-width: 100%; margin: 0; z-index: 2000;">
                            <h1 class="display-3" style="text-shadow: 2px 2px 10px #afafaf;">Game Over</h1>
                            <p style="text-shadow: 1px 1px 2px #9f9f9f;">
                                <c:choose>
                                    <c:when test='${reversiInstance.whitePieceCount > reversiInstance.blackPieceCount}'>White Wins, ${reversiInstance.whitePieceCount} - ${reversiInstance.blackPieceCount}</c:when>
                                    <c:when test='${reversiInstance.blackPieceCount > reversiInstance.whitePieceCount}'>Black Wins, ${reversiInstance.blackPieceCount} - ${reversiInstance.whitePieceCount}</c:when>
                                    <c:otherwise>Tie Game</c:otherwise>
                                </c:choose>
                            </p>
                            
                            <hr class="my-4" />
                            
                            <p class="lead">
                                <form method="post" action="<c:url value='/Reversi/NewGame'>
                                        <c:param name='width' value='8' />
                                        <c:param name='height' value='8' />
                                     </c:url>">
                                    <button class="btn btn-primary btn-lg" style="opacity: .85;">New Game</button>
                                </form>
                            </p>
                        </div>
                    </div>
                </c:if>

                <!-- The Reversi board itself, including score information and new game, give up, and hint buttons. -->
                <table border="1" class="reversiBoard table table-striped table-bordered" style="width: auto; margin-left: auto; margin-right: auto;">
                    
                    
                    <!-- Our score display. -->
                    <tbody class="score">
                        <tr class="table-sm">
                            <th class="table-light" colspan="${reversiInstance.width * .5}" style="text-align: center;">White Score</th>
                            <th class="table-dark" colspan="${reversiInstance.width * .5}" style="text-align: center;">Black Score</th>
                        </tr>
                        <tr class="table-sm">
                            <td class="table-light" colspan="${reversiInstance.width * .5}" style="text-align: center;">${reversiInstance.whitePieceCount}</td>
                            <td class="table-dark" colspan="${reversiInstance.width * .5}" style="text-align: center;">${reversiInstance.blackPieceCount}</td>
                        </tr>
                    </tbody>
                    
                    
                    <!-- Our main board displaying, showing all pieces and empty squares representing the board. -->
                    <tbody class="pieces">
                        <c:forEach var="row" items="${reversiInstance.pieces}" varStatus="rowMeta">
                            <tr>
                                <c:forEach var="column" items="${row}" varStatus="columnMeta">
                                    <td
                                        align="center"
                                        width="40"
                                        <c:if test='${column.name() == "BLANK"}'>
                                            onmouseover="
                                                $(this).attr('data-oldClass', $('div', this).attr('class'));
                                                $('div', this).attr('class', '${reversiInstance.getCurrentPlayer().name().toLowerCase()}Piece')
                                            "
                                            onmouseout="$('div', this).attr('class', $(this).attr('data-oldClass'));"
                                        </c:if>
                                        >

                                        <c:choose>
                                            <c:when test='${column.name() == "BLANK"}'>
                                                <!-- While bad practice, I do think the overall user experience is more effective when using anchors instead of forms. Thus, we send our move coords with GET parameters instead of POST ones. -->
                                                <a href="<c:url value='/Reversi/Move'>
                                                       <c:param name='yPos' value='${rowMeta.index}' />
                                                       <c:param name='xPos' value='${columnMeta.index}' />
                                                   </c:url>" style="display: block; overflow: hidden;">
                                                    <div class="blankPiece <c:if test='${reversiInstance.isLegalMove(reversiInstance.getCurrentPlayer(), columnMeta.index, rowMeta.index)}'>${reversiInstance.getCurrentPlayer().name().toLowerCase()}Piece hint</c:if>"></div>
                                                </a>
                                            </c:when>

                                            <c:otherwise>
                                                <div class="${column.name().toLowerCase()}Piece"></div>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                    
                    
                    <!-- Display errors; consume any error messages placed in the user session. -->
                    <c:if test='${!empty sessionScope.errMsgs}'>
                        <tbody class="errors">
                            <tr class="table-danger">
                                <td colspan="${reversiInstance.width}">
                                    <h4>Errors Encountered!</h4>
                                    <ul style="margin-bottom: 0px;">
                                        <c:forEach var="errMsg" items="${sessionScope.errMsgs}">
                                            <li>${errMsg}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </tbody>

                        <!-- Consume the error, preventing it from redisplaying. -->
                        <c:set var="errMsgs" scope="session" value="${null}" />
                    </c:if>

                        
                    <!-- Display buttons for hints, new games, and quitting. -->
                    <tbody class="utilities">
                        <tr>
                            <td colspan="${reversiInstance.width}">
                                <button onclick="
                                    $('.hint').toggleClass('hintDisplay'); $(this).text($(this).text() === 'Show Hints' ? 'Hide Hints' : 'Show Hints');
                                " class="btn btn-info form-control">Show Hints</button>
                            </td>
                        </tr>
                        
                        <tr>
                            <td colspan="${reversiInstance.width}">
                                <form action="<c:url value='/Reversi/GiveUp' />">
                                    <button class="btn btn-danger form-control">Quit Playing</button>
                                </form>
                            </td>
                        </tr>
                        
                        <form action="<c:url value='/Reversi/NewGame' />">
                            <tr>
                                <td colspan="${reversiInstance.width}">
                                    <div class="row">
                                        <div class="col-6">
                                            <label class="input-group">
                                                <span class="input-group-addon">Width</span>
                                                <input class="form-control" style="width: 3em;" name="width" value="8" />
                                            </label>
                                        </div>
                                        <div class="col-6">
                                            <label class="input-group">
                                                <span class="input-group-addon">Height</span>
                                                <input class="form-control" style="width: 3em;" name="height" value="8" />
                                            </label>
                                        </div>
                                    </div>
                                    
                                    <input type="submit" value="Start a New Game!" class="form-control btn btn-success" />
                                </td>
                            </tr>
                        </form>
                    </tbody>
                    
                    
                </table>
            </div>
        </div>
    </body>
</html>