package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.Attackable
import org.itheima.game.business.AutoMovable
import org.itheima.game.business.Destroyable
import org.itheima.game.business.Sufferable
import org.itheima.game.enums.Direction
import org.itheima.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * 子弹
 *
 * @author Shelly
 * @date 2017/11/30
 */
class Bullet(override val owner: View, override val currentDirection: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>)
    : AutoMovable, Destroyable, Attackable {

    override var attackPower: Int = 1

    override val speed: Int = 8

    override var x: Int = 0
    override var y: Int = 0

    override val width: Int
    override val height: Int

    private var isDestroyed = false

    private var imagePath = when (currentDirection) {
        Direction.UP -> "img/shot_top.gif"
        Direction.DOWN -> "img/shot_bottom.gif"
        Direction.LEFT -> "img/shot_left.gif"
        Direction.RIGHT -> "img/shot_right.gif"
    }

    init {
        val size = Painter.size(imagePath)
        width = size[0]
        height = size[1]

        val pair = create.invoke(width, height)
        x = pair.first
        y = pair.second

    }

    override fun draw() {
        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestroyed(): Boolean {
        return when {
            isDestroyed -> true
            x < -width -> true
            x > Config.gameWidth -> true
            y < -height -> true
            y > Config.gameHeight -> true
            else -> false
        }
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        isDestroyed = true
    }
}