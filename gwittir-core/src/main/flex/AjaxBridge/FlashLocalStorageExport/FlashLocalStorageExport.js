/**
 * WARNING! THIS IS A GENERATED FILE, AND WILL BE RE-GENERATED EACH TIME THE
 * AJAXBRIDGE IS RUN.
 *
 * You should keep your javascript code inside this file as light as possible, 
 * and rather keep the body of your Ajax application in separate *.js files. 
 *
 * Do make a backup of your changes, before re-generating this file (AjaxBridge 
 * will display a warning message to you).
 *
 * Please refer to the built-in documentation inside the AjaxBridge application 
 * for help on using this file.
 */
 
 
/**
 * Class "FlashLocalStorageExport"
 * Fully qualified class name: "com.totsp.gwittir.FlashLocalStorageExport"
 */
function FlashLocalStorageExport(obj) {
	if (arguments.length > 0) {
		this.obj = arguments[0];
	} else {
		this.obj = FABridge["b_FlashLocalStorageExport"].
			create("com.totsp.gwittir.FlashLocalStorageExport");
	}
}

// CLASS BODY
// Selected class properties and methods
FlashLocalStorageExport.prototype = {

	// Fields form class "FlashLocalStorageExport" (translated to getters/setters):

	getConstructor : function () {
		return this.obj.getConstructor();
	},
	setConstructor : function (value) {
		this.obj.setConstructor(value);
	},
	

	getSuper : function () {
		return this.obj.getSuper();
	},
	setSuper : function (value) {
		this.obj.setSuper(value);
	},
	

	getThis : function () {
		return this.obj.getThis();
	},
	setThis : function (value) {
		this.obj.setThis(value);
	},
	

	// Methods form class "FlashLocalStorageExport":
	
	flushAll : function() {
		return this.obj.flushAll();
	},
	
	FlashLocalStorageExport : function() {
		return this.obj.FlashLocalStorageExport();
	},
	
	setLocal : function() {
		return this.obj.setLocal();
	},
	
	getLocal : function() {
		return this.obj.getLocal();
	},
	
	flush : function() {
		return this.obj.flush();
	},
	
	toString : function() {
		return this.obj.toString();
	},
	
	hasOwnProperty : function(argString) {
		return this.obj.hasOwnProperty(argString);
	},
	
	isPrototypeOf : function(argObject) {
		return this.obj.isPrototypeOf(argObject);
	},
	
	propertyIsEnumerable : function(argString) {
		return this.obj.propertyIsEnumerable(argString);
	},
	
	Object : function() {
		return this.obj.Object();
	},
	
	toLocaleString : function() {
		return this.obj.toLocaleString();
	},
	
	setPropertyIsEnumerable : function(argString, argBoolean) {
		this.obj.setPropertyIsEnumerable(argString, argBoolean);
	},
	
	valueOf : function() {
		return this.obj.valueOf();
	}
}


/**
 * Listen for the instantiation of the Flex application over the bridge
 */
FABridge.addInitializationCallback("b_FlashLocalStorageExport", FlashLocalStorageExportReady);


/**
 * Hook here all the code that must run as soon as the "FlashLocalStorageExport" class
 * finishes its instantiation over the bridge.
 *
 * For basic tasks, such as running a Flex method on the click of a javascript
 * button, chances are that both Ajax and Flex may well have loaded before the 
 * user actually clicks the button.
 *
 * However, using the "FlashLocalStorageExportReady()" is the safest way, as it will 
 * let Ajax know that involved Flex classes are available for use.
 */
function FlashLocalStorageExportReady() {

	// Initialize the "root" object. This represents the actual 
	// "FlashLocalStorageExportHelper.mxml" flex application.
	b_FlashLocalStorageExport_root = FABridge["b_FlashLocalStorageExport"].root();
	

	// YOUR CODE HERE
	// var FlashLocalStorageExportObj = new FlashLocalStorageExport();
	// Example:
	// var myVar = FlashLocalStorageExportObj.flushAll ();
	// b_FlashLocalStorageExport_root.addChild(FlashLocalStorageExportObj);

}
