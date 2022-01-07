package app.puno.backend.util;

import java.util.UUID;

public class UUIDUtils {

	public static boolean isUUID(String string) {
		try {
			UUID.fromString(string);
			return true;
		} catch(IllegalArgumentException exception) {
			return false;
		}
	}

}
