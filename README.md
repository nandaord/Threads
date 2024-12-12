# Soluções para Exercícios de Deadlocks e Threads - Infraestrutura de Software

Este repositório contém as soluções para os problemas relacionados a deadlocks e threads da disciplina de Infraestrutura de Software, do 3º período do curso de Ciências da Computação da CESAR School.

## 📝 Exercícios

### 1. Sistema Produtor/Consumidor com Buffers Limitados 🍞

- Implementação de um sistema com dois buffers limitados (`B1` e `B2`) e semáforos, onde os processos `X`, `Y` e `Z` interagem com os buffers. O fluxo é organizado da seguinte forma: `X → B1 → Y → B2 → Z`.

#### Descrição:
- `X`, `Y`, `Z` são processos que interagem com os buffers `B1` e `B2`.
- Semáforos são usados para controlar o acesso aos buffers.
- O código define a interação entre os processos e os buffers, utilizando as operações `insere(Bi, item)` e `retira(Bi, item)`.

### 2. Problema do Jantar dos Filósofos com Possível Impasse 🍽️

- Análise e modificação do código para evitar deadlocks no problema clássico do jantar dos filósofos.

#### Descrição:
- Identificação do risco de deadlock no código fornecido.
- Modificação da implementação para garantir a eliminação do risco de impasse.

### 3. Coordenação de Robôs com Semáforos 🤖

- Implementação de semáforos para garantir que os robôs `Bart`, `Lisa` e `Maggie` se movam em sequência cíclica.

#### Descrição:
- A sincronização dos robôs é realizada de modo a garantir que cada um se mova em uma sequência específica: `Bart → Lisa → Maggie → Lisa → Bart → Lisa → Maggie → ...`.

### 4. Implementação do Operador Rendez-Vous ⏳

- Implementação do operador de sincronização `rendez-vous` para sincronizar dois processos ou threads.

#### Descrição:
- Uso de semáforos para garantir que dois processos se encontrem em um ponto comum antes de continuar a execução.

### 5. Implementação de uma Barreira para N Processos 🚧

- Implementação de uma barreira de sincronização para N processos ou threads, garantindo que todos os processos esperem até que todos tenham chegado à barreira antes de prosseguir.

#### Descrição:
- A sincronização é implementada utilizando semáforos ou mutexes, garantindo que todos os processos sincronizem suas execuções na barreira.

## 💻 Atividades Práticas

### 1. Solução do Problema do Produtor/Consumidor com Semáforos 🍞

- Implementação em C usando threads e semáforos POSIX.

### 2. Solução do Problema do Produtor/Consumidor com Variáveis de Condição 🍲

- Implementação em C usando threads e variáveis de condição POSIX.

### 3. Problema dos Leitores/Escritores com Prioridade para Escritores (Semáforos) 📖✍️

- Implementação em C usando threads e semáforos POSIX, com ênfase na prioridade para escritores.

### 4. Problema dos Leitores/Escritores com Prioridade para Escritores (rwlocks) 📚🔒

- Implementação em C usando threads e rwlocks POSIX, garantindo a priorização de escritores.

## ⚙️ Tecnologias Utilizadas

- Linguagem: C
- Padrão POSIX: Threads, Semáforos, Variáveis de Condição e rwlocks.

## 🚀 Como Rodar

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/deadlocks-threads.git

2. Navegue até o diretório do projeto:
   ```bash
   cd deadlocks-threads
3. Compile o código:
   ```bash
   gcc -o produtor_consumidor produtor_consumidor.c -lpthread
4. Execute o programa:
   ```bash
   ./produtor_consumidor

## 👥Autoria
- Beatriz Pereira
- Manuela Cavalcanti
- Maria Fernanda Ordonho
- Rafaela Vidal
- Ygor Rosa

