package wshandlers

import io.javalin.embeddedserver.jetty.websocket.WebSocketConfig
import io.javalin.embeddedserver.jetty.websocket.WebSocketHandler
import java.text.SimpleDateFormat
import java.util.*

class TimeWS : WebSocketConfig {

	override fun configure(ws: WebSocketHandler) {

		ws.onConnect { session ->
			println("ws:timews : ${session.remoteAddress} connected")
		}

		ws.onMessage { session, msg ->
			println("ws:timews : received: $msg")

			if (msg == "getTime") {

				val cal = Calendar.getInstance()
				cal.time = Date()
				cal.add(Calendar.HOUR_OF_DAY, 2)
				val outTime = SimpleDateFormat("HH:mm:ss").format(cal.time)

				println("ws:timews : responding with time -> $outTime")
				session.send(outTime)
			}

		}

		ws.onClose { session, statusCode, reason ->
			println("ws:timews : ${session.remoteAddress} disconnected")
		}

		ws.onError { session, throwable ->
			println("ws:timews : Error -> ${throwable.message}")
		}

	}


}