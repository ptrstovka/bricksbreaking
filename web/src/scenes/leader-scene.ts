
import axios from 'axios';

export default class LeaderScene extends Phaser.Scene {

    constructor() {
        super({
            key: 'leaderScene'
        })
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

    preload() {
        this.load.image('background', '/assets/background.png');
        this.load.image('exit_btn', '/assets/exit_button.png');
        this.load.image('leader_header', '/assets/leader_header.png');
	}

    create() {
        this.add.image(400, 320, 'background');
        this.add.image(400, 130, 'leader_header');

        this.addButton('exit_btn', 50, 590, () => {
			this.scene.start('menuScene');
        });
        
        axios.get('http://localhost:1705/rest/score/bricksbreaking')
            .then(response => {
                console.log(response.data);
                for (let i = 0; i < response.data.length; i++) {
                    // @ts-ignore
                    let num = `${i + 1}`.padStart(2, ' ');
                    this.add.text(300, 250 + (i * 20), `${num}. ${response.data[i].player} [${response.data[i].points}]`);
                }        
            })
            .catch(error => {
                this.add.text(280, 250, 'The leaderboard is empty.');
            });
    }

}
