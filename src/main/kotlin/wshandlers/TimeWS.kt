package wshandlers

import io.javalin.embeddedserver.jetty.websocket.WebSocketConfig
import io.javalin.embeddedserver.jetty.websocket.WebSocketHandler
import util.ServerClock

class TimeWS : WebSocketConfig {

	override fun configure(ws: WebSocketHandler) {

		ws.onConnect { session ->
			println("ws:timews : ${session.remoteAddress} connected")
		}

		ws.onMessage { session, msg ->
			println("ws:timews : received: $msg")

			if (msg == "getTime") {

				val outTime = ServerClock.europeTime()

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