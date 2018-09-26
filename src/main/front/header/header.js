import './header.scss';
import $ from 'jquery';
import Common from 'common';
import '../common/baselayout.js';
import 'jquery-ui';

// validate 필요한거
import 'bootstrap';

const $headerLogo = $('#headerLogo');
const $loginButton = $('.loginButton');
const $logoutButton = $('#logoutBox');
const $loginUserTxt = $('#loginUserTxt');
const $userInfoDialog = $('#userInfoDialog');
const $isSnsLogin = $('#isSnsLogin');

// weather location
const weatherLocations = [
  {'common.message.location.seoul' : '1835848'},
  {'common.message.location.incheon' : '1843561'},
  {'common.message.location.daejeon' : '1835235'},
  {'common.message.location.jeju' : '1846266'},
  {'common.message.location.busan' : '1838524'},
  {'common.message.location.gwangju' : '1841811'}
];
const $weatherMessage = $('#weatherMessage');
const $weatherIcon = $('#weatherIcon');
let count = 0;

const token = $("meta[name='_csrf']").attr("content");
const token_header = $("meta[name='_csrf_header']").attr("content");

// 비밀번호 변경
const $userInfoId = $('#userInfoId');
const $userInfoPasswd = $('#userInfoPasswd');
const $userInfoPasswdConfirm = $('#userInfoPasswdConfirm');
const $loginType = $('#loginType');

window.fbAsyncInit = function() {

    FB.init({
        appId      : '255697038387228',
        cookie     : true,  // enable cookies to allow the server to access
                            // the session
        xfbml      : true,  // parse social plugins on this page
        version    : 'v3.1' // use graph api version 2.8
    });
    // Now that we've initialized the JavaScript SDK, we call
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.

    FB.AppEvents.logPageView();
};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


// 날씨 표시
const weatherDisplay = () => {
  Common.sendAjax({
    url: Common.getFullPath('weather'),
    type: 'GET',
    success: (e) => {
      getWeaterData(e);
      setInterval(function() {
        getWeaterData(e);
      }, 5000);
    }
  });
};

// 페이스북 계정 로그아웃
const faceBookLogOut = function(callback) {
    FB.getLoginStatus(function(response) {
      if (response.status === 'connected') {
          FB.logout(function(response) {
              callback();
          });
      } else {
          callback();
      }
    });
};

// logout;
const logout = function() {
    let param = {};
    param[`${token_header}`] = token;

    // 로그아웃
    Common.sendAjax({
        url: Common.getFullPath('user/logout'),
        type: 'POST',
        param,
        success: () => {
            Common.pageMove('');
        },
        failed: () => {
            alert(Common.getMessage('common.message.fail'));
        }
    });
};

// logout button 클릭 시
$logoutButton.click(() => {
  if (confirm(Common.getMessage('common.message.account.logout'))) {
    let loginType = $loginType.val();

    switch (loginType) {
        case 'FACE_BOOK':
            faceBookLogOut(logout);
            break;

        case 'NORMAL':
        default:
          logout();
          break;
    }
  }
});

// 날씨 데이터 가져오기 함수
const getWeaterData = (data) => {
  $weatherIcon.prop('src', `https://openweathermap.org/img/w/${data[count].icon}.png`);
  $weatherMessage.text(Common.getMessage(Object.keys(weatherLocations[count])[0]) + ' ' + Number(data[count].temp - 273.15).toFixed(2) + ' ℃' );

  if (count >= 5) {
    count = 0;
  } else {
    count++;
  }
};

weatherDisplay();

// header 페이지 이동
$headerLogo.click(function() {
  Common.pageMove('');
});

// 로그인 회원가입 페이지 이동
$loginButton.click(function(e) {
  switch (e.currentTarget.id) {
    case 'loginBox':
      Common.pageMove('user/login');
      break;
    case 'joinBox':
      Common.pageMove('user/join');
      break;
    default:
      break;
  }
});

// 비밀번호 validate
const checkUserInfoValidate = function() {
  // 비밀번호를 입력하지 않았을 떄
  if (!$userInfoPasswd.val() || !$userInfoPasswdConfirm.val()) {
    alert(Common.getMessage('common.message.password_input'));
    return false;
  }

  // 비밀번호가 규칙에 안맞을 때
  if (!Common.isContainUpperCase($userInfoPasswd.val()) || $userInfoPasswd.val() <= 7) {
    alert(Common.getMessage('common.message.password'));
    return false;
  }

  // 비밀번호가 일치하지 않을 때
  if ($userInfoPasswd.val() != $userInfoPasswdConfirm.val()) {
    alert(Common.getMessage('common.message.password_chk'));
    return false;
  }

  return true;
};

const isSnsLogin = function() {
  return $isSnsLogin.val() == 'true';
};

// 로그인 유저 선택시 출력되는 다이얼로그
$loginUserTxt.click(() => {

  if (isSnsLogin()) {
    alert(Common.getMessage('common.message.sns_info_edit'));
    return;
  }

  $userInfoDialog.dialog({
      height: 320,
      width: 410,
      title: Common.getMessage('common.message.show.user'),
      resizable:true,
      draggable:true,
      open: () => {

      },
      close: () => {
        $userInfoPasswdConfirm.val('');
        $userInfoPasswd.val('');
      },buttons: [
        {
          text: Common.getMessage('common.message.confirm'),
          icon: "ui-icon-check",
          click: function() {
            if (checkUserInfoValidate()) {
              let param = {'email' : $userInfoId.val(), 'password' : $userInfoPasswd.val()};
              param[`${token_header}`] = token;

              // 임시 비밀번호 발급 요청
              Common.sendAjax({
                url: Common.getFullPath('user/password'),
                param,
                type: 'POST',
                success: (e) => {
                  if (e && e.length) {
                    alert(e);
                  } else {
                    alert(Common.getMessage('common.message.account.password_change_success'));
                    Common.sendAjax({url: Common.getFullPath('user/logout'), param, success: () => {
                      Common.pageMove('');
                    }});
                  }
                },
                failed: () => {
                  alert(Common.getMessage('common.message.fail'));
                }
              });
            }
          }
        },
        {
          text: Common.getMessage('common.message.cancel'),
          icon: "ui-icon-cancel",
          click: function() {
            $(this).dialog( "close" );
          }
        }
      ]
  });
});
