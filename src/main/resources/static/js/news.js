function sendRequest(type, url, data) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: type,
            url: url,
            data: data,
            success: function(response) {
                resolve(response);
            },
            error: function(xhr, status, error) {
                reject(xhr, status, error);
            }
        });
    });
}

function timeAgo(dateString) {
    // Convert the input date string to a Date object
    const date = new Date(dateString);

    // Calculate the time difference between the input date and the current date in milliseconds
    const diff = new Date() - date;

    // Define the time intervals in milliseconds
    const minute = 60 * 1000;
    const hour = 60 * minute;
    const day = 24 * hour;
    const week = 7 * day;
    const month = 30 * day;

    // Determine the appropriate time interval and return the human-readable string
    if (diff < minute) {
        return 'just now';
    } else if (diff < hour) {
        const minutes = Math.floor(diff / minute);
        return `${minutes} ${minutes === 1 ? 'minute' : 'minutes'} ago`;
    } else if (diff < day) {
        const hours = Math.floor(diff / hour);
        return `${hours} ${hours === 1 ? 'hour' : 'hours'} ago`;
    } else if (diff < week) {
        const days = Math.floor(diff / day);
        return `${days} ${days === 1 ? 'day' : 'days'} ago`;
    } else if (diff < month) {
        const weeks = Math.floor(diff / week);
        return `${weeks} ${weeks === 1 ? 'week' : 'weeks'} ago`;
    } else {
        const months = Math.floor(diff / month);
        return `${months} ${months === 1 ? 'month' : 'months'} ago`;
    }
}

function parseNewsContent(text) {
    const mentionLinksRegex = /@{([^\s]+)}/g;
    const newRowRegex = /\n/g;

    return text
        .replace(mentionLinksRegex, '<a href="/users/$1">$1</a>')
        .replace(newRowRegex, "<br>");
}

function getNewsBlock(news) {
    const likeIconClass = news["likes"].some(like => like["username"] === username)
        ? "bi-hand-thumbs-up-fill"
        : "bi-hand-thumbs-up";

    return `<div class="news" data-id="${news["id"]}">
                <div class="news__head">
                    <div class="news__title">${news["title"]}</div>
                    <div class="news__date">${timeAgo(news["date"])}</div>
                </div>
                <div class="news__content">${parseNewsContent(news["content"])}</div>
                <div class="news__reactions">
                    <div class="reactions">
                        <div class="reactions__buttons">
                            <div class="reactions__like">
                                <i class="bi ${likeIconClass}"></i> <span>${news["likes"].length}</span>
                            </div>
                            <div class="reactions__comment">Comment</div>
                            ${isAdmin ? "<div class=\"reactions__delete\">Delete</div>" : ""}
                        </div>
                        <div class="reactions__form hidden">
                            <form class="comment-form">
                                <div class="comment-form__top">
                                    <input type="text" name="content">
                                </div>
                                <div class="comment-form__bottom">
                                    <button class="comment-form__button">Comment</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="news__comments">
                    <div class="comments">
                        <div class="comments__button ${news["comments"].length === 0 ? "hidden" : ""}">
                            <div class="comments__button__icon">
                                <i class="bi bi-caret-down-fill"></i>
                            </div>
                            <div class="comments__button__text">
                                <span class="comments__button__count">${news["comments"].length}</span> comments
                            </div>
                        </div>
                        <div class="comments__list hidden"></div>
                    </div>
                </div>
            </div>`;
}

function getCommentBlock(comment) {
    return `<div class="comment" data-id="${comment["id"]}">
                <div class="comment__head">
                    <a href="/users/${comment["username"]}" class="comment__username">${comment["username"]}</a>
                    <div class="comment__date">${timeAgo(comment["date"])}</div>
                    ${isAdmin ? "<div class=\"comment__delete\">delete</div>" : ""}
                </div>
                <div class="comment__content">${comment["content"]}</div>
            </div>`;
}

function loadAllComments(newsId, $commentsList) {
    sendRequest("GET", `/news/${newsId}/comments`)
        .then((comments) => {
            for (let comment of comments) {
                $commentsList.append(getCommentBlock(comment));
            }
        });
}

$('#news-form').submit(function(event) {
    event.preventDefault();

    const formData = {
        'username': username,
        'title': $('#title').val(),
        'content': $('#content').val()
    };

    sendRequest("POST", "/news", formData)
        .then((newsId) => {
            $('#news-form')[0].reset();

            sendRequest("GET", `/news/${newsId}`)
                .then((news) => {
                    // Add news at the beginning
                    $newsFeed.prepend(getNewsBlock(news));

                    // Remove last news if at least one full page and there is next page
                    if ($newsFeed.children().length > pageSize && hasNextPage) {
                        $newsFeed.children().last().remove();
                    }
                })
                .catch((reason) => {
                    console.log(reason);
                });
        })
        .catch((reason) => {
            console.log(reason);
        });
});

const $newsFeed = $('.news-feed');

// Add or remove like under news
$newsFeed.on('click', '.reactions__like', function() {
    const $likeIcon = $(this).find('i');
    const $likeCount = $(this).find('span');
    const newsId = $(this).closest('.news').data("id");

    if ($likeIcon.hasClass("bi-hand-thumbs-up-fill")) {
        // Decrease likes count if was active
        sendRequest("DELETE", `/news/${newsId}/likes`)
            .then(() => {
                let likeCount = parseInt($likeCount.text());
                likeCount--;
                $likeCount.text(likeCount);

                $likeIcon.removeClass("bi-hand-thumbs-up-fill");
                $likeIcon.addClass("bi-hand-thumbs-up");
            });
    } else {
        // Increase likes count if wasn't active
        sendRequest("POST", `/news/${newsId}/likes`)
            .then(() => {
                let likeCount = parseInt($likeCount.text());
                likeCount++;
                $likeCount.text(likeCount);

                $likeIcon.removeClass("bi-hand-thumbs-up");
                $likeIcon.addClass("bi-hand-thumbs-up-fill");
            })
    }
});

// Show or hide comment form when comment button is pressed
$newsFeed.on('click', '.reactions__comment', function() {
    const form = $(this).closest('.news').find('.reactions__form');
    form.toggleClass("hidden");
});

// Save comment when comment form is submitted
$newsFeed.on('click', '.comment-form__button', function(event) {
    event.preventDefault();

    const newsId = $(this).closest('.news').data("id");
    const $form = $(this).closest('form');
    const data = {
        "content": $form.find('[name="content"]').val(),
        "username": username
    };

    // save new comment
    sendRequest("POST", `/news/${newsId}/comments`, data)
        .then((commentId) => {
            $form.trigger('reset');

            // updating list of comments after saving
            const $commentsList = $(this).closest('.news').find('.comments__list');

            if ($commentsList.children().length === 0) {
                // Load all if didn't load any comments before
                loadAllComments(newsId, $commentsList);
            } else {
                // Load only new comment if load comments before
                sendRequest("GET", `/news/${newsId}/comments/${commentId}`)
                    .then((comment) => {
                        $commentsList.append(getCommentBlock(comment));
                    });
            }

            // show comments button if it was hidden
            const $commentsButton = $(this).closest('.news').find('.comments__button');
            if ($commentsButton.hasClass("hidden")) {
                $commentsButton.removeClass("hidden");
            }

            // Increase comments count when new comment is saved
            const $commentsCount = $(this).closest('.news').find("span.comments__button__count");
            let commentsCount = parseInt($commentsCount.text());
            $commentsCount.text(++commentsCount);
        })
        .catch(reason => {
            if (reason.status === 429) {
                alert("You can comment only 3 times a minute. Cool down)")
            } else {
                console.log(reason);
            }
        });
});

// Deleting news
$newsFeed.on('click', '.reactions__delete', function() {
    if (!confirm("Are you sure you want to delete this news?")) return;

    const newsId = $(this).closest('.news').data("id");

    sendRequest("DELETE", `/news/${newsId}`)
        .then(() => {
            // Delete news from feed
            $newsFeed.find(`[data-id="${newsId}"]`).remove();

            // Load one news instead of deleted
            loadNewsPage(1, $newsFeed.children().length + 1);
        })
        .catch();
});

// Deleting comment
$newsFeed.on('click', '.comment__delete', function() {
    if (!confirm("Are you sure you want to delete this comment?")) return;

    const newsId = $(this).closest('.news').data("id");
    const commentId = $(this).closest('.comment').data("id");

    sendRequest("DELETE", `/news/${newsId}/comments/${commentId}`)
        .then(() => {
            // Decrease comments count when new comment is deleted
            const $commentsCount = $(this).closest('.news').find("span.comments__button__count");
            let commentsCount = parseInt($commentsCount.text());
            $commentsCount.text(--commentsCount);

            // If there is no comments, hide button and list
            if (commentsCount === 0) {
                const $commentsButton = $(this).closest('.news').find('.comments__button');
                $commentsButton.addClass("hidden");
            }

            // Delete comment from page
            $(this).closest('.comment').remove();
        })
        .catch();
});

// Show all comments for parent news
$newsFeed.on('click', '.comments__button', function() {
    // turn over the triangle
    $(this).find("i").toggleClass("bi-caret-down-fill");
    $(this).find("i").toggleClass("bi-caret-up-fill");

    const $commentsList = $(this).closest('.news').find('.comments__list');

    // If comments was loaded
    if ($commentsList.children().length !== 0) {
        $commentsList.toggleClass("hidden");
        return;
    }

    // Load comments if button is pressed for the first time
    const newsId = $(this).closest('.news').data("id");

    loadAllComments(newsId, $commentsList);
    $commentsList.removeClass("hidden");
});

function loadNewsPage(pageSize, pageNo) {
    const data = {
        "pageSize": pageSize,
        "pageNo": pageNo
    }

    sendRequest("GET", "/news", data)
        .then((page) => {
            for (let news of page["elements"]) {
                $newsFeed.append(getNewsBlock(news));
            }

            hasNextPage = page["hasNext"];
        });
}

$(window).scroll(() => {
    if (!hasNextPage) return;

    const scrollHeight = $(document).height();
    const scrollTop = $(window).scrollTop();
    const clientHeight = $(window).height();

    if (scrollTop + clientHeight >= scrollHeight) {
        pageNo++;
        loadNewsPage(pageSize, pageNo);
    }
});

let isAdmin;
const pageSize = 5;
let pageNo;
let hasNextPage = true;

$(() => {
    sendRequest("GET", `/users/${username}/role`)
        .then((role) => {
            isAdmin = role === "ROLE_ADMIN";

            // Load first page of news
            pageNo = 1;
            loadNewsPage(pageSize, pageNo);
        });
});