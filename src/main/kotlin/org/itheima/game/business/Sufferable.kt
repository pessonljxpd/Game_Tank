package org.itheima.game.business

import org.itheima.game.model.View

/**
 * 受攻击能力
 *
 * @author Shelly
 * @date 2017/11/30
 */
interface Sufferable:View {

    /**
     * 生命值
     */
    val blood:Int

    /**
     * 通知被攻击者遭受了攻击者的攻击
     */
    fun notifySuffer(attackable: Attackable)
}