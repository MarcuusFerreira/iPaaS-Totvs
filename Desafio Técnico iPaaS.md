# 🚀 Desafio Técnico iPaaS – Backend Java Pleno

Seja bem-vindo(a)! Este é o desafio técnico para a vaga de **Desenvolvedor(a) Backend Java Pleno** na iPaaS. A missão é construir uma API RESTful bem estruturada, funcional e escalável, seguindo boas práticas de engenharia de software.

---

## 📦 O que você vai construir

Você irá desenvolver uma **API para gerenciamento de tarefas internas**, permitindo que usuários criem, atualizem e acompanhem suas tarefas e subtarefas.

O objetivo é avaliar sua capacidade técnica com foco em qualidade de código, organização, clareza de estrutura e entendimento de requisitos.

---

## 🧠 Entidades e Regras de Negócio (Escolha o idioma que preferir para criar os nomes das entidades/endpoints)

### 👤 Usuário

**Campos obrigatórios:**
- `id`: UUID (gerado automaticamente)  
- `nome`: obrigatório  
- `email`: obrigatório, único e com formato válido  

**Endpoints esperados:**
- `POST /usuarios` → cria um novo usuário  
- `GET /usuarios/{id}` → busca um usuário por ID  

---

### ✅ Tarefa

**Campos obrigatórios:**
- `id`: UUID (gerado automaticamente)  
- `titulo`: obrigatório  
- `descricao`: opcional  
- `status`: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`  
- `dataCriacao`: gerada automaticamente  
- `dataConclusao`: preenchida somente se o status for `CONCLUIDA`  
- `usuarioId`: identifica o dono da tarefa  

**Regras de negócio:**
- O campo `dataConclusao` só deve ser preenchido se o status for `CONCLUIDA`
- Uma tarefa **só pode ser concluída** se **todas as subtarefas** estiverem com status `CONCLUIDA`

**Endpoints esperados:**
- `POST /tarefas` → cria uma nova tarefa para um usuário  
- `GET /tarefas?status=PENDENTE` → lista tarefas filtradas por status  
- `PATCH /tarefas/{id}/status` → atualiza o status da tarefa  

---

### 📌 Subtarefa

**Campos obrigatórios:**
- `id`: UUID (gerado automaticamente)  
- `titulo`: obrigatório  
- `descricao`: opcional  
- `status`: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`  
- `dataCriacao`: gerada automaticamente  
- `dataConclusao`: preenchida somente se o status for `CONCLUIDA`  
- `tarefaId`: identifica a tarefa principal  

**Regras de negócio:**
- A alteração de status da subtarefa **não** deve afetar automaticamente a tarefa principal, mas **impede** sua conclusão enquanto houver subtarefas pendentes

**Endpoints esperados:**
- `POST /tarefas/{tarefaId}/subtarefas` → cria uma nova subtarefa  
- `GET /tarefas/{tarefaId}/subtarefas` → lista subtarefas da tarefa  
- `PATCH /subtarefas/{id}/status` → atualiza o status de uma subtarefa  

---

## ✅ Requisitos obrigatórios

- ☕ Java 17+ com Spring Boot ou Quarkus  
- 🧠 Regras de negócio bem definidas e isoladas  
- 📦 Projeto organizado por responsabilidades  
- 🛢️ Banco de dados relacional, NoSQL ou em memória  
- 📏 Validações com Bean Validation (ou similar)  
- 🧪 Testes automatizados (unitários e/ou de integração)

---

## 🌟 Pontos bônus

- 🐳 Docker (Dockerfile ou docker-compose)  
- 📄 Documentação com Swagger/OpenAPI  
- 🔍 Paginação e filtros combinados (ex: `/tarefas?status=...&usuarioId=...`)  
- 🧪 Testes de exceções e integração  
- 🔐 Segurança básica (validações, tratamento de entrada, etc.)

---

## 📬 Entrega

- Repositório público (GitHub, GitLab, etc) ou `.zip`  
- Inclua um `README.md` no projeto com:  
  - Instruções para rodar o projeto (com ou sem Docker)  
  - Lista de endpoints disponíveis  
  - Tecnologias utilizadas  

---

## 🍀 Boa sorte!

Capriche no código, se divirta no processo e mostre o melhor do seu conhecimento técnico. Estamos ansiosos para ver sua solução!

