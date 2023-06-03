$('.best-solve-link').click(function(event) {
    event.preventDefault();

    const selectedOption = $('input[name="discipline"]:checked').val();
    const href = $(this).attr('href');

    window.location.href = href + '&discipline=' + selectedOption;
});