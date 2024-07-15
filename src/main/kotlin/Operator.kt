import java.math.BigDecimal

sealed class Operator(
    val x: BigDecimal,
) {
    abstract fun calculate(y: BigDecimal): BigDecimal

    class Add(
        x: BigDecimal,
    ) : Operator(x) {
        override fun calculate(y: BigDecimal): BigDecimal = x + y
    }

    class Sub(
        x: BigDecimal,
    ) : Operator(x) {
        override fun calculate(y: BigDecimal): BigDecimal = x - y
    }

    class Mult(
        x: BigDecimal,
    ) : Operator(x) {
        override fun calculate(y: BigDecimal): BigDecimal = x * y
    }

    class Div(
        x: BigDecimal,
    ) : Operator(x) {
        override fun calculate(y: BigDecimal): BigDecimal = x / y
    }

    class Pow(
        x: BigDecimal,
    ) : Operator(x) {
        override fun calculate(y: BigDecimal): BigDecimal = x.pow(y.toInt())
    }
}
