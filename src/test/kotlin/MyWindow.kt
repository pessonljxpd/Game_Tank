import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

/**
 * Created by Shelly on 2017-11-29.
 */
class MyWindow: Window() {
    override fun onCreate() {
        println("onCreate")

    }

    override fun onDisplay() {
//        println("onDisplay")
        // 绘制图片
        Painter.drawImage("tank_u.gif", 200, 200)
        Painter.drawColor(Color.GREEN, 0,0, 100,100)
        Painter.drawText("Hello Kotlin", 10,30)
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.ENTER -> {
                println("OnEnter")
            }
            KeyCode.L->{
                Composer.play("blast.wav")
            }
        }
    }

    override fun onRefresh() {
//        println("onRefresh")
    }
}


fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}