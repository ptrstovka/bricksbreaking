import { Board } from './board';
import { Tile } from './tile';
import { Joiner } from './joiner';

export class BoardJoiner extends Joiner<Tile> {

    constructor(private board: Board) {
        super(
            board.getTiles(),
            board.getWidth(),
            board.getHeight()
        )
    }
 
    getEmpty() : Tile {
        return Tile.EMPTY;
    }

}
