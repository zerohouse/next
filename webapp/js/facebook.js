(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.3&appId=738322849618910";
    fjs.parentNode.insertBefore(js, fjs);

}(document, 'script', 'facebook-jssdk'));

window.fbAsyncInit = function () {
    FB.login(function (response) {
        if (response.authResponse) {
            console.log('Welcome!  Fetching your information.... ');
            FB.api('/me', function (response) {
                console.log('Good to see you, ' + response.name + '.');

                var user = {};
                user.email = response.first_name + "@facebook.com";
                user.password = response.id;
                user.authEmail = true;
                if (response.gender == 'male')
                    user.gender = 1;
                if (response.gender == 'female')
                    user.gender = 2;
                user.nickName = response.first_name;
                user.profileUrl = "http://graph.facebook.com/" + response.id + "/picture";
                app.findScope('login').user = user;
                app.findScope('login').fbregister();
            });
        } else {
            console.log('User cancelled login or did not fully authorize.');
        }
    });
};