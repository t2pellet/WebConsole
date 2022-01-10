/**
 WebConsole Persistence Manager for WebConsole
 Used to save your servers and config into your browser
 https://github.com/mesacarlos
 2019-2020 Carlos Mesa under MIT License.
*/
class WebConsolePersistenceManager{
	
	/**
	* Saves or updates server into WebStorage
	*/
	saveServer(serverObject) {
		//Retrieve saved data
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		storageObj.server = serverObject;

		//Save to WebStorage
		window.localStorage.WebConsole = JSON.stringify(storageObj);
	}
	
	/**
	* Get server details as object
	*/
	getServer() {
		//Retrieve saved data
		var storageObj = JSON.parse(window.localStorage.WebConsole);

		if (storageObj.server != null) {
			return storageObj.server;
		}

		var host = settings.host === "local" ? self.location.hostname : settings.host;
		var address = "ws://" + host + ":" + settings.port;
		return new WSServer("Server Console", address);
	}

	/**
	 * Save language for this client
	 */
	setLanguage(lang){
		//Retrieve saved data
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		storageObj.language = lang;
		
		//Save to WebStorage
		window.localStorage.WebConsole = JSON.stringify(storageObj);
	}

	/**
	 * Get saved language for this client
	 */
	getLanguage(){
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		if(!storageObj.language)
			return "en_US";
		return storageObj.language;
	}
	
	/**
	* Create server list if not defined
	*/
	initializeLocalStorage(){
		if (typeof window.localStorage.WebConsole === 'undefined') {
			//Create empty object
			var storageObj = new Object();
			storageObj.servers = new Array();
			storageObj.language = "en_US";
			
			//Save to WebStorage
			window.localStorage.WebConsole = JSON.stringify(storageObj);
		}
	}
	
	/**
	* Replaces all server list with provided list
	*/
	replaceAllServers(newServerList){
		//Retrieve saved data
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		storageObj.servers = newServerList;
		
		//Save to WebStorage
		window.localStorage.WebConsole = JSON.stringify(storageObj);
	}

	/**
	* Create settings object if not defined or populate with new options if updating
	*/
	initializeSettings(){
		this.initializeLocalStorage();

		//Create settings object
		var currentSettings = JSON.parse(window.localStorage.WebConsole).settings;
		if (typeof currentSettings === 'undefined') {
			currentSettings = new Object();
		}

		//Setting array initialization. If you need to add more settings, add them here. Any object is valid as a value (not only bool)
		var settings = {
			dateTimePrefix : new Setting("dateTimePrefix", true),
			retrieveLogFile : new Setting("retrieveLogFile", true) 
		}

		//Set settings
		jQuery.each(settings, (key, settingObj) =>{
			if(!currentSettings.hasOwnProperty(settingObj.name))
				currentSettings[settingObj.name] = settingObj.defaultValue;
		});

		//Save all
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		storageObj.settings = currentSettings;
		window.localStorage.WebConsole = JSON.stringify(storageObj);
	}

	/**
	* Update setting value
	*/
	setSetting(name, value){
		var currentSettings = JSON.parse(window.localStorage.WebConsole).settings;
		currentSettings[name] = value;

		//Save all
		var storageObj = JSON.parse(window.localStorage.WebConsole);
		storageObj.settings = currentSettings;
		window.localStorage.WebConsole = JSON.stringify(storageObj);
	}

	/**
	* Get setting value
	*/
	getSetting(name){
		return JSON.parse(window.localStorage.WebConsole).settings[name];
	}
	
}