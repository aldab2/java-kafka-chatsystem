package org.example;

public class Client 
{

    public static void main(String[] args) 
    {
        chat_client client=new chat_client("20.4.51.187:9092,165.22.26.150:9092,159.65.113.104:9092,46.101.119.158:9092");
        client.startRunning();
    }
}
