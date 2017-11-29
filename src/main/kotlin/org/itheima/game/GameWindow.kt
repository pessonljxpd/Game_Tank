package org.itheima.game

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.game.business.Blockable
import org.itheima.game.business.Movable
import org.itheima.game.enums.Direction
import org.itheima.game.model.*
import org.itheima.kotlin.game.core.Window
import java.io.File

/**
 *
 */
class GameWindow : Window("GameTank V1.0", "img/logo.jpg", Config.gameWidth, Config.gameHeight) {

    /**
     * 管理视图元素的集合
     */
    private var views = arrayListOf<View>()

    private lateinit var tank: Tank

    override fun onCreate() {
        // 通过读文件的方式创建地图
        var file = File(javaClass.getResource("/map/00.map").path)
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
        var direction: Direction = when (event.code) {
            KeyCode.W -> Direction.UP
            KeyCode.S -> Direction.DOWN
            KeyCode.A -> Direction.LEFT
            KeyCode.D -> Direction.RIGHT
            else -> {
                TODO()
            }
        }

        tank.move(direction)
    }

    override fun onRefresh() {

        // 1. 找到运动的物体
        views.filter { it -> it is Movable }.forEach { move ->
            // 2. 找到阻塞的物体
            views.filter { it -> it is Blockable }.forEach { block ->
                // 3. 判断move和block是否碰撞

            }
        }

    }

    private fun drawMap() {
        views.forEach {
            it.draw()
        }
    }
}