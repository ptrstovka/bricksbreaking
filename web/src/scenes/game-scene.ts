import { Board } from '../game/board/board';
import { Tile } from '../game/board/tile';
import { Position } from '../game/foundation/position';
import { ShapeFinder } from '../game/board/shape-finder';

import { SpriteJoiner } from './game/sprite-joiner';
import { BoardJoiner } from '../game/board/board-joiner';
import { Score } from '../game/board/score';
import { Health } from '../game/board/health';
import Axios from 'axios';

class GameScene extends Phaser.Scene {
	
	board: Phaser.GameObjects.TileSprite;

	gameBoard: Board;

	aliens: any[][] = [];
	private alienList = [];

	gameScore: Score;
	gameHealth: Health;
	private complete: any;
	private replayButton: any;
	private homeButton: any;

	constructor() {
    super({
			key: 'gameScene'
		});
	}
	
	private buttons = [];

	private addButton(texture: string, x: number, y: number, callback: Function) : any {
        // @ts-ignore
        let button = this.add.sprite(x, y, texture).setInteractive();

        button.on('pointerdown', function (pointer) {
            this.setTint(0xF2BC85);
    
        });
    
        button.on('pointerout', function (pointer) {
            this.clearTint();
            button.setScale(1);
        });

        button.on('pointerover', function (pointer) {
            button.setScale(1.05);
        });
    
        button.on('pointerup', function (pointer) {
            callback();
            this.clearTint();
        });

		this.buttons.push(button);
        return button;
    }

	preload() {
		this.load.image('background', '/assets/background.png');
		this.load.image('board', '/assets/window.png');
		this.load.image('sprite1', '/assets/sprite1.png');
		this.load.image('score', '/assets/score.png');
		this.load.image('complete', '/assets/complete.png');
		this.load.image('home_btn', '/assets/home_btn.png');
		this.load.image('replay_btn', '/assets/replay_btn.png');

		this.load.image('red', '/assets/red.png');
		this.load.image('green', '/assets/green.png');
		this.load.image('blue', '/assets/blue.png');
		this.load.image('exit_btn', '/assets/exit_button.png');
		this.load.image('hp', '/assets/hp.png');
		
		this.load.image('hp1', '/assets/hp_1.png');
		this.load.image('hp2', '/assets/hp_2.png');
		this.load.image('hp3', '/assets/hp_3.png');
		this.load.image('hp4', '/assets/hp_4.png');
		this.load.image('hp5', '/assets/hp_5.png');
	}

	findIndex(sprite) : Position {
		for (let y = 0; y < this.gameBoard.getHeight(); y++) {
			for (let x = 0; x < this.gameBoard.getWidth(); x++) {
				if (this.aliens[y][x] !== null && this.aliens[y][x].body === sprite) {
					return new Position(x, y);
				}
			}
		}

		return null;
	}

	dumpAliens() {
		console.log();
        console.log("---------- ALIEN DUMP -----------");
        for (let tile of this.aliens) {
			let x = this.aliens.indexOf(tile);
            let bfr = '';
            for (let j = 0; j < this.aliens[0].length; j++) {
				if (tile[j] === null) {
					bfr += `0\t`;
				} else {
					bfr += `${this.gameBoard.getTiles()[x][j].getColor()}\t`;
				}
            }
            console.log(bfr);
        }
        console.log("---------------------------------");
        console.log();
	}

	onSpriteClicked(sprite, pointer) {
		let position : Position|null = this.findIndex(sprite);

		if (position === null) {
			return;
		}

		console.log(`clicked on sprite on index`, position);
		let finder = new ShapeFinder(this.gameBoard);
		let items = finder.findConnected(position.getX(), position.getY());
		this.gameBoard.remove(items);
		let joiner = new BoardJoiner(this.gameBoard);
		joiner.join();

		items.forEach(pos => {
			let item = this.aliens[pos.getY()][pos.getX()];
			if (item === null) {
				return;
			}

			console.log('removed', pos);
			this.aliens[pos.getY()][pos.getX()].body.gameObject.visible = false;
			console.log('invisle');
			// @ts-ignore
			this.matter.world.remove(this.aliens[pos.getY()][pos.getX()].body);
			console.log('removed body from world');
			this.aliens[pos.getY()][pos.getX()] = null;
		});

		this.gameScore.record(items.length);
		this.updateScore();
		this.gameHealth.record(items.length);
		this.showHp(this.gameHealth.getHealth());

		// @ts-ignore
		let spriteJoiner = new SpriteJoiner(this.matter, this.aliens, this.gameBoard.getWidth(), this.gameBoard.getHeight());
		spriteJoiner.join();

		if (this.gameBoard.isEmpty() && this.gameHealth.isAlive()) {
			this.gameScore.recordRound();
			this.updateScore();
			this.startRound();
		}

		if (!this.gameHealth.isAlive()) {
			console.log('mrtvy');
			this.playerIsDead();
		}
	}

	private updateScore() {
		// @ts-ignore
		this.scoreText.setText(`${this.gameScore.getScore()}`.padStart(4, '0'));
	}

	private scoreText : any;

	startRound() {
		
		this.gameBoard = new Board(12, 8);
		for (let y = 0; y < this.gameBoard.getHeight(); y++) {
			this.aliens[y] = [];
		}
		this.gameBoard.generate();
		// this.gameBoard.dump();

		let tiles = this.gameBoard.getTiles();

		this.input.on("pointerdown", (pointer) => {
			if (!this.gameHealth.isAlive()) {
				return;
			}
			// @ts-ignore
			let bodiesUnderPointer = Phaser.Physics.Matter.Matter.Query.point(this.matter.world.localWorld.bodies, pointer);
			if (bodiesUnderPointer.length > 0) {
				let first = bodiesUnderPointer[0];
				// console.log(first);
				this.onSpriteClicked(first, pointer);
			}
		});

		for (let y = 0; y < this.gameBoard.getHeight(); y++) {
			for (let x = 0; x < this.gameBoard.getWidth(); x++) {
				let spriteColor = 'sprite1';

				if (tiles[y][x].getColor() == Tile.COLOR_RED) {
					spriteColor = 'red';
				} else if (tiles[y][x].getColor() == Tile.COLOR_BLUE) {
					spriteColor = 'blue';
				} else if (tiles[y][x].getColor() == Tile.COLOR_GREEN) {
					spriteColor = 'green';
				}

				// @ts-ignore
				let sprite = this.matter.add.image(112 + (x * 51), 0 + (y * 51), spriteColor);
				sprite.setFriction(0.05);
						
				this.aliens[y][x] = sprite;
				this.alienList.push(sprite);
			}
		}
	}

	private scoreLabel: any;
	private hpLabel: any;
	private boardWindow: any;
	private boardBackground: any;
	private completeScoreLabel: any;

	create() {

		this.complete = this.add.image(400, 320, 'complete');
		this.complete.visible = false;

		this.completeScoreLabel = this.add.text(365, 430, '0000', { fontSize: '32px', fill: '#fff' });
		this.completeScoreLabel.visible = false;

		this.gameScore = new Score();
		this.gameHealth = new Health();
		
		this.boardBackground = this.add.image(400, 320, 'background');
		this.boardWindow = this.add.image(393, 312, 'board');
		
		this.addButton('exit_btn', 50, 590, () => {
			this.scene.start('menuScene');
		});
		
		this.scoreLabel = this.add.image(250, 85, 'score');
		this.scoreText = this.add.text(230, 68, '0000', { fontSize: '32px', fill: '#fff' });

		this.hpLabel = this.add.image(500, 85, 'hp');

		// @ts-ignore
		this.matter.world.setBounds(0, 0, 800, 505);

		this.startRound();
		this.setupHealth();
		// this.playerIsDead();
	}

	hp = [];

	private setupHealth() {
		this.hp = [
			this.add.image(435, 70, 'hp1'),
			this.add.image(435, 70, 'hp2'),
			this.add.image(435, 70, 'hp3'),
			this.add.image(435, 70, 'hp4'),
			this.add.image(435, 70, 'hp5'),
		];

		this.hp.forEach(item => {
			item.setOrigin(0, 0);
		});

		this.showHp(5);
	}

	private showHp(hp: number) {
		
		for (let i = 0; i < this.hp.length; i++) {
			if (i + 1 <= hp) {
				this.hp[i].visible = true;
			} else {
				this.hp[i].visible = false;;
			}
		}
	}

	playerIsDead() {
		this.homeButton = this.addButton('home_btn', 370, 511, () => {
			console.log('home');
			this.scene.start('menuScene');
		});

		this.replayButton = this.addButton('replay_btn', 430, 511, () => {
			console.log('reply');
			this.scene.start('loadGameScene');
		});
		this.buttons.forEach(btn => {
			btn.visible = false;
		});

		this.scoreLabel.visible = false;
		this.scoreText.visible = false;
		this.boardWindow.visible = false;
		this.hpLabel.visible = false;

		this.alienList.forEach(alien => {
			alien.visible = false;
		});
		this.boardBackground.visible = false;

		this.complete.visible = true;
		this.replayButton.visible = true;
		this.homeButton.visible = true;
		this.completeScoreLabel.visible = true;

		// @ts-ignore
		this.completeScoreLabel.setText(`${this.gameScore.getScore()}`.padStart(4, '0'));


		Axios.post(`http://localhost:1705/rest/score`, {
			"game" : "bricksbreaking",
			// @ts-ignore
			"player" : User.name,
			"points" : this.gameScore.getScore(),
			"playedon" : new Date().toISOString()
		})

	}

}

export default GameScene;
