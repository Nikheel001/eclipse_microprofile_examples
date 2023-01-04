package com.headshot;

public class MessageA {
	private String sender;
	private String msg;

//	public MessageA() throws IOException, InterruptedException {
//		this.setMsg("headshot");
//		this.setSender("works");
//		System.out.println("===============================================================");
//		String uri = "http://localhost:9900";
//		HttpClient client = HttpClient.newHttpClient();
//		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
//		HttpResponse<String> response;
//		response = client.send(request, BodyHandlers.ofString());
//		System.out.println("===============================================================");
//		System.out.println("Response in file:" + response.body());
//		System.out.println("===============================================================");
//		System.out.println("===============================================================");
//	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
