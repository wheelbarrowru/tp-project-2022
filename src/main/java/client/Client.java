package client;

import server.ui.*;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected;
    private final String UNEXPECTED_MESSAGE_TYPE = "Неожиданный тип сообщения";
    private final String CLIENT_EXCEPTION = "Произошла ошибка во время работы клиента.";

    public class SocketThread extends Thread {

        public void run() {
            try (Socket socket = new Socket(getServerAddress(), getServerPort())) {
                connection = new Connection(socket);

                clientHandshake();
                clientMainLoop();

            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);

                ConsoleHelper.writeMessage(CLIENT_EXCEPTION);
            }
        }

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }


        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            boolean hasName = false;
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.NAME_ACCEPTED) {
                    hasName = true;
                }
                if (message.getType() == MessageType.NAME_REQUEST) {
                    String name = getUserName();
                    connection.send(new Message(MessageType.USER_NAME, name));

                } else if (message.getType() == MessageType.TYPE_REQUEST) {
                    String type = getUserType();
                    if (type.equals("error")) {
                        throw new IOException();
                    }
                    connection.send(new Message(MessageType.TYPE, type));
                } else if (hasName) {

                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException(UNEXPECTED_MESSAGE_TYPE);
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.COMMAND) {
                    processIncomingMessage(message.getData());

                } else {
                    throw new IOException(UNEXPECTED_MESSAGE_TYPE);
                }
            }
        }

    }

    protected String getServerAddress() {
        return "127.0.0.1";
    }

    protected int getServerPort() {
        return 8080;
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите имя пользователя: ");
        return ConsoleHelper.readMessage();
    }

    protected String getUserType() {
        ConsoleHelper.writeMessage("Введите тип пользователя (freelancer/entity customer/physical customer): ");
        String type = ConsoleHelper.readMessage();
        return switch (type) {
            case "freelancer" -> "Freelancer";
            case "entity customer" -> "Entity";
            case "physical customer" -> "Individ";
            default -> "error";
        };
    }

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.COMMAND, text));
        } catch (IOException e) {
            this.clientConnected = false;
            ConsoleHelper.writeMessage(CLIENT_EXCEPTION);
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage(CLIENT_EXCEPTION);
            return;
        }

        if (clientConnected) ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        else ConsoleHelper.writeMessage(CLIENT_EXCEPTION);

        Map<String,String> info = addData("Freelancer");
        try {
            connection.sendObject(info);
        } catch (IOException e) {
            this.clientConnected = false;
            ConsoleHelper.writeMessage(CLIENT_EXCEPTION);
        }

        while (clientConnected) {

            String text = ConsoleHelper.readMessage();
            if ("exit".equalsIgnoreCase(text)) break;

            if (shouldSendTextFromConsole()) sendTextMessage(text);
        }
    }

    public static Map<String, String> addData(String type) {
        Map<String, String> map = new HashMap<>();
        if ("Freelancer".equals(type)) {
            ConsoleHelper.writeMessage("Введите данные (name, occupation, experience, summary, contacts): ");
            map.put("name", ConsoleHelper.readMessage());
            map.put("occupation", ConsoleHelper.readMessage());
            map.put("experience", ConsoleHelper.readMessage());
            map.put("summary", ConsoleHelper.readMessage());
            map.put("contacts", ConsoleHelper.readMessage());
            ConsoleHelper.writeMessage("принято");
        } else if ("Entity".equals(type)){
            ConsoleHelper.writeMessage("Введите данные (companyName, dateOfFoundation, registrationNumber, contacts): ");
            map.put("companyName", ConsoleHelper.readMessage());
            map.put("dateOfFoundation", ConsoleHelper.readMessage());
            map.put("registrationNumber", ConsoleHelper.readMessage());
            map.put("contacts", ConsoleHelper.readMessage());
            ConsoleHelper.writeMessage("принято");
        } else {
            ConsoleHelper.writeMessage("Введите данные (name, experience, contacts): ");
            map.put("name", ConsoleHelper.readMessage());
            map.put("experience", ConsoleHelper.readMessage());
            map.put("contacts", ConsoleHelper.readMessage());
            ConsoleHelper.writeMessage("принято");
        }
        return map;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}