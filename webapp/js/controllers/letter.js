/**
 * Created by park on 15. 4. 8..
 */
app.controller('controllers.letter', ['$scope', '$http', '$timeout', '$user', '$toggle', function ($scope, $http, $timeout, $user, $toggle) {

    $scope.toggle = $toggle;


    $scope.receiveLetters = [];
    $scope.sendLetters = [];
    $scope.sendLetters.contains = contains;
    $scope.receiveLetters.contains = contains;

    function contains(obj) {
        for (var i = 0; i < this.length; i++) {
            if (this[i].id == obj.id)
                return true;
        }
        return false;
    };

    var page;
    $scope.refresh = function () {
        $scope.getRecivedLetters();
        $scope.getSendLetters();
    };


    $scope.getRecivedLetters = function () {
        var reqLetter = {};
        if (page == undefined)
            page = 0;
        reqLetter.start = page * 5;
        reqLetter.size = 5;
        $http(req("GET", "/api/letter?" + getGetUrlParsed(reqLetter))).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            if (response.obj == undefined) {
                return;
            }
            if (response.obj.forEach == undefined)
                return;

            page++;

            response.obj.forEach(function (letter) {
                if ($scope.receiveLetters.contains(letter))
                    return;
                $scope.receiveLetters.push(letter);
                app.findScope('alert').error.push(letter);
                app.findScope('alert').errorBackup.push(letter);
            });
            app.findScope('alert').nextError();
        });
    };


    var spage;
    $scope.getSendLetters = function () {
        var reqLetter = {};
        if (spage == undefined)
            spage = 0;
        reqLetter.start = spage * 5;
        reqLetter.size = 5;
        $http(req("GET", "/api/sendletter?" + getGetUrlParsed(reqLetter))).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            if (response.obj == undefined) {
                return;
            }
            if (response.obj.forEach == undefined)
                return;

            spage++;

            response.obj.forEach(function (letter) {
                if ($scope.sendLetters.contains(letter))
                    return;
                $scope.sendLetters.push(letter);
            });
        });
    };


    $scope.update = function (obj) {
        var index = $scope.sendLetters.indexOf(obj);
        obj.date = new Date();
        $http(req("POST", "/api/letter", {letter: JSON.stringify(obj)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.sendLetters[index] = obj;
        });
    };

    $scope.delete = function (obj) {
        var index = $scope.receiveLetters.indexOf(obj);
        if (index == undefined)
            index = $scope.sendLetters.indexOf(obj);
        obj.date = new Date();
        if (!confirm("삭제하시겠습니까?"))
            return;
        $http(req("POST", "/api/letter/delete", {letter: JSON.stringify(obj)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.receiveLetters.splice(index, 1);
        });

    };


    $scope.writeLetter = function (User) {
        var reqLetter = {};
        reqLetter.key = User.email;
        reqLetter.email = $user.email;
        reqLetter.date = new Date();

        $http(req("POST", "/api/letter", {letter: JSON.stringify(reqLetter)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            if ($scope.sendLetters.contains(response.obj))
                return;
            $toggle.toggle('letterMod'+response.obj.id);
            $scope.sendLetters.push(response.obj);
        });
    };


}]);


app.directive("letter", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/letter.div",
        controller: "controllers.letter",
        scope: true
    }
});