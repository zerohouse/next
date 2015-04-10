/**
 * Created by park on 15. 4. 1..
 */

var app = angular.module('meetfit', ['ngAnimate']);

app.factory('$user', function () {
    var $user = {email: "", password: "", nickName: ""};
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

app.findScope = function (selector) {
    return angular.element(document.querySelector(selector)).scope();
};


app.directive("header", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/header.div"
    }
});

app.setting = {
    questionDelay: 800
};