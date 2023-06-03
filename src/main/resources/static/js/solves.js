import { CubeShower } from './cube-shower.js';


const $solvesList = $("#solve-list");
const $deleteButton = $( "#delete-btn" );

const $loadBtn = $("#load-btn");
const solvesOnPage = 50;
let currentPage;

const $modal = $( "#modal" );
const cubeShower = new CubeShower( $("#modal-cube") );


function getDiscipline() {
    return $( "input[type='radio'][name='discipline']:checked" ).val();
}

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

function showPage() {
    const data = {
        "discipline": getDiscipline(),
        "pageSize": solvesOnPage,
        "pageNo": currentPage
    }

    sendRequest("GET", `/users/${username}/solves`, data)
        .then((page) => {
            for (let solve of page["elements"]) {
                $solvesList.append(createSolveDiv(solve));
            }

            if (page["hasNext"]) {
                $loadBtn.removeClass("load-btn_hidden");
            } else {
                $loadBtn.addClass("load-btn_hidden");
            }
        })
        .catch(reason => {
            console.error(reason);
        });
}

function toDateFormat(timestamp) {
    const date = new Date(timestamp);

    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    return `${day}.${month}.${year}`;
}

function createSolveDiv(solve) {
    const id = solve["id"];
    const date = toDateFormat(solve["date"]);
    const status = solve["status"];
    const time = status === "DNF" ? "DNF" : solve["time"]["stringFormat"];
    const timeClass = status === "DNF" ? "attention" : "";
    const plusTwo = status === "PLUS_TWO" ? "+2" : "";

    return $( `<div class="solve" data-id="${id}">
                 <div class="solve__top">
                   <div class="solve__date">${date}</div>
                   <div class="solve__plus attention">${plusTwo}</div>
                 </div>
                 <div class="solve__time ${timeClass}">${time}</div>
               </div>` );

}

function showCube(scramble) {
    const url = "/cube/scrambled-by-scramble";
    const data = {
        "discipline": getDiscipline(),
        "scramble": scramble
    }
    sendRequest("GET", url, data)
        .then((cube) => {
            cubeShower.show(cube);
        });
}

function convertStatus(status) {
    if (status === "GOOD") return "";
    if (status === "DNF") return status;
    return "+2"
}


$(() => {
    currentPage = 1;
    showPage();
});

$("#disciplines").on("click", "input", function () {
    currentPage = 1;
    $solvesList.html("");
    showPage();
});

$loadBtn.click(() => {
    currentPage++;
    showPage();
});

$solvesList.on("click", "div.solve", (event) => {
    const solveId = $(event.target).closest("div.solve").data()["id"];

    sendRequest("GET", `/users/${username}/solves/${solveId}`)
        .then((solve) => {
            $modal.addClass("modal_active");

            $( "#modal-time" ).text(solve["time"]["stringFormat"]);
            $( "#modal-status" ).text(convertStatus(solve["status"]));
            $( "#modal-scramble" ).text(solve["scramble"]);
            $( "#modal-date" ).text(toDateFormat(solve["date"]));

            $deleteButton.data("id", solveId);

            showCube(solve["scramble"]);
        })
        .catch((response) => {
            if (response.status === 404) {
                alert("You've delete this solve. Try to update the page");
            } else {
                alert("Something went wrong");
            }
        });
});

$deleteButton.click(() => {
    if (!confirm("Are you sure you want to delete this solve?")) return;

    const id = $deleteButton.data()["id"];

    $modal.removeClass("modal_active");
    $solvesList.find(`[data-id='${id}']`).remove();

    sendRequest("DELETE", `/users/${username}/solves/${id}`)
        .then(() => {})
        .catch((response) => {
            console.warn(response.responseText);
        });
})

window.onclick = function(event) {
    if ($( event.target ).is($modal)) {
        $modal.removeClass("modal_active");
    }
}

