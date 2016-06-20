var isHidden = [];
var intervalValue = 1000;
var intval = "";
var plumbInstance;
$(document).ready(function () {
    funfunfun();
    refreshTaskList();
});

(window).resize(function(){
    plumbInstance.repaintEverything();
});

function toggleDetails(self)
{
    $(self.parentNode).find('.tasks-list').toggle();
    if (isHidden.indexOf(self.parentElement.children[2].className) !== -1) {
        self.innerHTML = ' - hide details - ';
        isHidden.splice($.inArray(self.parentElement.children[2].className, isHidden), 1);
    }
    else {
        isHidden.push(self.parentElement.children[2].className);
        self.innerHTML = ' - show details - ';
    }
    plumbInstance.repaintEverything(); 
}
function checkHiddenElements() {
    for (var i = 0; i < isHidden.length; i++)
    {
        $("[class*='" + isHidden[i] + "']").parent().children()[1].innerHTML = 'show details';
        $("[class*='" + isHidden[i] + "']").hide();
    }
}
function refreshView() {
    var addr = document.location.href;
    var x = $("#isLocked").val();
    if (x === "true") {
        $.ajax({
            type: "GET",
            url: addr.replace(/start|simulate|stop|reset/, "refresh"),
            dataType: "html",
            cache: false,
            success: function (data) {
                $(".simulation-container").html(data);
                checkHiddenElements();
                funfunfun();
            }
        });

    }
}
function setIntervalValue() {
    intervalValue = $("#intervalValueInput").val() * 1000;

    window.clearInterval(intval);
    refreshTaskList();

    refreshView();
}

function refreshTaskList() {
    var addr = document.location.href;
    var x = $("#isLocked").val();
    if (x === "true") {
        intval = setInterval(function () {
            $.ajax({
                type: "GET",
                url: addr.replace(/start|simulate|stop|reset/, "refresh"),
                dataType: "html",
                cache: false,
                success: function (data) {
                    $(".simulation-container").html(data);
                    checkHiddenElements();
                    funfunfun();
                }
            });
        }, intervalValue);
    }
}
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}



function funfunfun(){
    jsPlumb.ready(function () {
        plumbInstance = jsPlumb.getInstance({
            Anchor: ["Bottom"],
            Anchors: [null, null],
            ConnectionsDetachable: false,
            ConnectionOverlays: [],
            Connector: "Flowchart",
            Container: "jsPlumbContainer",
            DoNotThrowErrors: false,
            DragOptions: {},
            DropOptions: {},
            Endpoint: "Blank",
            EndpointOverlays: [],
            EndpointStyle: {fillStyle: "#456"},
            EndpointStyles: [{fillStyle: "#58A3D1"}, {fillStyle: "#9E4CB5"}],
            EndpointHoverStyle: null,
            EndpointHoverStyles: [null, null],
            HoverPaintStyle: null,
            LabelStyle: {color: "black"},
            LogEnabled: false,
            Overlays: [["Arrow", {width: 10, length: 10, location: 1, id: "arrow"}]],
            MaxConnections: 1,
            PaintStyle: {lineWidth: 2, strokeStyle: "#456"},
            ReattachConnections: false,
            RenderMode: "svg",
            Scope: "jsPlumb_DefaultScope"
        });

        drawPaths(plumbInstance);
    });
}

function drawPaths(plumbInstance) {
    var i = 0;

    var cPaths = document.getElementsByClassName("comingOutPath");

    for (i = 0; i < cPaths.length; i++) {

        var pathId = cPaths[i].id;
        var result = pathId.split("-");
        var nextTaskDispatcher = "TD-" + result[1];
        var colors = ['rgb(112,230,18)', 'rgb(0,0,255)', 'rgb(243,230,18)'];

        s = 15 + (i * 5);
        plumbInstance.connect({
            source: pathId,
            target: nextTaskDispatcher,
            connector: ["Flowchart", {stub: s, midpoint: 0.9, gap: 5}],
            anchors: [
                ["Bottom"],
                ["Top", "Left", "Right"]
            ]
        });
    }
}