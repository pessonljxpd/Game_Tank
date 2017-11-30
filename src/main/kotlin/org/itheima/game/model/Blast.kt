package org.itheima.game.model

import org.itheima.game.Config
import org.itheima.game.business.Destroyable
import org.itheima.kotlin.game.core.Painter

/**
 * TODO: description
 *
 * @author Shelly
 * @date 2017/11/30
 */
class Blast(override val x: Int, override val y: Int) : Destroyable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    private val imagePaths = arrayListOf<String>()
    private var index: Int = 0

    init {
        (1..32).forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        index = index % imagePaths.size
        Painter.drawImage(imagePaths[index], x, y)
        index++
    }


    override fun isDestroyed(): Boolean = index >= imagePaths.size
}