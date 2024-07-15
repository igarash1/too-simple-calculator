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
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(1)
                    }) {
                        Text("1")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(2)
                    }) {
                        Text("2")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(3)
                    }) {
                        Text("3")
                    }
                    Button(onClick = {
                        op = Operator.Add(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("+")
                    }
                }

                myRow {
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(4)
                    }) {
                        Text("4")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(5)
                    }) {
                        Text("5")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(6)
                    }) {
                        Text("6")
                    }
                    Button(onClick = {
                        op = Operator.Mult(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("x")
                    }
                }

                myRow {
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(7)
                    }) {
                        Text("7")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(8)
                    }) {
                        Text("8")
                    }
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(9)
                    }) {
                        Text("9")
                    }
                    Button(onClick = {
                        op = Operator.Sub(op.calculate(displayNumber))
                        displayNumber = BigDecimal(0)
                    }) {
                        Text("-")
                    }
                }

                myRow {
                    Button(onClick = {
                        displayNumber = displayNumber.addDigit(0)
                    }) {
                        Text("0")
                    }
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
        @Composable()
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
