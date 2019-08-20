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