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
                   }
                });
            }, 10000);
        }
    });
