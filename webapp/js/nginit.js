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

FB.login(function(response) {
    console.log(response);
    if (response.authResponse) {
        console.log('Welcome!  Fetching your information.... ');
        FB.api('/me', function(response) {
            console.log('Good to see you, ' + response.name + '.');
        });
    } else {
        console.log('User cancelled login or did not fully authorize.');
    }
});