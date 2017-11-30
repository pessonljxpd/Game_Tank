package org.itheima.game

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.game.business.*
import org.itheima.game.enums.Direction
import org.itheima.game.model.*
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 */
class GameWindow : Window("GameTank V1.0", "img/logo.jpg", Config.gameWidth, Config.gameHeight) {

    /**
     * 管理视图元素的集合
     */
    private var views = CopyOnWriteArrayList<View>()

    private lateinit var tank: Tank

    override fun onCreate() {
        // 通过读文件的方式创建地图
        var file = File(javaClass.getResource("/map/1.map").path)
        var lines: List<String> = file.readLines()

        var lineNum = 0
        lines.forEach { line ->

            var columnNum = 0

            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> {
                        views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    }
                    '草' -> {
                        views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                    }
                    '铁' -> {
                        views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    }
                    '水' -> {
                        views.add(Water(columnNum * Config.block, lineNum * Config.block))
                    }
                    '敌' -> {
                        views.add(Enemy(columnNum * Config.block, lineNum * Config.block))
                    }
                }

                columnNum++
            }

            lineNum++
        }

        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

    }

    override fun onDisplay() {
        // 绘制地图中的元素
        drawMap()
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.W -> tank.move(Direction.UP)
            KeyCode.S -> tank.move(Direction.DOWN)
            KeyCode.A -> tank.move(Direction.LEFT)
            KeyCode.D -> tank.move(Direction.RIGHT)
            KeyCode.ENTER -> {
                val bullet = tank.shot()
                views.add(bullet)
            }
        }
    }

    override fun onRefresh() {

        // 1. 找到运动的物体
        views.filter { it -> it is Movable }.forEach { move ->
            // 2. 找到阻塞的物体
            move as Movable

            var badDirection: Direction? = null
            var badBlock: Blockable? = null

            views.filter { it -> (it is Blockable) and (move != it) }.forEach blockTag@ { block ->
                // 3. 判断move和block是否碰撞
                block as Blockable

                val direction = move.willCollision(block)

                direction?.let {

                    badDirection = direction
                    badBlock = block

                    // 移动的物体发生碰撞就跳出当前的循环
                    return@blockTag
                }
            }

            // 4. 通知移动物体将要在哪个方向上碰撞
            move.notifyCollision(badDirection, badBlock)
        }


        // 找出所有自动移动的物体，触发autoMove动作
        views.filter { it -> it is AutoMovable }.forEach {
            (it as AutoMovable).autoMove()
        }

        // 检测销毁
        views.filter { it is Destroyable }.forEach {
            if ((it as Destroyable).isDestroyed()) {
                views.remove(it)
            }
        }

        views.filter { it is Attackable }.forEach { attack ->

            attack as Attackable

            views.filter { it is Sufferable }.forEach sufferTag@ { suffer ->

                suffer as Sufferable

                if (attack.isCollision(suffer)) {
                    // 检测到产生碰撞后，通知攻击者、被攻击者
                    attack.notifyAttack(suffer)
                    var sufferView = suffer.notifySuffer(attack)

                    sufferView?.let {
                        // 显示挨打的效果
                        views.addAll(sufferView)
                    }

                    return@sufferTag
                }
            }
        }

        // 检测自动射击的View
        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val autoShot = it.autoShot()
            autoShot?.let {
                views.add(autoShot)
            }
        }


    }

    private fun drawMap() {
        views.forEach {
            it.draw()
        }
    }
}