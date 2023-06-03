<#include "components/shared-variables.ftlh">

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="/js/jquery.js"></script>
    <link rel="stylesheet" href="/css/bootstrap-icons.min.css">
    <link rel="icon" type="image/svg+xml" href="/img/favicon.svg">
    <link rel="stylesheet" href="/css/timer.css">
    <title>Timer</title>
</head>
<body>
<#if isAdmin>
    <#include "components/admin-navbar.ftlh">
<#elseif isUser>
    <#include "components/user-navbar.ftlh">
<#else>
    <#include "components/anonymous-navbar.ftlh">
</#if>

<main>
    <div class="timer">
        <div class="timer__top">
            <div class="timer__buttons">
                <div id="update-scramble-button" class="timer__button"><i class="bi bi-arrow-counterclockwise"></i></div>
                <div id="discipline-button" class="timer__button">3x3 Cube</div>
                <div id="full-mode-button" class="timer__button"><i class="bi bi-arrows-fullscreen"></i></div>
            </div>
            <div id="scramble" class="timer__scramble"></div>
        </div>
        <div class="timer__touchscreen">
            <div id="stopwatch" class="timer__stopwatch time">
                <span id="minutes">0</span>:<span id="seconds">00</span>.<span id="microseconds" class="small">00</span>
            </div>
            <#if isAdmin || isUser>
                <div id="panel" class="timer-panel panel panel_hidden">
                    <div id="remove-btn" class="panel__item"><i class="bi bi-x-lg"></i></div>
                    <div id="dnf-btn" class="panel__item"><i class="bi bi-dash-circle"></i></div>
                    <div id="plus2-btn" class="panel__item"><i class="bi bi-flag"></i></div>
                </div>
            </#if>
        </div>
        <div class="timer__bottom">
            <div>
                <#if isAdmin || isUser>
                    <div>
                        Best: <span id="best" class="time"></span>
                    </div>
                    <div>
                        Worst: <span id="worst" class="time"></span>
                    </div>
                    <div>
                        Mean: <span id="mean" class="time"></span>
                    </div>
                    <div>
                        Count: <span id="count" class="time"></span>
                    </div>
                </#if>
            </div>

            <div id="cube-scene" class="cube-scene">
                <div class="cube" id="cube">
                    <div class="cube__side" id="top-side"></div>
                    <div class="cube__side" id="front-side"></div>
                    <div class="cube__side" id="bottom-side"></div>
                    <div class="cube__side" id="left-side"></div>
                    <div class="cube__side" id="right-side"></div>
                    <div class="cube__side" id="back-side"></div>
                </div>
            </div>

            <div style="text-align: right;">
                <#if isAdmin || isUser>
                    <div>
                        Ao5: <span id="ao5" class="time"></span>
                    </div>
                    <div>
                        Ao12: <span id="ao12" class="time"></span>
                    </div>
                    <div>
                        Ao50: <span id="ao50" class="time"></span>
                    </div>
                    <div>
                        Ao100: <span id="ao100" class="time"></span>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</main>
<div id="discipline-modal" class="modal">
    <div class="modal__container">
        <div class="modal-title">Choose discipline</div>
        <div class="modal-disciplines">
            <div class="modal-discipline">2x2 <br>Cube</div>
            <div class="modal-discipline active">3x3 <br>Cube</div>
            <div class="modal-discipline">4x4 <br>Cube</div>
            <div class="modal-discipline">5x5 <br>Cube</div>
            <div class="modal-discipline">6x6 <br>Cube</div>
            <div class="modal-discipline">7x7 <br>Cube</div>
        </div>
    </div>
</div>
<div id="cube-modal" class="modal">
    <div class="modal__container">
        <div id="cube-scene-modal" class="cube-scene-modal">
            <div class="cube" id="cube-in-modal">
                <div class="cube__side" id="top-side"></div>
                <div class="cube__side" id="front-side"></div>
                <div class="cube__side" id="bottom-side"></div>
                <div class="cube__side" id="left-side"></div>
                <div class="cube__side" id="right-side"></div>
                <div class="cube__side" id="back-side"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript">const username = "${username}";</script>
<script type="module" src="/js/timer/timer.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>