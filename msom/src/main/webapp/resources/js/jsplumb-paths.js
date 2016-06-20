jsPlumb.ready(function () {
    var plumbInstance = jsPlumb.getInstance({
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