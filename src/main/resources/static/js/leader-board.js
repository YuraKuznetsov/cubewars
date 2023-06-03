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

function getDiscipline() {
    return $( "input[type='radio'][name='discipline']:checked" ).val();
}

function getRank(rankNo) {
    switch (rankNo) {
        case 1: return '<div class="medal-gold"><i class="bi bi-trophy-fill"></i></div>';
        case 2: return '<div class="medal-silver"><i class="bi bi-award-fill"></i></div>';
        case 3: return '<div class="medal-bronze"><i class="bi bi-award-fill"></i></div>';
        default: return "#" + rankNo;
    }
}

function generateRow(rankNo, user) {
    const ao5 = toStringFormat(user["ao5"]);
    const rowClass = username === user["username"] ? "me" : ao5 === "-" ? "row-lowlight" : "";

    return $(`<tr class="${rowClass}">
                <td class="rank-col">${getRank(rankNo)}</td>
                <td class="user-col"><a href="/users/${user["username"]}">${user["username"]}</a></td>
                <td class="ao-col">${ao5}</td>
                <td class="count-col">${user["count"]}</td>
              </tr>`)
}

function toStringFormat(time) {
    if (time === null) return "-";

    return time["stringFormat"];
}

function showPage() {
    const data = {
        "discipline": getDiscipline(),
        "pageSize": pageSize,
        "pageNo": currentPage
    }

    sendRequest("GET", "/leader-board", data)
        .then((page) => {
            let rank = (currentPage - 1) * pageSize;
            const newRows = [];

            for (let user of page["elements"]) {
                const row = generateRow(++rank, user);
                newRows.push(row);
            }

            $users.append(...newRows);

            if (page["hasNext"]) {
                $loadBtn.removeClass("load-btn_hidden");
            } else {
                $loadBtn.addClass("load-btn_hidden");
            }
        })
        .catch((response) => console.error(response));
}

const $users = $( "#users" );
const $loadBtn = $("#load-btn");
const pageSize = 10;
let currentPage;

$(() => {
    currentPage = 1;
    showPage();
});

$("#disciplines").on("click", "input", function () {
    $loadBtn.addClass("load-btn_hidden");
    currentPage = 1;
    $users.html("");
    showPage();
});

$loadBtn.click(() => {
    currentPage++;
    showPage();
});