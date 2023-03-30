import androidx.compose.ui.window.Window
import com.seiko.markdown.demo.App
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("ComposeDemo") {
            App()
        }
    }
}
