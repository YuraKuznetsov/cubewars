<#include "components/shared-variables.ftlh">

<!DOCTYPE html>
<html lang="en" xmlns:link="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/bootstrap-icons.min.css">
    <link rel="icon" type="image/svg+xml" href="/img/favicon.svg">
    <link rel="stylesheet" href="/css/news.css">
    <title>News</title>
</head>
<body>
<#if isAdmin>
    <#include "components/admin-navbar.ftlh">
<#else>
    <#include "components/user-navbar.ftlh">
</#if>

<main>
    <div class="my-container">

        <#if isAdmin>
            <form action="#" class="news-form" id="news-form">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" required>

                <label for="content">Content:</label>
                <textarea id="content" name="content" form="news-form" required></textarea>

                <input type="submit" value="Publish">
            </form>
        </#if>

        <div class="news-feed"></div>
    </div>
</main>

<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript">const username = "${username}";</script>
<script type="text/javascript" src="/js/news.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>