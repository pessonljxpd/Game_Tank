package org.itheima.game.business

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
    fun willCollision(block: Blockable): Direction?

}