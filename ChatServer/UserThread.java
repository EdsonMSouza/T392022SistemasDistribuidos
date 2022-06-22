import java.io.*; // requisições de entrada/saída
import java.net.*; // conexões de rede (sockets)

public class UserThread extends Thread {
    private final Socket socket; // meio de comunicação (TCP)
    private final ChatServer server; // o servidor de Chat
    private PrintWriter writer; // Escrever no buffer de saída (I/[O])

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // leitura dos dados
            InputStream input = socket.getInputStream();

            // joga os dados no buffer
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // canal de saída (response)
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            // mostrar os usuários conectados
            printUsers();

            // capturar os dados do teclado (cliente) - remote mode
            String userName = reader.readLine();
            server.addUserName(userName);

            // broadcast - falar para a geral!!!!!!
            String serverMessage = "New user connected: " + userName;
            server.broadcast(serverMessage, this);

            // capturar a mensagem do cliente
            String clientMessage;

            // repetição para leitura remota das mensagens
            do {
                clientMessage = reader.readLine();
                // aqui é um ponto crítico para realizar várias verificações

                // filtragem de palavras não permitidas (azul -> ***)
                // Entrada: O céu está azul
                // Saída: O céu está ***
                // Uma ideia: enviar um broadcast avisando sobre palavras não permitidas
                // * Request via API

                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

                // mostra a mensagem enviada ao servidor no seu console
                System.out.println(serverMessage);

                // armazena os dados enviados em um BD e/ou Log
                // procedimentos para armazenamento no BD

            } while (!clientMessage.equals("bye"));

            // uma vez o usuário desconectado...

            // remover o usuário
            server.removeUser(userName, this);

            // fecha a conexão
            socket.close();

            // avisa a galera que userName saiu da parada!!!!!
            serverMessage = userName + " exited";
            server.broadcast(serverMessage, this);

        } catch (Exception ioe) {
            System.out.println("Err: " + ioe.getMessage());
        }
    }

    void printUsers() {
        if (server.hasUsers()) { // classe ChatServer
            writer.println("Users connected: " + server.getUserNames());
        } else {
            writer.println("No users connected");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}
