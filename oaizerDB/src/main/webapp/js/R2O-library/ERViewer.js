function Node()
{
	this.name="";
	this.jsPlumbeNode=null;	
	
}

function NodeView()
{
	this.graphNode=null;
	this.children=[];
	this.father=null;	
}


function ConnectionView()
{
    this.from=null;
    this.to= null;
    this.label=null
}

NodeView.prototype.debug=function()
{
	console.log("NODE =",this.graphNode.name)
	for(var i=0,j=this.children.length; i<j; i++)
	{
	  this.children[i].debug();
	};
}

function View()
{
	this.root=null;	
	this.toVisit=[];
	
}

View.prototype.Visit= function()
{
	this.root.debug();
}

View.prototype.isConnectionVisited=function()
{
    
}

View.prototype.isConnectionIntoVisit=function(from, to)
{
    
}

View.prototype.addRoot= function(myNode)
{
    if (this.root== null)
    {
        console.log("STO aggiungendo una root")
        this.root=myNode;
        this.VisitNode(myNode);
        return true;    
    }
    return false;	
}

View.prototype.VisitNode=function(myNode)
{
    var myarray=jsPlumb.getConnections({source:myNode.name});
    for(var i=0,j=myarray.length; i<j; i++){
      console.log("Source ",myarray[i].sourceId);
      console.log("target ",myarray[i].targetId);     
    };
    myarray=jsPlumb.getConnections({target:myNode.name});
    for(var i=0,j=myarray.length; i<j; i++){
      console.log("Source ",myarray[i].sourceId);
      console.log("target ",myarray[i].targetId);      
    };
    
    
    
    
    
}



function Graph()
{
	this.nodes=[];
	this.view= new View();

	this.stateMachineConnector = {				
				connector:"StateMachine",
				paintStyle:{lineWidth:4,strokeStyle:"#056"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
				anchors:["Center", "Center"],
				overlays:[ ["Arrow", {location:1, width:60, length:22} ]]
			};
			
			
	this.stateMachineConnector2={				
				connector:"StateMachine",
				paintStyle:{lineWidth:3,strokeStyle:"#056"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
                anchors:["Center", "Center"],
				overlays:[ ["Arrow", {location:1, width:30, length:22} ]],
                labelStyle:{
                    cssClass:"component"
                }
			};
			
			
	this.graphConnection={	
				connector:"StateMachine",		
				paintStyle:{lineWidth:3,strokeStyle:"#056"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
				anchors:["Center", "Center"]
				
			};
	this.connections=[];
			
}

Graph.prototype.addNode=function(nodename, container, top, left)
{
	var contHTML=container.html();	
	container.html(contHTML+"<div class=\"window\" id=\""+nodename+"\"><div class=\"inside\"> "+nodename+"</div></div>")
	$("#"+nodename).css({ "position": "absolute", "top": top, "left": left });
	
	tmp= new Node();
	tmp.name=nodename;
	this.nodes.push(tmp);
	
}

Graph.prototype.existConnection=function (from, to)
{
	for(var i=0,j=this.connections.length; i<j; i++){
	  if(this.connections[i][0].name==from)
	  	if (this.connections[i][1].name==to)
	  		return this.connections[i];
	  if(this.connections[i][1].name==from)
	  	if (this.connections[i][0].name==to)
	  		return this.connections[i];	  
	};
	return null;
}

Graph.prototype.addConnection=function(from, to, label)
{
	var nodeFrom=null;
	var nodeTo=null;
	for(var i=0,j=this.nodes.length; i<j; i++){
	  if(this.nodes[i].name==from)
	  	nodeFrom=this.nodes[i];
	  if(this.nodes[i].name==to)
	  {
	  	nodeTo=this.nodes[i];
	  }
	};
	if ((nodeFrom==null ) || (nodeTo==null))
		{
			alert("ERRORE un nodo non esiste");	
		}
		
	else
		{
			var Nconnection=this.existConnection(from,to)
			if (Nconnection==null)
			{
				this.connections.push([nodeFrom, nodeTo, label]);
			}			
		}	
}


Graph.prototype.getNodeByName=function(name)
{
	for(var i=0,j= this.nodes.length; i<j; i++){
	  if (this.nodes[i].name==name)
	  return this.nodes[i];
	};
	return null;
}



Graph.prototype.convertToRelation=function(node)
{
    var node=this.getNodeByName(node.id);
    var conns = jsPlumb.getConnections();
    var connectedNodes=[]
    for (var i = 0; i < conns.length; i++){
        var sourceId=conns[i].sourceId
        var targetId=conns[i].targetId


        console.log(sourceId+"<--->"+node.name)

        if ((sourceId== node.name) || targetId==node.name)
        {
            if (sourceId== node.name)
                connectedNodes.push(targetId);
            else
                connectedNodes.push(sourceId);
            jsPlumb.detach(conns[i]);
            this.stateMachineConnector2.label=""
            jsPlumb.connect({source:sourceId, target:targetId}, this.stateMachineConnector2);

            $("#"+node.name).attr("class", "diamond");
            $("#"+node.name).html("<div class=\"diamond-inner\">"+node.name+"</div>")


        }
    }

    //jsPlumb.detachAllConnections(node.jsPlumbeNode)
    //console.log(node);
}

Graph.prototype.toJSPlumbe= function()
{
	var dynamicAnchors = [ [ 0.2, 0, 0, -1 ],  [ 1, 0.2, 1, 0 ], 
			   [ 0.8, 1, 0, 1 ], [ 0, 0.8, -1, 0 ] ];
	jsPlumb.importDefaults({
				DragOptions : { cursor: 'pointer', zIndex:3000 },
				PaintStyle : { strokeStyle:'#666' },
				EndpointStyle : { width:1, height:1, strokeStyle:'#666' },
                connector:[ "StateMachine", { curviness:20 } ],
				Endpoint : "Rectangle",
				anchors:dynamicAnchors
			});
	
	for(var i=0,j=this.nodes.length; i<j; i++){
	  	var endpoint=jsPlumb.addEndpoint(this.nodes[i].name);
	  	var that =this;
	  	$("#" +this.nodes[i].name ).bind('dblclick', function(e, ui) {
				var node=that.getNodeByName(this.id);				
				console.log(node.name);
                that.convertToRelation(this);
			});  	
	  	this.nodes[i].jsPlumbeNode=tmp;
	  	jsPlumb.draggable(this.nodes[i].name);	  	
	};	
	
	jsPlumb.Defaults.Endpoints = [ [ "Dot", 100 ], [ "Dot", 1 ] ];
	var that=this;
	
	for(var i=0,j=this.connections.length; i<j; i++){
        this.stateMachineConnector2.label=this.connections[i][2]

		jsPlumb.connect({source:this.connections[i][0].name, target:this.connections[i][1].name}, this.stateMachineConnector2).bind("click",
		function(conn) {
			console.log("you clicked on ", conn);
			
		});
	}		
}


Graph.prototype.deleteGraph=function(){
    for(var i=0,j=this.nodes.length; i<j; i++){
        jsPlumb.detachAllConnections(this.nodes[i].jsPlumbeNode);
    }
}


