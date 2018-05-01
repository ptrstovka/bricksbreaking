import { Joiner } from '../../game/board/joiner';
import { Arrays } from '../../game/foundation/arrays';

import Phaser from 'phaser';

export class SpriteJoiner extends Joiner<any> {

    constructor(private matter: any, items: any[][], width: number, height: number) {
        super(items, width, height);
    }

    getEmpty() {
        return null;
    }

    onMoveDown(sprite: any, steps: number) {
        //sprite.y += sprite.height * steps;
    }

    moveLeft = [];

    onMoveLeft(sprite: any, steps: number) {
        this.moveLeft.push({
            sprite,
            steps
        });
    }

    moveRight = [];

    onMoveRight(sprite: any, steps: number) {
        this.moveRight.push({
            sprite,
            steps
        });
    }

    onMovedLeft() {
        this.moveLeft.forEach(item => {
            item.sprite.setPosition(item.sprite.body.position.x - (item.steps * 51), item.sprite.body.position.y);
        });

        Arrays.clear(this.moveLeft);
    }

    onMovedRight() {
        this.moveRight.forEach(item => {
            item.sprite.setPosition(item.sprite.body.position.x + (item.steps * 51), item.sprite.body.position.y);
        });

        Arrays.clear(this.moveRight);
    }

}
