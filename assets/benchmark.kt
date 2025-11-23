package sorting

import org.example.sorting.CountingSort
import org.example.sorting.HeapSort
import org.example.sorting.InsertionSort
import org.example.sorting.MergeSort
import org.example.sorting.QuickSort
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 2, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
open class Benchmark {
    private val seed = 2025
    private val random = Random(seed)

    @Param("10", "100", "1000", "10000")
    var size: Int = 0

    @Param("RANDOM", "SORTED", "REVERSED", "NEARLY_SORTED", "SMALL_RANGE", "BIG_RANGE")
    var distribution: String = ""

    lateinit var experimentList: List<Int>

    @Setup(Level.Trial)
    fun setUpTotalList() {
        experimentList = when (distribution) {
            "RANDOM" -> (0 until size).shuffled(random)
            "SORTED" -> (0 until size).toList()
            "REVERSED" -> (size - 1 downTo 0).toList()
            "SMALL_RANGE" -> {
                val range = (size / 5).coerceAtLeast(1)
                List(size) { random.nextInt(range) }
            }

            "BIG_RANGE" -> List(size) { random.nextInt(size * 5) }
            "NEARLY_SORTED" -> (0 until size).toMutableList().apply {
                val swapCount = (size / 100).coerceAtLeast(1)
                repeat(swapCount) {
                    val i = random.nextInt(size)
                    val j = random.nextInt(size)
                    val temp = this[i]; this[i] = this[j]; this[j] = temp
                }
            }
            else -> throw IllegalArgumentException("Unknown distribution: $distribution")
        }
    }

    @Benchmark
    fun baseline(bh: Blackhole) {
        bh.consume(experimentList.toMutableList())
    }

    @Benchmark
    fun insertionSort(bh: Blackhole) {
        val data = experimentList.toMutableList()
        InsertionSort.sort(data)
        bh.consume(data)
    }

    @Benchmark
    fun mergeSort(bh: Blackhole) {
        val data = experimentList.toMutableList()
        MergeSort.sort(data)
        bh.consume(data)
    }

    @Benchmark
    fun quickSort(bh: Blackhole) {
        val data = experimentList.toMutableList()
        QuickSort.sort(data)
        bh.consume(data)
    }

    @Benchmark
    fun heapSort(bh: Blackhole) {
        val data = experimentList.toMutableList()
        HeapSort.sort(data)
        bh.consume(data)
    }

    @Benchmark
    fun countingSort(bh: Blackhole) {
        val data = experimentList.toMutableList()
        CountingSort.sort(data)
        bh.consume(data)
    }
}
