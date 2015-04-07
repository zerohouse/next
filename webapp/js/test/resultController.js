/**
 * Created by park on 15. 4. 7..
 */

app.controller('resultController', ['$scope', function ($scope) {

    $scope.setResult = function (type, result) {
        switch (type) {
            case 'MBTI' :
                setMbti(result);
                break;
            case 'LoveType' :
                setLoveType(result);
                break;
            case 'EnneaGram' :
                setEnneaGram(result);
                break;
        }


        function setMbti(result) {
            $scope.result = app.findController('mbtiController').results[result];
        }

        function setLoveType(result) {
            var type;
            switch (result) {
                case 'Passionate':
                    type = "R";
                    break;
                case 'Friendship':
                    type = "B";
                    break;
                case 'Possessive':
                    type = "F";
                    break;
                case 'Selfless':
                    type = "A";
                    break;
                case 'Logical':
                    type = "L";
                    break;
                case 'Game-Playing':
                    type = "P";
                    break;
            }
            $scope.result = app.findController('loveTypeController').result[type];
        }


        function setEnneaGram(result) {
            $scope.result = app.findController('loveTypeController').results[result.substring(1)];
        }

    };

}
])
;