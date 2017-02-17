/*
 * Connection class javascript
 * for representation of the connection
 */ 
function Connection() {
	this.name="";
	this.login="";
	this.password="";
	this.host="";
	this.databaseName="";
	this.filters=[];	
}

/*
 * Return a Json representation
 */
Connection.prototype.toJson= function () {
	var outJSON=[];	
	outJSON.push({"name": this.name});
	outJSON.push({"login": this.login});
	outJSON.push({"password": this.password});
	outJSON.push({"host": this.host});
	outJSON.push({"databaseName": this.databaseName});
	alert($.toJSON(this));
	return outJSON; 
};

/*
 * Initialize the class by a Json
 */
Connection.prototype.fromJSON= function (inJSON) {
	this.name=inJSON.name;
	this.login=inJSON.login;
	this.password=inJSON.password;
	this.host=inJSON.host;
	this.databaseName=inJSON.databaseName;  
};


