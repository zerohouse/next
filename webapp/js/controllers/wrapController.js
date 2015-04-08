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
        error("들어올땐 마음대로 였지만 나갈땐 아니란다.");
        var user = {email: "", password: "", logged: false};
        $scope.user.email = "";
        angular.copy(user, $scope.user);
        $http(req("GET", "/api/user/logout")).success(function (response) {
            setTimeout(function () {
                location.reload()
            }, 1000);
        });
    };


    $scope.mailTo = function () {
        location.href = "mailto:parksungho86@gmail.com=문의";
    };
    $scope.nextError = function () {
        app.findController('alertController').nextError();
    };

}]);