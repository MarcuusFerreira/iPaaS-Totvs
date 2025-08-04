# 🚀 Desafio Técnico iPaaS – Backend Java Pleno

## 📝 Padrão de Commits

Este projeto segue o padrão de commits descrito no repositório [Padrões de Commits - iuricode](https://github.com/iuricode/padroes-de-commits).  
Consulte o link para entender como estruturar as mensagens e manter um histórico de commits organizado e padronizado.

## 🏃‍♂️ Como executar o projeto

O projeto pode ser executado de duas formas: sem Docker ou com Docker.

### Sem docker

1. Certifique-se de ter o PostgreSQL instalado na sua máquina
(no desenvolvimento foi utilizada a versão 16.9).

2. Exporte as seguintes variáveis de ambiente:
```shell
POSTGRES_URL=SUA_URL_DO_POSTGRES_AQUI
POSTGRES_DB=O_NOME_DO_SE_BANCO_DE_DADOS_DO_POSTGRES_AQUI
POSTGRES_USER=SEU_USUARIO_DO_POSTGRES_AQUI
POSTGRES_PASSWORD=SUA_SENHA_DO_POSTGRES_AQUI
```

3. Dentro da pasta do projeto, execute o comando:
```sh
./mvnw spring-boot:run
```

### Com docker

```sh
# Carrega a imagem do projeto usando o JRE para execução
docker-compose up -d
```
## 📌 Endpoints Disponíveis

#### **Observação**

- Você pode importar as coleções de requisições disponíveis na pasta docs/postman diretamente no Postman para facilitar o teste da API.

### 👤 **Usuários (Users)**

| Método  | Endpoint       | Descrição                          |
|---------|---------------|------------------------------------|
| **POST** | `/users`      | Cria um novo usuário.              |
| **GET**  | `/users/{id}` | Busca um usuário pelo seu ID.      |

---

### ✅ **Tarefas (Tasks)**

| Método  | Endpoint                              | Descrição |
|---------|---------------------------------------|-----------|
| **POST** | `/tasks`                             | Cria uma nova tarefa. |
| **GET**  | `/tasks?status=...&userId=...`       | Lista tarefas filtradas por **status** (obrigatório) e **userId** (opcional). Retorna resultados **paginados**. |
| **PATCH** | `/tasks/{id}/status`                | Atualiza o status de uma tarefa. |

---

### 📝 **Subtarefas (Sub Tasks)**

| Método  | Endpoint                                           | Descrição |
|---------|----------------------------------------------------|-----------|
| **POST** | `/tasks/{task_id}/subtasks`                       | Cria uma nova subtarefa vinculada a uma tarefa existente. |
| **GET**  | `/tasks/{task_id}/subtasks?status=...`            | Lista subtarefas de uma tarefa. O filtro **status** é opcional. Retorna resultados **paginados**. |
| **PATCH** | `/subtasks/{id}/status`                          | Atualiza o status de uma subtarefa. |

## 🛠 Tecnologias Utilizadas

O projeto foi desenvolvido com as seguintes tecnologias e ferramentas:

- **Java 21** — Linguagem principal do backend.
- **Spring Boot** — Framework para criação de aplicações Java.
- **GraalVM** — Para geração de binários nativos e otimização de performance.
- **PostgreSQL** — Banco de dados relacional.
- **Docker** — Containerização da aplicação e do ambiente.
- **Swagger UI** - Interface interativa para documentação e testes das APIs.
- **Postman** — Testes e documentação de APIs.

## ⚙️ Decisões Técnicas

Para garantir regras de negócio claras e bem isoladas, optei por implementar a Arquitetura Limpa (Clean Architecture), 
o que facilita a manutenção, escalabilidade e testabilidade do sistema.

O projeto foi desenvolvido em inglês para garantir padronização, facilitar a colaboração com equipes internacionais e 
aderir a boas práticas amplamente adotadas no mercado.

Utilizei o PostgreSQL por sua robustez e eficiência em consultas relacionais, adequado para os requisitos bem definidos do desafio. 
Porém, em ambientes de produção, o MongoDB pode ser uma alternativa interessante em cenários que demandem maior flexibilidade 
no modelo de dados — por exemplo, quando é necessário vincular tarefas a múltiplos usuários sem múltiplos joins, 
ou em arquiteturas multitenant com grande volume de dados. Nessas situações, o modelo documental do MongoDB pode oferecer melhor escalabilidade e desempenho.


Para filtros dinâmicos, utilizei Specifications, que permitem compor consultas complexas de forma flexível e reutilizável. 
Embora pudessem ser usados métodos padrões do Spring Data JPA (findByStatusAndUserId, etc), as Specifications 
melhoram a manutenção e extensão da lógica de filtragem.

