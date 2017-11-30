package org.itheima.game.business

import org.itheima.game.model.View

/**
 * 攻击能力
 *
 * @author Shelly
 * @date 2017/11/30
 */
interface Attackable : View {

    /**
     * 所有者
     */
    val owner: View

    /**
     * 攻击力
     */
    val attackPower: Int

    /**
     * 判断是否碰撞
     */
    fun isCollision(sufferable: Sufferable): Boolean

    /**
     * 通知攻击者攻击了被攻击者
     */
    fun notifyAttack(sufferable: Sufferable)
}