/**
 * Created by park on 15. 4. 3..
 */
app.controller('userController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;

    $scope.logout = function () {
        $http(req("GET", "/api/user/logout")).success(function (response) {
            var user = {email: "", password: "", logged: false};
            angular.copy(user, $scope.user);
        });
    }

}]);