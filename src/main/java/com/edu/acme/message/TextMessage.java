package com.edu.acme.message;

import com.edu.acme.Command;
import com.edu.acme.ServerState;

import java.io.*;
import java.net.SocketException;
import java.util.List;

public class TextMessage extends Message {
    private final Command command = Command.SEND;

    public TextMessage(String text) {
        super(text);
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public void process(ObjectOutputStream out) {
        this.setCurrentTime();
        this.setText(ServerState.getUserStreamMap().get(out) + ": " + text);
        sendMessageToAll(ServerState.getClientOutList());
        saveToHistory();
    }

    private void saveToHistory() {
//        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ServerState.getMessageHistoryPath(),
//                true))) {
//            out.writeObject(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (this.command == Command.SEND) {
            ServerState.messageHistory.add(this);
        }
    }

    private void sendMessageToAll(List<ObjectOutputStream> clientOutList) {
        System.out.println("Have " + clientOutList.size() + " clients");
        for (ObjectOutputStream out : clientOutList) {
            try {
                out.writeObject(this);
                out.flush();
            } catch (SocketException e) {
                clientOutList.remove(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
