package com.totsp.gwittir
{
import flash.external.ExternalInterface;
import flash.net.SharedObject;

import mx.controls.TextArea;
	
	public class FlashLocalStorageExport
	{
		var log:TextArea;
		var sharedObjects:Object = new Object();
		
		public function FlashLocalStorageExport(log)
		{
			this.log = log;
			this.log.text += "Starting up.\n";
			this.register();
		}
		
		private function register():void{
			this.log.text += "register()\n";
			ExternalInterface.addCallback("getLocal", getLocal );
			ExternalInterface.addCallback("setLocal", setLocal );
			ExternalInterface.addCallback("flushAll", flushAll );
			ExternalInterface.addCallback("flush", flush );
			ExternalInterface.call("FlashLocalStorageExportRegistered");
		}
	
		public function getLocal(name:String):Object{
			try{
				var so:SharedObject = SharedObject.getLocal(name, "/");
				log.text+= "getLocal("+name+")\n";
				sharedObjects[name] = so;
				log.text+= "getLocal("+name+")2\n";
				var result:Object = new Object();
				log.text+= "getLocal("+name+")3\n";
				log.text += so.toString()+"\n";
				var data:Object = so.data;
				log.text += data.toString()+"\n";
				if(data != null){
				for(var x in data){
					log.text+= "\t"+x+":"+data[x]+"\n";
					result[x] = data[x];
			    }
				}
			    log.text+= "getLocal("+name+")4\n";
			    sharedObjects[name] = so;
			    return result;
			 } catch(e) {
			 	log.text +=e;
			 }
			 return null;
		}
		
		public function setLocal(name:String, data:Object):void{
			try{
			log.text+= "setLocal("+name+", ...)\n";
			var so:SharedObject = sharedObjects[name];
			log.text += so.toString() +"\n";
			if(so == null){
				so = SharedObject.getLocal(name);
			}
			for(var x:String in data){
				log.text+= "\t"+x+":"+data[x]+"\n";
				so.data[x] = data[x];
			}
			} catch(e) {
			 	log.text +="Exception: "+e;
			}
	   }
	   public function flushAll():void {
	   		log.text+= "flushAll()";
	   		for( var x in sharedObjects ){
	   			var so:SharedObject = this.sharedObjects[x];
	   			var status:String = so.flush(128000);
	   			log.text+= "\t"+x+" status:"+status;
	   		}
	   }
	   
	   public function flush(name:String):void{
	   		this.log.text += "flush("+name+")"; 
	  		var so:SharedObject = this.sharedObjects[name];
   			var status:String = so.flush(128000);
   			this.log.text+= "\t"+name+" status:"+status;
	   }
	   
	   public function set(name:String, key:String, value:String):void{
	   		var so:SharedObject = this.sharedObjects[name];
	   		so.data[key] = value;
	   }
	   public function get(name, key):String{
	   		var so:SharedObject = this.sharedObjects[name];
	   		return so.data[key];
	   }
	}
}