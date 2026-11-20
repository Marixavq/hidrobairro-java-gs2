# 🌧️ HidroBairro Sul — API Java

API REST desenvolvida para o projeto **HidroBairro Sul**, uma solução CitizenTech voltada à prevenção de riscos de alagamento na Zona Sul de São Paulo.

A aplicação centraliza informações de usuários, pontos de coleta, leituras de sensores e alertas, além de oferecer orientações preventivas com inteligência artificial.

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security e JWT
- Oracle Database
- RabbitMQ
- Spring AI e Google Gemini
- Bean Validation
- Swagger / OpenAPI
- Maven

## 📌 Funcionalidades

- Cadastro, consulta e gerenciamento de usuários.
- Autenticação JWT e controle de acesso por papéis (`CIDADAO` e `GESTOR`).
- CRUD de pontos de coleta, com paginação.
- Registro e consulta de leituras.
- Geração, atualização e encerramento automático de alertas.
- Recebimento de leituras provenientes de sensores IoT.
- Publicação e consumo assíncrono de eventos com RabbitMQ.
- Geração de orientações preventivas com inteligência artificial.

## 🏗️ Estrutura do projeto

```text
src/main/java/com/fiap/gs_hidrobairro/
├── config/
├── consumer/
├── controller/
├── dto/
│   ├── request/
│   └── response/
├── event/
├── exception/
├── listener/
├── model/
├── publisher/
├── repository/
├── security/
└── service/

src/main/resources/
├── application.properties
└── application.yml
```

## 🌊 Leituras e alertas

Cada ponto de coleta possui um limiar configurável. A API avalia as leituras recebidas e determina o nível de alerta:

| Condição | Resultado |
|---|---|
| Abaixo do limiar | Sem alerta ativo |
| Igual ou acima do limiar | ATENÇÃO |
| Igual ou acima de 1,5 vez o limiar | CRÍTICO |

Quando já existe um alerta ativo, ele é atualizado com a nova leitura. Quando o nível retorna abaixo do limiar, o alerta é encerrado. Novos episódios geram registros independentes, preservando o histórico.

## 📡 Integração IoT

A API recebe dados enviados por um ESP32 equipado com sensor ultrassônico HC-SR04.

O sensor mede a distância entre sua posição e a superfície da água. Na simulação atual, utiliza-se uma altura de referência de 400 cm para calcular o nível:

```text
nível da água = 400 - distância medida
```

O `IoTService` valida os dados, realiza a conversão e encaminha a leitura ao `LeituraService`, reutilizando as regras de persistência e geração de alertas.

**Endpoint:**

`POST /api/iot/leituras?pontoId=2`

**Exemplo de requisição:**

```json
{
  "distanciaCm": 362.14,
  "data": "2026-11-18",
  "horario": "02:20:49",
  "leituraValida": true
}
```

**Observação:** os 400 cm são uma referência utilizada na simulação do Wokwi. Uma instalação real exige calibração conforme as características do local.

## 📨 RabbitMQ — comunicação assíncrona

O RabbitMQ é utilizado para comunicação assíncrona baseada em eventos.

Quando um novo alerta é criado:

1. A aplicação gera um evento `AlertaCriadoEvent`.
2. O `AlertaCriadoEventListener` aguarda a confirmação da transação no banco.
3. O `AlertaEventPublisher` publica o evento no RabbitMQ.
4. O `AlertaConsumer` recebe e processa a mensagem de forma assíncrona.

### Configurações

| Elemento | Nome |
|---|---|
| Exchange | `hidrobairro.alertas.exchange` |
| Queue | `hidrobairro.alertas.queue` |
| Routing key | `alerta.criado` |

### RabbitMQ local

O ambiente de desenvolvimento utiliza RabbitMQ instalado localmente, sem Docker.

A interface de gerenciamento está disponível em:

`http://localhost:15672`

Credenciais padrão do ambiente local:

- Usuário: `guest`
- Senha: `guest`

A aplicação utiliza `localhost:5672` para se conectar ao broker local.

Em ambientes hospedados, é necessário configurar um broker acessível pela aplicação.

## 🤖 Inteligência artificial — Spring AI e Gemini

A API utiliza Spring AI integrado ao Google Gemini para gerar orientações preventivas em linguagem natural.

Quando o cidadão envia uma pergunta, o `OrientacaoService` identifica o ponto mencionado e consulta o Oracle para obter a última leitura e verificar a existência de alertas ativos.

Essas informações são enviadas ao Gemini junto com a pergunta do usuário. O modelo utiliza o contexto fornecido para elaborar uma orientação preventiva.

O prompt orienta o modelo a não inventar informações, considerar os dados disponíveis e informar quando não houver informações suficientes.

Assim, as respostas são contextualizadas com os registros disponíveis no banco de dados.

**Endpoint:**

`POST /api/ia/orientacoes`

**Exemplo de requisição:**

```json
{
  "pergunta": "Posso ir até o Terminal Campo Limpo?"
}
```

A chave de acesso é configurada pela variável de ambiente `GEMINI_API_KEY`.

## 🗄️ Banco de dados

A aplicação utiliza Oracle Database com Spring Data JPA.

| Tabela | Finalidade |
|---|---|
| `USUARIO_HB` | Usuários |
| `PONTO_MONITORAMENTO_HB` | Pontos de coleta |
| `LEITURA_HB` | Histórico de leituras |
| `ALERTA_HB` | Alertas de alagamento |

O Hibernate utiliza `ddl-auto=validate`, validando a estrutura existente sem realizar alterações automáticas nas tabelas.

## 🔐 Segurança

A aplicação utiliza Spring Security e JWT para autenticação e controle de acesso.

- Senhas armazenadas com BCrypt.
- Autenticação por token JWT.
- Controle de acesso por papéis.
- Validação dos dados de entrada.
- Tratamento global de exceções.

### Papéis de acesso

| Papel | Permissões gerais |
|---|---|
| `CIDADAO` | Consultar informações permitidas e acessar funcionalidades próprias |
| `GESTOR` | Gerenciar pontos de coleta e executar operações administrativas |

## 📑 Principais endpoints

| Recurso | Endpoint |
|---|---|
| Login | `POST /api/auth/login` |
| Usuários | `/api/usuarios` |
| Pontos de coleta | `/api/pontos` |
| Leituras | `/api/leituras` |
| Alertas | `/api/alertas` |
| IoT | `POST /api/iot/leituras?pontoId=2` |
| Orientações com IA | `POST /api/ia/orientacoes` |

Os endpoints de consulta possuem operações com paginação, e as permissões variam conforme o papel do usuário.

A documentação completa, incluindo parâmetros, DTOs, códigos de resposta e demais operações, está disponível no Swagger.

## 👥 Usuários de teste

A API possui dois papéis para demonstrar o controle de acesso:

| Papel | Finalidade |
|---|---|
| `CIDADAO` | Testar operações permitidas a cidadãos |
| `GESTOR` | Testar operações administrativas |

Os usuários de teste podem ser cadastrados pelo endpoint de usuários, utilizando os papéis disponíveis.

As credenciais devem ser informadas conforme os usuários cadastrados no ambiente disponibilizado. Não são incluídas credenciais reais no repositório.

## ▶️ Como executar localmente

### Pré-requisitos

- Java 21.
- IntelliJ IDEA.
- Acesso ao Oracle Database.
- RabbitMQ local.
- Chave de API do Google Gemini.

### Configuração

1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o carregamento das dependências Maven.
3. Acesse **Run → Edit Configurations**.
4. Selecione a configuração da aplicação Spring Boot.
5. Configure as seguintes variáveis de ambiente:

| Variável | Finalidade |
|---|---|
| `ORACLE_USER` | Usuário do Oracle |
| `ORACLE_PASSWORD` | Senha do Oracle |
| `JWT_SECRET` | Chave de assinatura JWT |
| `GEMINI_API_KEY` | Chave da API Gemini |

6. Verifique se o Oracle e o RabbitMQ estão acessíveis.

**Verificação do RabbitMQ:**

- **Linux:** execute `sudo systemctl status rabbitmq-server`.
- **Windows:** abra `services.msc` e verifique se o serviço RabbitMQ está em execução.

7. Clique em **Apply** e **OK**.
8. Execute a aplicação pelo botão **Run**.

A API será iniciada na porta `8080`.

## 🧩 Requisito 5 — Arquitetura distribuída

A solução utiliza **RabbitMQ** para comunicação assíncrona entre componentes, por meio da publicação e do consumo de eventos relacionados à criação de alertas.

A API Java atua como produtora e consumidora de eventos, utilizando exchange, fila e routing key para o processamento assíncrono.

Essa abordagem permite desacoplar a publicação do evento de seu processamento, reduzindo a dependência de comunicação síncrona nessa operação.

## 🌐 Deploy e documentação

A API está hospedada no Render.

- **API:** https://hidrobairro-java-gs2.onrender.com
- **Swagger:** https://hidrobairro-java-gs2.onrender.com/swagger-ui/index.html
- **Execução local:** http://localhost:8080/swagger-ui/index.html

A documentação interativa permite consultar e testar os endpoints, visualizar os DTOs e informar o token JWT para acessar operações protegidas.

## 🧠 Uso saudável de Inteligência Artificial

A inteligência artificial foi utilizada como ferramenta de apoio durante o desenvolvimento, auxiliando na compreensão de tecnologias, análise de problemas, revisão de código, documentação e identificação de possíveis melhorias.

Na aplicação, a IA também integra a solução por meio do Spring AI e Google Gemini, que geram orientações preventivas contextualizadas com os dados armazenados no banco.

A equipe realizou a análise, adaptação e validação das sugestões geradas, mantendo a responsabilidade pelas decisões de implementação e pelo funcionamento da solução.

A IA foi utilizada como apoio ao desenvolvimento e à aprendizagem, não como substituta da compreensão técnica ou da validação humana.

## 👨‍💻 Equipe

| Integrante | RM | GitHub |
|---|---|---|
| Arthur dos Santos Cabral | RM566515 | [ArthurCPV](https://github.com/ArthurCPV) |
| Bruno Martins Bettio | RM564939 | [TaikaWaititi](https://github.com/TaikaWaititi) |
| José Diogo Da Silva Neves | RM562341 | [ZeDio](https://github.com/ZeDio) |
| Júlia Tiziotto Buttler | RM564975 | [JuliaTButtler](https://github.com/JuliaTButtler) |
| Mariana Xavier Quispe | RM566357 | [Marixavq](https://github.com/Marixavq) |

Este projeto foi desenvolvido para a disciplina de **Java Advanced** e integra conceitos aplicados em outras disciplinas da Global Solution interdisciplinar da FIAP.

## 📂 Repositório

https://github.com/Marixavq/hidrobairro-java-gs2