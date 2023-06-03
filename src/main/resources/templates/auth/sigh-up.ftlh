<!DOCTYPE html>
<html lang="en" xmlns:link="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap-icons.min.css">
    <link rel="icon" type="image/svg+xml" href="/img/favicon.svg">
    <link rel="stylesheet" href="/css/auth.css">
    <title>Sign up</title>
</head>
<body>
<main>
    <div class="auth-container">
        <form class="auth-form" action="/users/sign-up" method="post">
            <a href="/" class="auth-form__logo">Cubewars <i class="bi bi-box"></i></a>
            <div class="auth-form__fields">
                <div class="auth-form__row round-top">
                    <div class="row line-bottom">
                        <div class="row__icon"><i class="bi bi-envelope-fill"></i></div>
                        <input type="email" name="email" placeholder="email">
                    </div>
                </div>
                <div class="auth-form__row round-top">
                    <div class="row line-bottom">
                        <div class="row__icon"><i class="bi bi-person-circle"></i></div>
                        <input type="text" name="username" placeholder="username" required>
                    </div>
                </div>
                <div class="auth-form__row round-bottom">
                    <div class="row">
                        <div class="row__icon"><i class="bi bi-key-fill"></i></div>
                        <input type="password" name="password" placeholder="password" required>
                    </div>
                </div>
            </div>
            <#if error>
                <div style="margin: .5rem 0 -.5rem">
                    Username is not available
                </div>
            </#if>
            <button type="submit">Sign up</button>
        </form>
        <div class="auth-form__info">
            <a href="/users/log-in">Log in</a> if you already have an account?
        </div>
    </div>
</main>
</body>
</html>