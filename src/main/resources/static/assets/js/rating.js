$(document).ready( function() {
    // ------------------- rating here -------------------------
    var stars = $('#stars');
    var maxStars = 5;
    var currentRating = 5;
    for (var i = 0; i < maxStars; i++) {
        var el = stars.append('<div '+ 'id="star-' + (i + 1) +'" class="star fa fa-3x"></div>');
    }

    var starsArray = $('.star').sort(function (a, b) {
        var aName = a.id.toLowerCase();
        var bName = b.id.toLowerCase(); 
        return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
    });

    function setRating(rating) {
        currentRating = rating;
        for (var i = 0; i < starsArray.length; i++) {
            var el = $('#' + starsArray[i].id);
            if ((i + 1) <= rating)  {
                el.removeClass('fa-star-o');
                el.addClass('fa-star');
            } else {
                el.removeClass('fa-star');
                el.addClass('fa-star-o');
            }
        }
    }

    for (var i = 0; i < starsArray.length; i++) {
        $('#' + starsArray[i].id).hover(function (event) {
            var el = event.target;
            var rating = parseInt(el.id.replace('star-', ''));
            setRating(rating);
        });
    }

    setRating(currentRating);

    function updateAverageRating(rating) {
        $('.average-rating').show();
        var container = $('#averateRating');
        container.empty();
        for (var i = 0; i < 5; i++) {
            if ((i + 1) <= rating) {
                container.append('<div class="avg-star fa fa-star fa-2x"></div>');
            } else {
                container.append('<div class="avg-star fa fa-star-o fa-2x"></div>');
            }
        }
    }

    function loadAverageRating() {
        axios.get('http://localhost:1705/rest/rating/bricksbreaking')
        .then(response => {
            var content = response.data;
            console.log('got average rating', content);
            updateAverageRating(content);
        })
        .catch(error => {
            console.log('could not get average rating');
        });
    }

    $('#rateButton').on('click', function (event) {
        event.preventDefault();

        var comment = $('#ratingComment').val();
        console.log('rating game', currentRating, comment);

        if (comment) {
            axios.post('http://localhost:1705/rest/comment',{
                "game" : "bricksbreaking",
                "player" : User.name,
                "comment" : comment,
                "commentedOn" : new Date().toISOString()
            }).then(response => {
                // ok
            }).catch(error => {
                alert('We could not add comment right now.');
            });
        }

        axios.post('http://localhost:1705/rest/rating', {
            "game" : "bricksbreaking",
            "player" : User.name,
            "rating" : currentRating,
            "ratedon" : new Date().toISOString()
        }).then(response => {
            $('#ratingModal').modal('toggle');
            loadAverageRating();
        }).catch(error => {
            $('#ratingModal').modal('toggle');
            alert('We could not add rating right now.');
        });
    });

    loadAverageRating();


});
