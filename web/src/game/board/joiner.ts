import { Board } from './board';
import Stack from 'ts-data.stack';

export abstract class Joiner<T> {
    
    private empty: T;

    constructor(private items: T[][], private width: number, private height: number) {
        this.empty = this.getEmpty();
    }

    protected onMoveDown(item: T, step: number) : void {}

    protected onMovedDown() : void {}

    protected onMoveLeft(item: T, step: number) : void {}

    protected onMovedLeft() : void {}

    protected onMoveRight(item: T, step: number) : void {}

    protected onMovedRight() : void {}

    join() : void {
        this.joinVertical(this.items);
        this.center(this.items);
    }

    abstract getEmpty() : T;

    private joinVertical(picture: T[][]) : void {
        for (let x = 0; x < this.width; x++) {

            // ------------ before
            let colBefore = [];
            for (let i = 0; i < this.height; i++) {
                colBefore[i] = picture[i][x];
            }
            // ------------ end before

            let items : Stack<T> = new Stack();
            
            for (let y = 0; y < this.height; y++) {
                let el = picture[y][x];

                if (this.empty !== el) {
                    items.push(el);
                }

                picture[y][x] = this.empty;
            }

            for (let y = this.height - 1; y >= 0; y--) {
                if (items.isEmpty()) {
                    break;
                }

                picture[y][x] = items.pop();
            }

            // ------------ after
            let colAfter = [];
            for (let i = 0; i < this.height; i++) {
                colAfter[i] = picture[i][x];
            }
            // ----------- col after

            // diff calculating
            for (let i = 0; i < this.height; i++) {
                if (colBefore[i] === this.empty) {
                    continue;
                }

                let movedTo = colAfter.indexOf(colBefore[i]);

                let steps = movedTo - i;
                if (steps === 0) {
                    continue;
                }
                this.onMoveDown(colBefore[i], steps);
            }
            this.onMovedDown();
            // end of diff calculating
            
        }
    }

    private moveToLeft(picture: T[][], emptyColumn: number) : void {
        for (let x = emptyColumn; x < this.width - 1; x++) {
            for (let y = 0; y < this.height; y++) {
                picture[y][x] = picture[y][x + 1];
                picture[y][x + 1] = this.empty;
            }
        }
    }

    private isColumnEmpty(picture: T[][], column: number) : boolean {
        for (let y = 0; y < this.height; y++) {
            let item = picture[y][column];
            if (this.empty !== item) {
                return false;
            }
        }

        return true;
    }

    private joinHorizontal(picture: T[][]) : void {
        this.joinItemsHorizontal(picture);
    }

    private joinItemsHorizontal(picture: T[][]) : boolean {
        let moved = false;

        for (let x = 0; x < this.width; x++) {
            if (this.isColumnEmpty(picture, x)) {
                this.moveToLeft(picture, x);
                moved = true;
            }
        }

        return moved;
    }

    private center(picture: T[][]) : void {
        // ----- before
        let before : T[][] = picture.map(item => {
            return item.map(it => it);
        });
        // ----- end before

        this.joinHorizontal(this.items);
        let moved = this.joinItemsHorizontal(picture);

        console.log(moved);

        let rightSpace = 0;

        for (let x = this.width - 1; x >= 0; x--) {
            if (this.isColumnEmpty(picture, x)) {
                rightSpace++;
            } else {
                break;
            }
        }

        if (rightSpace > 1) {
            let steps = Math.round(rightSpace / 2);
            for (let x = this.width - 1 - rightSpace; x >= 0; x--) {
                for (let y = 0; y < this.height; y++) {
                    picture[y][x + steps] = picture[y][x];
                    picture[y][x] = this.empty;
                }
            }
        }

        // ----- after
        let after : T[][] = picture.map(item => {
            return item.map(it => it);
        });
        // ----- end after

        console.log('ok');

        if (moved) {
            // calculating diff
            for (let y = 0; y < this.height; y++) {
                let rowBefore = [];
                for (let x = 0; x < this.width; x++) {
                    rowBefore[x] = before[y][x];
                }

                let rowAfter = [];
                for (let x = 0; x < this.width; x++) {
                    rowAfter[x] = after[y][x];
                }

                for (let i = 0; i < this.width; i++) {
                    if (rowBefore[i] === this.empty) {
                        continue;
                    }

                    let movedTo = rowAfter.indexOf(rowBefore[i]);
                    let steps = movedTo - i;

                    if (i === movedTo) {
                        continue;
                    }

                    if (steps > 0) {
                        this.onMoveRight(rowBefore[i], steps);
                    } else if (steps < 0) {
                        this.onMoveLeft(rowBefore[i], -steps);
                    }
                }

                this.onMovedLeft();
                this.onMovedRight();
            }
            // end of calculating diff
        }
    }
    
}
