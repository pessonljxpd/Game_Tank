package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.*
import org.itheima.game.enums.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * 我方坦克
 *
 * @author Shelly
 * @date 2017/11/29
 */
class Tank(override var x: Int, override var y: Int)
    : Movable, Blockable, Sufferable, Destroyable {

    /**
     * 血量
     */
    override var blood: Int = 10

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

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    fun shot(): Bullet {
        return Bullet(this, currentDirection, { bulletWidth, bulletHeight ->
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

    override fun isDestroyed(): Boolean = blood <= 0

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        if (attackable.owner is Tank) {
            return null
        }
        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }
}