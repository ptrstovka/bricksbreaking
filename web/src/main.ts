import 'phaser';

import { AppWindow } from './app-window';
declare let window: AppWindow;
import { Player } from './game/player';

import GameScene from './scenes/game-scene';
import MenuScene from './scenes/menu-scene';
import LoadGameScene from './scenes/load-game-scene';
import LeaderScene from './scenes/leader-scene';

window.User = Player.init();

const config:GameConfig = {
    type: Phaser.AUTO,
    parent: 'content',
    width: 800,
    height: 640,
    resolution: 1, 
    backgroundColor: '#99CC33',
    physics: {
      default: 'matter',
      matter: {
      }
    },
    scene: [
      MenuScene,
      GameScene,
      LoadGameScene,
      LeaderScene,
    ]
};

new Phaser.Game(config);

import Vue from 'vue';
import axios from 'axios';

import * as firebase from "firebase";
import * as firebaseui from "firebaseui";

var app = new Vue({
  el: '#app',
    data: {
      // @ts-ignore
      playerName: User.name,
      isLoggedIn: false,
      comments: [],
      comment: '',
      firebaseConfig: {
        apiKey: "AIzaSyA0Hx9ulrpHbdbcj0SsTAd9sxH1G7J7O4I",
        authDomain: "bricks-breaking.firebaseapp.com",
        databaseURL: "https://bricks-breaking.firebaseio.com",
        projectId: "bricks-breaking",
        storageBucket: "bricks-breaking.appspot.com",
        messagingSenderId: "391266291785"
      },
      uiConfig: {
        signInOptions: [
          firebase.auth.EmailAuthProvider.PROVIDER_ID
        ],
        callbacks: {
          signInSuccess: () => {
            // @ts-ignore
            $('#loginModal').modal('toggle');
            return false;
          },
        },
      },
      ui: null,
    },

    created() {
      axios.get('http://localhost:1705/rest/comment/bricksbreaking')
        .then(response => {
          this.comments = response.data;
        })
        .catch(error => {
          // no op
        });


      firebase.initializeApp(this.firebaseConfig);
      this.ui = new firebaseui.auth.AuthUI(firebase.auth());

      let v = this;

      firebase.auth().onAuthStateChanged(function (user) {
        v.setUserLogin(user);
      });

      this.setUserLogin(firebase.auth().currentUser);
    },

    mounted() {
      if (this.ui.isPendingRedirect()) {
        this.startLogin();
      }
    },

    methods: {
      setUserLogin(user) {
        console.log(user);
        if (user) {
          this.isLoggedIn = true;
          // @ts-ignore
          User.name = user.displayName ? user.displayName : user.email;
        } else {
          this.isLoggedIn = false;
          // @ts-ignore
          User.reset();
        }

        // @ts-ignore
        this.playerName = User.name;
      },

      getCommentedOn(time) {
        return new Date(time).toDateString();
      },

      postComment() {
        let c = {
          "game" : "bricksbreaking",
          // @ts-ignore
          "player" : User.name,
          "comment" : this.comment,
          "commentedOn" : new Date().toISOString()
        };
        axios.post('http://localhost:1705/rest/comment', c).then(() => {
          this.comment = '';
          this.comments.push(c);
        }).catch(() => {
          alert('We could not add comment right now.');
        })
      },

      startLogin() {
        // @ts-ignore
        $('#loginModal').modal('toggle');
        this.ui.start('#firebaseui-auth-container', this.uiConfig);
      },

      logout() {
        firebase.auth().signOut();
      }

    }
})
