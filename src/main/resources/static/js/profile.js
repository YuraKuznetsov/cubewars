function sendRequest(type, url, data) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: type,
            url: url,
            data: data,
            success: function(response) {
                resolve(response);
            },
            error: function(response) {
                reject(response);
            }
        });
    });
}

function fetchStatistics() {
    const promises = [];

    for (const discipline in statisticsForDiscipline) {
        promises.push(
            sendRequest("GET", `/users/${username}/solves/statistics`, {"discipline": discipline})
        );
    }

    return Promise.all(promises)
        .then((results) => {
            for (let i = 0; i < results.length; i++) {
                statisticsForDiscipline[Object.keys(statisticsForDiscipline)[i]] = results[i];
            }
        })
        .catch((response) => console.log(response));
}


function showTotalSolves() {
    let totalSolves = 0;

    ["CUBE_2X2", "CUBE_3X3", "CUBE_4X4", "CUBE_5X5", "CUBE_6X6", "CUBE_7X7"].forEach((discipline) => {
        totalSolves += statisticsForDiscipline[discipline]["count"];
    });

    $("#total-solves").text(totalSolves);
}

function getCountList() {
    const result = [];

    result.push(statisticsForDiscipline["CUBE_2X2"]["count"]);
    result.push(statisticsForDiscipline["CUBE_3X3"]["count"]);
    result.push(statisticsForDiscipline["CUBE_4X4"]["count"]);
    result.push(statisticsForDiscipline["CUBE_5X5"]["count"]);
    result.push(statisticsForDiscipline["CUBE_6X6"]["count"]);
    result.push(statisticsForDiscipline["CUBE_7X7"]["count"]);

    return result;
}

function updatePieChart() {
    new Chart("pie-chart", {
        type: "pie",
        data: {
            labels: ["2x2 Cube", "3x3 Cube", "4x4 Cube", "5x5 Cube", "6x6 Cube", "7x7 Cube"],
            datasets: [{
                backgroundColor: [
                    "#7e7",
                    "#ff4d4d",
                    "#6795de",
                    "#fcd34d",
                    "#e8c3b9",
                    "#f29871"
                ],
                data: getCountList()
            }]
        },
        options: {
            legend: {
                position: "right",
                align: "middle",
                labels: {
                    fontSize: 12,
                    fontColor: "#FFF",
                }
            },
            title: {
                display: true,
                fontSize: 14,
                text: "Solves Count Of Each Discipline",
                fontColor: "#FFF",
            }
        }
    });
}


function getDiscipline() {
    return $( "input[type='radio'][name='discipline']:checked" ).val();
}

function generateXValues(list) {
    const n = list.length;
    const xValues = [];

    for (let i = 1; i <= n + 1; i++) {
        xValues.push(i);
    }

    return xValues;
}

function generateYValues(list, offset=0) {
    const yValues = [];

    for (let i = 0; i < offset; i++) {
        yValues.push(null);
    }

    for (let i = 0; i < list.length; i++) {
        if (list[i] === null) {
            yValues.push(null);
            continue;
        }

        yValues.push(list[i]["totalSeconds"])
    }

    return yValues;
}

function updateLineChart() {
    const disciplineStatistics = statisticsForDiscipline[getDiscipline()];

    lineChart.data.labels = generateXValues(disciplineStatistics["times"]);

    lineChart.data.datasets[0].data = generateYValues(disciplineStatistics["times"]);
    lineChart.data.datasets[1].data = generateYValues(disciplineStatistics["ao5"], 4);
    lineChart.data.datasets[2].data = generateYValues(disciplineStatistics["ao12"], 11);
    lineChart.data.datasets[3].data = generateYValues(disciplineStatistics["ao50"], 49);
    lineChart.data.datasets[4].data = generateYValues(disciplineStatistics["ao100"], 99);

    lineChart.update();
}

const lineChart = new Chart("graph", {
    type: "line",
    data: {
        datasets: [{
            label: 'all',
            borderColor: "#FFF",
            borderWidth: 1,
            pointRadius: 1.5,
            fill: false
        }, {
            label: "ao5",
            borderColor: "#ff4d4d",
            borderWidth: 1,
            pointRadius: 1.5,
            fill: false
        }, {
            label: "ao12",
            borderColor: "#6795de",
            borderWidth: 1,
            pointRadius: 1.5,
            fill: false
        }, {
            label: "ao50",
            borderColor: "#7e7",
            borderWidth: 1,
            pointRadius: 1.5,
            fill: false
        }, {
            label: "ao100",
            borderColor: "#fcd34d",
            borderWidth: 1,
            pointRadius: 1.5,
            fill: false
        }]
    },
    options: {
        legend: {
            position: 'bottom',
            align: 'start',
            labels: {
                fontSize: 16,
                fontStyle: 'bold',
                fontColor: "#FFF",
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: '#FFF',
                    autoSkip: true,
                    maxTicksLimit: 10,
                    min: 0,
                    stepSize: 1
                },
                gridLines: {
                    color: 'rgba(252, 252, 252, .7)',
                    borderDash: [3, 3],
                    borderWidth: 1,
                    drawBorder: false,
                    drawTicks: false
                }
            }],
            xAxes: [{
                ticks: {
                    fontColor: '#FFF',
                    autoSkip: true,
                    maxTicksLimit: 30,
                    maxRotation: 0,
                    minRotation: 0
                },
                gridLines: {
                    display: false
                },
            }],
        },
    }
});


function toStringFormat(time) {
    if (time === null) return "-";

    return time["stringFormat"];
}

function getSeconds(time) {
    if (time === null) return Number.MAX_VALUE;

    return time["totalSeconds"];
}

function getBestAo(aoList) {
    if (aoList.length === 0) return "-";

    const minSeconds = Math.min(...aoList.map(time => getSeconds(time)));

    if (minSeconds === Number.MAX_VALUE) return "-";

    function checkTime(time) {
        return time["totalSeconds"] === minSeconds;
    }

    return aoList.find(checkTime)["stringFormat"];
}

function fillTable() {
    $("#best2x2").text(toStringFormat(statisticsForDiscipline["CUBE_2X2"]["best"]));
    $("#best3x3").text(toStringFormat(statisticsForDiscipline["CUBE_3X3"]["best"]));
    $("#best4x4").text(toStringFormat(statisticsForDiscipline["CUBE_4X4"]["best"]));
    $("#best5x5").text(toStringFormat(statisticsForDiscipline["CUBE_5X5"]["best"]));
    $("#best6x6").text(toStringFormat(statisticsForDiscipline["CUBE_6X6"]["best"]));
    $("#best7x7").text(toStringFormat(statisticsForDiscipline["CUBE_7X7"]["best"]));

    $("#mean2x2").text(toStringFormat(statisticsForDiscipline["CUBE_2X2"]["mean"]));
    $("#mean3x3").text(toStringFormat(statisticsForDiscipline["CUBE_3X3"]["mean"]));
    $("#mean4x4").text(toStringFormat(statisticsForDiscipline["CUBE_4X4"]["mean"]));
    $("#mean5x5").text(toStringFormat(statisticsForDiscipline["CUBE_5X5"]["mean"]));
    $("#mean6x6").text(toStringFormat(statisticsForDiscipline["CUBE_6X6"]["mean"]));
    $("#mean7x7").text(toStringFormat(statisticsForDiscipline["CUBE_7X7"]["mean"]));

    $("#ao52x2").text(getBestAo(statisticsForDiscipline["CUBE_2X2"]["ao5"]));
    $("#ao53x3").text(getBestAo(statisticsForDiscipline["CUBE_3X3"]["ao5"]));
    $("#ao54x4").text(getBestAo(statisticsForDiscipline["CUBE_4X4"]["ao5"]));
    $("#ao55x5").text(getBestAo(statisticsForDiscipline["CUBE_5X5"]["ao5"]));
    $("#ao56x6").text(getBestAo(statisticsForDiscipline["CUBE_6X6"]["ao5"]));
    $("#ao57x7").text(getBestAo(statisticsForDiscipline["CUBE_7X7"]["ao5"]));

    $("#ao122x2").text(getBestAo(statisticsForDiscipline["CUBE_2X2"]["ao12"]));
    $("#ao123x3").text(getBestAo(statisticsForDiscipline["CUBE_3X3"]["ao12"]));
    $("#ao124x4").text(getBestAo(statisticsForDiscipline["CUBE_4X4"]["ao12"]));
    $("#ao125x5").text(getBestAo(statisticsForDiscipline["CUBE_5X5"]["ao12"]));
    $("#ao126x6").text(getBestAo(statisticsForDiscipline["CUBE_6X6"]["ao12"]));
    $("#ao127x7").text(getBestAo(statisticsForDiscipline["CUBE_7X7"]["ao12"]));

    $("#ao502x2").text(getBestAo(statisticsForDiscipline["CUBE_2X2"]["ao50"]));
    $("#ao503x3").text(getBestAo(statisticsForDiscipline["CUBE_3X3"]["ao50"]));
    $("#ao504x4").text(getBestAo(statisticsForDiscipline["CUBE_4X4"]["ao50"]));
    $("#ao505x5").text(getBestAo(statisticsForDiscipline["CUBE_5X5"]["ao50"]));
    $("#ao506x6").text(getBestAo(statisticsForDiscipline["CUBE_6X6"]["ao50"]));
    $("#ao507x7").text(getBestAo(statisticsForDiscipline["CUBE_7X7"]["ao50"]));

    $("#ao1002x2").text(getBestAo(statisticsForDiscipline["CUBE_2X2"]["ao100"]));
    $("#ao1003x3").text(getBestAo(statisticsForDiscipline["CUBE_3X3"]["ao100"]));
    $("#ao1004x4").text(getBestAo(statisticsForDiscipline["CUBE_4X4"]["ao100"]));
    $("#ao1005x5").text(getBestAo(statisticsForDiscipline["CUBE_5X5"]["ao100"]));
    $("#ao1006x6").text(getBestAo(statisticsForDiscipline["CUBE_6X6"]["ao100"]));
    $("#ao1007x7").text(getBestAo(statisticsForDiscipline["CUBE_7X7"]["ao100"]));

    $("#count2x2").text(statisticsForDiscipline["CUBE_2X2"]["count"]);
    $("#count3x3").text(statisticsForDiscipline["CUBE_3X3"]["count"]);
    $("#count4x4").text(statisticsForDiscipline["CUBE_4X4"]["count"]);
    $("#count5x5").text(statisticsForDiscipline["CUBE_5X5"]["count"]);
    $("#count6x6").text(statisticsForDiscipline["CUBE_6X6"]["count"]);
    $("#count7x7").text(statisticsForDiscipline["CUBE_7X7"]["count"]);
}

const statisticsForDiscipline = {
    "CUBE_2X2": null,
    "CUBE_3X3": null,
    "CUBE_4X4": null,
    "CUBE_5X5": null,
    "CUBE_6X6": null,
    "CUBE_7X7": null,
}

$(() => {
    fetchStatistics()
        .then(() => {
            showTotalSolves();
            updatePieChart();
            fillTable();
            updateLineChart();
        });
});

$("#disciplines").on("click", "input", function () {
    updateLineChart();
});