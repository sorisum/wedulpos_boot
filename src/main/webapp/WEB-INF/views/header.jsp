<%@ page language="java" contentType="text/html; charset=EUC-KR"
         pageEncoding="EUC-KR" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.parameterName}"/>
<div id="headerLogo"></div>
<div class="headerBody">

    <div class="weatherArea">
        <img id="weatherIcon"></img>
        <span id="weatherMessage"></span>
    </div>

    <!-- login Before Area -->
    <div id="loginBeforeArea" class="loginArea ${fn:length(loginUser) > 0 ? 'displayNone' : ''}">
        <span id="loginBox" class="loginButton"><spring:message code="common.message.login"/></span>
        <span id="joinBox" class="loginButton"><spring:message code="common.message.login.join"/></span>
    </div>


    <!-- login after area -->
    <input type="hidden" id="loginUser" value="${loginUser}"/>
    <input type="hidden" id="loginType" value="${loginType}"/>
    <input type="hidden" id="isSnsLogin" value="${isSnsLogin}"/>

    <div id="loginAfterArea" class="loginArea ${fn:length(loginUser) > 0 ? '' : 'displayNone'}">
        <div id="loginUserIdBox">
            <span class="far fa-user"></span>
            <span id="loginUserTxt">${loginUser}</span>
        </div>
        <div id="logoutBox">
            <span class="fas fa-power-off"></span>
            <span><spring:message code="common.message.login.logout"/></span>
        </div>
    </div>
    <!-- User Info Dialog -->
    <div id="userInfoDialog" class="displayNone">
        <form id="userInfoForm" class="form-signin" onsubmit="return false;" method="post">

            <!-- nickname -->
            <div class="userInfoDiv">
                <label for="userInfoNickname" class="userInfoLabel"><spring:message
                        code="common.message.account.nickname"/></label>
                <input type="text" id="userInfoNickname" value="${loginUserNickname}" class="userInfoText" disabled
                       required="">
            </div>

            <!-- id -->
            <div class="userInfoDiv">
                <label for="userInfoId" class="userInfoLabel"><spring:message code="common.message.account.id"/></label>
                <input type="text" id="userInfoId" value="${loginUser}" class="userInfoText" disabled required="">
            </div>

            <!-- password -->
            <div class="userInfoDiv">
                <label for="userInfoPasswd" class="userInfoLabel"><spring:message
                        code="common.message.account.passwd"/></label>
                <input type="password" id="userInfoPasswd" value="" class="userInfoText" required="">
            </div>

            <!-- password confirm -->
            <div class="userInfoDiv">
                <label for="userInfoPasswdConfirm" class="userInfoLabel"><spring:message
                        code="common.message.account.passwd_confirm"/></label>
                <input type="password" id="userInfoPasswdConfirm" value="" class="userInfoText" required="">
            </div>
        </form>
    </div>
</div>
