function Node() {
    this.name = "";
    this.jsPlumbeNode = null;
    this.convertedToRelation = false

}


function Connections() {
    this.from = null;
    this.to = null;
    this.label = null
}


function NodeView() {
    this.nodeEr = null
    this.children = []


    this.reachNode = function (from, to) {
        if (this.children.length == 0)
            return null
        for (i = 0; i < this.children.length; i++) {
            var currentNode = this.children[i].nodeEr
            if ((currentNode.name == from) || (currentNode.name == to))
                return currentNode
        }
        for (i = 0; i < this.children.length; i++) {
            var currentNode = this.children[i]
            var out = currentNode.reachNode(node)
            if (out != null)
                return out
        }
        return null
    }


}


function View() {
    this.root = null
    this.graph = null
    this.possibleRelation = []
    this.visitedNode = []
    this.followedRel = []

    this.correctRelation = function (from, to) {
        if (this.possibleRelation.length == 0)
            return false
        for (i = 0; i < this.possibleRelation.length; i++) {
            var currentRel = this.possibleRelation[i]
            if (((currentRel[0] == from) && (currentRel[1] == to)) || ((currentRel[0] == to) && (currentRel[1] == from))) {
                this.possibleRelation.splice(i, 1)
                return true
            }

        }
        return false
    }


    this.followNode = function (nodename, label) {
        var node = this.graph.getNodeByName(nodename);
        var conns = jsPlumb.getConnections();
        var connectedNodes = []
        for (var i = 0; i < conns.length; i++) {
            var sourceId = conns[i].sourceId
            var targetId = conns[i].targetId

            if ((sourceId == node.name) || targetId == node.name) {
                connectedNodes.push([sourceId, targetId, conns[i]])
            }
        }

        for (var i = 0; i < connectedNodes.length; i++) {
            var from = connectedNodes[i][0];
            var to = connectedNodes[i][1]
            if (this.canAdd(from, to)) {

                var pstyle = {lineWidth: 5, strokeStyle: "#00FF00", dashstyle: "4 2"}
                connectedNodes[i][2].setPaintStyle(pstyle)
                this.possibleRelation.push([from, to]);

            }
        }
    }


    this.isNodeVisited = function (node) {
        if (this.root.nodeEr.name == node)
            return true;

        for (i = 0; i < this.visitedNode.length; i++) {
            var currentNode = this.visitedNode[i]
            if (currentNode == node)
                return true
        }
        return false
    }


    this.canAdd = function (from, to) {
        for (i = 0; i < this.possibleRelation.length; i++) {
            var currentRel = this.possibleRelation[i]
            if (((currentRel[0] == from) && (currentRel[1] == to)) || ((currentRel[0] == to) && (currentRel[1] == from))) {
                return false
            }

        }

        for (i = 0; i < this.followedRel.length; i++) {
            var currentRel = this.followedRel[i]
            if (((currentRel[0] == from) && (currentRel[1] == to)) || ((currentRel[0] == to) && (currentRel[1] == from))) {
                return false
            }

        }

        return true;
    }


}

function Graph() {
    this.nodes = []
    this.connections = []
    this.view = new View()
    this.view.graph = this
    this.enableView = false
    this.states = ["editGraph", "selectRoot", "defineView", "none"]
    this.status = this.states[3]


    this.stateMachineConnector2 = {
        connector: "StateMachine",
        paintStyle: {lineWidth: 3, strokeStyle: "#056"},
        hoverPaintStyle: {strokeStyle: "#dbe300"},
        endpoint: "Blank",
        anchors: ["Center", "Center"],
        overlays: [
            ["Arrow", {location: 1, width: 30, length: 22} ]
        ],
        labelStyle: {
            cssClass: "component label"

        }
    };

    this.addNode = function (nodename, container, top, left, entityClass) {
        var contHTML = container.html();
        container.html(contHTML + "<div class=\"" + entityClass + "\" id=\"" + nodename + "\"><div class=\"inside\"> " + nodename + "</div></div>")
        $("#" + nodename).css({ "position": "absolute", "top": top, "left": left });
        tmp = new Node();
        tmp.name = nodename;
        this.nodes.push(tmp);
    }

    this.addConnection = function (from, to, label) {
        var nodeFrom = null;
        var nodeTo = null;
        for (var i = 0, j = this.nodes.length; i < j; i++) {
            if (this.nodes[i].name == from)
                nodeFrom = this.nodes[i];
            if (this.nodes[i].name == to) {
                nodeTo = this.nodes[i];
            }
        }
        if ((nodeFrom == null ) || (nodeTo == null)) {
            console.log("ERROR a node doesn't exist :" +nodeFrom+" <---> "+nodeTo);
        }
        else {
            var newConnection = this.existConnection(from, to)
            if (newConnection == null) {
                newConnection = new Connections()
                newConnection.from = nodeFrom
                newConnection.to = nodeTo
                newConnection.label = label
                this.connections.push(newConnection);
            }
        }
    }

    this.existConnection = function (from, to) {
        for (var i = 0, j = this.connections.length; i < j; i++) {
            if (this.connections[i].from.name == from)
                if (this.connections[i].to.name == to)
                    return this.connections[i];
            if (this.connections[i].to.name == from)
                if (this.connections[i].from.name == to)
                    return this.connections[i];
        }
        return null;
    }

    this.getNodeByName = function (name) {
        for (var i = 0, j = this.nodes.length; i < j; i++) {
            if (this.nodes[i].name == name)
                return this.nodes[i];
        }
        return null;
    }

    this.convertToRelation = function (node) {
        var node = this.getNodeByName(node.id);
        var conns = jsPlumb.getConnections();
        var connectedNodes = []
        for (var i = 0; i < conns.length; i++) {
            var sourceId = conns[i].sourceId
            var targetId = conns[i].targetId

            if ((sourceId == node.name) || targetId == node.name) {
                if (sourceId == node.name)
                    connectedNodes.push(targetId);
                else
                    connectedNodes.push(sourceId);
                jsPlumb.detach(conns[i]);


                this.stateMachineConnector2.label = ""
                jsPlumb.connect({source: sourceId, target: targetId}, this.stateMachineConnector2);
                node.convertedToRelation = true
                $("#" + node.name).attr("class", "diamond");
                $("#" + node.name).html("<div class=\"diamond-inner\">" + node.name + "</div>")
            }
        }
        if (connectedNodes.length > 0)
            node.connections = connectedNodes
    }


    this.drawRelation = function (node, color) {
        var node = this.getNodeByName(node.id);
        var conns = jsPlumb.getConnections();
        var connectedNodes = []
        for (var i = 0; i < conns.length; i++) {
            var sourceId = conns[i].sourceId
            var targetId = conns[i].targetId

            if ((sourceId == node.name) || targetId == node.name) {

                var paintStyle = conns[i].getPaintStyle()
                paintStyle.strokeStyle = color
                //"#dbe300"
                conns[i].setPaintStyle(paintStyle)
            }
        }
    }

    this.toJSPlumbe = function () {
        var dynamicAnchors = [
            [ 0.2, 0, 0, -1 ],
            [ 1, 0.2, 1, 0 ],
            [ 0.8, 1, 0, 1 ],
            [ 0, 0.8, -1, 0 ]
        ];
        jsPlumb.importDefaults({
            DragOptions: { cursor: 'pointer', zIndex: 3000 },
            PaintStyle: { strokeStyle: '#666' },
            EndpointStyle: { width: 1, height: 1, strokeStyle: '#666' },
            connector: [ "StateMachine", { curviness: 20 } ],
            Endpoint: "Rectangle",
            anchors: dynamicAnchors
        });

        for (var i = 0, j = this.nodes.length; i < j; i++) {
            var endpoint = jsPlumb.addEndpoint(this.nodes[i].name);
            var that = this;
            $("#" + this.nodes[i].name).bind('dblclick',function (e, ui) {
                var node = that.getNodeByName(this.id);
                if (that.status == that.states[0]) {
                    that.convertToRelation(this);
                }
                else if (that.status == that.states[1]) {
                    if (that.view.root == null) {
                        var newRoot = new NodeView();
                        newRoot.nodeEr = node;
                        that.view.root = newRoot;
                        $("#" + node.name).attr("class", "root");
                        var conns = jsPlumb.getConnections();
                        var connectedNodes = []
                        for (var i = 0; i < conns.length; i++) {
                            var sourceId = conns[i].sourceId
                            var targetId = conns[i].targetId

                            if ((sourceId == node.name) || targetId == node.name) {
                                if (sourceId == node.name) {
                                    var pstyle = {lineWidth: 5, strokeStyle: "#00FF00", dashstyle: "4 2"}
                                    conns[i].setPaintStyle(pstyle)
                                    that.view.possibleRelation.push([node.name, targetId]);
                                }
                                else {
                                    var pstyle = {lineWidth: 5, strokeStyle: "#00FF00", dashstyle: "4 2"}
                                    conns[i].setPaintStyle(pstyle)
                                    that.view.possibleRelation.push([node.name, sourceId]);


                                }

                            }
                        }


                    }
                }

            }).bind('click', function (e, ui) {
                    var node = that.getNodeByName(this.id);
                    if (node.convertedToRelation) {
                        if (that.status == that.states[2])
                            alert("selected")
                    }

                })


            this.nodes[i].jsPlumbeNode = tmp;
            jsPlumb.draggable(this.nodes[i].name);
        }
        ;

        jsPlumb.Defaults.Endpoints = [
            [ "Dot", 100 ],
            [ "Dot", 1 ]
        ];
        var that = this;

        for (var i = 0, j = this.connections.length; i < j; i++) {
            this.stateMachineConnector2.label = this.connections[i].label
            var that = this;
            jsPlumb.connect({source: this.connections[i].from.name, target: this.connections[i].to.name}, this.stateMachineConnector2).
                bind("click",
                function (conn) {
                    if (that.status == that.states[1]) {

                        if (that.view.correctRelation(conn.sourceId, conn.targetId) == true) {
                            that.view.followedRel.push([conn.sourceId, conn.targetId,  conn.getLabel()])
                            var pstyle = {lineWidth: 5, strokeStyle: "#FF0000", dashstyle: "1 0"}
                            conn.setPaintStyle(pstyle)

                            var notVisitedNode = null
                            that.view.followNode(conn.sourceId, conn.getLabel())
                            that.view.followNode(conn.targetId, conn.getLabel())
                            /*if (that.view.isNodeVisited(conn.sourceId) == false) {
                                notVisitedNode = conn.sourceId


                            }
                            else if (that.view.isNodeVisited(conn.targetId) == false) {
                                notVisitedNode = conn.targetId
                            }
                            that.view.followNode(notVisitedNode) */

                        }

                    }


                });
        }
    }

    this.deleteGraph = function () {
        for (var i = 0, j = this.nodes.length; i < j; i++) {
            jsPlumb.detachAllConnections(this.nodes[i].jsPlumbeNode);
        }
    }

}