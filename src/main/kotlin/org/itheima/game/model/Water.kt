package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 水墙
 *
 * @author Shelly
 * @date 2017/11/29
 */
class Water(override val x: Int, override val y: Int) : View {

    /**
     * 重载View的成员变量并赋值
     */
    override var width: Int = Config.block
    override val height: Int = Config.block

    /**
     * 绘制水墙
     */
    override fun draw() {
        Painter.drawImage("img/water.gif", x, y)
    }
}