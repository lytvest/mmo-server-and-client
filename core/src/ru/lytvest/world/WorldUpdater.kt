package ru.lytvest.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Net
import com.badlogic.gdx.net.HttpRequestBuilder
import com.badlogic.gdx.utils.Json
import ru.lytvest.mmo.World
import ru.lytvest.mmo.WorldInfo

class WorldUpdater(val updateWorld: (World) -> Unit) : Thread(), Net.HttpResponseListener {

    val httpBuilder = HttpRequestBuilder()
    val json = Json()
    var startTime = 0L

    init {
        isDaemon = true
        start()
    }

    fun createRequest(){
        startTime = System.currentTimeMillis()
        val requst = httpBuilder.newRequest().method(Net.HttpMethods.POST).url("http://localhost:8080/").build()
        Gdx.net.sendHttpRequest(requst, this)
    }

    override fun run() {
        createRequest()
    }

    override fun handleHttpResponse(httpResponse: Net.HttpResponse) {
        try {
            Thread.sleep(200)
            println("http ok!")

            val worldInfo = json.fromJson(WorldInfoReader::class.java, httpResponse.resultAsStream).world!!
            val world = World(worldInfo.entities)
            val endTime = System.currentTimeMillis()
            println("time quest:${(endTime - startTime) / 1000f} sec, world size: ${world.map.size}")
            updateWorld(world)
            //Thread.sleep(1000)
            createRequest()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun failed(t: Throwable) {
    }

    override fun cancelled() {
    }

    class WorldInfoReader(){
        var world: WorldInfo? = null
    }
}