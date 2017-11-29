package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.Blockable
import org.itheima.game.business.Movable
import org.itheima.game.enums.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * 我方坦克
 *
 * @author Shelly
 * @date 2017/11/29
 */
class Tank(override var x: Int, override var y: Int) : Movable, Blockable {

    /**
     * 坦克移动的速度
     */
    override val speed: Int = 8

    /**
     * 坦克的默认大小
     */
    override val width: Int = Config.block
    override val height: Int = Config.block

    /**
     * 坦克的初始朝向
     */
    override var currentDirection: Direction = Direction.UP

    /**
     * 根据方向绘制坦克
     */
    override fun draw() {
        var imagePath = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    fun move(direction: Direction) {
        if (currentDirection != direction) {
            currentDirection = direction
            return
        }

        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameWidth - width) y = Config.gameWidth - height
    }

    override fun willCollision(block: Blockable): Direction? {
        return null;
    }

}