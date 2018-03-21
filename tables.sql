CREATE TABLE score (
    player VARCHAR(64) NOT NULL,
    game VARCHAR(64) NOT NULL,
    points INTEGER NOT NULL,
    playedon TIMESTAMP NOT NULL
);

CREATE TABLE rating (
    player VARCHAR(64) NOT NULL,
    game VARCHAR(64) NOT NULL,
    rating INTEGER NOT NULL,
    ratedon TIMESTAMP NOT NULL
);

CREATE TABLE comment (
    player VARCHAR(64) NOT NULL,
    game VARCHAR(64) NOT NULL,
    comment TEXT NOT NULL,
    commentedon TIMESTAMP NOT NULL
);