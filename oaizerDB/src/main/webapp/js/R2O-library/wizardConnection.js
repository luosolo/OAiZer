/**
 * WizardConnection is responsible to manage
 * the creation of a new Connection to the database
 */
function WizardConnection()
{
    this.panel=null;
    this.filterTable=null;
    this.filterButton=null;
    this.connectionName=null;
    this.username=null;
    this.password=null;
    this.hostname=null;
    this.databaseName=null; 
    this.createButton=null;   
}


	/**
     * Associate the jquery panel Button object
     * @param aPanelId
     */
    WizardConnection.prototype.associatePanel=function (aPanelId )
    {
        this.panel=$("#"+aPanelId);
    }
    /**
     * Associate the jquery Button object
     * @param aButtonId
     */
    WizardConnection.prototype.associateFilterButton= function(aButtonId)
    {
        this.filterButton= $('#'+aButtonId);
        this.filterButton.button();
    }
    /**
     * Associate the jquery filtertable object
     * @param aFilterTableId
     */
    WizardConnection.prototype.associateFilterTable=function (aFilterTableId)
    {
        this.filterTable=$('#'+aFilterTableId);
    }
    /**
     * Associate the connection text box object
     * @param aConnectionName
     */
    WizardConnection.prototype.associateConnectionName=function (aConnectionName)
    {
        this.connectionName=$('#'+aConnectionName);
    }
    /**
     * Associate the userName textbox object
     * @param aUsername
     */
    WizardConnection.prototype.associateUsername=function (aUsername)
    {
        this.username=$('#'+aUsername);
    }
    
    WizardConnection.prototype.associateCreateButton=function(aButton)
    {
    	this.createButton=$("#"+aButton);
    	this.createButton.button();
    }

    /**
     * Associate the password text box object
     * @param aPassword
     */
    WizardConnection.prototype.associatePassword=function(aPassword)
    {
        this.password=$('#'+aPassword);
    }
    
    /**
     * Associate the database Name textbox object
     * @param aDatabaseName
     */
    WizardConnection.prototype.associateDatabaseName=function(aDatabaseName)
    {
        this.databaseName=$('#'+aDatabaseName);
    }
    
    /**
     * Associate the host name textbox object
     * @param aHostName
     */
    WizardConnection.prototype.associateHostName=function(aHostName)
    {
        this.hostname=$('#'+aHostName);
    }
    
    WizardConnection.prototype.createAssociatonToCreateButton=function()
    {
    	var that =this;    	
    	this.createButton.click(function(){
    		c=that.getConnectionInfo();
    		var s=that.filterTable;
    		$.post("./mvc/manager/CreateConnection.do", {"connection" :$.toJSON(c), "filters":$.toJSON(that.filterTable.val())},
 				function(data){
   					alert(data);
 					}, 
 			"text");  		    		
    	});
    }
    
    /**
     * this function retrieve all the information 
     */
    WizardConnection.prototype.getConnectionInfo=function()
    {
    	c=				new Connection();
        c.name=			this.connectionName.val();
        c.login=		this.username.val();
        c.password=		this.password.val();
        c.host=			this.hostname.val();
		c.databaseName=	this.databaseName.val();
		return c;
    }
    

    /**
     * this function must be called after each
     * item in html has been associated
     *
     */
    WizardConnection.prototype.initialize=function()
    {
        if(this.allFieldAreOK())
        {
        	var that=this;
            this.filterButton.click(function()
            {
            	if(that.checkIfcanFilter())
            	{
            		c=that.getConnectionInfo();			
					$.post("./mvc/manager/SetConnection.do", {"connection" :$.toJSON(c)},
 					function(data){
   						that.createFilterTable(data);
 						}, 
 					"json");
            	}
            });
            
            that.createAssociatonToCreateButton();
        }       
    }
    
    
    WizardConnection.prototype.createFilterTable=function(data)
    {
    	
    	$("#tbfilter").show();
    	mhtml="";
    	for(var i=0,j=data.length; i<j; i++){
			  mhtml+="<option>"+ data[i]+ "</option>";
		};
    	this.filterTable.html(mhtml);
    	this.filterTable.multiselect();
    	
    }
    
    
    WizardConnection.prototype.checkIfcanFilter= function()
    {
    	return ( (this.connectionName.val()!="") &&(this.username.val()!="")&&
    				(this.password.val()!="") && (this.hostname.val()!="") &&
    				(this.databaseName.val()!=""));    	
    }
    /**
     * Clear all the field on the Show
     *
     */
    WizardConnection.prototype.clearAll=function()
    {
    	this.connectionName.val("");
    	this.username.val("");
    	this.password.val("");
    	this.hostname.val("");
    	this.databaseName.val("");
    }
    
    /**
	 * Show the wizard panel
	 */
    WizardConnection.prototype.showPanel=function()
    {
        this.panel.show();
        this.clearAll();       
    }

	/**
	 * return if all field are correctly 
	 * initialized
	 */
    WizardConnection.prototype.allFieldAreOK=function()
    {
        return ((this.panel!=null) && (this.filterTable!=null) &&
            (this.filterButton!=null) && (this.connectionName!=null) &&
            (this.username!=null)&&  (this.password!=null) &&
            (this.hostname!=null) && (this.databaseName!=null) 	)
    }

