/**
 * Created by park on 15. 4. 8..
 */
app.controller('essayController', ['$scope', '$http', '$timeout', '$user', '$toggle', function ($scope, $http, $timeout, $user, $toggle) {

    $scope.toggle = $toggle;

    $scope.essays = [];

    $scope.essays.contains = function (obj) {
        for (var i = 0; i < this.length; i++) {
            if (this[i].id == obj.id)
                return true;
        }
        return false;
    };

    $scope.addEssay = function () {
        var essay = {};
        essay.email = $scope.user.email;
        essay.key = $scope.user.email;
        $http(req("POST", "/api/essay", {essay: JSON.stringify(essay)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.push(response.obj);
            $toggle['essayMod' + response.obj.id] = true;
        });
    };

    $scope.refresh = function () {
        $scope.getEssays($user);
    };

    $scope.push = function (essay) {
        if (!$scope.essays.contains(essay))
            $scope.essays.push(essay);
    };


    $scope.update = function (obj) {
        var index = $scope.essays.indexOf(obj);

        obj.date = new Date();
        $http(req("POST", "/api/essay", {essay: JSON.stringify(obj)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.essays[index] = obj;
        });

    };

    $scope.delete = function (obj) {
        var index = $scope.essays.indexOf(obj);
        obj.date = new Date();
        if (!confirm("삭제하시겠습니까?"))
            return;
        $http(req("POST", "/api/essay/delete", {essay: JSON.stringify(obj)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.essays.splice(index, 1);
        });

    };


    $scope.getEssays = function (User) {
        var reqEssay = {};
        reqEssay.key = User.email;
        if (User.page == undefined)
            User.page = 0;
        reqEssay.start = User.page * 5;
        reqEssay.size = 5;

        $http(req("GET", "/api/essay?" + getGetUrlParsed(reqEssay))).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            if (response.obj == undefined) {
                error(User.email + "님의 글이 없네요");
                return;
            }
            if (response.obj.forEach == undefined)
                return;

            User.page++;

            response.obj.forEach(function (essay) {
                $scope.push(essay);
                app.findController('alertController').error.push(essay.body);

            });
            if (User.email == $user.email)
                return;
            app.findController('alertController').nextError();
        });
    };


}]);