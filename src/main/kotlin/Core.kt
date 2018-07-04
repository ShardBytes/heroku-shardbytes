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