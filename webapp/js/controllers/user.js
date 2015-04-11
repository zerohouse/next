/**
 * Created by park on 15. 4. 3..
 */
app.controller('controllers.user', ['$scope', '$http', '$user', '$toggle', '$timeout', function ($scope, $http, $user, $toggle, $timeout) {
    $scope.user = $user;

    $scope.reMail = function () {
        if (!confirm("메일을 다시 전송할까요?"))
            return;
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

            app.findScope('matched').refresh();
            app.findScope('letter').refresh();
        });
    };

    $scope.refresh();

    $scope.setResult = function (type, result, user) {
        app.findScope('result').setResult(type, result, user);
    };

    $scope.$watch(function () {
        return $scope.user.gender;
    }, function () {
        if ($scope.user.email == "")
            return;
        if (!$scope.toggle.gender)
            return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.gender = $scope.user.gender;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(response.errorMessage);
                    return;
                }
            });
        }, 500);
    });

    $scope.$watch(function () {
        return $scope.user.nickName;
    }, function () {
        if ($scope.user.email == "")
            return;
        if (!app.findScope('user').toggle.nickName)
            return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.nickName = $scope.user.nickName;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(response.errorMessage);
                    return;
                }
            });
        }, 500);
    });

    $scope.ifProfile = function (user) {
        if (user.profileUrl == undefined)
            return;
        else if (user.profileUrl == "")
            return;
        else
            return 'background-image:url(' + user.profileUrl + ');  background-size: 100%;   background-position: center;';
    };

    $scope.$watch(function () {
        return $scope.user.age;
    }, function () {
        if ($scope.user.email == "")
            return;
        if (!app.findScope('user').toggle.age)
            return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.age = $scope.user.age;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(response.errorMessage);
                    return;
                }
            });
        }, 500);
    });

    $scope.$watch(function () {
        return $scope.user.location;
    }, function () {
        if ($scope.user.email == "")
            return;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            var obj = {};
            obj.email = $scope.user.email;
            obj.location = $scope.user.location;
            obj.address = $scope.user.address;
            $http(req("POST", "/api/user/update", {user: JSON.stringify(obj)})).success(function (response) {
                if (response.error) {
                    error(response.errorMessage);
                    return;
                }
            });
        }, 500);
    });


    var position;

    function getGoogleMapUrl(response) {
        position = response.coords.latitude + "," + response.coords.longitude;
        var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + position;
        $http(req("GET", url)).success(function (response) {
            if (response.status != "OK") {
                error("주소를 가져오는 과정에서 문제가 생겼네요.");
                return;
            }
            $scope.user.location = position;
            $scope.user.address = response.results[4].formatted_address;
        });
    }

    $scope.getLocation = function () {
        if (!confirm("현재 위치를 내 지역으로 설정합니다."))
            return;

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(getGoogleMapUrl);
        } else {
            error("브라우저가 지원하지 않는 기능입니다.");
        }
    };


}]);


app.directive("user", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/user.div",
        controller: "controllers.user",
        scope: true
    }
});