<!DOCTYPE html>
<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" errorPage="../error/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css"/>--%>

<html>
<head>
    <title>Авторизация</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="/assets/scripts/script.js"></script>
</head>
<body onload="setLocale()">
<%@include file="header/header.jsp" %>
<%--<c:set var="t" value="${textMap}"/>--%>
<%--${t.get('searchParameters')}--%>
<form name="findForm" method="POST" action="controller">
    <input type="hidden" name="command" value="selectHotel"/>
    <%--<label for="country">${t.get('country')}</label>--%>
    <input type="text" name="country" id="country" value="Belarus"/>
    <input type="text" name="city" id="city" placeholder="Город" value="Minsk"/>
    <input type="text" name="hotelName" id="hotelName" placeholder="Отель" value="any"/><br/>
    <label id="amountRooms" for="noRooms">${roomsAmount}</label>
    <select id="noRooms" name="totalRooms">
        <option value="1" selected="selected">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
    </select>
    <br/>
    <label for="noPersons">${personsAmount}</label>
    <select id="noPersons" name="totalPersons">
        <option value="1" selected="selected">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
        <option value="9">9</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
    </select>
    <br/>

    <label for="checkInDate">Дата въезда</label>
    <input id="checkInDate" type="text" name="checkInDate" value=""/>
    <label for="checkOutDate">Дата отъезда</label>
    <input id="checkOutDate" type="text" name="checkOutDate" value=""/>
    <input type="submit" value="Искать">
</form>


${operationMessage}

</body>

</html>