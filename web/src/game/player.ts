
export class Player {

    constructor(public name: string){}

    static init() : Player {
        return new Player(Player.generateName());
    }

    reset() {
        this.name = Player.generateName();
    }

    private static generateName() : string {
        return `player-${Math.floor(Math.random() * 10000)}`
    }

}
