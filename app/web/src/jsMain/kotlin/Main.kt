import androidx.compose.ui.window.Window
import com.seiko.markdown.demo.App
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    Napier.base(DebugAntilog())
    onWasmReady {
        Window("ComposeDemo") {
            App()
        }
    }
}
