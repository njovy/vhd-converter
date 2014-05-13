package util;

/**
 * A utility class for qemu. A raw file can be converted to vhd using qemu-img.
 * 
 * @author Taehyun Park <thpark@softonnet.com>
 * 
 */
public class QemuUtils {

	private static QemuUtils _instance;
	private static final String QEMU_IMG = "qemu-img";
	private static final String COMMAND_CONVERT = "convert";
	private static final String TYPE_VHD = "vpc";

	// private QemuLibrary library = QemuLibrary.INSTANCE;

	public static QemuUtils getInstance() {
		if (_instance == null) {
			_instance = new QemuUtils();
		}
		return _instance;
	}

	// qemu-img convert -f raw -O vpc 1mb.img 1mb.vhd
	public boolean convertToVhd(String input, String output) {
		final String[] params = new String[] { QEMU_IMG, COMMAND_CONVERT, "-f",
				"raw", "-O", TYPE_VHD, "-o", "subformat=fixed", input, output };
		CommandLine.exec(params);
		return true;
		// final String[] params = new String[] { COMMAND_CONVERT, "-f", "raw",
		// "-O", TYPE_VHD, input, output };
		// return library.img_convert(params.length, params) == 0;
	}

}
