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

    $scope.refresh = function(){
        $http(req("GET", "/api/user")).success(function (response) {
            if (response.error) {
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $scope.user.logged = true;
        });
    }

    $scope.refresh();


}]);

function getValue(){


}