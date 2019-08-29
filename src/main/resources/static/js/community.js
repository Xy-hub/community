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
//二级评论
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
        var subCommentContainer=$("#comment-"+id);
        if(subCommentContainer.children().length!=1){
            comments.addClass("in");
            e.classList.add("active");
        }else{
            $.getJSON("/comment/"+id,function(data){
                $.each(data.data,function(index,comment){
                    var mediaLeftElement=$("<div/>",{
                       "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));
                    var mediaElement=$("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    var commentElement= $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.classList.add("active");
            });
        }

    }
}

function SelectTag(value){
    var oldTag=$("#tag").val();
    if(oldTag){
        var strings = oldTag.split(",");
        var index=strings.length;
        var flag=true;
        while(flag&&index>=0){
            if(strings[index]===value){
                flag=false;
            }
            index--;
        }
        if(flag){
            $("#tag").val(oldTag+","+value);
        }
    }else{
        $("#tag").val(value);
    }
}

function showSelectTag(){
    $("#selectTag").toggle();
}