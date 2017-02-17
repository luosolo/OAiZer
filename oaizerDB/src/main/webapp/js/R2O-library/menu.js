function MainMenu()
{
	this.wizardConnectionPanel="";	
}

MainMenu.prototype.newConnection= function()
{
	this.wizardConnectionPanel.showPanel();
}

function fillValues()
{
	var s=$("#filterDBConnection");
	s.html("<option>ciao</option><option>mondo</option><option>da</option><option>sandro</option>");	
	s.val(['ciao','mondo' ])	
	$("#filterDBConnection").multiselect("destroy").multiselect();	
}


 
var menu= new MainMenu();
var connectionPanel= new WizardConnection();


menu.wizardConnectionPanel=connectionPanel;


function inizializeWizardConnection(aConnectionPanel)
{
	aConnectionPanel.associateDatabaseName("databaseName");
	aConnectionPanel.associateHostName("hostName");
	aConnectionPanel.associatePanel("display_new_step_1");
	aConnectionPanel.associateConnectionName("connectionName");
	aConnectionPanel.associateUsername("userName");
	aConnectionPanel.associatePassword("password");
	aConnectionPanel.associateFilterButton("fButton");
	aConnectionPanel.associateFilterTable("filterDBConnection");
	aConnectionPanel.associateCreateButton("createConnectionButton");
	aConnectionPanel.initialize();	
}


$(document).ready(function prova() {
	inizializeWizardConnection(connectionPanel);
	
	
});





