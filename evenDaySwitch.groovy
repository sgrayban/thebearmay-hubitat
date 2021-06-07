/* Even Day Switch - check the day of the year and turn on a switch based on even/odd
*
*/

@SuppressWarnings('unused')
static String version() {return "0.0.1"}

metadata {
    definition (
        name: "Even Day Switch", 
        namespace: "thebearmay", 
        author: "Jean P. May, Jr.",
        importUrl:"https://raw.githubusercontent.com/thebearmay/hubitat/main/evenDaySwitch.groovy"
    ) {
        capability "Actuator"
        capability "Initialize"
        capability "Switch"
        
        attribute "evenOdd", "string"
	attribute "dayOfYear", "number"
    }   
}

preferences {
    input("onWhenEven", "bool", title: "Turn switch on when even day of the year", defaultValue: true)
}
		
def initialize() {
	if(onWhenEven==null) device.updateSetting("onWhenEven",[value:"true",type:"bool"])
	dateNow = new Date()
	dayOfYear = dateNow[Calendar.DAY_OF_YEAR]
	sendEvent(name:"dayOfYear", value: dayOfYear)
	if(dayOfYear % 2 == 0) {
	   sendEvent(name:"evenOdd", value:"even")
	   if(onWhenEven) on()
	   else off()
	}	else {
	   sendEvent(name:"evenOdd", value:"odd")
	   if(onWhenEven) off()
	   else on()
	}
	schedule("0 0 0 ? * *", initialize)
}

def updated(){
	initialize()
}

def on(){
	sendEvent(name:"switch", value:"on")
}

def off(){
	sendEvent(name:"switch", value:"off")
}
