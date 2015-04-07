/**
 * Created by park on 15. 4. 7..
 */
app.controller('resultController', ['$scope', '$user', '$timeout', function ($scope, $user, $timeout) {

    $scope.show = false;

    $scope.setResult = function (type, result, user) {
        $scope.show = true;
        $scope.factor = result;
        switch (type) {
            case 'MBTI' :
                setMbti(result.type);
                break;
            case 'LoveType' :
                setLoveType(result.type);
                break;
            case 'EnneaGram' :
                setEnneaGram(result.type);
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
            var ctx = document.querySelector('#canvasResult').getContext("2d");
            $timeout(function () {
                if (user == $user) {
                    drawChart(ctx, $user);
                    return;
                }
                drawChart(ctx, [$user, user]);
            }, 300);
        }


        function setEnneaGram(result) {
            $scope.result = app.findController('ennController').results[result.substring(1)];
        }

    };


    $scope.friend = false;

    function drawChart(ctx, user) {
        var data = {
            labels: ["Passionate", "Friendship", "Possessive", "Selfless", "Logical", "Game-Playing"],
            datasets: [
                {
                    fillColor: "rgba(220,220,220,0.2)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: []
                },
                {
                    fillColor: "rgba(151,187,205,0.2)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(151,187,205,1)",
                    data: []
                }
            ]
        };
        var options = {
            scaleShowLabels: true,
            scaleOverride: true,
            scaleSteps: 5,
            scaleStepWidth: 20,
            scaleStartValue: 0
        };
        if (user.length != 2) {
            $scope.friend = false;
            data.datasets[0].label = user.email;
            var loveData = user.factors.LoveType.types;
            loveData.forEach(function (loveDatum) {
                data.datasets[0].data.push(loveDatum.percent);
            });
            data.datasets.splice(1);
            var chart = new Chart(ctx).Radar(data, options);
            return;
        }
        $scope.friend = true;
        data.datasets[0].label = user[0].email;
        var loveData = user[0].factors.LoveType.types;
        loveData.forEach(function (loveDatum) {
            data.datasets[0].data.push(loveDatum.percent);
        });

        data.datasets[1].label = user[1].email;
        var loveData = user[1].factors.LoveType.types;
        loveData.forEach(function (loveDatum) {
            data.datasets[1].data.push(loveDatum.percent);
        });
        var chart = new Chart(ctx).Radar(data, options);
    };
}
])
;