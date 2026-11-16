# Sistema HidroBairro Sul 


## 🛠️ Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT (JSON Web Token)
* Bean Validation

### Banco de Dados

* Oracle Database — persistência dos dados da aplicação;
* Flyway — controle e versionamento das migrations do banco de dados.

### Ferramentas

* Maven
* Git
* GitHub
* IntelliJ IDEA
* Insomnia

### Deploy

* Render

---

## 📂 Estrutura do Projeto

```text
src/
└── main/
    ├── java/
    │   └── com/fiap/challengepetcenter/
    │       ├── config/
    │       ├── consumer/
    │       ├── controller/
    │       ├── dto/
    │       │   ├── request/
    │       │   └── response/
    │       ├── event/
    │       ├── exception/
    │       ├── listener
    │       ├── model/
    │       ├── publisher
    │       ├── repository/
    │       ├── security/
    │       ├── service/
    │       └── GsHidrobairroApplication.java
    │
    └── resources/
        ├── db/
        │   └── migration/
        ├── application.properties
        └── application.yml
        
```

A aplicação utiliza uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Oracle Database
```

---

### Conversão da distância em nível da água

O sensor ultrassônico HC-SR04 mede a distância entre sua posição de instalação e a superfície da água, em centímetros (`distanciaCm`).

Para a simulação no Wokwi, adotamos uma altura de referência fictícia de **400 cm**. A partir dessa referência, a API Java calcula o nível estimado da água:

**nivelCm = 400 - distanciaCm**

Exemplo: se o sensor medir 100 cm até a superfície da água, o nível estimado será de 300 cm.

Quanto menor a distância entre o sensor e a água, maior será o nível calculado.

**Importante:** a referência de 400 cm é uma premissa utilizada exclusivamente para a simulação. Em uma instalação física, essa altura deverá ser medida e calibrada conforme a posição do sensor e o ponto de referência adotado. O nível calculado é uma estimativa, não uma medição direta da profundidade de um rio ou córrego.

---

Render:  https://hidrobairro-java-gs2.onrender.com/swagger-ui/index.html

Local: http://localhost:8080/swagger-ui/index.html




https://wokwi.com/projects/480842555616945153
RabbitMQ tá rodando localmente apenas.

---

## 👨‍💻 Equipe

| Integrante                | RM       | Perfil GitHub                                     |
|---------------------------|----------|---------------------------------------------------|
| Arthur dos Santos Cabral  | RM566515 | [ArthurCPV](https://github.com/ArthurCPV)         |
| Bruno Martins Bettio      | RM564939 | [TaikaWaititi](https://github.com/TaikaWaititi)   |
| José Diogo Da Silva Neves | RM562341 | [ZeDio](https://github.com/ZeDio)                 |
| Júlia Tiziotto Buttler    | RM564975 | [JuliaTButtler](https://github.com/JuliaTButtler) |
| Mariana Xavier Quispe     | RM566357 | [Marixavq](https://github.com/Marixavq)           |

Este projeto foi desenvolvido para a disciplina de **Java Advanced** e integra conceitos aplicados em outras disciplinas
da GS interdisciplinar da FIAP.

