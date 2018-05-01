import { Position } from '../foundation/position';
import { Board } from './board';
import { Tile } from './tile';
import { Arrays } from '../foundation/arrays';

export class ShapeFinder {

    private positions: Position[] = [];

    constructor(private board: Board){}

    findConnected(x: number, y: number) : Position[] {
        Arrays.clear(this.positions);

        let picture = this.board.copy().getTiles();
        this.apply(picture, this.getValueAt(picture, y, x), y, x);

        return Arrays.copy(this.positions);
    }
    
    private apply(picture: Tile[][], colorToReplace: number, y: number, x: number) : void {
        let currentColor = this.getValueAt(picture, y, x);

        if (Tile.COLOR_TRANSPARENT === currentColor) {
            return;
        }

        if (colorToReplace === currentColor) {
            picture[y][x] = Tile.EMPTY;
            this.positions.push(new Position(x, y));
            this.apply(picture, colorToReplace, y + 1, x);
            this.apply(picture, colorToReplace, y - 1, x);
            this.apply(picture, colorToReplace, y, x + 1);
            this.apply(picture, colorToReplace, y, x - 1);
        }
    }
    
    private getValueAt(picture: Tile[][], y: number, x: number) : number {
        if (y < 0 || x < 0 || y > this.board.getHeight() - 1 || x > this.board.getWidth() - 1) {
            return -1;
        } else {
            return picture[y][x].getColor();
        }
    }

}
