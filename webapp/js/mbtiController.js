app.controller('mbtiController', ['$http', '$scope', function ($http, $scope) {
    var questions = ["나는 ____이 좋다.", "처음 만나는 사람을 보면 어떤 기분이 드나요?", "'해야할 일 목록'을 작성하는 것을 좋아하나요?", "다른 사람들은 나를 알기 쉬운 사람이라고 생각하나요?", "관심을 받는 느낌은 어떠한가요?", "나는 말할 때 생각하며 말을 하나요, 아니면 생각을 한 후에 말을 하나요?", "나는 '말'을 잘 하나요?", "나는 어떤 사람과도 수다를 떨 수 있나요?", "나의 사교성은?", "나의 관심은?", "일상 생활에서든 회사에서든 나는 ____이(가) 더 좋다.", "나의 취미는?", "나는 평소에 컴퓨터나 휴대폰 등으로 게임하는 것을 즐긴다.", "내가 더 믿는 것은?", "나의 성별은?", "나는 어떠한 사람인가요?", "만약 현실적인 한계가 없다면, 나는 어떤 일을 더 선호할까요?", "나는 어떠한 방식으로 무언가를 이해하고 설명하나요?", "나는 어떠한 사람인가요?", "나는 무엇을 하기를 좋아하나요?", "내가 더 중요히 여기는 것은?", "남이 하는 말을 들을 때면...", "나의 생활 방식은?", "내가 어떠한 이론을 이해할 때...", "나는...", "나는 평소에...", "나는 토론을 좋아하나요?", "나는 어떠한 사람인가요?", "나는 가끔...", "나의 생각을 표현할 때...", "다른 사람들과 함께 지낼 때...", "나를 표현할 때는...", "나에게 공평이란?", "표현할 때 가장 중요한 것은 무엇인가요?", "비평에 대해 어떻게 생각하나요?", "남의 눈에 나는...", "나는 _____에 따른다.", "내가 더 좋아하는 것은 무엇인가요?", "나의 집중력은?", "데드라인에 대한 나의 태도?", "나는 ____고 생각한다.", "무언가를 할 때....", "시간을 지키는 것은 중요하나요?", "나는 어디에서 힘을 얻나요?", "나는 계획 짜는 것을 좋아하나요?", "나만의 공간에서...", "일과 오락에 대한 나의 태도는?"];

    var answers = ["무엇이든 그대로 유지되는 것", "변화와 새로운 것", "새로운 만남을 시작하는 것은 매우 흥미로운 일이며, 어떤 사람과도 이야깃거리 있다.", "나는 모르는 사람들과 함께 있는 것이 불편하고 익숙하지 않다. 어떤 사람들은 내가 내성적이라고 생각하기도 한다.", "좋아한다. 내가 어떠한 일을 해 내었을 때 만족감과 성취감을 느끼며, 한가지 일을 마치고 다음 일을 시작한다.", "별로 좋아하지 않는다. 작성을 한다고 해도 목록대로 엄격히 실천하지 않는다. 그것은 그냥 나에게 주의를 주는 역할을 할 뿐이다.", "매우 알기 쉽다고 생각한다. 대부분의 사람들은 내가 우호적이고 활기차다고 생각한다.", "쉽게는 알지 못한다. 나는 먼저 나서지 않고 다른 사람이 어떤 제안을 할 때까지 기다리는 편이다.", "나는 관심의 중심이 되는 것을 즐긴다. 스포트라이트 받는 것이 좋다.", "나는 관심의 중심이 되는 것을 피하는 편이다.", "생각하며 말한다.", "생각 후 말한다.", "매우 잘 하는 편이다. 가끔은 내가 쉬지 않고 말해서 타인이 내 말을 끊어야만 멈추기도 한다.", "나는 타인과 오래 이야기하는 것을 좋아하지 않는다. 전화보다는 문자나 SNS를 사용하는 것이 더 편하다.", "난 언제 어디서나 누구랑도 열정적인 대화가 가능하다.", "나는 일대일 대화나 작은 범위 내에서 이야기 하는 것을 선호한다.", "나는 일명 '마당발'이라고 할 수 있을 만큼 아는 사람과 친한 사람이 많다.", "나는 소수의 사람들하고만 친하게 지내고, 친구를 선택할 때 매우 조심하는 편이다.", "더 큰 외부의 세계에 있다.", "나만의 세계에 있다. 나는 꽤 긴 시간을 자기반성 하는데 보낸다.", "팀워크(많은 사람들과 함께 토론하고 함께 일하는 것에서 큰 편안함을 느낀다.)", "내가 스스로 할 수 있는 일은 최대한 스스로 하고 나만의 공간과 나만의 자유를 느끼는 것", "매우 다양하다. 무엇에든 다 조금씩 관심이 있다.", "적다. 나는 한가지(혹은 소수)만 파는 것을 좋아한다.", "그렇다. 가상세계는 나에게 기쁨을 가져다 준다.", "그렇지 않다. 시간낭비이다.", "사실과 정확하고 뚜렷한 정보.", "나의 직감과 영감, 상상력, 그리고 통찰력.", "남", "여", "나는 상상속에 사는 사람이다. 나는 종종 머릿속의 다른 생각들에 심취되곤 한다. 현실에 별로 관심 갖지 않는다.", "나는 착실하고 진지하게 일하며，꿈을 꾸는 데 많은 시간을 할애하지 않는다.", "나는 복잡한 추상적인 이론을 연구하기보다는 구체적인 임무를 완성하는 것이 더 좋다.", "나는 반복적인 일보다는 연결성, 추세, 가능성 등을 발견하고 분석하는 것을 좋아한다.", "나는 그 자체의 실제 상황에 따라 이해하고, 환원하고, 묘사하기를 좋아한다. 나는 '그것이 어떻게 왔는가'가 더 중요하다.", "나는 은유와 비유하는 것을 좋아하고, '그것은 아마 어떠할 것이다'에 초점을 맞춘다.", "나는 정밀한 것이 좋은 뛰어난 관찰자이다. 나는 내 주변에 있는 실마리를 관찰하고 기억할 수 있다.", "나는 자유롭게 미래를 상상하는 것을 좋아한다. 언제나 나만의 규율을 찾으며 지루한 디테일은 싫다.", "나는 나의 능력을 이용하고 발전시키는 것이 좋다. 나는 가구를 해체하고 다시 조립하거나, 기계를 제조하거나, 직접 공예품을 만드는 등 몸소 체험하고 실천하는 일을 좋아한다.", "나는 내가 기존에 가지고 있는 능력에 만족하지 못하고 언제나 새로운 아이디어나 창조적인 방법을 기획하고 시도한다.", "경험.", "추론, 사물의 깊은 의미 찾아내기.", "나는 다른사람의 암시나 은유 등을 잘 이해하지 못한다.", "나는 말의 숨은 뜻을 쉽게 이해한다.", "현재를 즐기고 현재에 만족한다.", "항상 더 큰 미래를 꿈꾼다.", "나는 상대방이 구체적인 예와 함께 실제의 응용을 이야기 해주길 바란다.", "나는 어떠한 추상적인 이론을 신속히 이해하는 것이 좋다. 나에게 과도한 상세한 설명을 하거나 어떠한 사실을 묘사하는 것은 지루할 뿐만 아니라 그저 시간낭비일 뿐이다.", "전체가 아닌 디테일을 본다.", "디테일이 아닌 전체를 본다.", "객관적으로, 그리고 논리적으로 충분한 생각을 한 후 결정을 내리는 것을 좋아한다.", "내가 말하는 한마디 한마디와 내가 하는 모든 행위가 다른 사람들에게 어떠한 영향을 내릴 지 항상 생각한다.", "좋아하지 않는다. 누군가와 충돌하거나 남에게 상처주는 것이 두렵다.", "좋아한다. 나는 나의 주장을 펼치고 내 관점을 방어하는 것을 좋아한다.", "나는 강한 동정심, 자상함, 열정을 가지고 있다. 어떨 땐 사람들이 너무 감성적이라고 말하기도 한다.", "나는 냉정적이고 이성적이다. 남의 감정이나 누가 무얼 필요로 하는지 잘 알지 못한다.", "객관적인 사실을 과도하게 중시하는 반면 인정이 없을 때가 있다.", "다른 사람들을 과도하게 생각하고 심지어 남에게 이용당하거나 속기도 한다.", "남이 어떻게 생각할 지가 매우 중요하다.그래서 언제나 남들이 나의 생각을 맘에 들어하지 않는데 겉으로만 표출하지 않는 것이 아닌가 걱정한다.", "나는 직접적으로 솔직히 내 생각을 이야기한다. 다른 사람들의 생각이 나랑 같든 말든 상관없다.", "나는 그들의 생각과 아이디어가 매우 흥미롭지만 그들의 감정이나 기분에는 별로 민감하지 않다.", "그들은 나에게서 위로와 따뜻함을 찾아가곤 한다. 나 역시 그들에게 작게나마 힘이 되었으면 좋겠다고 생각한다.", "나는 나의 감정을 노출하는 것을 좋아하지 않는다.", "내가 무엇을 필요로 하는 지 입을 열기가 어려울 때가 있다. 특히 다른 사람과 의견 충돌이 있을 때면 나 자신을 희생시키기도 한다.", "누구에게나 다 똑같은 것. 모든 사람들이 다 같은 원칙과 방법으로 대우 받아야 한다.", "사람마다 다른 방식으로 대우받아야 하는 것. 각각 사람마다 사정이 다르기 때문이다.", "솔직함이 융통성보다 중요하다.", "융통성이 진실보다 중요하다. 하나의 생각을 표현하기 위해서 분위기를 깰 필요는 없다.", "남을 비평할 때 나는 망설이지 않는다. 나는 이것을 적극적인 의견이며 타인의 발전과 성장에 도움이 된다고 생각한다.", "남을 비평할 때 타인의 감정을 상하게 하므로 반드시 말해야 한다면 돌려서 말한다.", "나만의 생각이 뚜렷하고 다른사람이 어떻게 생각하든 별로 신경쓰지 않는 사람.", "친절하고 착하며 다른사람의 생각을 많이 신경쓰는 사람.", "대뇌의 지휘", "감정의 수요", "더 많은 정보를 얻은 후에 결정하는 것. 언제나 신중하게 생각하고 행동하기. 나는 새로운 무언가를 생각할 때 매우 기쁘고 흥분한다.", "신속한 결정과 완성. 무언가가 해결이 안된 채로 남아있는 것은 싫다. 모든 것을 다 끝내야만 편안함을 느낀다.", "나만의 일에 집중하기 쉽다. 주변이 어수선할 때면 나는 집중하기가 힘들고 정신적으로 긴장을 풀 수가 없다.", "집중을 할 수 없는 것은 아니지만 각각 다른 과목이나 종목, 프로젝트 등을 각 다른 방식으로 해 나간다.", "나는 나만의 일하는 방식과 과정이 있다. 데드라인 전 마지막 1초에 변화가 있거나 나의 일에 지장이 생기는 것은 끔찍하다.", "데드라인이란 언제든 바뀔 수 있는 대략적인 날짜일 뿐이다.  만약 마지막 1초에 변화가 생기거나 처음부터 다시 해야한다고 해도 별 상관은 없다.", "물건을 정리 없이 아무렇게나 놓는 것이 편하다.", "어떤 물건이든 각자 있어야 할 제 자리가 있다.", "무엇이든 미리미리 해놓는 것이 좋다. 심지어 마감일이 되기도 전에 뭐든 미리 다 해놓는다.", "나는 마감일이 다가올 때가 되어서야 시작한다. 가끔은 마감일 전에 끝내지 못하기도 한다.", "그렇게 중요하지는 않다. 시간은 반드시 지켜야 하는 눈금 같은 것이 아니다. 나에게 1시 45분은 1시 반이나 마찬가지고 시간은 그냥 대략적인 구간같은 것이다.", "매우 중요하다. 시간은 매우 정확한 눈금이고 1시반은 말 그대로 1시 반이다. 다른 사람이 시간을 중요하게 생각하지 않는 것은 나를 분노하게 한다.", "다른사람과 이야기하거나 모임을 가질 때. 그래서 나는 큰 모임에 자주 참여하고 많은 사람과 친분을 맺는다.", "혼자 있을 때. 그래서 나는 독거를 즐긴다. 나는 나만의 프라이버시가 있는 공간과 나만의 시간을 갖는 것이 필요하다.", "좋아한다. 게다가 난 그 계획을 고집해서 끝까지 해내고 만다. 계획이 없으면 불안함을 느낀다.", "그렇게 좋아하지는 않는다. 나는 계획없이 떠돌다 우연히 마주치는 기쁨같은 것을 즐긴다.", "나는 질서정연한 생활방식이 좋다. 어떤 사람은 내가 과도하게 체계적이라고 말한다.", "나는 이것저것 잘 빠뜨린다. 어떤 사람들은 나의 집이나 사무실을 보고 너무 무질서하다고 말할지도 모른다.", "나는 해야할 일을 모두 마친 후 여가시간을 갖는 것이 좋다.", "해야할 일을 하는 동시에 틈틈히 쉰거나 논다. 심지어 나의 여가시간은 일하는 시간 전에 배치되어 있기도 하다."];

    var types = ["S", "N", "E", "I", "J", "P", "E", "I", "E", "I", "E", "I", "E", "I", "E", "I", "E", "I", "E", "I", "E", "I", "E", "I", "A", "B", "S", "N", "Y", "X", "N", "S", "S", "N", "S", "N", "S", "N", "S", "N", "S", "N", "S", "N", "S", "N", "S", "N", "S", "N", "T", "F", "F", "T", "F", "T", "T", "F", "F", "T", "T", "F", "T", "F", "T", "F", "T", "F", "T", "F", "T", "F", "T", "F", "P", "J", "J", "P", "J", "P", "P", "J", "J", "P", "P", "J", "E", "I", "J", "P", "J", "P", "J", "P"];

    $scope.mbti = [];
    for (var i = 0; i < questions.length; i++) {
        var each = {};
        each.question = questions[i];
        each.answers = [];
        for (var j = 0; j < 2; j++) {
            var answer = {};
            answer.answer = answers[2 * i + j];
            answer.type = types[2 * i + j];
            each.answers.push(answer);
        }
        $scope.mbti.push(each);
    }

    $scope.types = {};
    $scope.types.E = '외향성(Extraversion)(E)';
    $scope.types.S = '감각(Sensing)(S)';
    $scope.types.F = '느낌(Feeling)(F)';
    $scope.types.J = '판단(Judging)(J)';
    $scope.types.I = '내향성(Introversion)(I)';
    $scope.types.N = '직감(iNtuition)(N)';
    $scope.types.T = '생각(Thinking)(T)';
    $scope.types.P = '인식(Perceiving)(P)';

    $scope.value = {E: 0, S: 0, F: 0, J: 0, I: 0, N: 0, T: 0, P: 0};

    $scope.$watch("mbti", computeType, true);

    function computeType() {
        $scope.value = {E: 0, S: 0, F: 0, J: 0, I: 0, N: 0, T: 0, P: 0};
        $scope.testDone = 0;
        $scope.mbti.forEach(function (each) {
            if (each.selectedType == undefined)
                return;
            $scope.testDone++;
            $scope.value[each.selectedType]++;
        });
        if ($scope.testDone != 47)
            return;
        $scope.userType = userType();
        var test = {};
        test.name = "MBTI";
        test.result = $scope.userType;
        $http(req("POST", "/api/user/login", {user: JSON.stringify(test)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            $scope.test = response.obj;
        });
    }

    var userType = function () {
        var type = [];
        if ($scope.value.E > $scope.value.I) {
            type.push("E");
        } else {
            type.push("I");
        }
        if ($scope.value.S > $scope.value.N) {
            type.push("S");
        } else {
            type.push("N");
        }
        if ($scope.value.F > $scope.value.T) {
            type.push("F");
        } else {
            type.push("T");
        }
        if ($scope.value.J > $scope.value.P) {
            type.push("J");
        } else {
            type.push("P");
        }
        return type.join("");
    };

    $scope.results = {};
    $scope.results.ISTJ = {};
    $scope.results.ISTJ.title = "소금형";
    $scope.results.ISTJ.subTitle = "나는 모든 것을 철저하게 절약합니다";
    $scope.results.ISTJ.description = "실제 사실에 대하여 정확하고 체계적으로 기억하며 일 처리에 있어서도 신중하며 책임감이 강하다. 집중력이 높으며 강한 현실감각으로 실질적이고 현실성 있게 조직적으로 일을 처리하며 직무가 요구하는 그 이상으로 일을 생각한다. 어지간한 위기 상태에서도 침착하게 보인다. 충동적으로 일을 처리하지 않으며 일관성 있고 관례적이고 보수적인 입장을 취하는 경향이 있다. 개인적인 반응을 얼굴에 잘 드러내지 않는다. 그러나 상황을 대단히 개인적으로 받아들인다. <br><br> 현재의 문제를 해결하는 데 있어 과거의 경험을 잘 적용하며, 반복되는 일상적인 일에 대한 인내력이 강하다. 때로 일의 세부사항에 집착하고 고집하는 경향이 있으나, 업무수행이나 세상일에 대처할 때 매우 확고하고 분별력이 있다.";
    $scope.results.ISTJ.celebrity = "조지 워싱턴, 조지 부시";

    $scope.results.ISTP = {};
    $scope.results.ISTP.title = "백과사전형";
    $scope.results.ISTP.subTitle = "나는 무엇이 문제인지 압니다";
    $scope.results.ISTP.description = "조용하고 말이 없으며 논리적, 분석적이고 객관적으로 인생을 관찰하는 형이다. 사실적인 정보를 조직하기 좋아하는 반면, 일과 관계되지 않은 이상 어떤 상황이나 다른 사람들의 일에 직접 뛰어들지 않는 경향이 있다. 필요 이상으로 자신을 개방하지 않으며, 가까운 친구들 외에는 대체로 사람들과 사귀지 않는다. 일상 생활에 있어 매우 적응력이 강하다. 과학분야, 기계 계통, 엔지니어링 분야에 관심이 많다. 기술적인 분야나 기계 분야에 흥미가 없는 경우에는, 비조직화된 사실들을 조직화하는 재능이 많으므로 법률, 경제, 마케팅, 판매, 통계 분야에 관심이 많다. <br><br> 논리적, 분석적이며 객관적으로 비판한다. 뚜렷한 사실에 기초를 둔 객관적 추론 이외에는 어떤 것에 의해서도 확신을 가지지 않는다. 열정적이지만 조용하고 호기심이 많으며 사람을 사귈 때 친한 친구들을 제외하고는 수줍어한다. 외부상황을 잊을 만큼 자신의 관심거리에 종종 깊이 몰두하기도 한다.";
    $scope.results.ISTP.celebrity = "톰 크루즈, 제임스 딘";

    $scope.results.ESTP = {};
    $scope.results.ESTP.title = "수완 좋은 활동가형";
    $scope.results.ESTP.subTitle = "모든 사람들이 너무 격식을 갖춘 것 같군요";
    $scope.results.ESTP.description = "관대하고 느긋하며 어떤 사람이나 사건에 대해서 별로 선입관을 갖지 않으며 개방적이다. 자신에게나 타인에게나 관용적이며, 일을 있는 그대로 보고 받아들인다. 그래서 갈등이나 긴장이 일어나는 상황을 잘 무마하는 능력이 있다. 꼭 이렇게 되어야 하고 저렇게 되어야 된다는 규범을 적용하기보다는 누구나 만족할 수 있는 해결책을 모색하고 타협하고 적응하는 힘이 있다.<br><br> 주의를 현재 상황에 맞추고 현실을 있는 그대로 보는 경향으로 인해 현실적으로 야기되는 문제의 해결에 뛰어난 능력을 발휘하기도 한다. 친구, 운동, 음식, 다양한 활동 등, 오관으로 보고 듣고 느끼고 만질 수 있는 생활의 모든 것을 즐기는 형이다. 특히 주어진 현실 상황과 그 순간에 무엇이 필요한지를 잘 감지하며 많은 사실들을 쉽게 기억한다. 예술적인 멋과 판단력을 지니고 연장이나 재료들을 다루는 데 능하다.<br><br> 개인의 느낌이나 주관적 가치에 기준을 두고 결정을 내리기보다 논리적, 분석적으로 일을 처리한다. 읽고 쓰는 것을 통해 배우기보다 직접적인 경험을 통해 배우는 것을 선호한다. 반면 추상적인 아이디어나 개념에 대해서는 흥미가 없다.";
    $scope.results.ESTP.celebrity = "고갱, 어니스트 헤밍웨이, 잭 니콜슨";

    $scope.results.ESTJ = {};
    $scope.results.ESTJ.title = "사업가형";
    $scope.results.ESTJ.subTitle = "실행만이 전부입니다";
    $scope.results.ESTJ.description = "일을 조직하고 프로젝트를 계획하고 출범시키는 능력이 있다. 현실적이고 사실적이며 체계적, 논리적으로 사업이나 조직체를 이끌어 나가는 타고난 재능을 가졌다. 혼돈스러운 상태나 불분명한 상태 또는 실용성이 없는 분야에는 큰 흥미가 없으나, 필요시에는 언제나 응용하는 힘이 있다.<br><br> 분명한 규칙을 중요시하고 그에 따라 행동하고 일을 추진하고 완성한다. 비합리적이고 일관성이 결여된 상황을 쉽게 파악하는 능력이 있으며, 어떤 계획이나 결정을 내릴 때 확고한 사실에 바탕을 두고 이행한다. 대체로 결과를 현재에 볼 수 있는 일을 즐기는 편이다. 사업이나 기업체, 조직체를 이끌며 행정 관리자로서 일의 목표를 설정, 지시하는 결정권을 이행하는 역할을 즐긴다.<br><br> 따라서 인간중심의 가치와 타인의 감정을 고려하며 타인의 말을 경청하는 노력이 필요하다. 이러한 면을 고려할 수 있을 때 행정가로서의 능력을 효율적으로 발휘할 수 있다.";
    $scope.results.ESTJ.celebrity = "해리 S 트루먼, 존 D 록펠러";

    $scope.results.ISFJ = {};
    $scope.results.ISFJ.title = "전통주의형";
    $scope.results.ISFJ.subTitle = "나는 가족을 위해 기꺼이 봉사합니다";
    $scope.results.ISFJ.description = "책임감이 강하고 온정적이며 헌신적이다. 세부적이고 치밀성과 반복을 요하는 일을 끝까지 해 나가는 인내력이 높다. 이들이 가진 침착성과 인내력은 가정이나 집단에 안정성을 가져다준다. 다른 사람의 사정을 고려하며 자신과 타인의 감정 흐름에 민감하다.<br><br> 일 처리에 있어서 현실감각을 가지고 실제적이고 조직적으로 이행한다. 경험을 통해서 자신이 틀렸다고 인정하기까지 어떠한 난관이 있어도 꾸준히 밀고 나가는 형이다. 많은 양의 사실을 기억하고 이용할 수 있지만, 그 사실이 모두 정확하기를 바라며 모든 것이 명확하게 쓰여진 것을 좋아한다. 위기 상황에 대처할 때에도 차분하며 안정되어 있다. 그들을 잘 알기 전까지는 그들이 어떤 상황에 대처할 때 외면의 차분함 뒤에 심할 정도로 개인적인 감정을 느끼고 있음을 알기가 어렵다.<br><br> 이 유형의 사람들은 일을 하고 세상일에 대처할 때 행동에 분별력이 있다. 대체로 열심이며 세부적인 사항과 절차에 세심하다. 일을 완성하기 위해서 필요한 세부적이고 사소한 일을 해낼 수 있고 또 그러려고 한다. 이들의 인내력은 연관된 모든 일을 안정시킨다.";
    $scope.results.ISFJ.celebrity = "찰스 디킨스, 마이클 조던";

    $scope.results.ISFP = {};
    $scope.results.ISFP.title = "성인군자형";
    $scope.results.ISFP.subTitle = "나는 즐거움과 기쁨을 추구합니다";
    $scope.results.ISFP.description = "마음이 따뜻하고 동정적이며, 말보다는 행동으로 따뜻함을 나타낸다. 그러나 상대방을 잘 알게 될 때까지 이 따뜻함을 잘 드러내지 않는다. 사람이나 일을 대하는데 있어 내적인 이상향과 개인적인 가치에 준하여 대하며, 말로 잘 표현하지 않는다. 자신의 주관이나 가치를 타인에게 요구하지 않으며 모든 성격유형 중에 자기 능력에 대해서 가장 겸손하다.<br><br> 적응력과 관용성이 많으며, 자연에 대한 사랑과 미적 감각과 균형 감각이 뛰어나다. 삶의 현재를 즐기는 형이다. 일의 목표 도달에 안달하지 않으며 여유를 즐긴다. 어떤 실제적 대가보다 인간을 이해하고 타인의 기쁨이나 건강 등에 공헌하는 일에 관심이 많다. 이 분야의 일을 신념을 가지고 할 때 헌신과 적응력을 보이며 서두르지 않으면서 완벽주의에 가깝게 일을 처리하는 경향이 있다.<br><br> 인간사를 다루는 데 있어 자신과 타인의 감정에 지나치게 민감할 수 있으며, 결정력과 추진력에 대해 고려할 줄 아는 것이 필요하다.";
    $scope.results.ISFP.celebrity = "베토벤, 마리 앙루와트, 마를린 먼로";

    $scope.results.ESFP = {};
    $scope.results.ESFP.title = "사교형";
    $scope.results.ESFP.subTitle = "특별히 남보다 더 친밀한 사람";
    $scope.results.ESFP.description = "친절하고 수용적이며, 현실적이고 실제적이다. 어떤 상황에도 잘 적응하고 타협적이다. 선입견이 별로 없으며 개방적, 관용적이고 대체로 사람들을 잘 받아들인다. 주위에서 진행되고 있는 일들을 알고자 하고 또한 열심히 참견하고 끼여든다. 다른 사람의 일이나 활동에 관심이 많으며 새로운 사건이나 물건에도 관심과 호기심이 많다.<br><br> 이론이나 책을 통해 배우기보다 실생활을 통해 배우는 것을 선호하며, 추상적 관념이나 이론보다는 구체적인 사실들을 잘 기억하는 편이다. 논리적 분석보다는 인간 중심의 가치에 따라 어떤 결정을 내린다. 그러므로 동정적이고 사람들에게 관심이 많고 재치 있고 꾀가 많다. 특히 사람들을 접하는 일에 능숙하고, 사람이나 사물을 다루는 사실적인 상식이 풍부하다.";
    $scope.results.ESFP.celebrity = "골디 혼, 밥 호프, 김경식";

    $scope.results.ESFJ = {};
    $scope.results.ESFJ.title = "친선도모형";
    $scope.results.ESFJ.subTitle = "누구보다 친절한 협력가";
    $scope.results.ESFJ.description = "동정심과 동료애가 많으며, 친절하고 재치가 있다. 다른 사람들에게 관심을 쏟고 인화를 도모하는 일을 중요하게 여긴다. 일상적인 일에 잘 적응하며 현실적이고 실제적이다. 물질적인 소유를 즐기기도 한다. 양심적이고 정리정돈을 잘하며 참을성이 많고 다른 사람들을 잘 돕는다. 또한 다른 사람들의 지지를 받으면 일에 더욱 열중하나 다른 사람들의 무관심한 태도에 민감하다.<br><br> 이들의 기쁨과 만족의 대부분은 주위 사람들이 감정에서 기인한 온정을 보여주는 것에서 온다. 이들은 다른 사람들의 존경할 만한 자질에 주의를 집중하는 경향이 있어서 존경받는 사람, 널리 알려진 기관이나 연구소, 대의명분에 경의를 나타내며, 그들이 존경, 칭찬하는 것이라면 무엇이든지 대체로 이상적인 것으로 받아들인다.<br><br> 따라서 비판과 객관성이 없이 다른 사람들의 의견에 동의하는 경향이나 다른 사람의 견해에 집착하는 경향을 보일 수 있다.";
    $scope.results.ESFJ.celebrity = "빌 클린턴, 데니 글로버";

    $scope.results.INFJ = {};
    $scope.results.INFJ.title = "예언자형";
    $scope.results.INFJ.subTitle = "내면의 정신력으로 삶의 여정을 이끈다";
    $scope.results.INFJ.description = "창의력과 통찰력이 뛰어나다. 강한 직관력으로 의미와 진실된 관계를 추구한다. 뛰어난 영감으로 말없이 타인에게 영향력을 미친다. 독창성과 사적인 독립심이 강하고, 확고한 신념과 뚜렷한 원리원칙을 생활 속에 가지고 있으면서 공동의 이익을 가져오는 일에 심혈을 기울이고 인화와 동료애를 중요시하는 경향을 가지고 있어 존경을 받고 사람들이 따른다.<br><br> 이 유형에는 열정과 신념으로 자신들이 믿는 영감을 구현시켜 나가는 정신적 지도자들이 많다. 남에게 강요하기보다는 행동과 권유로 사람들의 마음을 움직여 따르게 만드는 지도력이 있다.";
    $scope.results.INFJ.celebrity = "테레사 수녀, 마틴 루터 킹";

    $scope.results.INFP = {};
    $scope.results.INFP.title = "이상주의형";
    $scope.results.INFP.subTitle = "나는 어디에서도 완벽함을 발견할 수가 없노라";
    $scope.results.INFP.description = "마음이 따뜻하나 상대방을 잘 알기 전에는 표현을 잘하지 않는 조용한 사람들이다. 자신이 관계하는 사람이나 일에 대하여 책임감이 강하고 성실하다. 또한 자신이 지향하는 이상에 대해 정열적인 신념을 지니고 있다. 이들은 자신이 지닌 내적 성실성과 이상, 깊은 감정이나 부드러운 마음을 좀처럼 표현하지 않으나, 조용하게 생활 속에서 배어 나온다. 이해심과 적응력이 많고 대체로 관대하며 개방적이다. 그러나 내적인 신의가 위협을 당하면 한 치의 양보가 없다.<br><br> 남을 지배하거나 좋은 인상을 주고자 하는 경향이 거의 없다. 어떤 일에 깊이 관심을 가질 때 완벽주의적으로 나가는 경향이 있다. 노동의 대가를 능가해서 자신이 하는 일에 의미를 찾고자 하는 경향이 있으며 인간이해와 인간복지에 기여할 수 있는 일을 하기를 원한다.";
    $scope.results.INFP.celebrity = "윌리엄 세익스피어, 헬런 켈러";

    $scope.results.ENFP = {};
    $scope.results.ENFP.title = "스파크형";
    $scope.results.ENFP.subTitle = "매일이 새롭고 환상적인 가능성으로 벅찹니다";
    $scope.results.ENFP.description = "열성적이고 창의적이다. 풍부한 상상력과 영감을 가지고 새로운 프로젝트를 잘 시작한다. 풍부한 에너지를 가지고 순발력있게 일을 재빨리 해결하는 솔선 수범력과 상상력이 있다. 관심이 있는 일이면 무엇이든 척척 해내는 열성파이다. 뛰어난 통찰력으로 타인 안에 있는 성장 발전할 가능성을 들여다보며, 자신의 열성으로 다른 사람들도 어떤 프로젝트에 흥미를 가지게 하고 다른 사람들을 잘 도와준다. <br><br> 이들이 가지는 어려움 중의 하나는 반복되는 일상적인 일을 견디지 못해하는 경향이다. 새로운 가능성을 추구하고 창의적, 즉흥적으로 일을 시작하고 또 다른 일로 옮겨 다니며, 영감과 통찰력과 창의력이 요구되지 않는 일상적이고 세부적인 일에는 도무지 흥미와 열성을 불러일으키지 못한다. 그러나 어려움을 당할 때는 더욱 자극을 받고 어려움을 해결하는 데 매우 독창적으로 대응한다.";
    $scope.results.ENFP.celebrity = "로빈 윌리암스, 산드라 블록";

    $scope.results.ENFJ = {};
    $scope.results.ENFJ.title = "언변능숙형";
    $scope.results.ENFJ.subTitle = "모든 사람들이 삶의 충만한 기쁨을 누릴 수 있어야 합니다";
    $scope.results.ENFJ.description = "동정심과 동료애가 많으며 친절하고 재치 있고 인화를 아주 중요하게 여긴다. 그들은 주위의 사람들에게 주로 관심을 갖고 조화 있는 인간관계에 높은 가치를 두며, 우호적이다. 민첩하고 참을성이 많고 성실하다. 다른 사람들의 의견을 존중하고 그 가치를 본다. 공동선을 위해서는 대체로 상대방의 의견에 동의한다. 새로운 아이디어에 대한 호기심이 많다. 쓰기보다는 말로써 생각을 잘 표현한다. 편안하고 능란하게 계획을 제시하거나 조직을 이끌어 가는 능력이 있다.<br><br> 사교적이고 사람들을 좋아하며, 때로 다른 사람들의 좋은 점을 지나치게 이상화하고 맹목적 충성을 보이는 경향도 있다. 다른 사람들로부터 인정과 칭찬을 받으면 맡은 일에 더욱 열중하나 비판에 민감하다. 또한 이들은 끈기가 있고 성실하며 작은 일에도 순서를 따른다. 한편 다른 사람들에 대해서도 자기와 같을 것이라고 생각하는 경향이 있다. 또한 자신이 존경하는 인물, 제도 혹은 이념을 지나치게 이상화하는 경향도 있다.";
    $scope.results.ENFJ.celebrity = "로날드 레이건, 아브라함 링컨";

    $scope.results.INTJ = {};
    $scope.results.INTJ.title = "과학자형";
    $scope.results.INTJ.subTitle = "충분히 정당화되지 않았군요";
    $scope.results.INTJ.description = "행동과 사고에 있어 독창적이다. 내적인 신념과 가치관은 산이라도 움직일 만큼 강하다. MBTI 성격유형 중에서 가장 독립적이고 단호하며 문제에 대하여 고집이 세다. 자신이 가진 영감과 목적을 실현시키려는 의지와 결단력과 인내심을 가지고 있다. 자신과 타인의 능력을 중요시하며, 목적달성을 위하여 온 시간과 노력을 바쳐 일한다. 또 행동에서 뿐만 아니라 생각에 있어서도 냉철한 혁신을 추구하는 사람들이다. 그들은 이미 확립된 권위나 널리 수용된 신념들이 있음에도 불구하고, 진실과 의미를 추구하는 데 그들의 직관적인 통찰력을 발휘한다.<br><br> 이들은 이면을 탐색해내는 명철한 분석력 때문에 일과 사람을 있는 그대로 수용하고 음미하는 것이 어렵다. 그러므로 현실을 있는 그대로 보며 구체적이고 사실적인 면을 보고자 하는 노력이 필요하다. 또한 타인의 감정을 고려하고 타인의 관점에 귀기울이는 노력이 필요하다.";
    $scope.results.INTJ.celebrity = "줄리어스 시저, 제인 오스틴";

    $scope.results.INTP = {};
    $scope.results.INTP.title = "아이디어형";
    $scope.results.INTP.subTitle = "이론상 불가능한 것은 없다";
    $scope.results.INTP.description = "조용하고 과묵하나 관심이 있는 분야에 대해서는 말을 잘한다. 사람을 중심으로 한 가치보다는 ‘아이디어’에 관심이 많다. 매우 분석적이고 논리적이며 객관적 비평을 잘한다. 일의 원리와 인과관계에 관심이 많으며 실체보다는 실체가 안고 있는 가능성에 관심이 많다. 이해가 빠르며 높은 직관력으로 통찰하는 재능과 지적 관심이 많다. <br><br> 개인적인 인간관계나 파티 혹은 잡담에는 흥미가 별로 없다. 사람을 사귀는 데 있어서 보통 아이디어를 토론하고 나누는 소수의 가까운 사람들을 주위에 두고 있다. 어떤 때는 아이디어에 몰입하여 주위에서 돌아가고 있는 일을 모를 때가 많다. 이들의 주된 관심은 현재 명확하고 이미 알려진 것을 넘어선 가능성을 보는 것에 있고, 이들의 직관력은 그들의 통찰력, 연구심, 지적인 호기심을 고조시킨다.";
    $scope.results.INTP.celebrity = "아이작 뉴턴, 소크라테스";

    $scope.results.ENTP = {};
    $scope.results.ENTP.title = "발명가형";
    $scope.results.ENTP.subTitle = "새로운 아이디어마다 추진력을 발휘합니다";
    $scope.results.ENTP.description = "독창적인 혁신가로, 창의력이 풍부하며 항상 새로운 가능성을 찾고 새로운 시도를 하는 유형이다. 넓은 안목을 가지고 있으며 다방면에 재능이 많다. 풍부한 상상력과 계획을 시도하는 솔선력이 강하며 민첩하고 여러 가지 일에 재능을 발휘하고 자신감이 많다. <br><br> 사람들의 동향에 대해 기민하고 박식하며, 다른 사람을 판단하기보다는 이해하려고 노력한다. 또한 복잡한 문제 해결에 뛰어난 재능을 지녔고 지칠 줄 모르는 에너지를 소유하고 있으며 새로운 관심사로 눈을 돌리고 새 프로젝트를 시작하는 가운데서 끊임없이 에너지 충전을 받는다.";
    $scope.results.ENTP.celebrity = "토마스 에디슨, 알프레드 히치콕";

    $scope.results.ENTJ = {};
    $scope.results.ENTJ.title = "지도자형";
    $scope.results.ENTJ.subTitle = "지도자는 험한 일을 필요로 한다";
    $scope.results.ENTJ.description = "활동적인 사람이며, 행정적인 일과 장기계획을 선호하며 논리적이고 분석적이다. 사전 준비를 철저히 하며 계획, 조직하고 체계적으로 목적달성을 추진하는 지도자들이 많다. 비능률적이거나 확실치 않는 상황에 대해서는 별로 인내심이 없다. 그러나 상황이 필요로 할 때는 강하게 대처한다.<br><br> 솔직하고 결정력과 통솔력이 있으며 거시적 안목으로 일을 밀고 나간다. 관념 자체에 집중하는 경향이 있으며 관념 이면의 사람에는 관심이 별로 없다. 새로운 지식에 대한 관심이 많으며 복잡한 문제나 지적인 자극을 주는 새로운 아이디어에 호기심이 많다. 때로 현 상황이 처해 있는 현실적 사항들을 쉽게 지나쳐 버리는 경향과 성급하게 일을 추진시키는 경향이 있다.<br><br> 그러므로 이들에게는 현실이 안고 있는 치밀한 상황을 있는 그대로 볼 줄 알고 다른 사람들의 의견에 귀를 기울임이 필요하다. 또한 자신과 타인의 감정에 관심을 갖고 자신의 느낌이나 감정을 인정하고 표현하는 것이 필요하다.";
    $scope.results.ENTJ.celebrity = " 빌 케이츠, 우피 골드버그";

}]);


/**
 * Created by park on 15. 4. 1..
 */
