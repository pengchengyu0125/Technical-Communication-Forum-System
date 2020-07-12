function post(){
    var postId=$("#post_id").val();
    var content=$("#comment_content").val();
    var commentator=$("#commentator").val();
    comment2target(postId, 1, content, commentator);
}

function comment2target(targetId, type, content, commentator){
    $.ajax({
            type: "POST",
            url: "/comment",
            contentType: 'application/json',
            data: JSON.stringify({
                "parentId": targetId,
                "content": content,
                "type": type,
                "commentator": commentator
            }),
            success: function(){
                window.location.reload();
            },
            dataType: "json"
        });
}

function comment(e) {
    var commentId=e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    var commentator=$("#replyCommentator").val();
    comment2target(commentId, 2, content, commentator);
}

function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    //获取展开状态
    var collapse=e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }
    else{
        $.getJSON("/comment/"+id, function(data){
            var commentBody=$("comment-body-"+id);
            var items=[];
            $.each(data.data, function(comment){
                var c=$("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html:comment.content
                });
                items.push(c);
            });
            commentBody.append($("<div/>",{
                                                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                                                    "id":"comment-"+id,
                                                    html:items.join("")
                                                }));

            //展开二级评论
            comments.addClass("in");
            //标记二级评论状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        })
    }
}