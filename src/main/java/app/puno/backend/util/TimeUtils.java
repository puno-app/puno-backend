package app.puno.backend.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class TimeUtils {

	private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

	public static Date getDateNow() {
		return Date.from(Instant.now().atOffset(ZONE_OFFSET).toInstant());
	}

	public static Date getDateNowAfter(long amount, TemporalUnit unit) {
		return Date.from(Instant.now().atOffset(ZONE_OFFSET).plus(amount, unit).toInstant());
	}

}
