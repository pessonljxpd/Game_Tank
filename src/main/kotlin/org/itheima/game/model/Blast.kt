package org.itheima.game.model

import org.itheima.game.Config

/**
 * TODO: description
 *
 * @author Shelly
 * @date 2017/11/30
 */
class Blast(override val x: Int, override val y: Int) :View {
    override val width: Int = Config.block
    override val height: Int = Config.block

    private val imagePaths = arrayListOf<String>()

    init {
        (1..32).forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {

    }
}