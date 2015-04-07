/**
 * Created by park on 15. 4. 1..
 */

app.controller('loginController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;

    $scope.$watch(function () {
        return $scope.user.email;
    }, function () {
        if (!$scope.check.email())
            return;
        $scope.registeredEmail = false;
        $scope.checking = true;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            $http(req("POST", "/api/user/registeredEmail", {email: $scope.user.email})).success(function (response) {
                $scope.checking = false;
                if ($scope.user.email == response)
                    $scope.registeredEmail = true;
            });
        }, 500);
    });

    $scope.check = {
        email: function () {
            var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return regex.test($scope.user.email);
        },
        password: function () {
            return /^[\w\W]{4,12}$/i.test($scope.user.password);
        },
        all: function () {
            if (!$scope.check.password())
                return false;
            if (!$scope.check.email())
                return false;
            return true;
        }
    }

    $scope.register = function () {
        if (!$scope.check.all())
            return;
        $http(req("POST", "/api/user", {user: JSON.stringify($scope.user)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $scope.user.logged = true;
        });
    };

    $scope.login = function () {
        if (!$scope.check.all())
            return;
        $http(req("POST", "/api/user/login", {user: JSON.stringify($scope.user)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            setTimeout(function () {
                app.findController('matchedController').refresh();
            }, 300);
            $scope.user.logged = true;
        });
    }

    $scope.passwordRedefine = function () {
        error("이메일을 보내는 중입니다.");
        $http(req("GET", "/api/passwordRedefine?email=" + $scope.user.email)).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            error("이메일을 확인해주세요.");
        });
    }

}]);