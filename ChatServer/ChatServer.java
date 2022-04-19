import java.io.*; // requisições de entrada/saída
import java.net.*; // conexões de rede (sockets)
import java.util.*; // fins gerais

public class ChatServer{
    // porta que ficará disponível para o serviço
    private final int port;

    // lista dos usuários conectados
    private final Set<String> userNames = new HashSet<>();

    // lista dos Threads (objetos Thread)
    private final Set<UserThread> userThreads = new HashSet<>();
    
    // construtor
    public ChatServer(int port){
        this.port = port;
    }

    // método para executar o serviço (servidor)
    // modo listening (escutando)
    public void execute(){
        try(ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server executing in port: " + port);
            System.out.println("CTRL+C to finish");

            // executando o serviço
            while(true){
                Socket socket = serverSocket.accept();

                // pegar o IP do cliente
                InetAddress ip = socket.getInetAddress();
                System.out.println("New user connected: [" + ip + "]");

                // criar o Thread dos usuários
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
                System.out.println(newUser);
            }

        }catch(IOException ioex){
            System.out.println("Server error " + ioex.getMessage());
        }
    }

    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("");
            System.out.println("To execute, type:");
            System.out.println("java ChatServer <port>");
            System.out.println("Eg. java ChatServer 9000");
            System.out.println("");
            System.exit(0); // sai do programa sem gerar erro
        }
        
        // vamos pegar o valor (a porta) e executar o serviço
        int port = Integer.parseInt(args[0]); // converte String para inteiro

        // instancia o Servidor de Chat
        ChatServer server = new ChatServer(port);
        server.execute();
    }

    // implementação de métodos auxiliares para a manutenção do software
    boolean hasUsers(){
        return !this.userNames.isEmpty();
    }

    Set<String> getUserNames(){
        return this.userNames;
    }

    public void addUserName(String userName){
        userNames.add(userName);
    }

    public void removeUser(String userName, UserThread aUser){
        boolean removed = userNames.remove(userName);
        if (removed){
            userThreads.remove(aUser);
            System.out.println("User: " + userName + " exit.");
        }
    }

    // envia uma mensagem de aviso para a "rede", comunicando a saída do User
    public void broadcast(String serverMessage, UserThread excludeUser){
        userThreads.stream().filter( (aUser) -> (aUser != excludeUser)).forEachOrdered( (aUser) -> aUser.sendMessage(serverMessage));
    }
}