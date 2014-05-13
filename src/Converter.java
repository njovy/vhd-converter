import java.io.File;

import util.VhdUtils;

/**
 * A main class for converting a file between raw(img) and fixed vhd files.
 * 
 * @author Taehyun Park <thpark@softonnet.com>
 * 
 */
public class Converter {

	private static final String PREFIX_RAW = "img";
	private static final String PREFIX_VHD = "vhd";

	public static void main(String[] args) {
		if (args.length != 3) {
			correctUsage();
			System.exit(0);
		}

		String arg = args[0];
		String outputDirectory = args[1];
		String option = args[2];

		boolean isCurrentDirectory = outputDirectory.equals(".");
		if (!isCurrentDirectory) {
			File file = new File(outputDirectory);
			file.mkdirs();
		}
		System.out.println("img <-> vhd(fixed) converter");
		long startTime = System.currentTimeMillis();
		if (arg.contains(PREFIX_VHD) || arg.contains(PREFIX_RAW)) {
			checkOption(option, arg, isCurrentDirectory ? "" : outputDirectory);
		} else {
			// this is a folder!
			File directory = new File(arg);
			if (!directory.isDirectory()) {
				System.out
						.println("The given path is not a folder nor a raw file(.img)");
			} else {
				for (File rawFile : directory.listFiles()) {
					checkOption(option, rawFile.getAbsolutePath(),
							isCurrentDirectory ? "" : outputDirectory);
				}
			}
		}
		System.out.println("Elapsed Time: "
				+ (System.currentTimeMillis() - startTime) + "ms");

	}

	/**
	 * Check the option parameter
	 * 
	 * @param option
	 *            the option
	 * @param input
	 *            the input path
	 * @param output
	 *            the output path
	 */
	private static void checkOption(String option, String input, String output) {
		if (option.contains(PREFIX_VHD)) {
			convertRawToVhd(input, output);
		} else if (option.contains(PREFIX_RAW)) {
			convertVhdToRaw(input, output);
		} else {
			System.out.println("Unknwon option : " + option);
		}
	}

	/**
	 * Convert a raw file to a fixed vhd. It appends a vhd footer based on a
	 * size at the end of a file.
	 * 
	 * @param input
	 *            the input path
	 * @param output
	 *            the output path
	 * @return the result of converting
	 */
	private static boolean convertRawToVhd(String input, String output) {
		return VhdUtils.convertRawToVhd(
				input,
				output + (output.isEmpty() ? "" : File.separator)
						+ input.replace(PREFIX_RAW, PREFIX_VHD));
	}

	/**
	 * Convert a fixed vhd file to a raw file. Check if the given file has a vhd
	 * footer, then truncate the last 512 bytes to convert a raw file.
	 * 
	 * @param input
	 *            the input path
	 * @param output
	 *            the output path
	 * @return the result of converting
	 */
	private static boolean convertVhdToRaw(String input, String output) {
		return VhdUtils.convertVhdToRaw(
				input,
				output + (output.isEmpty() ? "" : File.separator)
						+ input.replace(PREFIX_VHD, PREFIX_RAW));
	}

	private static void correctUsage() {
		System.out.println("Please input a valid path or file.");
		System.out.println("ex) java -jar *.jar 1mg.img . vhd");

	}

}
