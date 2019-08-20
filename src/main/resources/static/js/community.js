function post(){
    var questionId=$("#question_id").val();
    var content=$("#comment_content").val();
    $.ajax({
       type:"POST",
        url:"/comment",
        data:JSON.stringify({
           "parentId":questionId,
            "content":content,
            "type":1
        }),
        dataType:"json",
        contentType:"application/json",
        success:function(result){
           if(result.code==200){
               $("#comment_section").hide();
           }else{
               alert(result.message);
           }

        }
    });

}