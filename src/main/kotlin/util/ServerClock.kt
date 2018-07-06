package util

import java.text.SimpleDateFormat
import java.util.*

class ServerClock {
	companion object {

		fun time() = time(0)
		fun europeTime() = time(2)

		fun time(offsetHours: Int): String {
			val cal = Calendar.getInstance()
			cal.time = Date()
			cal.add(Calendar.HOUR_OF_DAY, offsetHours)
			return SimpleDateFormat("HH:mm:ss").format(cal.time)
		}

	}
}