# Modelo Cliente/Servidor e __Sockets__ em Java
Este projeto mostra como executar um __servidor__ em Java que espera por conexões originadas de um __cliente__. Neste projeto o cliente está escrito em Java, mas pode ser qualquer cliente que aceite conexões _sockets_.

Algumas soluções distribuídas adotam o modelo de comunicação orientada por mensagem através da camada de transporte. A camada de transporte utiliza os protocolos TCP e UDP (_User Datagram Protocol_) para a troca de mensagens por meio de portas de nível de serviço.

O modelo de troca de mensagens a partir de portas TCP permite escrever programas em rede utilizando-se de um terminal de comunicação denominado por soquete (_sockets_). O soquete é utilizado pelo sistema operacional local (exemplo: Debian Linux) para um protocolo de transporte adotado. Assim, o sistema operacional de rede deve associar a porta com um endereço IP para que a troca de mensagens somente ocorra entre aqueles computadores e através daquela porta.

## Compilação
Acesse os diretórios individualmente e execute os comandos abaixo no `terminal`.
+ __Servidor__: javac ChatServer.java
+ __Cliente__: javac ChatClient.java

## Execução

O servidor e o cliente deverão ser executados em um `terminal` independente, conforme as instruções a seguir:

+ __Servidor__: java ChatServer `<porta>`
+ __Cliente__: java ChatClient `<servidor> <porta>`