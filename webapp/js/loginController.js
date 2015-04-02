/**
 * Created by park on 15. 4. 1..
 */

app.controller('loginController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;
    $scope.login = function () {
        $http(req("POST", "/api/user/login", {user: JSON.stringify($scope.user)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.user = response.obj;
        });
    }
}]);