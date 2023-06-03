<#include "components/shared-variables.ftlh">

<!DOCTYPE html>
<html lang="en" xmlns:link="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/bootstrap-icons.min.css">
    <link rel="icon" type="image/svg+xml" href="/img/favicon.svg">
    <link rel="stylesheet" href="/css/leader-board.css">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <title>Leader Board</title>
</head>
<body>
<#if isAdmin>
    <#include "components/admin-navbar.ftlh">
<#else>
    <#include "components/user-navbar.ftlh">
</#if>

<main>
    <div class="my-container">
        <#include "components/disciplines-radio.ftlh">

        <div class="block">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th class="rank-col">Position</th>
                        <th class="user-col">User</th>
                        <th class="ao-col">Ao5</th>
                        <th class="count-col">Count</th>
                    </tr>
                </thead>
                <tbody id="users"></tbody>
            </table>
            <button id="load-btn" class="load-btn load-btn_hidden">Load more</button>
        </div>
    </div>
</main>

<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript">const username = "${username}";</script>
<script type="text/javascript" src="/js/leader-board.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>