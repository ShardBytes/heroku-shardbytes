import io.javalin.Javalin
import io.javalin.embeddedserver.Location
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

		app.before {
			println("BEFORE : protocol=${it.protocol()} host=${it.host()} ip=${it.ip()} requestRemoteAddr=${it.request().remoteAddr}")
			if (
					(!it.request().isSecure) &&
					(it.host()?.run {contains("localhost") || contains("192.168.0.")} ?: false)
			) {
				println("[BEFORE] ${it.ip()} is accessing through http, redirecting to https ...")
				it.redirect(it.url().replace("http://", "https://"))
			}
		}

		// routing -> app.get(<route>) { (it = client) -> ... }

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