    package Character;

    import Main.*;
    import java.awt.*;

    public class Player extends Human implements Walkable {
        final int walkAnimDelay = 7;
        int currentFrame = 0;
        int currentIdleFrame = 0;


        int footstepTimer = 0;
        int footstepDelay = 25;
        public Sound dirtFootstep, grassFootstep, brickFootstep, woodFootstep;



        public int screenX;
        public int screenY;

        public static int worldX, worldY, speed, speedDiag;
        public KeyHandler keyH;
        UI ui;

        CollisionChecker collisionChecker;

        public Player(GamePanel gamePanel, KeyHandler keyH, ImageManager imageManager) {
            this.setSpeed(2);
            this.setWorldX(665);
            this.setWorldY(817);
            this.gp = gamePanel;
            this.keyH = keyH;
            direction = "right";
            animDirection = "right";
            state = "idle";
            this.setHitbox((gp.tileSize * 2 - 24) / 2 , (gp.tileSize * 2 - 24) / 2 , 24 , 24);
            this.ui = gp.ui;
            this.collisionChecker = new CollisionChecker(gp, this);
            playerSize = gp.tileSize * 2;

            loadSound();

            valuesSetting();
            playerLoading(imageManager);
        }

        public void valuesSetting() {
    //    world position -> position จริง ๆ ในเกม ทุก object, character, tile ถูก fixed ไว้แล้ว
    //      screen position -> position ที่เราทำการ draw (สั่งให้ java draw แล้วเห็นในจอ ว่ามันคือตรงไหน)
            worldX = this.getWorldX();
            worldY = this.getWorldY();
            speed = this.getSpeed();
            screenX = gp.mapX / 2 - gp.tileSize / 2; //Center of the screen (because it's placed at top-left corner)
            screenY = gp.mapY / 2 - gp.tileSize / 2;
            speedDiag = (int) (speed/Math.sqrt(2));
            idleAnimLeft = new String[9];
            idleAnimRight = new String[9];
            walkingAnimLeft = new String[8];
            walkingAnimRight = new String[8];
        }

        public void loadSound() {
            dirtFootstep= new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/footstep/dirt-footstep.wav");
            grassFootstep = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/footstep/grass-footstep.wav");
            brickFootstep = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/footstep/brick-footstep.wav");
            woodFootstep = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/footstep/wood-footstep.wav");
        }

        public void playerLoading(ImageManager imageManager) {
            for (int i = 0; i < 9; i++) { //Storing player.png in an array
                idleAnimLeft[i] = "res/Character/Hansel/Hansel_Idle/hansel_idle_left"+(i+1)+".png";
            }
            imageManager.setImage("idleAnimLeft",idleAnimLeft);

            for (int i = 0; i < 9; i++) {
                idleAnimRight[i] = "res/Character/Hansel/Hansel_Idle/hansel_idle_right"+(i+1)+".png";
            }
            imageManager.setImage("idleAnimRight",idleAnimRight);

            for (int i = 0; i < 8; i++) {
                walkingAnimLeft[i] = "res/Character/Hansel/Hansel_Walking/hansel_walking_left"+(i+1)+".png";
            }
            imageManager.setImage("walkingAnimLeft",walkingAnimLeft);

            for (int i = 0; i < 8; i++) {
                walkingAnimRight[i] = "res/Character/Hansel/Hansel_Walking/hansel_walking_right"+(i+1)+".png";
            }
            imageManager.setImage("walkingAnimRight",walkingAnimRight);
        }

        public void animationHandler(ImageManager imageManager) {
            currentFrame++;
            if (currentFrame >= walkAnimDelay) {
                currentFrame = 0;
                currentIdleFrame++;
            }

            if (state.equals("idle")) {
                if (currentIdleFrame > walkingAnimLeft.length-1) {
                    currentIdleFrame = 0;
                }
                if (animDirection.equals("left")) {
                    animImg = imageManager.getImages("idleAnimLeft");
                } else if (animDirection.equals("right")) {
                    animImg = imageManager.getImages("idleAnimRight");
                }
            } else if (state.equals("walking")) {
                if (currentIdleFrame > walkingAnimLeft.length-1) {
                    currentIdleFrame = 0;
                }
                if (animDirection.equals("left")) {
                    animImg = imageManager.getImages("walkingAnimLeft");
                } else if (animDirection.equals("right")) {
                    animImg = imageManager.getImages("walkingAnimRight");
                }
            }
        }

        public void moveCharacter(Walkable walker){
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                state = "walking";
                if (keyH.upPressed && keyH.rightPressed) {
                    direction = "upRight";
                    animDirection = "right";
                } else if (keyH.upPressed && keyH.leftPressed) {
                    direction = "upLeft";
                    animDirection = "left";
                } else if (keyH.downPressed && keyH.rightPressed) {
                    direction = "downRight";
                    animDirection = "right";
                } else if (keyH.downPressed && keyH.leftPressed) {
                    direction = "downLeft";
                    animDirection = "left";
                } else if (keyH.upPressed) {
                    direction = "up";
                    //animDirection จะเป็น direction ล่าสุด
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                    animDirection = "left";
                } else if (keyH.rightPressed)  {
                    direction = "right";
                    animDirection = "right";
                }

                if (collisionChecker.isBlockWalkable(this.direction)) { //if walkable
                    switch (direction) {
                        case "upRight":
                            walker.walkDiagUpRight();
                            break;
                        case "upLeft":
                            walker.walkDiagUpLeft();
                            break;
                        case "downRight":
                            walker.walkDiagDownRight();
                            break;
                        case "downLeft":
                            walker.walkDiagDownLeft();
                            break;
                        case "up":
                            walker.walkUp();
                            break;
                        case "down":
                            walker.walkDown();
                            break;
                        case "left":
                            walker.walkLeft();
                            break;
                        case "right":
                            walker.walkRight();
                            break;
                    }
                }

            } else {
                state = "idle";
            }
        }

        public void soundHandler() {
            if (state.equals("walking")) {
                if (gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2) {
                    if (footstepTimer >= footstepDelay) {
                        if (collisionChecker.footstepChecker().equals("dirt")) {
                            dirtFootstep.play();
                        } else if (collisionChecker.footstepChecker().equals("grass")) {
                            grassFootstep.play();
                        }
                        footstepTimer = 0;
                    } else {
                        footstepTimer++;
                    }
                } else if (gp.currentTileMap == gp.tileMap3) {
                    if (footstepTimer >= footstepDelay) {
                        if (collisionChecker.footstepChecker().equals("wood")) {
                            woodFootstep.play();
                        }
                        footstepTimer = 0;
                    } else {
                        footstepTimer++;
                    }
                } else if (gp.currentTileMap == gp.tileMap4) {
                    if (footstepTimer >= footstepDelay) {
                        if (collisionChecker.footstepChecker().equals("brick")) {
                            brickFootstep.play();
                        }
                        footstepTimer = 0;
                    } else {
                        footstepTimer++;
                    }
                }

            }
        }


        public void update(ImageManager imageManager) {
    //        System.out.println("X :" + worldX);
    //        System.out.println("Y :" + worldY);
            if (gp.gameState == UI.MOVING) {
                setScreenPosition();

                //code สำหรับ map 1 เมื่อเดินเข้าใกล้ระยะน้อง จะเปลี่ยนเเมพ
                if (((gp.mapM.screenIdleX <= -120 && gp.mapM.screenIdleX >= -280) && (gp.mapM.screenIdleY <= 550 && gp.mapM.screenIdleY >= 400)) && gp.currentTileMap == gp.tileMap1){
                        ui.bookPage.play();
                        UI.SCENE = 2;
                        gp.ui.showText = false;
                        gp.ui.startFade();
                        keyH.keyBoolRelease();
                }

                //เจอบ้าน
                if (((gp.mapM.screenIdleX <= -30 && gp.mapM.screenIdleY >= -280) && (gp.mapM.screenIdleY >= -100)) && gp.currentTileMap == gp.tileMap2){
                    UI.SCENE = 3;
                    ui.chalkPlayed = false;
                    ui.cutsceneCandy.play();
                    gp.ui.showText = false;
                    gp.ui.showImage = true;
                    gp.ui.imageDelay = 60;
                    gp.ui.startFade();
                    keyH.keyBoolRelease();
                }

                if (keyH.shiftPressed) {
                    if(ui.devmode){
                        speed = this.getSpeed()+8;
                    }else {
                        speed = this.getSpeed()+1;
                    }
                    if (gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2) {
                        footstepDelay = 18;
                    }  else if (gp.currentTileMap == gp.tileMap3) {
                        footstepDelay = 10;
                    } else if (gp.currentTileMap == gp.tileMap4) {
                        footstepDelay = 28;
                    }
                } else {
                    speed = this.getSpeed();
                    if (gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2) {
                        footstepDelay = 25;
                    } else if (gp.currentTileMap == gp.tileMap3) {
                        footstepDelay = 16;
                    } else if (gp.currentTileMap == gp.tileMap4) {
                        footstepDelay = 35;
                    }

                }
                speedDiag = (int) (speed/Math.sqrt(2));

                isCollisionOn = !keyH.isPlayerCollisionOn;

                moveCharacter(this);
                soundHandler();
                animationHandler(imageManager);
                if (gp.currentTileMap == gp.tileMap2) {
                    updateToGratel();
                } else if (gp.currentTileMap == gp.tileMap4) {
                    this.setHitbox((gp.tileSize * 2 - 18) / 2 , (gp.tileSize * 2 - 18) / 2 , 18 , 18);
                }

            }
        }

        public void updateToGratel() {
            gp.gratel.stateUpdate(state, animDirection);
        }

        public void draw(Graphics g) {
    //        g.fillRect(x, y, gp.xTile, gp.yTile);
            if (animImg != null && animImg.length > 0) {
                g.drawImage(animImg[currentIdleFrame], screenX, screenY, playerSize, playerSize, null);
                g.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
            }
        }

        public void setScreenPosition() {
            screenX = gp.mapX / 2 - gp.tileSize / 2;
            screenY = gp.mapY / 2 - gp.tileSize / 2;
        }

        //used for player position(x,y) changes
        @Override
        public void walkUp() {
            worldY -= speed;
        }

        @Override
        public void walkDown() {
            worldY += speed;
        }

        @Override
        public void walkLeft() {
            worldX -= speed;
        }

        @Override
        public void walkRight() {
            worldX += speed;
        }

        @Override
        public void walkDiagUpLeft() {
            worldX -= speedDiag;
            worldY -= speedDiag;
        }

        @Override
        public void walkDiagDownLeft() {
            worldX -= speedDiag;
            worldY += speedDiag;
        }

        @Override
        public void walkDiagUpRight() {
            worldX += speedDiag;
            worldY -= speedDiag;
        }

        @Override
        public void walkDiagDownRight() {
            worldX += speedDiag;
            worldY += speedDiag;
        }
    }
