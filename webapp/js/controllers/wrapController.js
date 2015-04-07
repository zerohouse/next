/**
 * Created by park on 15. 4. 4..
 */
app.controller('wrapController', ['$scope', '$user', '$toggle', '$http', '$timeout', function ($scope, $user, $toggle, $http) {
    $scope.user = $user;
    $scope.toggle = $toggle;

    $scope.toggle.user = true;
    $scope.toggle.partner = true;
    $scope.toggle.test = true;


    $scope.logout = function () {
        var user = {email: "", password: "", logged: false};
        $scope.user.email = "";
        angular.copy(user, $scope.user);
        $http(req("GET", "/api/user/logout")).success(function (response) {
        });
    };
}]);