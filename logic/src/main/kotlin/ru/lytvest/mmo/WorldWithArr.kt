package ru.lytvest.mmo


class WorldWithArr(val countBox: Int = 20, val size: Float = 100f) {
    val sizeItem = size / countBox
    val hash = HashMap<Point, String>()
    val arr = Array(countBox + 1) { Array(countBox + 1) { mutableSetOf<Point>() } }

    fun indexBox(d: Float): Int {
        var stx = (d / sizeItem).toInt()
        if (stx < 0) stx = 0
        if (stx > countBox) stx = countBox
        return stx
    }

    operator fun set(point: Point, value: String) {
        if (indexBox(point.x) >= countBox || indexBox(point.y) >= countBox || point.x < 0 || point.y < 0)
            return
        arr[indexBox(point.x)][indexBox(point.y)].add(point)
        hash[point] = value
    }

    fun remove(point: Point) {
        arr[indexBox(point.x)][indexBox(point.y)].remove(point)
        hash.remove(point)
    }

    fun find(start: Point, end: Point): List<Pair<Point, String>> {
        val res = mutableListOf<Pair<Point, String>>()
        for (x in indexBox(start.x)..indexBox(end.x))
            for (y in indexBox(start.y)..indexBox(end.y)) {
                for (point in arr[x][y]) {
                    if (point.x >= start.x && point.x <= end.x && point.y >= start.y && point.y <= end.y)
                        res.add(point to hash[point]!!)
                }
            }
        return res
    }
}