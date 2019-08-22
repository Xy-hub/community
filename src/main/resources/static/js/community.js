/**
 * 提交回复
 */
function post(){
    var questionId=$("#question_id").val();
    var content=$("#comment_content").val();
    commentTarget(questionId,1,content)

}

function commentTarget(targetId, type, content){
    if(!content){
        alert("不能回复空内容！！");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        dataType:"json",
        contentType:"application/json",
        success:function(result){
            if(result.code==200){
                window.location.reload();
            }else{
                if(result.code==2003){
                    var confirm=window.confirm(result.message);
                    if(confirm){
                        window.open("https://github.com/login/oauth/authorize?client_id=d9716f23fa8342a094f3&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else{
                    alert(result.message);
                }

            }

        }
    });
}
function comment(e){
    var commentId = e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    commentTarget(commentId,2,content);
}
/**
 * 展开二级评论
 */
function collapseComments(e){
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    if(comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active");
    }else{
        $.getJSON("/comment/"+id,function(data){
            console.log(data);
            var commentBody=$("#comment-body-"+id);
            var items=[];
            $.each(data.data,function(){
               var c= $("<div/>",{
                   "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12",
                   html:comment.content
               });
               items.push(c)
            });
            $("<div/>",{
                "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comment",
                "id":"comment-"+id,
                html:items.join("")
            }).appendTo(commentBody);

            comments.addClass("in");
            e.classList.add("active");
        });
    }
}