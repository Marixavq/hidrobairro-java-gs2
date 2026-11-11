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
    │       ├── controller/
    │       ├── dto/
    │       │   ├── request/
    │       │   └── response/
            ├── exception/
    │       ├── model/
    │       ├── repository/
    │       ├── security/
    │       ├── service/
    │       └── GsHidrobairroApplication.java
    │
    └── resources/
        ├── db/
        │   └── migration/
        └── application.properties
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

