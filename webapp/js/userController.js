/**
 * Created by park on 15. 4. 3..
 */
app.controller('userController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;

    $scope.logout = function () {
        var user = {email: "", password: "", logged: false};
        $scope.user.email = "";
        angular.copy(user, $scope.user);
        $http(req("GET", "/api/user/logout")).success(function (response) {
        });
    };

    $scope.reMail = function () {
        $http(req("POST", "/api/user/mailRequest", {email: $user.email})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            error("인증 메일을 재전송 하였습니다.");
        });
    };

    $scope.refresh = function () {
        $http(req("GET", "/api/user")).success(function (response) {
            if (response.error) {
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $scope.user.logged = true;
            app.findController('matchedController').refresh();
        });
    };

    $scope.refresh();

    $scope.setResult = function (type, result) {
        app.findController('resultController').setResult(type, result);
    };

    $scope.$watch(function () {
        return $scope.user.gender;
    }, function () {
        if ($scope.user.email == "")
            return;
        if (!app.findController('userController').toggle.gender)
            return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.gender = $scope.user.gender;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(reponse.errorMessage);
                    return;
                }
            });
        }, 500);
    });

    $scope.$watch(function () {
        return $scope.user.age;
    }, function () {
        if ($scope.user.email == "")
            return;
        if (!app.findController('userController').toggle.age);
        return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.age = $scope.user.age;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(reponse.errorMessage);
                    return;
                }
            });
        }, 500);
    });

}]);