package com.edu.acme.message;

import com.edu.acme.Command;

/**
 * Класс, служащий для проверки строки на корректное сообшение
 */
public class MessageValidator implements Validator {
    private static final int MAX_MESSAGE_LENGTH = 150;

    /**
     * Проверяет, является ли message корректным сообщением для сервера
     *
     * @param message сообщение, которое необходимо проверить
     */
    @Override
    public boolean validate(String message) {
//        String[] messageArr = message.split("\\s+", 2);
//        return !Command.get(messageArr[0]) != null &&
//                (messageArr.length <= 1 || messageArr[1].length() <= MAX_MESSAGE_LENGTH) &&
//                !(messageArr.length == 1);
        return true;
    }

    public String getErrorDescription(String message) {
        String[] messageArr = message.split("\\s+", 2);
        if (Command.get(messageArr[0]) == null) {
            return "Command is unknown. Use /snd {message} to send message " +
                    "/hist to receive all messages and /chid to register";
        } else if (messageArr.length < 2 || messageArr[1].equals("") || messageArr[1].equals(" ")) {
            return "Can't send empty message";
        }else if (messageArr[1].length() >= MAX_MESSAGE_LENGTH) {
            return "Your message is too long";
        }
        return null;
    }
}
