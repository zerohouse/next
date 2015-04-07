/**
 * Created by park on 15. 4. 4..
 */


app.controller('ennController', ['$http', '$scope', function ($http, $scope) {

    var test = {};
    test.a = [];
    test.a.push({question: "나는 독립적인 편이고 자기주장을 잘 한다."});
    test.a.push({question: "나는 목표를 설정하고 그 일을 추진해 나간다. 그리고 그것이 성취되기를 원한다."});
    test.a.push({question: "나는 가만히 앉아 있는 것을 좋아하지 않는다."});
    test.a.push({question: "나는 큰 일을 성취하고 영향력을 행사하기를 원한다."});
    test.a.push({question: "나는 정면 대결을 원하지는 않지만 사람들이 나를 통제하는 것도 좋아하지 않는다."});
    test.a.push({question: "대개의 경우 나는 내가 원하는 것을 잘 알고 있다."});
    test.a.push({question: "나는 일도 노는 것도 열심히 한다."});

    test.b = [];
    test.b.push({question: "나는 조용하게 혼자 있는 것을 좋아한다."});
    test.b.push({question: "나는 사회적인 활동에 주의를 쏟지 않으며 대체로 내 의견을 강하게 주장하지 않는다."});
    test.b.push({question: "나는 앞에 나서거나 다른 사람과 경쟁하는 것을 그리 좋아하지 않는다."});
    test.b.push({question: "사람들은 나를 몽상가라고 말한다."});
    test.b.push({question: "내 상상의 세계 안에서는 많은 흥미로운 일들이 벌어진다."});
    test.b.push({question: "나는 적극적이고 활동적이기라기보다는 조용한 성격이다."});

    test.c = [];
    test.c.push({question: "나는 아주 책임감이 강하고 헌신적이다."});
    test.c.push({question: "나는 내 의무를 다하지 못할 때 아주 기분이 나쁘다."});
    test.c.push({question: "나는 사람들이 필요할 때 그들을 위해 내가 그 자리에 있다는 것을 알아 주었으면 좋겠다."});
    test.c.push({question: "나는 그들을 위해 최선을 다할 것이다."});
    test.c.push({question: "이따금씩 나는 사람들이 나를 알아 주든 알아 주지 않든 그들을 위해 큰 희생을 한다."});
    test.c.push({question: "나는 내 자신을 제대로 돌보지 않는다."});
    test.c.push({question: "나는 해야 할 일을 한 다음에 시간이 나면 휴식을 취하거나 내가 원하는 일을 한다."});

    test.x = [];
    test.x.push({question: "나는 대개 긍정적인 자세로 생활하며, 모든 일이 나에게 유리한 쪽으로 풀린다고 느낀다."});
    test.x.push({question: "나는 나의 열정을 쏟을 수 있는 여러 가지 방법을 찾는다."});
    test.x.push({question: "나는 사람들과 함께 하고 사람들이 행복해지도록 돕는 것을 좋아한다."});
    test.x.push({question: "나는 나와 마찬가지로 다른 사람들도 잘 지내기를 바란다.(항상 기분이 좋은 것은 아니다. 그러나 나는 다른사람에게 그렇게 보이기를 원한다)"});
    test.x.push({question: "나는 다른 사람들에게 항상 긍정적으로 보이고자 노력하기 때문에 때로는 내 자신의 문제를 다루는 것을 미루기도 한다."});

    test.y = [];
    test.y.push({question: "나는 어떤 것에 대해 강한 감정을 갖는다."});
    test.y.push({question: "대부분의 사람들은 내가 모든 것에 대해 불만을 갖고 있다고 생각한다."});
    test.y.push({question: "나는 사람들과 함께 있을 때 그들이 어떤 사람인지, 무엇을 기대할 수 있는지를 알기 원한다."});
    test.y.push({question: "어떤 일에 내가 화가 났을 때 나는 사람들이 그것에 대해 반응하고 나만큼 그 일을 해결하려고 노력해 주기를 원한다."});
    test.y.push({question: "나는 규칙을 알고 있다. 하지만 사람들이 내게 무엇을 하라고 지시하는 것을 좋아하지 않는다."});
    test.y.push({question: "나는 내 스스로 결정하기를 원한다."});

    test.z = [];
    test.z.push({question: "나는 스스로를 잘 통제하고 논리적이다."});
    test.z.push({question: "나는 느낌을 다루는 것을 편안해하지 않는다. 나는 효율적이고 완벽하게 일을 처리하며 혼자 일하는 것을 좋아한다."});
    test.z.push({question: "문제나 개인적인 갈등이 있을 때 나는 그 상황에 감정이 끼여들지 않도록 한다."});
    test.z.push({question: "어떤 사람들은 내가 너무 차고 초자연하다고 말하지만 나는 감정 때문에 중요한 일을 그르치고 싶지 않다."});
    test.z.push({question: "나는 사람들이 나를 화나게 할 때 대부분의 경우 반응을 보이지 않는다."});

    $scope.test = test;


    $scope.result = function get_type(str) {
        var a = 0;
        var undefinecount = 0;
        $scope.test.a.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                a++;
        });

        var b = 0;
        $scope.test.b.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                b++;
        });

        var c = 0;
        $scope.test.c.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                c++;
        });

        var x = 0;
        $scope.test.x.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                x++;
        });

        var y = 0;
        $scope.test.y.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                y++;
        });

        var z = 0;
        $scope.test.z.forEach(function (q) {
            if (q.answer == undefined) {
                undefinecount++;
                return;
            }
            if (q.answer == 'y')
                z++;
        });

        if (undefinecount != 0)
            return 0;

        var abc = Math.max(a, b, c);
        var abcString;
        if (abc == a)
            abcString = 'a';
        if (abc == c)
            abcString = 'c';
        if (abc == b)
            abcString = 'b';

        var xyz = Math.max(x, y, z);
        var xyzString;
        if (xyz == y)
            xyzString = 'y';
        if (xyz == x)
            xyzString = 'x';
        if (xyz == z)
            xyzString = 'z';

        var result;
        switch (abcString + xyzString) {
            case 'ax':
                result = 7;
                break;
            case 'ay':
                result = 8;
                break;
            case 'az':
                tmp = 3;
                result;
            case 'bx':
                result = 9;
                break;
            case 'by':
                result = 4;
                break;
            case 'bz':
                result = 5;
                break;
            case 'cx':
                result = 2;
                break;
            case 'cy':
                result = 6;
                break;
            case 'cz':
                result = 1;
                break;
        }

        if (this.sended)
            return result;
        this.sended = true;
        var test = {};
        test.name = "EnneaGram";

        var gender = app.findController('userController').user.gender;
        if (gender == 1) {
            gender = 'M'
        } else if (gender == 2) {
            gender = 'F'
        }
        test.result = gender + result;
        $http(req("POST", "/api/test", {test: JSON.stringify(test)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            app.findController('userController').refresh();
        });
        return result;
    }

    $scope.results = {};
    $scope.results[1] = {};
    $scope.results[1].title = "개혁가(The Reformer)";
    $scope.results[1].description = "1번 유형들은 그것이 얼마만큼이든지 자신이 미칠 수 있는 영향력을 사용하여 세상을 개선시키고자 하는 사명감을 갖고 있끼 때문에 우리는 1번유형에게 개혁가라는 이름을 붙였다. 이들은 역경을 극복하려고 노력한다. 역경의 극복을 통해서 인간의 정신은 빛날 수 있다. 이들은 큰 희생을 치르고라도 높은 이상을 실현시키기 위해 노력한다.";
    $scope.results[2] = {};
    $scope.results[2].title = "돕고자 하는 사람(The Helper)";
    $scope.results[2].description = "우리는 이 유형에게 돕고자 하는 사람이라고 이름 붙였다. 이 유형의 사람들은 다른 사람들에게 정말로 도움이 되기 때문이다. 이들은 건강하지 않은 상태에 있을 때 자신을 도움을 주는 사람으로 보는 데 많은 관심을 쏟는다. 2번 유형은 다른 사람을 너그럽게 대하고 사람들을 위해서 뭔가를 할 때 삶을 가장 의미 있고 풍요롭게 느낀다. 사람들에 대한 이들의 사랑과 관심, 그리고 친절은 이들 자신의 가슴을 따뜻하게 하고 가치 있는 사람이라고 느끼게 해 준다. 2번 유형은 사랑, 친밀함, 가족, 우정과 같이 삶에서 정말로 기분 좋게 느껴지는 것들에 많은 관심을 쏟는다.";
    $scope.results[3] = {};
    $scope.results[3].title = "성취하는 사람(The Achiever)";
    $scope.results[3].description = "우리는 3번 성격 유형에게 성취하는 사람이라는 이름을 붙였다. 건강할 때 삶의 많은 영역에서 성공을 이룰 수 있기 때문이다. 이들은 인간 본성의 '별'이다. 사회적으로 많은 것을 성취하기 때문에 사람들로부터도 존경을 받는다. 건강한 3번 유형은 어떻게 자신을 개발하고 세상을 위해서 자신의 능력을 사용해야하는지를 안다. 또한 이들은 다른 사람들을 격려해서 그들 스스로가 생각하는 것보다 훨씬 더 많은 능력을 끌어 낼 수 있다. 이들은 사회에서 존경받을 수 있는 자질을 갖고 있다. 사람들은 이들에게서 자신의 꿈과 희망을 본다.";
    $scope.results[4] = {};
    $scope.results[4].title = "개인주의자(The Individualist)";
    $scope.results[4].description = "우리는 이 유형에게 개인주의자라는 이름을 붙였다. 4번 유형은 자신이 다른 사람들과 기본적으로 다르다고 생각함으로써 자신의 정체성을 유지하기 때문이다. 자신이 다른 사람들과 다르며, 그렇기 때문에 아무도 자신을 이해하고 사랑하지 않는다고 느낀다. 이들은 자신에게는 특별한 재능과 특별한 결함이 동시에 있다고 여긴다. 4번 유형은 다른 어떤 유형보다도 자신의 개성과 자신의 결함을 잘 이해하고 있다.";
    $scope.results[5] = {};
    $scope.results[5].title = "탐구자(The Investigator)";
    $scope.results[5].description = "우리는 5번 유형을 탐구자라고 이름 붙였다. 5번 유형은 다른 어떤 유형보다도 일이 일어나는 방식에 대해 알고 싶어하기 때문인다. 이들은 우주, 동물계, 식물계, 광물계, 그리고 내면의 세계까지 모든 세계가 어떻게 움직여지는지 알고 싶어한다. 항상 뭔가를 추구하고 질문을 던지고 깊이 탐구해 들어간다. 이들은 일반적으로 받아들여지고 있는 의견과 학설을 받아들이지 않으며 자기 나름대로 검증해 보아야 한다고 생각한다.";
    $scope.results[6] = {};
    $scope.results[6].title = "충실한 사람(The Loyalist)";
    $scope.results[6].description = "우리는 6번 유형에게 충실한 사람이라고 이름 붙였다. 모든 성격 유형 중에서 6번 유형은 친구나 자기가 믿는 신념에 가장 충실한 사람들이기 때문인다. 이들은 다른 어떤 유형들보다도 관계를 오래 지속시킨다. 또한 6번 유형은 이상, 체제, 신념 등에도 충실하다. 그러면서도 모든 6번 유형이 있는 그대로의 현상에 만족하는 것은 아니다. 이들의 신념은 반항적이고 체제에 반대되는 것이거나, 심지어 혁명적일 수도 있다. 어떤 경우라도 이들은 아주 격렬하게 자신의 신념을 위해 싸운다. 이들은 자신보다 자신의 지역 사회나 가족을 보호하는 마음이 더 강하다.";
    $scope.results[7] = {};
    $scope.results[7].title = "열정적인 사람(The Enthusiast)";
    $scope.results[7].description = "7번 유형은 자신의 주위를 끄는 거의 모든 것에 대해 열정적이기 때문에 우리는 이 유형에게 열정적인 사람이라는 이름을 붙였다. 이들은 자신이 경험하는 모든 재미있는 것에 대해 어린아이와 같은 기대로 가득 차 있으며 호기심과 낙천주의, 모험심을 가지고 삶에 접근한다. 이들은 대담하고 쾌활하며 삶에서 자신이 원하는 것을 좇는다.";
    $scope.results[8] = {};
    $scope.results[8].title = "도전하는 사람(The Challenger)";
    $scope.results[8].description = "우리는 8번 유형에게 도전하는 사람이라고 이름을 붙였다. 이들은 스스로가 도전하는 것 뿐 아니라 다른 사람들도 어떤 일에 도전해서 자신의 능력 이상의 일을 해 내도록 격려하는 것을 즐기기 때문이다. 이들은 사람들을 설득해서 온갖 종류의 일, 회사를 시작하는 것, 도시를 건설하는 것, 집안을 꾸려 나가는 것, 전쟁을 하는 것, 평화를 이루는 것 등을 할 만한 카리스마와 신체적, 심리적 능력을 가지고 있다.";
    $scope.results[9] = {};
    $scope.results[9].title = "평화주의자(The Peacemaker)";
    $scope.results[9].description = "우리는 9번 유형을 평화주의자라고 부른다. 이들이 다른 어떤 유형보다도 스스로와 다른 사람들을 위해서 내면과 외부의 평화를 추구하려고 애쓰기 때문이다. 이들은 다른 사람들과 혹은 우주와의 연결을 갈망하는 구도자가 될 수도 있다. 자신의 세상에서 조화와 평화를 이루기 위해 노력하는 것처럼 마음의 평화를 이루기 위해 노력한다. 9번 유형에게서 만나는 문제들은 내면의 성장을 추구함에 있어 우리가 생각해 보아야 할 주제들이다. 깨어남과 본성을 보지 못하고 잠들어 있기, 현재에 존재하기와 정신을 놓고 있기, 긴장과 이완, 평화와 고통, 통합과 분리 등.";

}]);

