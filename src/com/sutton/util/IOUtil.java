package com.sutton.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class IOUtil {

	public static void read(List<String> data, String fileName) {

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				data.add(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read(InputStream in) {

		StringBuilder text = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				text.append(line).append("\n");
			}

		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return text.toString();
	}

	public static void write(String webpage, long id) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(
			new OutputStreamWriter(new FileOutputStream(String.valueOf(id) + ".html"), "UTF-8"))) {
			bufferedWriter.write(webpage);

		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		} 

	}

}
