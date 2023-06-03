import { CubeShower } from '../cube-shower.js';
import { StopWatch } from './stopwatch.js';

const cubeShower = new CubeShower( $("#cube") );
const cubeShowerModal = new CubeShower( $("#cube-in-modal") );
const stopWatch = new StopWatch( $("#stopwatch") );

let curSolveId;
let curScramble;

function touchstart() {
    if (stopWatch.isInProgress) {
        stopWatch.end();
        saveSolve();

        $("#panel").removeClass("panel_hidden");
        $(".timer__top").removeClass("timer__top_hidden");
        $(".timer__bottom").removeClass("timer__bottom_hidden");
        $("#stopwatch").removeClass("timer__stopwatch_enlarged");
    } else {
        stopWatch.reset();

        $dnfButton.removeClass("panel__item_active");
        $dnfButton.removeClass("panel__item_disabled");
        $plus2Button.removeClass("panel__item_active");
        $plus2Button.removeClass("panel__item_disabled");
        $removeButton.removeClass("panel__item_disabled");

        $("#panel").addClass("panel_hidden");

        curScramble = $("#scramble").text();
    }
}

function touchend() {
    if (!stopWatch.isInProgress) {
        stopWatch.start();
        $(".timer__top").addClass("timer__top_hidden");
        $(".timer__bottom").addClass("timer__bottom_hidden");
        $("#stopwatch").addClass("timer__stopwatch_enlarged");
        scrambleCube();
    }

    stopWatch.isInProgress = !stopWatch.isInProgress;
}

const $touchscreen = $(".timer__touchscreen");
$touchscreen.on("touchstart", function(event) {
    // If panel button was pressed, do nothing
    if ($(event.target).closest(".timer-panel").length) return;

    touchstart();
});
$touchscreen.on("touchend", (event) => {
    // If panel button was pressed, do nothing
    if ($(event.target).closest(".timer-panel").length) return;

    touchend();
});

// Timer events
document.addEventListener("keydown", (event) => {
    if (event.code !== "Space") return;
    if (event.repeat) return;

    touchstart();
});
document.addEventListener("keyup", (event) => {
    if (event.code !== "Space") return;

    touchend();
});

// Update scramble
function scrambleCube() {
    $.ajax({
        url: "/cube/scrambled-cube",
        data: {
            "discipline": getDiscipline()
        },
        success: function(response) {
            $("#scramble").text(response["scramble"]);
            cubeShower.show(response["cube"]);
            cubeShowerModal.show(response["cube"]);
        }
    });
}
$("#update-scramble-button").on("click", scrambleCube);


// Full mode
const fullModeButton = document.querySelector("#full-mode-button");
const navbar = document.querySelector(".navbar");
const main = document.querySelector("main");

fullModeButton.addEventListener("click", () => {
    navbar.classList.toggle("hidden");
    main.classList.toggle("full-mode");
});


// Choosing discipline
const disciplineButton = document.querySelector("#discipline-button");
const disciplineModal = document.querySelector("#discipline-modal");
const modalButtons = document.querySelectorAll(".modal-discipline");

function getDiscipline() {
    const buttonText = disciplineButton.textContent.trim();

    switch (buttonText) {
        case "2x2 Cube": return "CUBE_2X2";
        case "3x3 Cube": return "CUBE_3X3";
        case "4x4 Cube": return "CUBE_4X4";
        case "5x5 Cube": return "CUBE_5X5";
        case "6x6 Cube": return "CUBE_6X6";
        default: return "CUBE_7X7";
    }
}

disciplineButton.addEventListener("click", () => {
    disciplineModal.classList.add("modal_active");
});

disciplineModal.addEventListener("click", (event) => {
    if (!isDisciplineButton(event.target)) return;

    const button = event.target;

    disciplineButton.textContent = button.textContent;
    scrambleCube();
    updateStatistics();
    disciplineModal.classList.remove("modal_active");

    deactivateModelButtons();
    button.classList.add("active");

});

function isDisciplineButton(button) {
    for (const b of modalButtons) {
        if (button === b) return true;
    }

    return false;
}

function deactivateModelButtons() {
    for (const button of modalButtons) {
        button.classList.remove("active");
    }
}

// cube modal
const $cubeModal = $( "#cube-modal" );

$( "#cube-scene" ).click(() => {
    $cubeModal.addClass("modal_active");
});


/////// Status Buttons /////////
const $removeButton = $("#remove-btn");
const $dnfButton = $("#dnf-btn");
const $plus2Button = $("#plus2-btn");

$removeButton.click(() => {
    if ($removeButton.hasClass("panel__item_disabled")) return;
    if (!confirm("Are you sure you want to delete this solve?")) return;

    stopWatch.reset();
    $("#panel").addClass("panel_hidden");

    deleteSolve(curSolveId)
    curSolveId = null;
});

$dnfButton.click(() => {
    if ($dnfButton.hasClass("panel__item_disabled")) return;

    if ($dnfButton.hasClass("panel__item_active")) {
        $dnfButton.removeClass("panel__item_active");
        $plus2Button.removeClass("panel__item_disabled");
        $removeButton.removeClass("panel__item_disabled");
        stopWatch.showTime();

        updateStatus("GOOD");
    } else {
        $dnfButton.addClass("panel__item_active");
        $plus2Button.addClass("panel__item_disabled");
        $removeButton.addClass("panel__item_disabled");
        stopWatch.showDNF();

        updateStatus("DNF");
    }
});

$plus2Button.click(() => {
    if ($plus2Button.hasClass("panel__item_disabled")) return;

    if ($plus2Button.hasClass("panel__item_active")) {
        $plus2Button.removeClass("panel__item_active");
        $dnfButton.removeClass("panel__item_disabled");
        $removeButton.removeClass("panel__item_disabled");
        stopWatch.subtractTwoSeconds();

        updateStatus("GOOD");
    } else {
        $plus2Button.addClass("panel__item_active");
        $dnfButton.addClass("panel__item_disabled");
        $removeButton.addClass("panel__item_disabled");
        stopWatch.addTwoSeconds();

        updateStatus("PLUS_TWO");
    }
});

$(() => {
    scrambleCube();
    updateStatistics();
});

window.onclick = function(event) {
    if (event.target === disciplineModal) {
        disciplineModal.classList.remove("modal_active");
    }

    if ($cubeModal.is($( event.target ))) {
        $cubeModal.removeClass("modal_active");
    }
}

// Requests sending
function saveSolve() {
    if (username === "anonymousUser") return;

    $.ajax({
        url: `/users/${username}/solves`,
        type: "POST",
        data: {
            "time": stopWatch.getTime(),
            "discipline": getDiscipline(),
            "scramble": curScramble
        },
        success: function(solve) {
            curSolveId = solve["id"];
            updateStatistics();
        },
        error: function () {
            alert("Error while updating ao");
        }
    });
}

function updateStatus(status) {
    $.ajax({
        url: `/users/${username}/solves/${curSolveId}`,
        type: "PUT",
        data: {
            "time": stopWatch.getTime(),
            "status": status,
        },
        success: function() {
            updateStatistics();
        },
        error: function () {
            alert("Error while updating status");
        }
    });
}

function deleteSolve(id) {
    $.ajax({
        url: `/users/${username}/solves/${id}`,
        type: "DELETE",
        success: function() {
            updateStatistics();
        },
        error: function () {
            alert("Error while deleting solve");
        }
    });
}

function updateStatistics() {
    if (username === "anonymousUser") return;

    $.ajax({
        url: `/users/${username}/solves/statistics`,
        data: {
            "discipline": getDiscipline(),
        },
        success: function(response) {
            $("#best").text(toStringFormat(response["best"]));
            $("#worst").text(toStringFormat(response["worst"]));
            $("#mean").text(toStringFormat(response["mean"]));
            $("#count").text(response["count"]);

            $("#ao5").text(getCurrentAo(response["ao5"]));
            $("#ao12").text(getCurrentAo(response["ao12"]));
            $("#ao50").text(getCurrentAo(response["ao50"]));
            $("#ao100").text(getCurrentAo(response["ao100"]));
        },
        error: function () {
            alert("Error while updating statistics");
        }
    });
}

function toStringFormat(time) {
    if (time === null) return "-";

    return time["stringFormat"];
}

function getCurrentAo(aoList) {
    if (aoList.length === 0) return "-";

    return toStringFormat(aoList[aoList.length - 1]);
}
