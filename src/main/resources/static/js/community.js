function post(){
    var postId=$("#post_id").val();
    var content=$("#comment_content").val();
    var commentator=$("#commentator").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": postId,
            "content": content,
            "type": 1,
            "commentator": commentator
        }),
        success: function(){
            window.location.reload();
        },
        dataType: "json"
    });
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
        //展开二级评论
        comments.addClass("in");
        //标记二级评论状态
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
}