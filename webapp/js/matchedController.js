/**
 * Created by park on 15. 4. 3..
 */
app.controller('matchedController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;

    $scope.refresh = function () {
        if (!$scope.user.logged)
            return;
        $http(req("GET", "/api/matched")).success(function (response) {
            if (response.error) {
                return;
            }
            $scope.matchedUsers = response.obj;
        });
    }


}]);
