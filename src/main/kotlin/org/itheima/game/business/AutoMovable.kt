package org.itheima.game.business

import org.itheima.game.enums.Direction
import org.itheima.game.model.View


/**
 * TODO: description
 *
 * @author Shelly
 * @date 2017/11/30
 */
interface AutoMovable: View {
    val currentDirection:Direction
    val speed:Int

    fun autoMove()
}