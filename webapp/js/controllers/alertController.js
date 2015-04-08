/**
 * Created by park on 15. 4. 7..
 */
app.controller('alertController', ['$scope', function ($scope) {
    $scope.close = function () {
        $scope.show = false;
    };

    $scope.error = [];

    $scope.alert = function (error) {
        $scope.show = true;
        $scope.error.push(error);
        $scope.errorMessage = $scope.error.pop();
    };

    $scope.errorBackup = [
        "마지막 글입니다.",
        "parksungho86@gmail.com\n\n\nNhn Next",
        "테스트 내용이 많을 수록, 더 잘 맞는 사람과 만나게 되고 \n\n 더 많은 매칭 기회를 가지게 됩니다.",
        "MBTI, Enneagram은 해외 논문을 인용해서 알고리즘을 짰고 \n\n" +"연애 유형은 비슷한 사람끼리 잘 맞을거라고 가정했습니다..",
    ];

    $scope.nextError = function () {
        $scope.show = true;
        var error = $scope.error.pop();
        if (error == undefined) {
            angular.copy($scope.errorBackup, $scope.error);
            var error = $scope.error.pop();
        }
        $scope.errorMessage = error;
    };


}]);