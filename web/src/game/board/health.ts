
export class Health {

    private health = 5;

    record(removeTiles: number) : void {
        if (removeTiles <= 1) {
            this.health -= 1;
        }
    }

    isAlive() : boolean {
        return this.health > 0;
    }

    getHealth() : number {
        return this.health;
    }

}
