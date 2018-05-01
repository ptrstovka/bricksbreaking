
export default class LoadGameScene extends Phaser.Scene {

    constructor() {
        super({
            key: 'loadGameScene'
        })
    }

    preload() {
        
	}

    create() {

        this.scene.start('gameScene');

    }

}
