
export class Tile {

    static COLOR_TRANSPARENT = 0;
    static COLOR_RED = 1;
    static COLOR_GREEN = 2;
    static COLOR_BLUE = 3;

    static EMPTY : Tile = new Tile(Tile.COLOR_TRANSPARENT);

    constructor(private color: number) {}

    getColor() : number {
        return this.color;
    }

    copy() : Tile {
        return new Tile(this.color);
    }

}
