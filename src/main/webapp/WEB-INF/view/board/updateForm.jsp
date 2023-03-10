<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <form>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Enter title" name="title" id="title"
                        value="${board.title}">
                </div>

                <div class="form-group">
                    <textarea class="form-control summernote" rows="5" id="content" name="content">
                    ${board.content}
                </textarea>
                </div>
            </form>
            <button type="button" class="btn btn-primary" onclick="updateBoard(${board.id})">글수정완료</button>

        </div>

        <script>
            $('.summernote').summernote({
                tabsize: 2,
                height: 400
            });
        </script>

        <script>
            function updateBoard(id) {
                let requestData = {
                    title: $("#title").val(),
                    content: $("#content").val(),
                }

                $.ajax({
                    type: "put",
                    url: "/board/" + id,
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                    data: JSON.stringify(requestData),
                    dataType: "json",
                })
                    .done((res) => {
                        alert(res.msg);
                        location.href = "/board/" + id;
                    })
                    .fail((err) => {
                        alert(err.responseJSON.msg);
                        history.back();
                    })
            }
        </script>

        <%@ include file="../layout/footer.jsp" %>