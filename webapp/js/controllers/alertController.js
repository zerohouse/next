/**
 * Created by park on 15. 4. 7..
 */
app.controller('alertController', ['$scope', function ($scope) {
    $scope.close = function () {
        $scope.show = false;
    }

    $scope.alert = function (error) {
        $scope.show = true;
        $scope.errorMessage = error;
    }


}]);