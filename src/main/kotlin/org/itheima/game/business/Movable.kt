package org.itheima.game.business

import org.itheima.game.Config
import org.itheima.game.enums.Direction
import org.itheima.game.model.View

/**
 * 定义物体移动的能力
 *
 * @author Shelly
 * @date 2017/11/29
 */
interface Movable : View {

    /**
     * 可移动物体的方向
     */
    val currentDirection: Direction

    /**
     * 可移动物体需要移动的速度
     */
    val speed: Int


    /**
     * 判断移动的物体是否和阻塞的物体发生碰撞
     *
     * @return 返回将要碰撞的方向
     */
    fun willCollision(block: Blockable?): Direction? {
        // 1. 计算出下一时刻移动物体的坐标
        var x = this.x
        var y = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        if (x < 0) return Direction.LEFT
        if (y < 0) return Direction.UP
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y > Config.gameHeight - height) return Direction.DOWN


        val collision = checkCollision(block?.x!!, block.y, block.width, block.height, x, y, width, height);

        return if (collision) currentDirection else null
    }


    fun notifyCollision(direction: Direction?, block: Blockable?)

}