var isHidden = [];
    $(document).ready(function(){
        var addr = document.location.href;
        var x = $("#isLocked").val();
        if (x === "true") {
            setInterval(function(){ 
                $.ajax({
                   type : "GET",
                   url : addr.replace(/start|simulate|stop|reset/, "refresh"),
                   dataType : "html",
                   cache : false,
                   success : function(data) {
                       $(".simulation-container").html(data);
                       checkHiddenElements();
                   }
                });
            }, 1000);
        }
    });

    function toggleDetails(self)
    {   
        $(self.parentNode).find('.tasks-list').toggle(); 
        if(isHidden.includes(self.parentElement.children[2].className))
        isHidden.splice( $.inArray(self.parentElement.children[2].className, isHidden), 1 );
        else
          isHidden.push(self.parentElement.children[2].className);
    }

     function checkHiddenElements(){
         for (var i = 0;i<isHidden.length;i++)
         {
             $("[class*='"+isHidden[i]+"']").hide();
         }
     }
     