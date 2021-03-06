package com.ruserba.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Service
{
	private static final String REMOTE_URL = "http://if3110-tubes-iii.herokuapp.com/";
	//private static final String REMOTE_URL = "http://localhost/remote/";

	public static ArrayList<String> httpGet(String urlStr) throws IOException {
	  URL url = new URL(REMOTE_URL + urlStr);
	  HttpURLConnection conn =
		  (HttpURLConnection) url.openConnection();

	  if (conn.getResponseCode() != 200) {
		throw new IOException(conn.getResponseMessage());
	  }

	  // Buffer the result into a string
	  BufferedReader rd = new BufferedReader(
		  new InputStreamReader(conn.getInputStream()));
	  ArrayList<String> lst = new ArrayList<String>();
	  String line;
	  while ((line = rd.readLine()) != null) {
		lst.add(line);
	  }
	  rd.close();

	  conn.disconnect();
	  return lst;
	}

	public static ArrayList<String> httpPost(String urlStr, String[] paramName, String[] paramVal) throws Exception {
	  URL url = new URL(REMOTE_URL + urlStr);
	  HttpURLConnection conn =
		  (HttpURLConnection) url.openConnection();
	  conn.setRequestMethod("POST");
	  conn.setDoOutput(true);
	  conn.setDoInput(true);
	  conn.setUseCaches(false);
	  conn.setAllowUserInteraction(false);
	  conn.setRequestProperty("Content-Type",
		  "application/x-www-form-urlencoded");

	  // Create the form content
	  OutputStream out = conn.getOutputStream();
	  Writer writer = new OutputStreamWriter(out, "UTF-8");
	  for (int i = 0; i < paramName.length; i++) {
		writer.write(paramName[i]);
		writer.write("=");
		writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
		writer.write("&");
	  }
	  writer.close();
	  out.close();

	  if (conn.getResponseCode() != 200) {
		throw new IOException(conn.getResponseMessage());
	  }

	  // Buffer the result into a string
	  BufferedReader rd = new BufferedReader(
		  new InputStreamReader(conn.getInputStream()));
	  ArrayList<String> lst = new ArrayList<String>();
	  String line;
	  while ((line = rd.readLine()) != null) {
		lst.add(line);
	  }
	  rd.close();

	  conn.disconnect();
	  return lst;
	}

	public static void main(String args[])
	{
		try	{
			ArrayList<String> lst = httpGet("db.php");
			for (int i = 0; i < lst.size(); i++)
				System.out.println(lst.get(i));
		} catch (Exception ex)	{
			ex.printStackTrace();
		}
	}
}
