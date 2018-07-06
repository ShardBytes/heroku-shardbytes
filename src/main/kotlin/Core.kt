import io.javalin.Javalin
import io.javalin.embeddedserver.Location
import models.RestTime
import models.TestJsonResponse
import util.ServerClock
import wshandlers.EchoWS
import wshandlers.TimeWS


class Core {

	val app : Javalin

	init {
		app = Javalin.create()
				.port(getHerokuAssignedPort())
				.enableStaticFiles("static", Location.EXTERNAL)
				.start()

		app.error(404) {
			it.html("<b>Theres no page like this my dude yo got balboolzeld xDD</b>")
		}

		// before routing
		app.before {
			println("BEFORE : protocol=${it.protocol()} host=${it.host()} ip=${it.ip()} isSecure=${it.request().isSecure} url=${it.url()}")

			// redirect if insecure request and not in devmode ( locahost )
			// actually no because heroku doesnt allow this
			/*
			if (
					!it.request().isSecure &&
					!(it.host()?.run {contains("localhost") || contains("192.168.0.")} ?: false)
			) {
				println("[BEFORE] ${it.ip()} is accessing through http, redirecting to https ...")
				it.redirect(it.url().replace("http://", "https://"), 301)
			}
			*/
		}


		// routing -> app.get(<route>) { (it = client) -> ... }
		app.get("/resttest") {
			it.json(TestJsonResponse(4))
		}

		app.get("/time") {
			it.json(RestTime(ServerClock.europeTime()))
		}

		// wshandlers handling

		app.ws("/timews", TimeWS())
		app.ws("/echo", EchoWS())


	}

	private fun getHerokuAssignedPort(): Int {
		val processBuilder = ProcessBuilder()
		return if (processBuilder.environment()["PORT"] != null) {
			Integer.parseInt(processBuilder.environment()["PORT"])
		} else 7000
	}

}

fun main(args: Array<String>) {

	Core()

}