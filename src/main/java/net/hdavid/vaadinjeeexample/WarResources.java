package net.hdavid.vaadinjeeexample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.google.gwt.thirdparty.guava.common.base.Joiner;

public class WarResources {

	public static List<String> getResourceLines(String resourcePath) {
		List<String> resp = new ArrayList<>();
		try {
			InputStream ras = WarResources.class.getClassLoader().getResourceAsStream(resourcePath);
			InputStreamReader isr = new InputStreamReader(ras, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				resp.add(line);
			}
			br.close();
			isr.close();
			ras.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	public static String getResourceAsString(String resourcePath) {
		return Joiner.on("\n").join(getResourceLines(resourcePath).iterator());
	}
}
