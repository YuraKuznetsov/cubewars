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
    <link rel="stylesheet" href="/css/admin.css">
    <title>Admin</title>
</head>
<body>
<#include "components/admin-navbar.ftlh">

<main>
    <div class="my-container">
        <div class="block">
            <div class="block__title">
                Admins Statistics
            </div>
            <div class="block__text">
                <p>
                    The admins-stats file is a document that contains a table with information about the activity of the admins in the system. The table shows the number of news, comments, and likes for each admin. Please note that the comments and likes count correspond to the number of comments and likes made by users under the news articles, rather than the number of comments or likes made by the admin themselves.
                </p>
            </div>
            <div class="block__download">
                Download
                <a href="/admin/export/admins-stats?format=WORD" class="block__button">.docx</a>
                <a href="/admin/export/admins-stats?format=PDF" class="block__button">.pdf</a>
                <a href="/admin/export/admins-stats?format=EXCEL" class="block__button">.xlsx</a>
            </div>
        </div>
        <div class="block">
            <div class="block__title">
                Users Activity
            </div>
            <div class="block__text">
                <p>
                    The users-activity file is a document that contains a table with user activity data including the number of solves, comments, and likes for each user. The table also includes the date and time of the user's last activity. Note that users with no activity are not included in the document.
                </p>
                <p>
                    Use this document to track user activity and to identify your most active users.
                </p>
            </div>
            <div class="block__download">
                Download
                <a href="/admin/export/users-activity?format=WORD" class="block__button">.docx</a>
                <a href="/admin/export/users-activity?format=PDF" class="block__button">.pdf</a>
                <a href="/admin/export/users-activity?format=EXCEL" class="block__button">.xlsx</a>
            </div>
        </div>
        <div class="block">
            <div class="block__title">
                Users Best Solve
            </div>
            <div class="block__text">
                The best-solves file is a document that contains a table with the best users solve of chosen discipline. Note that users who don't have positive solves (GOOD or PLUS_TWO) are not included in the document.

                <p>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_2X2" id="flexRadioDefault1">
                        <label class="form-check-label" for="flexRadioDefault1">2x2 Cube</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_3X3" id="flexRadioDefault2" checked>
                        <label class="form-check-label" for="flexRadioDefault2">3x3 Cube</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_4X4" id="flexRadioDefault3">
                        <label class="form-check-label" for="flexRadioDefault3">4x4 Cube</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_5X5" id="flexRadioDefault4">
                        <label class="form-check-label" for="flexRadioDefault4">5x5 Cube</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_6X6" id="flexRadioDefault5">
                        <label class="form-check-label" for="flexRadioDefault5">6x6 Cube</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="discipline" value="CUBE_7X7" id="flexRadioDefault6">
                        <label class="form-check-label" for="flexRadioDefault6">7x7 Cube</label>
                    </div>
                </p>

            </div>

            <div class="block__download">
                Download
                <a href="/admin/export/best-solves?format=WORD" class="block__button best-solve-link">.docx</a>
                <a href="/admin/export/best-solves?format=PDF" class="block__button best-solve-link">.pdf</a>
                <a href="/admin/export/best-solves?format=EXCEL" class="block__button best-solve-link">.xlsx</a>
            </div>
        </div>
    </div>
</main>

<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript" src="/js/admin.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</body>
</html>