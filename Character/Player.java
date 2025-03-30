    package Character;

    import Main.*;
    import java.awt.*;

    public class Player extends Human implements Walkable {
        final int walkAnimDelay = 7;
        int currentFrame = 0;
        int currentIdleFrame = 0;


        int footstepTimer = 0;
        int footstepDelay = 25;
        public Sound dirtFootstep, grassFootstep, brickFootstep;



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
            ui = new UI(gp,this);
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
            dirtFootstep= new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/dirt-footstep.wav");
            grassFootstep = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/grass-footstep.wav");
            brickFootstep = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/brick-footstep.wav");
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

                //เสายาวซ้าย
                collisionChecker.setCollisionPlusX(860,829,1508,850, gp.tileMap3);
                //
                collisionChecker.setCollisionPlusY(1163,858,1150,1134,gp.tileMap3);
                //
                collisionChecker.setCollisionMinusY(1163,860,1000,990,gp.tileMap3);
                //
                collisionChecker.setCollisionMinusX(1100,1088,1366,1150,gp.tileMap3);
                //
                collisionChecker.setCollisionPlusX(1165,1120,1360,1145,gp.tileMap3);
                //ขอบขนบรรได
                collisionChecker.setCollisionPlusY(1100,859,920,910, gp.tileMap3);
                //ขอบขวาเสาซ้าย
                collisionChecker.setCollisionPlusX(1180,1150,1129,1025,gp.tileMap3);
                //ขอบเสาซ้ายเล็กๆ
                collisionChecker.setCollisionPlusY(1135,1100,1390,1376, gp.tileMap3);
                //ขอบล่าง
                collisionChecker.setCollisionMinusY(1500,850,1513,1505,gp.tileMap3);
                //ขอบขวา
                collisionChecker.setCollisionMinusX(1500,1491,1503,918,gp.tileMap3);
                //ขอบประตูขวา
                collisionChecker.setCollisionPlusY(1500,1470,1410,1400, gp.tileMap3);
                //ขอบประตูขวา ขวา
                collisionChecker.setCollisionMinusX(1500,1449,1384,1290,gp.tileMap3);
                //ขอบขวา ซ้าย
                collisionChecker.setCollisionPlusY(1405,1245,1405,1400, gp.tileMap3);
                //ขอบขวา ขวา
                collisionChecker.setCollisionMinusX(1235,1226,1389,1028,gp.tileMap3);
                //ขอบขวา ซ้าย
                collisionChecker.setCollisionPlusX(1440,1429,1389,1293,gp.tileMap3);
                //ขวาซ้าย บน
                collisionChecker.setCollisionMinusY(1400,1286,1273,1268,gp.tileMap3);
                //ขวาซ้าย ซ้าย
                collisionChecker.setCollisionPlusX(1300,1289,1266,1133,gp.tileMap3);
                //ขอบบนห้องนอนเล็ก
                collisionChecker.setCollisionPlusY(1490,1280,1150,1148, gp.tileMap3);
                //ขอบล่าง
                //ขอบบน
                collisionChecker.setCollisionPlusY(1490,1260,1020,1011, gp.tileMap3);
                //
                collisionChecker.setCollisionMinusX(1100,1085,957,922,gp.tileMap3);

                //code สำหรับ map 1 เมื่อเดินเข้าใกล้ระยะน้อง จะเปลี่ยนเเมพ
                if (((gp.mapM.screenIdleX <= -120 && gp.mapM.screenIdleX >= -280) && (gp.mapM.screenIdleY <= 550 && gp.mapM.screenIdleY >= 400)) && gp.currentTileMap == gp.tileMap1){
                        ui.bookPage.play();
                        UI.SCENE = 2;
                        ui.chalkPlayed = false;
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
                    speed = this.getSpeed()+1;
                    if (gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2) {
                        footstepDelay = 18;
                    } else if (gp.currentTileMap == gp.tileMap4) {
                        footstepDelay = 28;
                    }
                }else {
                    speed = this.getSpeed();
                    if (gp.currentTileMap == gp.tileMap1 || gp.currentTileMap == gp.tileMap2) {
                        footstepDelay = 25;
                    } else if (gp.currentTileMap == gp.tileMap4) {
                        footstepDelay = 35;
                    }

                }
                speedDiag = (int) (speed/Math.sqrt(2));

                isCollisionOn = !keyH.isPlayerCollisionOn;

                moveCharacter(this);
                soundHandler();
                animationHandler(imageManager);
            }
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
