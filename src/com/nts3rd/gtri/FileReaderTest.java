/**
 * Created by Tracy on 1/12/2016.
 */
package com.nts3rd.gtri;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.*;

public class FileReaderTest {
	public static void main(String[] args) {
		System.out.println(howdy(args[0]));
		String sFileName = args[1];
		System.out.println("Test func. returned: " + testFunc(sFileName));
	}

	public static Boolean testFunc (String fileName) {
		Boolean result = false;
		String curDir = System.getProperty("user.dir");
		String fileSpec = curDir + "\\" + fileName;
		System.out.println("File spec: " + fileSpec);
		ArrayList<Integer> alInts = new ArrayList();
		Integer iCount = 0;

		try (Stream<String> stream = Files.lines(Paths.get(fileSpec))) {
			stream.forEach(item->{
				alInts.add(Integer.parseInt(item));
			});
			iCount = alInts.size();

			for (int val1: alInts) {
				for(int val2: alInts){
					if (val1 + val2 == 0) {
						result = true;
						System.out.println("returning TRUE! for " + val1 + " and " + val2);
						break;
					}
				}
				if (result) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String howdy (String name) {
		return ("Howdy " + name + " from GTRI");
	}
}
