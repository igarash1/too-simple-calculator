import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun myRow(
    content:
    @Composable
    (RowScope.() -> Unit),
) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content,
    )
}

enum class InputState {
    NUMBER,
    FRACTIONAL,
}

@Composable
@Preview
fun App() {
    var op: Operator by remember { mutableStateOf(Operator.Add(BigDecimal(0))) }
    var inputState: InputState by remember { mutableStateOf(InputState.NUMBER) }
    var displayNumber by remember { mutableStateOf(BigDecimal(0)) }
    var fracNth by remember { mutableStateOf(0) }

    // Utility functions
    fun BigDecimal.round() = this.setScale(6, RoundingMode.HALF_UP)

    fun BigDecimal.trim() = this.round().toString().dropLastWhile { it  == '0' }

    fun BigDecimal.trimAndPad(): String {
        val trimmed = this.trim()
        val fractionalLength = trimmed.dropWhile { it != '.' }.length + 1
        val padded = if (fracNth < fractionalLength) {
            trimmed
        } else {
            trimmed.padEnd(trimmed.length + fracNth - fractionalLength, '0')
        }
        return padded.takeIf { it.isNotEmpty() } ?: "0"
    }

    fun BigDecimal.addDigit(digit: Int, inputState: InputState) =
        if (inputState == InputState.NUMBER) {
            this.multiply(BigDecimal(10)).plus(BigDecimal(digit))
        } else {
            fracNth++
            this.plus(BigDecimal(digit).scaleByPowerOfTen(-fracNth))
        }

    @Composable
    fun buttonNum(num: Int) {
        Button(onClick = {
            displayNumber = displayNumber.addDigit(num, inputState)
        }) {
            Text(num.toString())
        }
    }

    fun resetStates() {
        displayNumber = BigDecimal(0)
        inputState = InputState.NUMBER
        fracNth = 0
    }

    // Layout
    MaterialTheme(colors = darkColors(primary = Color(0xffffeb46))) {
        Surface(color = Color(0xff91a4fc)) {
            Column {
                Column (modifier = Modifier.align(Alignment.End)) {
                    Text(
                        op.x.trim(),
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Text(
                        displayNumber.trimAndPad(),
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                myRow {
                    Button(onClick = {
                        resetStates()
                    }) {
                        Text("C")
                    }
                    Button(onClick = {
                        inputState = InputState.FRACTIONAL
                    }) {
                        Text(".")
                    }
                    Button(onClick = {
                        op = Operator.Pow(op.calculate(displayNumber))
                        resetStates()
                    }) {
                        Text("x^y")
                    }
                    Button(onClick = {
                        op = Operator.Add(BigDecimal(0))
                        resetStates()
                    }) {
                        Text("AC")
                    }
                }

                myRow {
                    (1..3).forEach { buttonNum(it) }
                    Button(onClick = {
                        op = Operator.Add(op.calculate(displayNumber))
                        resetStates()
                    }) {
                        Text("+")
                    }
                }

                myRow {
                    (4..6).forEach { buttonNum(it) }
                    Button(onClick = {
                        op = Operator.Mult(op.calculate(displayNumber))
                        resetStates()
                    }) {
                        Text("x")
                    }
                }

                myRow {
                    (7..9).forEach { buttonNum(it) }
                    Button(onClick = {
                        op = Operator.Sub(op.calculate(displayNumber))
                        resetStates()
                    }) {
                        Text("-")
                    }
                }

                myRow {
                    buttonNum(0)
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(0, inputState)
                        displayNumber = displayNumber.addDigit(0, inputState)
                    }) {
                        Text("00")
                    }
                    Button(onClick = {
                        displayNumber = op.calculate(displayNumber)
                        op = Operator.Add(BigDecimal(0))
                    }) {
                        Text("=")
                    }
                    Button(onClick = {
                        op = Operator.Div(op.calculate(displayNumber.round()))
                        resetStates()
                    }) {
                        Text("/")
                    }
                }
            }
        }
    }
}

fun main() =
    application {
        val state = rememberWindowState(
            position = WindowPosition(Alignment.Center), size = DpSize(300.dp, 330.dp)
        )
        Window(
            onCloseRequest = ::exitApplication,
            title = "Simple Calculator",
            state = state,
        ) {
            App()
        }
    }
