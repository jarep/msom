    $(document).ready(function(){
        var addr = document.location.href;
        if (addr.includes("simulate")) {
            setInterval(function(){ 
                $.ajax({
                   type: "GET",
                   url: addr.replace("simulate", "refresh"),
                   dataType : "html",
                   success : function(data) {
                       $(".simulation-container").html(data);
                   }
                });
            }, 10000);
        }
    });
