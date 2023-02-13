<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <div class="mb-3">
                <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
                <button id="btn-delete" class="btn btn-danger" onclick="deleteBoard(${board.id})">삭제</button>
            </div>

            <div class="mb-2">
                글 번호 : <span id="id"><i>${board.id} </i></span> 작성자 : <span><i>${board.username} </i></span>
            </div>  

            <div>
                <h3>${board.title}</h3>
            </div>
            <hr />
            <div>
                <div>${board.content}</div>
            </div>
            <hr />

            <div class="card">
                <form action="/board/${board.id}/reply" method="post">
                    <div class="card-body">
                        <textarea name="comment" id="reply-comment" class="form-control" rows="1"></textarea>
                    </div>
                    <div class="card-footer">
                        <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
                    </div>
                </form>
            </div>
            <br />
            <div class="card">
                <div class="card-header">댓글 리스트</div>
                <ul id="reply-box" class="list-group">
                    <c:forEach items="${replyDtoList}" var="reply">
                        <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                            <div>${reply.comment}</div>
                            <div class="d-flex">
                                <div class="font-italic">작성자 : ${reply.username} &nbsp;</div>
                                <c:choose>
                                    <c:when test="${principal.id==reply.userId}">
                                        <button onClick="deleteByReplyId(${reply.id})"
                                            class="badge bg-danger">삭제</button>
                                    </c:when>

                                    <c:otherwise>
                                        <button style="visibility: hidden;">삭제</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <script>
            function deleteBoard(boardId) {
                $.ajax({
                    type: "delete",
                    url: "/board/" + boardId,
                    dataType: "json",
                })
                    .done((res) => {
                        alert(res.msg);
                        location.href = "/";
                    })
                    .fail((err) => {
                        alert(err.responseJSON.msg);
                        history.back();
                    })
            }

            function deleteReply(replyId) {
                $.ajax({
                    type: "delete",
                    url: "/reply/" + replyId,
                    dataType: "json",
                })
                    .done((res) => {
                        alert(res.msg);
                        $("#reply-" + replyId).remove();
                    })
                    .fail((err) => {
                        alert(err.responseJSON.msg);
                    })
            }
        </script>

        <%@ include file="../layout/footer.jsp" %>