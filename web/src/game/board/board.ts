import { Tile } from './tile';
import { Arrays } from '../foundation/arrays';
import { Position } from '../foundation/position';

export class Board {

    private tiles: Tile[][] = [];

    private width: number;
    private height: number;

    constructor(tiles: number, height?: number);
    constructor(tiles: Tile[][]);
    constructor(tiles: Tile[][]|number, height?: number) {
        if (height) {
            this.width = <number>tiles;
            this.height = height;
            for (let y = 0; y < this.height; y++) {
                this.tiles[y] = [];
            }    
        } else {
            console.log('creating array from tiles');
            this.tiles = <Tile[][]>tiles;
            this.width = tiles[0].length;
            this.height = (<Tile[][]>tiles).length;
        }
    }

    getWidth() : number {
        return this.width;
    }

    getHeight() : number {
        return this.height;
    }

    getTiles() : Tile[][] {
        return this.tiles;
    }

    copy() : Board {
        let board = new Board(this.width, this.height);

        for (let y = 0; y < this.height; y++) {
            for (let x = 0; x < this.width; x++) {
                board.tiles[y][x] = this.tiles[y][x].copy();
            }
        }

        return board;
    }

    generate() : void {
        console.log('generate start');
        for (let y = 0; y < this.height; y++) {
            for (let x = 0; x < this.width; x++) {
                let tile = new Tile(Arrays.randomElement(this.getColors()));
                this.tiles[y][x] = tile;
            }
        }
        console.log('generate end');
    }
    
    remove(positions: Position[]) {
        positions.forEach(position => {
            this.tiles[position.getY()][position.getX()] = Tile.EMPTY;
        })
    }

    isEmpty() : boolean {
        for (let y = 0; y < this.height; y++) {
            for (let x = 0; x < this.width; x++) {
                if (Tile.EMPTY !== this.tiles[y][x]) {
                    return false;
                }
            }
        }

        return true;
    }

    // For dev purposes.
    dump() {
        console.log();
        console.log("---------- BOARD DUMP -----------");
        for (let tile of this.tiles) {
            let bfr = '';
            for (let j = 0; j < this.tiles[0].length; j++) {
                bfr += `${tile[j].getColor()}\t`;
            }
            console.log(bfr);
        }
        console.log("---------------------------------");
        console.log();
    }
    
    private getColors() : number[] {
        return [Tile.COLOR_RED, Tile.COLOR_GREEN, Tile.COLOR_BLUE];
    }

}
