var myApp = angular.module("oaizerDB", []);

var parameters = new Object()
parameters.connectionName = "prova";
parameters.login = "dnet";
parameters.password = "dnetPwd";
parameters.database = "dnet_openaireplus"
parameters.hostname = "localhost";


function reloadGraph(ascope, connectionName) {


    $.post("./oaizerserver/connection/loadGraph.do",
        { "connectionName": connectionName },
        function (data) {

            data = $.parseJSON(data);
            ascope.graph = createExternGraph(data);
            ascope.graph.status = ascope.graph.states[0]
        }, "text");
}


function createExternGraph(data) {

    var g = new Graph()
    if (data.entities == null)
        return;
    for (i = 0; i < data.entities.length; i++) {
        var x = $.parseJSON(data.entities[i].positionX) + 100;
        var y = $.parseJSON(data.entities[i].positionY);

        g.addNode(data.entities[i].name, $("#container"), x + "px", y + "px", "window")
    }


    for (i = 0; i < data.relations.length; i++) {
        var relations = $.parseJSON(data.relations[i].relations);

        console.log(relations[0])
        console.log(relations[1])
        g.addConnection(relations[0], relations[1], data.relations[i].name);
    }

    g.status = g.states[2]
    g.toJSPlumbe();
    return g;

}


myApp.config(
    function ($routeProvider, $locationProvider) {


        $routeProvider.
            when('/', {
                templateUrl: './partials/hello.html'
            }).
            when('/NewConnection', {
                templateUrl: './partials/connection.html',
                controller: "OAIzerNewConnection"}).
            when('/ER-View/:id', {
                templateUrl: './partials/erview.html',
                controller: "OAIzerErView"}).
            when('/View/:id', {
                templateUrl: './partials/view.html',
                controller: "OAIzerView"}).
            otherwise({redirectTo: '/'});


    });
myApp.controller("OAIzerView", function ($scope, $http) {
    $scope.graph = null;
    $scope.selectedConnection = ""
    $scope.connections = []


    $scope.createGraph = function () {


        $.post("./oaizerserver/connection/loadGraph.do",
            { "connectionName": $scope.selectedConnection },
            function (data) {
                data = $.parseJSON(data);
                $scope.graph = createExternGraph(data);
                $scope.graph.status = $scope.graph.states[1]
                $scope.selectRoot()
            }, "text");
    }


    $scope.exportView=function(){
        console.log($scope.graph.view.followedRel)
        $.post("./oaizerserver/connection/exportView.do",
            { "connectionName": $scope.selectedConnection,
               "view":JSON.stringify($scope.graph.view.followedRel)},
            function (data) {

            }, "text");

    }

    $scope.selectRoot = function () {


        alert("Double click to set an entity as root of the view, then click on the green connection to follow")


    }

    $scope.retreiveListOfConnection = function () {
        $.get("./oaizerserver/connection/connectionList.do",

            function (data) {
                $scope.$apply($scope.connections = $.parseJSON(data));
            }, "text");
    }
    $("#myModal").modal()
    $scope.retreiveListOfConnection();

    //$scope.createGraph()
    //$scope.selectRoot()


});

myApp.controller("OAIzerErView", function ($scope, $http) {

    $scope.selectedConnection = ""
    $scope.connections = []

    $scope.retreiveListOfConnection = function () {
        $.get("./oaizerserver/connection/connectionList.do",

            function (data) {
                $scope.$apply($scope.connections = $.parseJSON(data));
            }, "text");
    }



    $scope.loadGraph=function()
    {

        $scope.retreiveListOfConnection();
        $scope.graph = null;
        reloadGraph($scope,$scope.selectedConnection);

    }



    $scope.valueButton = "Re-generate ER"
    //reloadGraph($scope);
    $("#myModal").modal()
    $scope.retreiveListOfConnection();


});

myApp.controller("OAIzerNewConnection", function ($scope, $location, $http) {


    $scope.footerVisible = true;

    if ($scope.mLogged == false) {
        $location.path("/")
    }

    $scope.selectAllEntities = function (items) {
        for (var i = 0, j = items.length; i < j; i++) {
            for (var f = 0, q = $scope.entities.length; f < q; f++) {
                if ($scope.entities[f].name == items[i].name) {
                    $scope.selectedItems.push($scope.entities[f])
                    var entities = $scope.entities;
                    entities.splice(f, 1);
                    break;
                }
            }
            ;
        }
        ;
    }


    $scope.unSelectAllEntities = function (items) {
        for (var i = 0, j = items.length; i < j; i++) {
            for (var f = 0, q = $scope.selectedItems.length; f < q; f++) {
                if ($scope.selectedItems[f].name == items[i].name) {
                    $scope.entities.push($scope.selectedItems[f])
                    var selectedItems = $scope.selectedItems;
                    selectedItems.splice(f, 1);
                    break;
                }
            }
            ;
        }
        ;
    }

    $scope.tryconnection = function () {
        var myparameters = new Object();

        myparameters.connectionName = $scope.connName;
        myparameters.login = $scope.connUsr;
        myparameters.password = $scope.connPasswrd;
        myparameters.database = $scope.connDb;
        myparameters.hostname = $scope.connHost;
        $scope.saved = true
        $scope.entities = []
        $scope.selectedItems = [];


        jQuery.post("./oaizerserver/connection/connectDB.do",
            { "parameters": JSON.stringify(myparameters) },
            function (data) {
                data = $.parseJSON(data);
                $scope.$apply($scope.entities = [])
                $scope.$apply($scope.selectedItems = [])
                if (data != null) {
                    for (i = 0; i < data.entities.length; i++) {
                        $scope.$apply($scope.entities.push({"id": data.entities[i].name, "name": data.entities[i].name}))
                    }
                    $scope.$apply($scope.filterShow = true)
                }
                else {
                    $scope.$apply($scope.saved = false)
                    $('#myModal').modal();
                    $scope.resetConnectionValues()
                }
                console.log($scope.entities)
            }, "text");


    }

    $scope.resetConnectionValues = function () {
        $scope.connName = "";
        $scope.connUsr = "";
        $scope.connPasswrd = "";
        $scope.connDb = "";
        $scope.connHost = "";
        $scope.filterShow = false
    }

    $scope.saveConnection = function () {


        var myparameters = new Object();

        myparameters.connectionName = $scope.connName;
        myparameters.login = $scope.connUsr;
        myparameters.password = $scope.connPasswrd;
        myparameters.database = $scope.connDb;
        myparameters.hostname = $scope.connHost;

        var myFilter = []
        for (i = 0; i < $scope.selectedItems.length; i++) {
            var item = $scope.selectedItems[i]
            myFilter.push(item.id)
        }
        if (myFilter.length > 0) {
            $.post("./oaizerserver/connection/saveConnection.do",
                { "connection": JSON.stringify(myparameters),
                    "filter": JSON.stringify(myFilter)},
                function (data) {

                    $scope.$apply($scope.resetConnectionValues());
                    $scope.$apply($scope.saved = true)
                    $('#myModal').modal();
                }, "text");
        }
        else{
            alert("You have to select at least on entity")
        }
    }

    $scope.addIndex = function (item) {
        var idx = -1
        for (var i = 0, j = $scope.entities.length; i < j; i++) {
            if ($scope.entities[i].name == item.name) {
                idx = i
                break;
            }

        }
        $scope.selectedItems.push($scope.entities[idx])
        var entities = $scope.entities;
        entities.splice(idx, 1);

    }


    $scope.removeIndex = function (item) {
        var idx = -1
        for (var i = 0, j = $scope.selectedItems.length; i < j; i++) {
            if ($scope.selectedItems[i].name == item.name) {
                idx = i
                break;
            }

        }
        ;
        $scope.entities.push($scope.selectedItems[idx])
        var selectedItem = $scope.selectedItems;
        selectedItem.splice(idx, 1);

    }

    $scope.filterShow = false;
    $scope.connName = "test";
    $scope.connUsr = "dnet";
    $scope.connPasswrd = "dnetPwd";
    $scope.connDb = "oaizerdb";
    $scope.connHost = "localhost";

    $scope.entities = [
        {"id": "suca", "name": "mela"}
    ];
    for (var i = 0, j = $scope.entities.length; i < j; i++) {
        $scope.entities[i].order = i;
    }
    ;

    $scope.selectedItems = [];


});


myApp.controller("OAIzerMainCtrl", function ($scope, $location, $http) {

    $scope.login = function () {

        jQuery.post("./oaizerserver/connection/connect.do",
            { "user": $scope.muser  },
            function (data) {
                data = $.parseJSON(data);
                if (data.logged == "yes") {
                    $scope.$apply($scope.mLogged = true)
                    $scope.$apply($scope.muser = data.user)
                }
                else
                    $scope.$apply($scope.mLogged = false);
            }, "text");
    }

    $scope.logout = function () {
        $scope.muser = ""
        $scope.mpasswd = ""
        $scope.mLogged = false;
        $location.path("/")

    }
    $scope.muser = "user"
    $scope.mpasswd = ""
    $scope.mLogged = true;


});
