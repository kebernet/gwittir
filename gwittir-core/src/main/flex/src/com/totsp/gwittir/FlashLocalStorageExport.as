package com.totsp.gwittir
{
import flash.external.ExternalInterface;
import flash.net.SharedObject;

import mx.controls.TextArea;
	
	public class FlashLocalStorageExport
	{
		var log:TextArea;
		var sharedObjects:Array = new Array();
		
		public function FlashLocalStorageExport(log)
		{
			this.log = log;
			this.log.text += "Starting up.";
			this.register();
		}
		
		private function register(){
			this.log.text += "register()";
			ExternalInterface.addCallback("getLocal", this.getLocal );
			ExternalInterface.addCallback("setLocal", this.setLocal );
			ExternalInterface.addCallback("flushAll", this.flushAll );
			ExternalInterface.addCallback("flush", this.flush );
		}
	
		public function getLocal(name){
			var so:SharedObject = SharedObject.getLocal(name);
			log.text+= "getLocal("+name+")\n";
			sharedObjects[name] = so;
			var result = new Array();
			for(var x in so.data){
				log.text+= "\t"+x+":"+so[x]+"\n";
				result[x] = so[x];
		    }
		    this.sharedObjects[name] = so;
		}
		
		public function setLocal(name, data){
			log.text+= "setLocal("+name+", ...)\n";
			var so:SharedObject = this.sharedObjects[name];
			if(so == null){
				so = SharedObject.getLocal(name);
			}
			for(var x in data){
				log.text+= "\t"+x+":"+data[x]+"\n";
				so.setProperty(x, data[x]);
			}
	   }
	   public function flushAll(){
	   		log.text+= "flushAll()";
	   		for( var x in this.sharedObjects ){
	   			var so:SharedObject = this.sharedObjects[x];
	   			var status:String = so.flush(128000);
	   			log.text+= "\t"+x+" status:"+status;
	   		}
	   }
	   
	   public function flush(name){
	   		this.log.text += "flush("+name+")"; 
	  		var so:SharedObject = this.sharedObjects[name];
   			var status:String = so.flush(128000);
   			this.log.text+= "\t"+name+" status:"+status;
	   }
	}
}