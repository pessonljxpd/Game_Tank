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
    val x: Int
    val y: Int

    /**
     * 宽、高
     */
    val width: Int
    val height: Int

    /**
     * 绘制视图到窗体
     */
    fun draw()

    /**
     * 碰撞检测
     */
    fun checkCollision(blockX: Int, blockY: Int, blockWidth: Int, blockHeight: Int,
                       viewX: Int, viewY: Int, viewWidth: Int, viewHeight: Int): Boolean {

        return when {
            viewY + viewHeight <= blockY -> //如果 阻挡物 在运动物的上方时 ，不碰撞
                false
            blockY + blockHeight <= viewY -> //如果 阻挡物 在运动物的下方时 ，不碰撞
                false
            viewX + viewWidth <= blockX -> //如果 阻挡物 在运动物的左方时 ，不碰撞
                false
            else -> blockX + blockWidth > viewX
        }

    }


}