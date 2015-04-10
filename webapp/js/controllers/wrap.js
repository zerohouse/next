/**
 * Created by park on 15. 4. 4..
 */
app.controller('wrapController', ['$scope', '$user', '$toggle', '$http', '$timeout', function ($scope, $user, $toggle, $http, $timeout) {
    $scope.user = $user;
    $scope.toggle = $toggle;

    $toggle.test = true;
    $toggle.profile = true;
    $toggle.letter = true;
    $toggle.hideDone = true;


    $scope.move = function (id) {
        var body = document.querySelector('#body');
        if (body.children[0].id == id)
            $toggle.toggle(id);
        body.insertBefore(document.querySelector('#' + id), body.children[0]);
    };

    $scope.logout = function () {
        error("Begin Again!");
        var user = {email: "", password: "", logged: false};
        $scope.user.email = "";
        angular.copy(user, $scope.user);
        $http(req("GET", "/api/user/logout")).success(function (response) {
            FB.logout(function (response) {
                location.reload()
            });
        });
    };


    $scope.mailTo = function () {
        location.href = "mailto:parksungho86@gmail.com=문의";
    };

    $scope.nextError = function () {
        app.findScope('alert').nextError();
    };

}]);