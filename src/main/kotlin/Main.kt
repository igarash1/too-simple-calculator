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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.math.BigDecimal

@Composable
@Preview
fun App() {
    var op: Operator by remember { mutableStateOf(Operator.Add(BigDecimal(0))) }
    var displayNumber by remember { mutableStateOf(BigDecimal(0)) }

    @Composable
    fun buttonNum(num: Int) {
        Button(onClick = {
            displayNumber = displayNumber.addDigit(num)
        }) {
            Text(num.toString())
        }
    }

    MaterialTheme(colors = darkColors(primary = Color(0xffffeb46))) {
        Surface(color = Color(0xff91a4fc)) {
            Column {
                Column(modifier = Modifier.align(Alignment.End)) {
                    Text(op.x.toPlainString(), fontSize = 20.sp)
                    Text(
                        displayNumber.toPlainString(),
                        fontSize = 30.sp,
                    )
                }

                myRow {
                    Button(onClick = {
                        if (displayNumber == BigDecimal(0)) {
                            op = Operator.Add(BigDecimal(0))
                        }
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("C")
                    }
                    Button(onClick = {
                        // TODO: decimal support
                    }) {
                        Text(".")
                    }
                    Button(onClick = {
                        op = Operator.Pow(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("x^y")
                    }
                    Button(onClick = {
                        op = Operator.Add(BigDecimal(0))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("AC")
                    }
                }

                myRow {
                    buttonNum(1)
                    buttonNum(2)
                    buttonNum(3)
                    Button(onClick = {
                        op = Operator.Add(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("+")
                    }
                }

                myRow {
                    buttonNum(4)
                    buttonNum(5)
                    buttonNum(6)
                    Button(onClick = {
                        op = Operator.Mult(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("x")
                    }
                }

                myRow {
                    buttonNum(7)
                    buttonNum(8)
                    buttonNum(9)
                    Button(onClick = {
                        op = Operator.Sub(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("-")
                    }
                }

                myRow {
                    buttonNum(0)
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(0)
                        displayNumber = displayNumber.addDigit(0)
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
                        op = Operator.Div(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("/")
                    }
                }
            }
        }
    }
}

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

fun BigDecimal.addDigit(digit: Int) = this.multiply(BigDecimal(10)).plus(BigDecimal(digit))

fun main() =
    application {
        Window(onCloseRequest = ::exitApplication, title = "Simple Calculator") {
            App()
        }
    }
