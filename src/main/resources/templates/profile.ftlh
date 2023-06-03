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
    <link rel="stylesheet" href="/css/profile.css">
    <title>Profile</title>
</head>
<body>
<#if isAdmin>
    <#include "components/admin-navbar.ftlh">
<#else>
    <#include "components/user-navbar.ftlh">
</#if>

<main>
    <div class="my-container">

        <div class="overall block">
            <div class="overall__left">
                <div id="username" class="overall__username">${profileOwner}</div>
                <div>
                    Total solves: <span id="total-solves"></span>
                </div>
            </div>
            <div class="overall__right">
                <canvas id="pie-chart" class="pie-chart"></canvas>
            </div>
        </div>

        <div class="block">
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th></th>
                        <th>2x2</th>
                        <th>3x3</th>
                        <th>4x4</th>
                        <th>5x5</th>
                        <th>6x6</th>
                        <th>7x7</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Best</td>
                        <td id="best2x2"></td>
                        <td id="best3x3"></td>
                        <td id="best4x4"></td>
                        <td id="best5x5"></td>
                        <td id="best6x6"></td>
                        <td id="best7x7"></td>
                    </tr>
                    <tr>
                        <td>Mean</td>
                        <td id="mean2x2"></td>
                        <td id="mean3x3"></td>
                        <td id="mean4x4"></td>
                        <td id="mean5x5"></td>
                        <td id="mean6x6"></td>
                        <td id="mean7x7"></td>
                    </tr>
                    <tr>
                        <td>Ao5</td>
                        <td id="ao52x2"></td>
                        <td id="ao53x3"></td>
                        <td id="ao54x4"></td>
                        <td id="ao55x5"></td>
                        <td id="ao56x6"></td>
                        <td id="ao57x7"></td>
                    </tr>
                    <tr>
                        <td>Ao12</td>
                        <td id="ao122x2"></td>
                        <td id="ao123x3"></td>
                        <td id="ao124x4"></td>
                        <td id="ao125x5"></td>
                        <td id="ao126x6"></td>
                        <td id="ao127x7"></td>
                    </tr>
                    <tr>
                        <td>Ao50</td>
                        <td id="ao502x2"></td>
                        <td id="ao503x3"></td>
                        <td id="ao504x4"></td>
                        <td id="ao505x5"></td>
                        <td id="ao506x6"></td>
                        <td id="ao507x7"></td>
                    </tr>
                    <tr>
                        <td>Ao100</td>
                        <td id="ao1002x2"></td>
                        <td id="ao1003x3"></td>
                        <td id="ao1004x4"></td>
                        <td id="ao1005x5"></td>
                        <td id="ao1006x6"></td>
                        <td id="ao1007x7"></td>
                    </tr>
                    <tr>
                        <td>Count</td>
                        <td id="count2x2"></td>
                        <td id="count3x3"></td>
                        <td id="count4x4"></td>
                        <td id="count5x5"></td>
                        <td id="count6x6"></td>
                        <td id="count7x7"></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <#include "components/disciplines-radio.ftlh">

        <div class="block">
            <canvas id="graph" class="graph"></canvas>
        </div>

    </div>
</main>

<script type="text/javascript" src="/js/navbar.js"></script>
<script type="text/javascript">const username = "${profileOwner}";</script>
<script type="module" src="/js/profile.js"></script>
<script type="text/javascript" src="/js/chart.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

</body>
</html>