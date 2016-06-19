var isHidden = [];
var intervalValue = 1000;
var intval = "";
    $(document).ready(function(){
      refreshTaskList();
    });

    function toggleDetails(self)
    {   
        $(self.parentNode).find('.tasks-list').toggle(); 
        if(isHidden.includes(self.parentElement.children[2].className)){
            self.innerHTML = 'hide details';
        isHidden.splice( $.inArray(self.parentElement.children[2].className, isHidden), 1 );      
    }
        else{
          isHidden.push(self.parentElement.children[2].className);
       
      }
    }

     function checkHiddenElements(){
         for (var i = 0;i<isHidden.length;i++)
         {
             $("[class*='"+isHidden[i]+"']").parent().children()[1].innerHTML = 'show details';
             $("[class*='"+isHidden[i]+"']").hide(); 
         }
     }

        function refreshView() {
            var addr = document.location.href;
        var x = $("#isLocked").val();
        if (x === "true") {
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
          
        }
   }
   
   function setIntervalValue(){
      intervalValue =  $("#intervalValueInput").val()*1000;
       
          window.clearInterval(intval);
          refreshTaskList();
      
      refreshView();
   }
   
   function refreshTaskList(){
     var addr = document.location.href;
        var x = $("#isLocked").val();
        if (x === "true") {
         intval =   setInterval(function(){ 
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
            }, intervalValue);
        }
   }