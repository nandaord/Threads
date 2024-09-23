#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

sem_t acesso_area;    // Controla o acesso à área compartilhada (para escritores)
sem_t mutex_leitores; // Controla o acesso ao contador de leitores

int leitores_ativos = 0; // Contador de leitores ativos
int escritores_esperando = 0; // Contador de escritores esperando
int recurso_compartilhado = 0; // O recurso compartilhado

void *leitor(void *arg) {
    while (1) {
        sem_wait(&mutex_leitores);
        if (leitores_ativos == 0) {
            sem_wait(&acesso_area);
        }
        leitores_ativos++; 
        sem_post(&mutex_leitores);

        printf("Leitor %lu está lendo o valor: %d\n", pthread_self(), recurso_compartilhado);
        sleep(1);

        sem_wait(&mutex_leitores);
        leitores_ativos--; 
        if (leitores_ativos == 0) {
            sem_post(&acesso_area);
        }
        sem_post(&mutex_leitores);

        sleep(1);
    }
}

void *escritor(void *arg) {
    while (1) {
        sem_wait(&acesso_area);

        recurso_compartilhado++;
        printf("Escritor %lu está escrevendo o valor: %d\n", pthread_self(), recurso_compartilhado);
        sleep(2);

        sem_post(&acesso_area);
        sleep(2); 
    }
}

int main() {
    pthread_t leitores[10], escritores[5];

    sem_init(&acesso_area, 0, 1);        
    sem_init(&mutex_leitores, 0, 1);     

    for (int i = 0; i < 10; i++) {
        pthread_create(&leitores[i], NULL, leitor, NULL);
    }

    for (int i = 0; i < 5; i++) {
        pthread_create(&escritores[i], NULL, escritor, NULL);
    }

    for (int i = 0; i < 10; i++) {
        pthread_join(leitores[i], NULL);
    }

    for (int i = 0; i < 5; i++) {
        pthread_join(escritores[i], NULL);
    }

    sem_destroy(&acesso_area);
    sem_destroy(&mutex_leitores);
}
