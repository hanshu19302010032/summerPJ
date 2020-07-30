function getAllUrlParams(url) {
    
    let queryString = url ? url.split('?')[1] : window.location.search.slice(1);
  
    let obj = {};
   
    if (!queryString) {
        return obj;
    }
   
    queryString = queryString.split('#')[0];
   
    let arr = queryString.split('&');
    for (let i = 0; i < arr.length; i++) {
       
        let a = arr[i].split('=');
      
        let paramName = a[0];
        let paramValue = typeof (a[1]) === 'undefined' ? true : a[1];
        
        if (paramName.match(/\[(\d+)?]$/)) {
          
            let key = paramName.replace(/\[(\d+)?]/, '');
            if (!obj[key]) obj[key] = [];
          
            if (paramName.match(/\[\d+]$/)) {
               
                let index = /\[(\d+)]/.exec(paramName)[1];
                obj[key][index] = paramValue;
            } else {
               
                obj[key].push(paramValue);
            }
        } else {
           
            if (!obj[paramName]) {
              
                obj[paramName] = paramValue;
            } else if (obj[paramName] && typeof obj[paramName] === 'string') {
                
                obj[paramName] = [obj[paramName]];
                obj[paramName].push(paramValue);
            } else {
               
                obj[paramName].push(paramValue);
            }
        }
    }
    return obj;
}
