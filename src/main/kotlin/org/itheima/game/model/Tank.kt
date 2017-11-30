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
     * 坦克不可以移动的方向
     */
    private var badDirection: Direction? = null

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
        if (direction == badDirection) {
            return
        }

        if (currentDirection != direction) {
            currentDirection = direction
            return
        }

        when (currentDirection) {
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

    override fun willCollision(block: Blockable?): Direction? {

        // 1. 计算出下一时刻移动物体的坐标
        var x = this.x
        var y = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        val collision = checkCollision(block?.x!!, block.y, block.width, block.height, x, y, width, height);

        return if (collision) currentDirection else null

    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    fun shot(): Bullet {
        return Bullet(currentDirection, { bulletWidth, bulletHeight ->
            // 计算子弹的真实坐标
            var tankX = x
            var tankY = y
            var tankHeight = height
            var tankWidth = width
            var bulletX = 0
            var bulletY = 0

            when (currentDirection) {
                Direction.UP -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY + tankHeight - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = tankX + tankWidth - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
            }

            Pair(bulletX, bulletY)
        })
    }

}