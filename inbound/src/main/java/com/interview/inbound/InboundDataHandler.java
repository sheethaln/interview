package com.interview.inbound;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.inbound.dto.Trade;

public class InboundDataHandler extends Thread {

	public static void main(String[] args) throws FileNotFoundException {
		InboundDataHandler inputHandler = new InboundDataHandler();
		inputHandler.start();
	}

	public void run() {
		try {
			RandomAccessFile in = new RandomAccessFile("TestFile.txt", "r");
			String line = null;
			while (true) {
				try {
					if ((line = in.readLine()) != null) {
						Trade trade = createTrade(line);
						if (null != trade) {
							storeTrade(trade);
						}
					} else {
						Thread.sleep(2000); // poll the file every 2 seconds
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
					in.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Trade createTrade(String inputData) {
		Trade trade = null;
		List<String> alist = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(inputData, ",");
		
		while(st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		
		if (CollectionUtils.isNotEmpty(alist)) {
			trade = new Trade();
			trade.setTradeId(alist.get(0));
			trade.setVersion(Long.parseLong(alist.get(1)));
			trade.setCounterPatryId(alist.get(2));
			trade.setBookId(alist.get(3));
			trade.setMaturityDate(LocalDate.parse(alist.get(4), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		
		return trade;
	}

	private void storeTrade(Trade trade) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonTrade = mapper.writeValueAsString(trade);

			URL obj = new URL("http://localhost:8080/api/v1/trades");
			HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Type", "application/json");
			postConnection.setDoOutput(true);
			OutputStream os = postConnection.getOutputStream();
			os.write(jsonTrade.getBytes());
			os.flush();
			os.close();

			int responseCode = postConnection.getResponseCode();
			// System.out.println("POST Response Code : " + responseCode);
			// System.out.println("POST Response Message : " +
			// postConnection.getResponseMessage());

			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("Trade " + trade + " - Successfully stored");
			}
			if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getErrorStream()));
				String inputLine;

				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				System.out.println("Error stroing trade " + trade + "!" + response.toString());
			}
		} catch (IOException e) {
			System.out.println("Exception calling storeTrades");
			e.printStackTrace();
		}

	}
}
