/**
 * Created by park on 15. 4. 1..
 */

app.controller('registerController', ['$scope', '$http', '$user', function ($scope, $http, $user) {
    $scope.user = $user;


    $scope.$watch(function () {
        return $scope.user.id
    }, function () {
        $scope.existId = true;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            $http(req("POST", "/api/user/existId", {id: $scope.user.id})).success(function (response) {
                if ($scope.user.id == response)
                    $scope.existId = true;
            });
        }, 1000);
    });

    $scope.check = {
        id: function () {
            return /^[a-z][a-z\d]{3,11}$/i.test($scope.user.id);
        },
        password: function () {
            return /^[\w\W]{3,11}$/i.test($scope.user.password);
        },
        email: function () {
            var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return regex.test($scope.user.email);
        },
        gender: function () {
            return $scope.user.gender != undefined;
        },
        profileUrl: function () {

        },
        all: function () {
            if ($scope.existId)
                return false;
            if (!$scope.check.id())
                return false;
            if (!$scope.check.password())
                return false;
            if (!$scope.check.email())
                return false;
            if (!$scope.check.gender())
                return false;
            return true;
        }

    };

}
]);