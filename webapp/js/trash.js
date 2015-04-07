/**
 * Created by park on 15. 4. 7..
 */

app.directive('chart', function () {
    return {
        restrict: 'A',
        scope: {
            user: "=chart",
            show: "=ngShow"
        },
        link: function (scope, element, attrs) {
            var ctx = element[0].getContext("2d");

            scope.$watch('show', function () {
                if (!scope.show)
                    return;
                if(this.draw)
                    return;
                setTimeout(function () {
                    drawChart(ctx, scope.user);
                    this.draw = true;
                }, 300);
            });

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
                if (user.length != 2) {
                    data.datasets[0].label = user.email;
                    var loveData = user.factors.LoveType.types;
                    loveData.forEach(function (loveDatum) {
                        data.datasets[0].data.push(loveDatum.percent);
                    });
                    data.datasets.splice(1);
                    var chart = new Chart(ctx).Radar(data);
                    return;
                }

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
                var chart = new Chart(ctx).Radar(data);
            };
        }
    }
});
