package per.owisho.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {

	private static final String DEFAULT_CHARACTERENCODING = "UTF-8";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	private static final int HEX_RIGHT_SHIFT_COEFFICIENT = 4;
	private static final int HEX_HIGH_BITS_BITWISE_FLAG = 0x0f;

	private static final String encodingAlgorithm = "MD5";

	private MD5Encoder() {
	}
	
	/**
	 * 使用UTF-8编码对字符串进行MD5加密
	 * @param value
	 * @return
	 */
	public static String encode(final String value){
		return encode(value, "UTF-8");
	}
	
	/**
	 * 使用指定的编码格式对字符串进行MD5加密
	 * @param value
	 * @param characterEncoding
	 * @return
	 */
	public static String encode(final String value, final String characterEncoding) {
		if (value == null) {
			return null;
		}

		final String encodingCharToUse = characterEncoding == null || characterEncoding.equals("")
				? DEFAULT_CHARACTERENCODING : characterEncoding;

		try {
			
			final MessageDigest messageDigest = MessageDigest.getInstance(encodingAlgorithm);

			messageDigest.update(value.getBytes(encodingCharToUse));

			final byte[] digest = messageDigest.digest();

			return getFormattedText(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);

		for (int j = 0; j < bytes.length; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> HEX_RIGHT_SHIFT_COEFFICIENT) & HEX_HIGH_BITS_BITWISE_FLAG]);
			buf.append(HEX_DIGITS[bytes[j] & HEX_HIGH_BITS_BITWISE_FLAG]);
		}
		return buf.toString();
	}
}
