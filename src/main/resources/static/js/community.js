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