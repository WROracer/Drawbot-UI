package de.wrolu.drawbot.ui;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:25600/"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                    if (message.contains("ping")) {
                        clientEndPoint.sendMessage("pong");
                    }
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }


    public static void testGlobalKey() throws NativeHookException {
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
                NativeKeyListener.super.nativeKeyTyped(nativeEvent);
                System.out.println("nativeKeyTyped " + nativeEvent.getKeyChar());
            }
        });

        GlobalScreen.registerNativeHook();

        new Scanner(System.in).nextLine();
    }
}