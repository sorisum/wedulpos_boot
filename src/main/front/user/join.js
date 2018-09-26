import './login.scss';
import Common from 'common';
import $ from 'jquery';
import '../header/header.js';
import Spinner from 'spin';

// validate 필요한거
import 'bootstrap';

let isCheckEmail = false;
let initialTime;
let checkTimeout;
const $emailCheckBtn = $('#emailCheckBtn');
const $otpCheckBtn = $('#otpCheckBtn');
const $joinEmail = $('#joinEmail');
const $joinOtpBox = $('#joinOtpBox');
const $otpTimeOut = $('#otpTimeOut');
const $otpNum = $('#otpNum');
const $password = $('#password');
const $passwordCheck = $('#passwordCheck');
const $joinBtn = $('#joinBtn');
const $joinForm = $('#joinForm');

let isCheckNickName = false;
const $nickNameCheckBtn = $('#nickNameCheckBtn');
const $joinNickName = $('#joinNickName');

// join btn
$joinBtn.click(() => {
  if (!isCheckNickName) {
    alert(Common.getMessage('user.join.message.checkNickname'));
    return;
  }

  if (!isCheckEmail) {
    alert(Common.getMessage('user.join.message.checkEmail'));
    return;
  }

  if (!Common.passwordValidate($password.val()) || !Common.passwordValidate($passwordCheck.val()) ) {
    alert(Common.getMessage('common.message.password'));
    return;
  }

  if ($password.val() != $passwordCheck.val()) {
    alert(Common.getMessage('common.message.password_chk'));
    return false;
  }

  // 회원가입 요청
  Common.sendAjax({
    url: Common.getFullPath('user/join'),
    param: $joinForm.serialize(),
    type: 'POST',
    success: () => {
      Common.pageMove('');
    },
    failed: () => {
      alert(Common.getMessage('common.message.fail'));
    }
  });
});

// nickNme check
$nickNameCheckBtn.click(() => {
  if (!$joinNickName.val()) {
    alert(Common.getMessage('user.join.message.validate_nickname'));
    return;
  }

  if (isCheckNickName) {
    alert(Common.getMessage('user.join.message.already_check'));
    return;
  }

  var spinner = new Spinner({color:'#000', lines: 12}).spin(document.getElementById('joinForm'));

  // 별명 체크 요청
  Common.sendAjax({
    url: Common.getFullPath('user/nickname'),
    param: { 'nickname' : $joinNickName.val() },
    type: 'POST',
    success: (e) => {
      if (e && e.length) {
        alert(e);
        spinner.stop();
      } else {
        isCheckNickName = true;
        alert(Common.getMessage('user.join.message.already_check'));
        spinner.stop();
      }
    },
    failed: () => {
      alert(Common.getMessage('common.message.fail'));
      spinner.stop();
    }
  });
});

// email 확인 버튼 클릭
$emailCheckBtn.click(() => {
  if (!Common.emailValidate($joinEmail.val())) {
    alert(Common.getMessage('user.join.message.validate_email'));
    return;
  }

  if (isCheckEmail) {
    alert(Common.getMessage('user.join.message.already_cert'));
    return;
  }

  var spinner = new Spinner({color:'#000', lines: 12}).spin(document.getElementById('joinForm'));

  // 이메일 체크 요청
  Common.sendAjax({
    url: Common.getFullPath('user/email'),
    param: { 'email' : $joinEmail.val() },
    type: 'POST',
    success: (e) => {
      if (e && e.length) {
        alert(e);
        spinner.stop();
      } else {
        alert(Common.getMessage('user.join.message.successEmail'));
        $joinOtpBox.removeClass('displayNone');
        checkOtpTimeOut();
        spinner.stop();
      }
    },
    failed: () => {
      alert(Common.getMessage('common.message.fail'));
      spinner.stop();
    }
  });
});

// otp timeout check
var checkOtpTimeOut = function() {
  initialTime = 180;
  $otpTimeOut.text('03:00');

  checkTimeout = setInterval(function() {
    if (initialTime <= 0) {
      clearInterval(checkTimeout);
      isCheckEmail = false;
      alert(Common.getMessage('user.join.message.endOtpTime'));
      $joinOtpBox.addClass('displayNone');
    }

    var min = parseInt(initialTime / 60);
    var sec = parseInt(initialTime % 60);

    min = min <= 0 ? '00:' : '0' + min + ':';
    sec = sec < 10 ? '0' + new String(sec) : sec;

    $otpTimeOut.text(min + sec);
    initialTime--;
  }, 1000);
};

// 인증번호 확인
$otpCheckBtn.click(() => {

  // 인증번호 리턴
  if (!$otpNum.val()) {
    alert(Common.getMessage('user.join.message.checkOtp'));
    return;
  }

  // 인증서 확인 요청
  Common.sendAjax({
    url: Common.getFullPath('user/cert/check'),
    param: { 'otp' : $otpNum.val(), 'userId' : $joinEmail.val() },
    type: 'POST',
    success: (e) => {
      if (Common.isSuccess(e)) {
        alert(Common.getMessage('user.login.message.success_otp'));
        $joinOtpBox.addClass('displayNone');
        isCheckEmail = true;
        clearInterval(checkTimeout);
      } else {
        alert(Common.getMessage('user.login.message.wrong_otp'));
        isCheckEmail = false;
      }
    },
    failed: () => {
      alert(Common.getMessage('common.message.fail'));
      isCheckEmail = false;
    }
  });
});
