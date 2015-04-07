/**
 * Created by park on 15. 4. 1..
 */


Array.prototype.contains = function (obj) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == obj)
            return true;
    }
    return false;
}

var app = angular.module('meetfit', ['ngAnimate']);

app.factory('$user', function () {
    var $user = {email: "", password: ""};
    return $user;
});


app.service('$toggle', function () {
    var toggle = {};
    toggle.toggle = function (selector) {
        if (toggle[selector]) {
            toggle[selector] = false;
            return;
        }
        toggle[selector] = true;
    }
    return toggle;
});

app.findController = function (controller) {
    return angular.element(document.querySelector("[ng-controller='" + controller + "']")).scope();
};

