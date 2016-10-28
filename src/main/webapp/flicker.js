$(function() {

    var popup = $('#popup');

    var pics =
        ['content/images/demon.jpg',
        'content/images/monster1.jpg',
        'content/images/monster2.jpg',
        'content/images/monster3.jpg',
        'content/images/monster4.jpg'];

    setInterval(function() {
        flicker(60, 500, 000);
    }, 8000);

    function flicker(interval, duration, delay) {

        popup[0].src = pics[~~(Math.random()*5)];

        setTimeout(function() {

            var timer = setInterval(function() {
                popup.toggleClass('hide');
            }, interval);

            setTimeout(function() {
                clearTimeout(timer);
                popup.addClass('hide');
            }, duration + interval);

        }, delay - interval);

    }

});

