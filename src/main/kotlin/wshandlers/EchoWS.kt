package wshandlers

import io.javalin.embeddedserver.jetty.websocket.WebSocketConfig
import io.javalin.embeddedserver.jetty.websocket.WebSocketHandler

class EchoWS : WebSocketConfig {

	override fun configure(ws: WebSocketHandler) {

		ws.onConnect {
			println("ws:echo : ${it.remoteAddress} connected")
		}

		ws.onMessage { session, msg ->
			session.send(msg)
		}

		ws.onClose { sess, status, reason ->
			println("ws:echo : ${sess.remoteAddress} disconected")
		}

		ws.onError { session, throwable ->
			println("ws:timews : Error -> ${throwable.message}")
		}

	}

}