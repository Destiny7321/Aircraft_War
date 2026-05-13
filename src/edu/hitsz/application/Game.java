package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.dao.Score;
import edu.hitsz.dao.ScoreDao;
import edu.hitsz.dao.ScoreDaoImpl;
import edu.hitsz.observer.EnemyObserver;
import edu.hitsz.observer.PropSubject;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.PropFactory;
import edu.hitsz.application.MusicThread;
import edu.hitsz.swing.RankFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Timer;
import javax.swing.*;
/**
 * 游戏主面板，游戏启动
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;
    // 观察者模式：道具主题（被观察者）
    private PropSubject propSubject;
    //调度器, 用于定时任务调度
    private final Timer timer;
    //时间间隔(ms)，控制刷新频率
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> propList;

    // 音效线程
    private MusicThread bgmThread;       // 普通背景音乐
    private MusicThread bossBgmThread;  // Boss背景音乐

    // 标记音乐是否在播放
    private boolean isBgmPlaying = false;
    private boolean isBossBgmPlaying = false;

    // 难度控制
    private String difficulty;

    // 难度核心参数
    private int enemyBaseHp;           // 敌机基础血量
    private double enemySpeed;         // 敌机速度
    private int heroShootCycle;        // 英雄射击间隔
    private int enemyShootCycle;       // 敌机射击间隔
    private boolean canSpawnBoss;      // 是否生成 Boss
    private boolean difficultyIncrease; // 是否随时间变难
    protected double enemySpawnCycle  =  20;
    private int enemySpawnCounter = 0;
    private int enemyShootCounter = 0;
    private int bossHp = 500;          // Boss 基础血量
    //屏幕中出现的敌机最大数量
    private final int enemyMaxNumber = 5;

    //敌机工厂生成
    private EnemyFactory enemyFactory;

    //英雄机和敌机射击周期
    protected double shootCycle = 10;
    private int shootCounter = 0;

    //当前玩家分数
    private int score = 0;
    private int scoreToNextBoss = 200; // 距离下一只BOSS还需多少分

    // 难度递进参数（控制台输出）
    private double eliteEnemyRate = 0.20;      // 初始精英机概率
    private double enemySpawnRate = 1.0;       // 敌机生成周期倍率（数值越小越难）
    private double enemyAttrMultiplier = 1.0;  // 敌机属性（血量/速度）提升倍率
    private final double difficultyStep = 0.02;// 每次提升的步长

    // 标记防止重复输出日志
    private boolean difficultyRaised = false;

    //游戏结束标志
    private boolean gameOverFlag = false;
    //判断boss敌机是否出现
    private boolean bossAppeared = false;
    // 肉鸽强化系统（附加内容）
    private boolean gamePaused = false;
    private int heroDamage = 15;      // 英雄子弹伤害
    private int heroMaxHp = 100;      // 血量上限
    private TimerTask gameTask;
    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        propList = new LinkedList<>();
        propSubject = new PropSubject() {};
        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        this.timer = new Timer("game-action-timer", true);

    }

    public Game(String difficulty) {
        this();
        this.difficulty = difficulty;

        switch (difficulty) {
            case "traveller":
                // 简单：无Boss，不变难
                this.enemySpawnCycle = 30;
                this.enemyBaseHp = 30;
                this.enemySpeed = 1;
                this.heroShootCycle = 10;
                this.enemyShootCycle = 40;
                this.canSpawnBoss = false;
                this.difficultyIncrease = false;
                break;
            case "survivor":
                // 普通：Boss血固定，慢慢变难
                this.enemySpawnCycle = 20;
                this.enemyBaseHp = 50;
                this.enemySpeed = 2;
                this.heroShootCycle = 10;
                this.enemyShootCycle = 35;
                this.canSpawnBoss = true;
                this.difficultyIncrease = true;
                break;
            case "warrior":
                // 困难：Boss血递增，急剧变难
                this.enemySpawnCycle = 10;
                this.enemyBaseHp = 80;
                this.enemySpeed = 3;
                this.heroShootCycle = 15;
                this.enemyShootCycle = 30;
                this.canSpawnBoss = true;
                this.difficultyIncrease = true;
                break;
        }

        JFrame frame = new JFrame();
        frame.add(this);
        frame.setSize(512, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // 启动背景音乐
        bgmThread = new MusicThread("src/videos/bgm.wav");
        bgmThread.start();
        isBgmPlaying = true;
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、及结束判定
        gameTask = new TimerTask() {
            @Override
            public void run() {
                //难度递增逻辑
                if (difficultyIncrease) {
                    if (score % 200 == 0 && score > 0) {
                        if (difficulty.equals("survivor")) {
                            // 普通：提升 敌机周期、速度、血量
                            enemySpawnCycle = Math.max(10, enemySpawnCycle - 1);
                            enemySpeed += 0.1;
                            enemyBaseHp += 5;
                        }
                        else if (difficulty.equals("warrior")) {
                            // 困难：全部拉满
                            enemySpawnCycle = Math.max(5, enemySpawnCycle - 1);
                            enemySpeed += 0.2;
                            enemyBaseHp += 10;
                            heroShootCycle = Math.min(20, heroShootCycle + 1);
                            enemyShootCycle = Math.max(8, enemyShootCycle - 1);
                        }
                    }
                }
        //敌机生成规律控制
                //BOSS 200 分触发
                if (canSpawnBoss && score >= scoreToNextBoss && !bossAppeared) {

                    AbstractAircraft boss = new BossEnemyFactory().createEnemy();

                    int bossHp = (score / 200 - 1) * 500;

                    boss.decreaseHp(-bossHp);



                    enemyAircrafts.add(boss);
                    bossAppeared = true;
                    System.out.println("BOSS 已生成，当前血量：" + boss.getHp());

                    // 停止普通背景音乐
                    if (bgmThread != null) {
                        bgmThread.interrupt();
                        isBgmPlaying = false;
                    }
                    // 播放Boss背景音乐
                    if (!isBossBgmPlaying) {
                        bossBgmThread = new MusicThread("src/videos/bgm_boss.wav");
                        bossBgmThread.start();
                        isBossBgmPlaying = true;
                    }
                }
                enemySpawnCounter++;
                if (enemySpawnCounter >= enemySpawnCycle) {
                    enemySpawnCounter = 0;

                    if (enemyAircrafts.size() < enemyMaxNumber) {

                        // 生成 0~1 随机数，平均分配4种敌机（各25%）
                        double rand = Math.random();
                        EnemyFactory factory;

                        // 四种敌机，每种 25% 概率
                        if (rand < 0.25) {
                            // 25% 普通敌机
                            factory = new MobEnemyFactory();
                        } else if (rand < 0.50) {
                            // 25% 精英敌机 Pro
                            factory = new ProEnemyFactory();
                        } else if (rand < 0.75) {
                            // 25% 精锐敌机 Promax
                            factory = new PromaxEnemyFactory();
                        } else {
                            // 25% 王牌敌机 Ace
                            factory = new AceEnemyFactory();
                        }
                        AbstractAircraft enemy = factory.createEnemy();
                        propSubject.attach((EnemyObserver) enemy);
                        enemyAircrafts.add(enemy);
                    }
                }
                // 火力道具计时自动恢复
                if (heroAircraft.getFireDuration() > 0) {
                    heroAircraft.setFireDuration(heroAircraft.getFireDuration() - 1);
                    //恢复普通单发
                    if (heroAircraft.getFireDuration() <= 0) {
                        heroAircraft.resetFire();
                    }
                }
                // 开启难度递进
                if ("survivor".equals(difficulty) || "warrior".equals(difficulty)) {
                    // 每获得200分提升一次难度（可根据需求调整）
                    if (score % 200 == 0 && score > 0 && !difficultyRaised) {
                        difficultyRaised = true;

                        // 更新难度参数
                        eliteEnemyRate = Math.min(0.5, eliteEnemyRate + difficultyStep);
                        enemySpawnRate = Math.max(0.5, enemySpawnRate - difficultyStep);
                        enemyAttrMultiplier += difficultyStep;

                        // 控制台输出
                        System.out.printf("提高难度: 精英机概率:%.2f, 敌机周期:%.2f, 敌机属性提升倍率:%.2f.%n",
                                eliteEnemyRate,
                                enemySpawnCycle * enemySpawnRate,
                                enemyAttrMultiplier);
                    }
                    //重置标记
                    if (score % 200 != 0) {
                        difficultyRaised = false;
                    }
                }
                // 飞机发射子弹
                shootAction();
                // 子弹移动
                bulletsMoveAction();
                // 飞机移动
                aircraftsMoveAction();
                //道具移动
                propsMoveAction();
                // 撞击检测
                crashCheckAction();
                // 后处理
                postProcessAction();
                // 重绘界面
                repaint();
                // 游戏结束检查
                checkResultAction();
            }
        };
        // 以固定延迟时间进行执行：本次任务执行完成后，延迟 timeInterval 再执行下一次
        timer.schedule(gameTask,0,timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        shootCounter++;
        enemyShootCounter++;

        // 英雄机射击
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            heroBullets.addAll(heroAircraft.shoot());
        }

        // 敌机射击单独计时
        if (enemyShootCounter >= enemyShootCycle) {
            enemyShootCounter = 0;
            for (AbstractAircraft enemy : enemyAircrafts) {
                List<BaseBullet> bullets = enemy.shoot();
                enemyBullets.addAll(bullets);
                for (BaseBullet bullet : bullets) {
                    propSubject.attach((EnemyObserver) bullet);
                }
            }
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : propList) {
            prop.forward();
        }
    }
    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄机
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    continue;
                }

                if (enemyAircraft.crash(bullet)) {
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();

                    if (enemyAircraft.notValid()) {
                        score += 10;
                        new MusicThread("src/videos/bullet_hit.wav").start();

                        // 普通精英敌机掉落道具
                        if (enemyAircraft instanceof ProEnemy
                                || enemyAircraft instanceof PromaxEnemy
                                || enemyAircraft instanceof AceEnemy) {
                            AbstractProp prop = PropFactory.createProp(
                                    enemyAircraft,
                                    enemyAircraft.getLocationX(),
                                    enemyAircraft.getLocationY(),
                                    propSubject
                            );
                            if (prop != null) {
                                propList.add(prop);
                            }
                        }

                        //BOSS 死亡处理
                        if (enemyAircraft instanceof BossEnemy) {
                            // BOSS 掉落3个道具
                            for (int i = 0; i < 3; i++) {
                                AbstractProp prop = PropFactory.createProp(
                                        enemyAircraft,
                                        enemyAircraft.getLocationX(),
                                        enemyAircraft.getLocationY(),
                                        propSubject
                                );
                                if (prop != null) {
                                    propList.add(prop);
                                }
                            }
                            scoreToNextBoss = score + 200;
                            bossAppeared = false;
                            //肉鸽强化
                            String[] opts = {"伤害增加", "射速增加", "血量上限提升"};
                            int select = JOptionPane.showOptionDialog(
                                    Game.this,
                                    "已击败Boss，请选择一项强化：",
                                    "我要变强！",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.PLAIN_MESSAGE,
                                    null, opts, opts[0]
                            );

                            // 应用强化
                            switch (select) {
                                case 0:
                                    heroDamage += 5;
                                    System.out.printf("选择伤害增加，当前伤害：%d，当前射速：%.0f%n",
                                            heroDamage, shootCycle);
                                    break;
                                case 1:
                                    shootCycle = Math.max(4, shootCycle - 1);
                                    System.out.printf("选择射速增加，当前伤害：%d，当前射速：%.0f%n",
                                            heroDamage, shootCycle);
                                    break;
                                case 2:
                                    heroMaxHp += 400;
                                    heroAircraft.decreaseHp(-heroMaxHp);
                                    System.out.printf("选择血量恢复，当前伤害：%d，当前射速：%.0f%n",
                                            heroDamage, shootCycle);
                                    break;
                            }

                            // 切换BGM
                            if (bossBgmThread != null) {
                                bossBgmThread.interrupt();
                                bossBgmThread = null;
                                isBossBgmPlaying = false;
                            }
                            bgmThread = new MusicThread("src/videos/bgm.wav");
                            bgmThread.start();
                            isBgmPlaying = true;
                        }
                    }
                }

                // 英雄机和敌机相撞
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 获得道具
        for (AbstractProp prop : propList) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.vanish();
                prop.apply(heroAircraft);
                new MusicThread("src/videos/get_supply.wav").start();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 删除无效的道具
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        propList.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 检查游戏是否结束，若结束：关闭线程池
     */
    private void checkResultAction(){
        if (heroAircraft.getHp() <= 0) {
            timer.cancel();
            gameOverFlag = true;
            System.out.println("Game Over!");

            if (bgmThread != null) {
                bgmThread.interrupt();
                bgmThread = null;
            }
            if (bossBgmThread != null) {
                bossBgmThread.interrupt();
                bossBgmThread = null;
            }
            new MusicThread("src/videos/game_over.wav").start();

            String playerName = JOptionPane.showInputDialog("游戏结束！请输入玩家姓名：");
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "匿名玩家";
            }

            ScoreDao scoreDao = new ScoreDaoImpl();
            scoreDao.addScore(new Score(playerName.trim(), score, LocalDateTime.now()));

            SwingUtilities.invokeLater(() -> new RankFrame(scoreDao.getAllScore()));
        }
    }

    //***********************
    //      Paint 各部分
    //***********************
    /**
     * 重写 paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage currentBg;

        if ("traveler".equals(difficulty)) {
            currentBg = ImageManager.BACKGROUND_TRAVELLER;
        }
        else if ("survivor".equals(difficulty)) {
            currentBg = ImageManager.BACKGROUND_SURVIVOR;
        }
// 这里改成 warrior
        else if ("warrior".equals(difficulty)) {
            currentBg = ImageManager.BACKGROUND_WARRIOR;
        }
        else {
            currentBg = ImageManager.BACKGROUND_IMAGE;
        }

        // 绘制背景,图片滚动
        g.drawImage(currentBg, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(currentBg, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);

        //绘制道具
        paintImageWithPositionRevised(g, propList);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.isEmpty()) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE: " + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE: " + this.heroAircraft.getHp(), x, y);
    }

}