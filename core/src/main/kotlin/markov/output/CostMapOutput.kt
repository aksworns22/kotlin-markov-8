package markov.output

import markov.cost.CostMap

interface CostMapOutput {
    fun println(costMap: CostMap)
}
