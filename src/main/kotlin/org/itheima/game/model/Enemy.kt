package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.*
import org.itheima.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.util.*

/**
 * 敌方坦克
 *
 * @author Shelly
 * @date 2017/11/30
 */
class Enemy(override var x: Int, override var y: Int)
    : Movable, AutoMovable, Blockable, AutoShot {

//    override var blood: Int = 2
    override val width: Int = Config.block
    override val height: Int = Config.block
    override val speed: Int = 6

    override var currentDirection: Direction = Direction.DOWN
    /**
     * 坦克不可以移动的方向
     */
    private var badDirection: Direction? = null

    private var lastShotTime = 0L
    private var shotFrequence = 800L

    private var lastMoveTime = 0L
    private var moveFrequence = 30L

    override fun draw() {
        var imagePath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

//    override fun isDestroyed(): Boolean = blood <= 0

    override fun autoMove() {
        var currentMoveTime = System.currentTimeMillis()
        if (currentMoveTime - lastMoveTime < moveFrequence) return
        lastMoveTime = currentMoveTime

        if (currentDirection == badDirection) {
            currentDirection = randomDirection(badDirection)
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

    private fun randomDirection(badDirection: Direction?): Direction {
        val i = Random().nextInt(4)
        val direction = when (i) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }
        if (direction == badDirection) {
            return randomDirection(badDirection)
        } else {
            return direction
        }
    }

    override fun autoShot(): View? {

        var currentShotTime = System.currentTimeMillis()
        if (currentShotTime - lastShotTime < shotFrequence) return null
        lastShotTime = currentShotTime

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

//    override fun notifySuffer(attackable: Attackable): Array<View>? {
//        if (attackable.owner is Enemy) {
//            return null
//        }
//
//        blood -= attackable.attackPower
//        return arrayOf(Blast(x, y))
//    }
}