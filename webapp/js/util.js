/**
 * Created by park on 15. 4. 1..
 */

var req = function (method, url, data) {
    return {
        method: method,
        url: url,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        transformRequest: function (obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "="
                + encodeURIComponent(obj[p]));
            return str.join("&");
        },
        data: data
    }
};

function error(message){
    app.findController('alertController').alert(message);
}