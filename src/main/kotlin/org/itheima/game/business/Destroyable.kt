package org.itheima.game.business

import org.itheima.game.model.View

/**
 * 自动销毁的能力
 *
 * @author Shelly
 * @date 2017/11/30
 */
interface Destroyable : View {

    /**
     * 判断是否销毁了
     */
    fun isDestroyed(): Boolean


    /**
     * 死给你看
     */
    fun showDestroy(): Array<View>? {
        return null
    }
}