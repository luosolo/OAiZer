function Attribute()
{
	this.name="";
	this.value="";
	this.jsPlumbeNode=null;
	this.setInfo= function(key, type)
	{
		this.name= key;
		this.value=type;
	}	
	this.getInfo= function()
	{
		return this.name+" "+this.value;
	}	
}

function Relation()
{
	this.entity=null;
	this.connection= null;
}


function Entity(name)
{
	this.name=name;
	this.attributes=new Array();
	this.relations= new Array();
	
		
	this.addRelations= function(theEntity)
	{
		s= new Relation();
		s.entity=theEntity;
		this.relations.push(s);
	}
	
	this.addAttribute=function(a)
	{
		this.attributes.push(a);
	}	
	
	this.debugAttribute=function()
	{
		for(i=0; i<this.attributes.length; i++)
		{
			alert(this.attributes[i].getInfo());
		}
	}
	
	this.debugRelations=function()
	{
		
		for(i=0; i<this.relations.length; i++)
		{
			alert(this.relations[i].entity.getInfo());
		}
	}
	
	this.getInfo= function () {		
		return "name "+this.name;	  
	}
	
}

function Graph()
{
	this.nodes=[];	
	this.addNode=function (node)
	{
		this.nodes.push(node);
	}	
	this.addNodeWithName=function(nodeName)
	{
		var tmp= new Entity();
		tmp.name=nodeName;
		this.nodes.push(tmp);
	}		
	this.createRelation= function(nodeFrom, nodeTo)
	{
		var from= this.findNode(nodeFrom);
		var to=	  this.findNode(nodeTo);
		
		if((from!=null) && (to!=null))
		{
			from.addRelations(to);
			return true;			
		}
		alert("qualcuno è null");
		return false;	
	}	
	
	
	this.findNode=function (nodeName)
	{
		for(i=0; i<this.nodes.length; i++)
		{
			if(this.nodes[i].name== nodeName)
				return this.nodes[i]
		}
		return null;
	}
	
	this.findConnection= function (nodeFrom)
	{
		
		from= this.findNode(nodeFrom);
		
		if(from==null)
			return null;
		var f=[];
		
		
		
		
	}
	
	this.generateJsPlumbe=function()
	{
		alert("non faccio niente");
		
	}
	
	
	this.debugNode=function()
	{
		for(i=0; i<this.nodes.length;i++)
		{
			alert(this.nodes[i].name);
		}
	}
			
}


function View()
	{
		
		this.graph= null;	
		this.root= null;
		
		this.toVisit=[];
		this.visitable=[];
		
		
		
		function getConnectionVisitable(nodeEntity, view)
		{
			
		}
		
		
		function setRoot(idRoot, view)
		{
			var graph= view.graph;
			var entity= graph.findNode(idRoot);
			view.toVisit.push(idRoot);
					
		}
		
		
		function changeConnection(idAttr, view)
		{
			
			setRoot("pippo", view);
			var graph= view.graph;
			
			var nodo= graph.findNode(idAttr);
			if(nodo.visited==null)
				nodo.visited= true;
			else if(nodo.visited== true)
				return;
			
        	var stateMachineConnector = {				
				connector:"StateMachine",
				paintStyle:{lineWidth:3,strokeStyle:"#006"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
				anchor:"Continuous",
				overlays:[ ["PlainArrow", {location:1, width:30, length:22} ]]
			};    
        	var targets=[];
        	$("#"+idAttr).removeClass("window");
        	$("#"+idAttr).addClass("window1");

        	var conn=jsPlumb.getConnections({"source":idAttr});
        	
        	
        	for(j=0; j<conn.length; j++)
        	{
        	
        		var connessione= conn[j];
        		var targetId=conn[j].targetId;
        		var tID=graph.findNode(targetId);        		
        		if(tID.visited==null)
        		{   
        			targets.push(targetId);
        			jsPlumb.detach(connessione);       			
        		}
        	}  
        	
        	getConnectionVisitable(targets, graph);
        	  	
        	conn=jsPlumb.getConnections({"target":idAttr});
        	for(var j=0; j<conn.length; j++)
        	{
        		var sID=graph.findNode(conn[j].sourceId);
        		if(sID.visited!=true)
        		{
        			targets.push(conn[j].sourceId);
        			jsPlumb.detach(conn[j]);
        		}
        	}
        	
        	for (i=0;i<targets.length; i++)
        	{
        		var mconn=jsPlumb.connect({
						source:idAttr,
						target:targets[i]																
					}, stateMachineConnector);	
					mconn.bind("click", function(conn) {						
						var source="";
						var target="";
						$.each(conn, function(key, val) {
							if(key=="targetId")
								target=val;	
							if(key=="sourceId")
								source=val;
							});								
							
						});		
									
        	}
		}
		
		
		this.initialize= function()
		{			
			var mygraph= this.graph;
			var theView=this;
			
			jsPlumb.ready(function() {
				
			jsPlumb.DefaultDragOptions = { cursor: "pointer", zIndex:2000 };
			for(i=0; i<mygraph.nodes.length;i++)
        	{        	
        		jsPlumb.draggable($("#"+mygraph.nodes[i].name));
        		
        		$("#"+mygraph.nodes[i].name).dblclick(function() {
  						if(mygraph.root==null)
  						{
  							var mroot= mygraph.findNode($(this).attr('id'));
  							mygraph.root= mroot;
  							changeConnection($(this).attr('id'), theView);
  						}
				});
        		
        		
        			
        	}
        	
        	var stateMachineConnector = {				
				connector:"StateMachine",
				paintStyle:{lineWidth:3,strokeStyle:"#056"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
				anchor:"Continuous",
				overlays:[ ["PlainArrow", {location:1, width:30, length:22} ]]
			};    
			var dynamicAnchors = [ [ 0.2, 0, 0, -1 ],  [ 1, 0.2, 1, 0 ], 
			   [ 0.8, 1, 0, 1 ], [ 0, 0.8, -1, 0 ] ];
			
			var standardConnector={				
				
				paintStyle:{lineWidth:3,strokeStyle:"#056"},
				hoverPaintStyle:{strokeStyle:"#dbe300"},
				endpoint:"Blank",
				anchor:dynamicAnchors
				
			};   	
        	
        	for(i=0; i<mygraph.nodes.length;i++)
        	{
        		var from=mygraph.nodes[i];
        		var rels= from.relations;
        		for(j=0; j<rels.length; j++)
        		{
        			
        			
        			rels[j].connection=jsPlumb.connect({
						source:from.name,
						target:rels[j].entity.name																
					}, standardConnector);
					rels[j].connection.bind("click", function(conn) {						
						var source="";
						var target="";
						$.each(conn, function(key, val) {
							if(key=="targetId")
								target=val;	
							if(key=="sourceId")
								source=val;
							});
							alert(source+" <---> "+target);	
							jsPlumb.detach(conn);
						});				
        		}
        	}
        	
        	
        	
        	
        	});
        	
		}
	}



