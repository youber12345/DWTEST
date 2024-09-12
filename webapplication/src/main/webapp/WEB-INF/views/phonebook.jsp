<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>전화번호 관리 시스템</title>
    <style>
        body {
            font-family: 'Nanum Gothic', sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        h2 {
            color: #2db400;
            text-align: center;
            margin-bottom: 20px;
        }
        .container {
            width: 500px;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            padding: 20px;
        }
        .input-section, .output-section, .selected-section {
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            margin-bottom: 10px;
            font-size: 14px;
        }
        button {
            background-color: #2db400;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        button:hover {
            background-color: #28a700;
        }
        .output-section div, .selected-section div {
            padding: 10px;
            background-color: #f4f4f4;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        .output-section div a {
            text-decoration: none;
            color: #2db400;
            font-weight: bold;
        }
        .output-section div a:hover {
            text-decoration: underline;
        }
        .selected-section {
            border-top: 2px solid #2db400;
            padding-top: 20px;
        }
        .selected-section div {
            color: #555;
        }
        .selected-section form {
            display: flex;
            flex-direction: column;
        }
        .selected-section button {
            background-color: #ff6b6b;
            margin-top: 10px;
        }
        .selected-section button[type="submit"]:first-of-type {
            background-color: #2db400;
        }
        .selected-section button:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>전화번호 관리 시스템</h2>

        <!-- 1) 입력 기능 -->
        <div class="input-section">
            <form action="addPhonebook" method="post">
                <label>이름: <input type="text" name="name" required></label>
                <label>전화번호: <input type="text" name="hp" required></label>
                <label>메모: <input type="text" name="memo"></label>
                <button type="submit">입력</button>
            </form>
        </div>

        <!-- 2) 전체 출력 기능 / 3) 검색 출력 기능 -->
        <div class="output-section">
            <form action="searchPhonebook" method="get">
                <label>검색: <input type="text" name="search" placeholder="이름 또는 전화번호로 검색"></label>
                <button type="submit">검색</button>
            </form>

            <c:forEach var="entry" items="${phonebookList}">
                <div>
                    <a href="selectPhonebook?id=${entry.id}">
                        ${entry.name} - ${entry.hp}
                    </a>
                </div>
            </c:forEach>
        </div>

        <!-- 4) 선택된 항목 출력 기능 -->
        <div class="selected-section">
            <c:if test="${selectedEntry != null}">
                <div>이름: ${selectedEntry.name}</div>
                <div>전화번호: ${selectedEntry.hp}</div>
                <div>메모: ${selectedEntry.memo}</div>
                <form action="updatePhonebook" method="post">
                    <input type="hidden" name="id" value="${selectedEntry.id}">
                    <input type="text" name="name" value="${selectedEntry.name}" required>
                    <input type="text" name="hp" value="${selectedEntry.hp}" required>
                    <input type="text" name="memo" value="${selectedEntry.memo}">
                    <button type="submit">수정</button>
                    <button type="submit" formaction="deletePhonebook">삭제</button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>
