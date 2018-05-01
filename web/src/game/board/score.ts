
export class Score {

    private score: number = 0;

    record(removedTiles: number) {
        if (removedTiles > 1) {
            this.score += (removedTiles * 2);
        }
    }

    getScore() : number {
        return this.score;
    }

    recordRound() {
        this.score += 100;
    }

}
