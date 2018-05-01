
export default class MenuScene extends Phaser.Scene {

    constructor() {
        super({
            key: 'menuScene'
        })
    }

    preload() {
        this.load.image('background', '/assets/background.png');
        this.load.image('new_game_normal', '/assets/new_game_normal.png');
        this.load.image('leaderboard_normal', '/assets/leaderboard_btn_normal.png');
	}

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

        return button;
    }

    create() {

        this.add.image(400, 320, 'background');
        
        let newGameBtn = this.addButton('new_game_normal', 400, 220, () => {
            this.scene.start('gameScene');
        });

        let leaderBoardButton = this.addButton('leaderboard_normal', 400, 320, () => {
            this.scene.start('leaderScene');
        });


    }

}
