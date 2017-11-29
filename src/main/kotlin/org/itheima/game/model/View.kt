package org.itheima.game.model

/**
 * 显示视图，定义显示规范
 *
 * @author Shelly
 * @date 2017/11/29
 */
interface View {
    /**
     * x、y轴坐标
     */
    val x:Int
    val y:Int

    /**
     * 宽、高
     */
    val width:Int
    val height:Int

    /**
     * 绘制视图到窗体
     */
    fun draw()


}