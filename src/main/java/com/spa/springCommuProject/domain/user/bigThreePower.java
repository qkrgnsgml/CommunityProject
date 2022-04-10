package com.spa.springCommuProject.domain.user;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class bigThreePower {


    private int squat;
    private int bench;
    private int dead;

    protected bigThreePower() {
    }

    public bigThreePower(int squat, int bench, int dead) {
        this.squat = squat;
        this.bench = bench;
        this.dead = dead;
    }
}
