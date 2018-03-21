package com.peterstovka.universe.bricksbreaking.service;

import com.peterstovka.universe.BuildConfig;
import com.peterstovka.universe.bricksbreaking.foundation.Lists;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

public class Scores extends ScoreServiceJDBC {

    public Score getBestScore() {
        return Lists.first(getBestScores(BuildConfig.NAME));
    }

}
