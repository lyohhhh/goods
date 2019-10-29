(
    function() {
        function addEvent(obj, type, fn) {  
            if (obj.attachEvent) {
                obj.attachEvent('on' + type, function() {      
                    fn.call(obj);    
                })  
            } else {    
                obj.addEventListener(type, fn, false);  
            }
        }
        window.addEvent = addEvent;
    }
)();