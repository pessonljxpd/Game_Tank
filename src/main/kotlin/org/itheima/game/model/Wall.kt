package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.Attackable
import org.itheima.game.business.Blockable
import org.itheima.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 砖墙
 *
 * @author Shelly
 * @date 2017/11/29
 */
class Wall(override val x: Int, override val y: Int) : Blockable, Sufferable {

    /**
     * 重载View的成员变量并赋值
     */
    override var width: Int = Config.block
    override val height: Int = Config.block

    /**
     * 绘制砖墙
     */
    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notifySuffer(attackable: Attackable) {
        println("砖墙接收到挨打了。。。。")
    }
}