package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * http://stackoverflow.com/questions/10723346/why-should-avoid-using-runtime-
 * exec-in-java Use ProcessBuilder to execute a shell command
 * 
 * @author Taehyun Park <thpark@softonnet.com>
 * 
 */
public class CommandLine {

	public static String exec(String... args) {

		ProcessBuilder pb = new ProcessBuilder(args);
		BufferedReader br = null;
		Process proc = null;
		StringBuilder builder = new StringBuilder();
		try {
			proc = pb.start();

			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (proc.exitValue() == 0) {
				br = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(
						proc.getErrorStream()));
			}
			String line = null;
			while ((line = br.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (proc != null) {
				proc.destroy();
			}

		}
		return builder.toString();
	}

	public static String exec(String command) {
		return exec(command.split(" "));
	}
}
