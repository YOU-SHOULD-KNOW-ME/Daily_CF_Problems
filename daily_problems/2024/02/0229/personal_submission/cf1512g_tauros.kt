import java.io.InputStream
import java.io.Writer
import java.io.OutputStreamWriter
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.PrintWriter
import java.io.Closeable

open class FastReader(private val input: InputStream, bufCap: Int = 8192) {
    companion object {
        private const val NC = 0.toChar()
    }

    private val buf = ByteArray(bufCap)
    private var bId = 0
    private var size = 0
    private var c = NC
    private val char: Char
        get() {
            while (bId == size) {
                size = input.read(buf) // no need for checked exceptions
                if (size == -1) return NC
                bId = 0
            }
            return buf[bId++].toInt().toChar()
        }

    private fun isWhitespace(c: Char) = c.code !in 33..126

    private fun skip(): Char {
        var b: Char
        while (char.also { b = it } != NC && isWhitespace(b));
        return b
    }

    fun ns() = buildString {
        while (true) {
            c = char
            if (!isWhitespace(c)) {
                break
            }
        }
        append(c)
        while (true) {
            c = char
            if (isWhitespace(c)) {
                break
            }
            append(c)
        }
    }

    fun ns(n: Int): CharArray {
        val buf = CharArray(n)
        var b = skip()
        var p = 0
        while (p < n && !isWhitespace(b)) {
            buf[p++] = b
            b = char
        }
        return if (n == p) buf else buf.copyOf(p)
    }

    fun ni(): Int {
        var neg = false
        if (c == NC) c = char
        while (c < '0' || c > '9') {
            if (c == '-') neg = true
            c = char
        }
        var res = 0
        while (c in '0'..'9') {
            res = (res shl 3) + (res shl 1) + (c - '0')
            c = char
        }
        return if (neg) -res else res
    }

    fun nl(): Long {
        var neg = false
        if (c == NC) c = char
        while (c < '0' || c > '9') {
            if (c == '-') neg = true
            c = char
        }
        var res = 0L
        while (c in '0'..'9') {
            res = (res shl 3) + (res shl 1) + (c - '0')
            c = char
        }
        return if (neg) -res else res
    }

    fun na(n: Int) = IntArray(n) { ni() }
    fun nal(n: Int) = LongArray(n) { nl() }
    fun nad(n: Int) = DoubleArray(n) { nd() }
    fun nm(n: Int, m: Int) = Array(n) { ns(m) }
    fun nmi(n: Int, m: Int) = Array(n) { na(m) }
    fun nd() = ns().toDouble()
    fun nc() = skip()
}
open class FastWriter : Closeable {
    private val writer: PrintWriter

    constructor(output: OutputStream, bufCap: Int = 8192) {
        this.writer = PrintWriter(BufferedWriter(OutputStreamWriter(output), bufCap))
    }

    constructor(writer: Writer, bufCap: Int = 8192) {
        this.writer = PrintWriter(BufferedWriter(writer, bufCap))
    }

    override fun close() {
        writer.flush()
        try {
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun print(b: Boolean) = writer.print(b)
    fun print(c: Char) = writer.print(c)
    fun print(i: Int) = writer.print(i)
    fun print(l: Long) = writer.print(l)
    fun print(f: Float) = writer.print(f)
    fun print(d: Double) = writer.print(d)
    fun print(s: CharArray) = writer.print(s)
    fun print(s: String?) = writer.print(s)
    fun print(obj: Any?) = writer.print(obj)
    fun println() = writer.println()
    fun println(x: Boolean) = writer.println(x)
    fun println(x: Char) = writer.println(x)
    fun println(x: Int) = writer.println(x)
    fun println(x: Long) = writer.println(x)
    fun println(x: Float) = writer.println(x)
    fun println(x: Double) = writer.println(x)
    fun println(x: CharArray) = writer.println(x)
    fun println(x: String) = writer.println(x)
    fun println(x: Any) = writer.println(x)
    fun printf(format: String, vararg args: Any?) = writer.format(format, *args)
    fun flush() = writer.flush()
}
inline fun iar(size: Int, init: (Int) -> Int = { 0 }) = IntArray(size) { init(it) }

/**
 * generated by kotlincputil@tauros
 */
private val bufCap = 65536
private val rd = FastReader(System.`in`, bufCap)
private val wt = FastWriter(System.out, bufCap)
private fun solve() {
    // https://codeforces.com/problemset/problem/1512/G
    // 1e7暴力筛能过，居然也能积性函数线性筛，佛了
    val cap = 1e7.toInt()
    val sum = iar(cap + 1)
    val min = iar(cap + 1) { -1 }
    for (i in 1 .. cap) {
        for (j in i .. cap step i) sum[j] += i
        if (sum[i] <= cap && (min[sum[i]] == -1 || min[sum[i]] > i)) min[sum[i]] = i
    }

    val cases = rd.ni()
    repeat(cases) {
        val c = rd.ni()
        val num = min[c]
        wt.println(num)
    }
}
fun main(args: Array<String>) {
    solve()
    wt.flush()
}