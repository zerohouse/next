/**
 * Created by park on 15. 4. 4..
 */
app.controller('wrapController', ['$scope', '$user', '$toggle', function ($scope, $user, $toggle) {
    $scope.user = $user;
    $scope.toggle = $toggle;
}]);