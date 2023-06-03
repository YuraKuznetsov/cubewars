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
    <link rel="stylesheet" href="/css/solves.css">
    <title>Solves</title>
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

        <div id="solve-list" class="solve-list"></div>
        <button id="load-btn" class="load-btn load-btn_hidden">Load more</button>

        <div id="modal" class="modal">
            <div class="modal__container">
                <div class="solve-modal">
                    <div class="solve-modal__top">
                        <div class="solve-modal__time">
                            <div id="modal-time"></div>
                            <span id="modal-status"></span>
                        </div>
                        <div id="modal-date" class="solve-modal__date"></div>
                    </div>
                    <div class="solve-modal__middle">
                        <div id="modal-scramble" class="solve-modal__scramble"></div>
                        <div class="solve-modal__cube-scene">
                            <div id="modal-cube" class="cube">
                                <div class="cube__side" id="top-side"></div>
                                <div class="cube__side" id="front-side"></div>
                                <div class="cube__side" id="bottom-side"></div>
                                <div class="cube__side" id="left-side"></div>
                                <div class="cube__side" id="right-side"></div>
                                <div class="cube__side" id="back-side"></div>
                            </div>
                        </div>
                    </div>
                    <div class="solve-modal__bottom">
                        <div id="delete-btn" class="solve_modal__button btn btn-danger">Delete</div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</main>

<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript">const username = "${username}";</script>
<script type="module" src="/js/solves.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>