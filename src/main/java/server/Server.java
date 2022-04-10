package server;

import server.ui.Connection;
import server.ui.ConsoleHelper;
import server.ui.Message;
import server.ui.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private Burse burse = Burse.getInstance();
    private ArrayList<Customer> customers;
    private static ArrayList<Freelancer> freelancers;

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен");
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("Произошла непредвиденная ошибка");
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с " + socket.getRemoteSocketAddress());

            String userName = null;
            String type = null;

            try (Connection connection = new Connection(socket)) {
                ArrayList<String> list = serverHandshake(connection);
                userName = list.get(0);
                type = list.get(1);
                Map<String,String> map = (Map<String, String>) connection.receiveObject();
                ConsoleHelper.writeMessage(map.toString());
                serverMainLoop(connection, userName);
                if ("Freelancer".equals(type)){
                    map.put("name", userName);
                    freelancers.add(new Freelancer(map));
                }

            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными с " + socket.getRemoteSocketAddress());
            }

            if (userName != null) {
                connectionMap.remove(userName);
            }

            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто.");
        }

        private ArrayList<String> serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            String userName = "";
            String type = "";
            while (true) {
                ;
                if (userName.isEmpty()) {connection.send(new Message(MessageType.NAME_REQUEST));}
                else {connection.send(new Message(MessageType.TYPE_REQUEST));}
                Message message = connection.receive();
                if (message.getType() != MessageType.USER_NAME && message.getType() != MessageType.TYPE) {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу");
                    continue;
                }
                if (message.getType() == MessageType.USER_NAME) {
                    userName = message.getData();
                }
                if (message.getType() == MessageType.TYPE) {
                    type = message.getData();
                }
                if (userName.isEmpty() || type.isEmpty()) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с пустым именем или типом от " + socket.getRemoteSocketAddress());
                    continue;
                }

                if (connectionMap.containsKey(userName)) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с уже используемым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
                connectionMap.put(userName, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                ArrayList<String> list = new ArrayList<>();
                list.add(userName);
                list.add(type);
                return list;
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.COMMAND) {
                    //do
                } else {
                    ConsoleHelper.writeMessage("Ошибка на стороне сервера");
                }
            }
        }

    }


}
