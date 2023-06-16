<#include "components/shared-variables.ftlh">

<!DOCTYPE html>
<html lang="en" xmlns:link="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap-icons.min.css">
    <link rel="icon" type="image/svg+xml" href="/img/favicon.svg">
    <link rel="stylesheet" href="/css/index.css">
    <title>Cubewars</title>
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
    <header class="header">
        <h1 class="header__title">
            <span class="header__gradient">Improve skills</span>
            <br>
            through competitions
        </h1>
        <p class="header__text">
            Practice on your own with our timer or invite your friends to cubewars community and have fun together. Achieve mastery and beat all records!
        </p>
        <div class="header__join-block">
            <a href="/users/sign-up" class="header__button">Create an account</a>
            <div class="header__join-msg">
                You must have an account <br> to use all features we provide
            </div>
        </div>
    </header>
    <section class="use">
        <div class="use__container">
            <div class="use__head">
                <h2 class="use__title">What can I use Cubewars for?</h2>
                <p class="use__remark">From beginner to expert and beyond...</p>
            </div>
            <div class="use__variants">
                <div class="use-card">
                    <div class="card">
                        <div class="card__icon">
                            <i class="bi bi-stopwatch-fill"></i>
                        </div>
                        <div class="card__title">
                            Measure time
                        </div>
                        <div class="card__text">
                            Use our algorithms in order to scramble a puzzle. Then try to solve it as quickly as you can with our timer. And finally see how much time it took.
                        </div>
                    </div>
                </div>
                <div class="use-card">
                    <div class="card">
                        <div class="card__icon">
                            <i class="bi bi-bar-chart-line-fill"></i>
                        </div>
                        <div class="card__title">
                            View statistic
                        </div>
                        <div class="card__text">
                            We save and analyze all your attempts, so you can understand whether you made good progress or deteriorated your results.
                        </div>
                    </div>
                </div>
                <div class="use-card">
                    <div class="card">
                        <div class="card__icon">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <div class="card__title">
                            Compete with cubers
                        </div>
                        <div class="card__text">
                            Use our leaderboard to watch your competitors, gain inspiration from the fastest cubers, and set your sights on reaching the top.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script src="/js/navbar.js"></script>
</body>
</html>