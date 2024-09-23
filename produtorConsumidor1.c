#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#define TAM_BUFFER 100

int buffer[TAM_BUFFER];
int cont = 0;
sem_t vagasDisponiveis;
sem_t itensDisponiveis;
pthread_mutex_t mutex;

void *consumidor(){
    while(1){
        sem_wait(&itensDisponiveis);
        pthread_mutex_lock(&mutex);
        cont--;
        int item = buffer[cont];
        printf("Consumidor %lu consumiu: %d (Buffer: %d/%d)\n", pthread_self(), item, cont, TAM_BUFFER);

        pthread_mutex_unlock(&mutex);
        sem_post(&vagasDisponiveis);
        sleep(2);
    }
}
void *produtor(){
    while (1){
        int item = rand() % 100;
        sem_wait(&vagasDisponiveis);
        pthread_mutex_lock(&mutex);
        buffer[cont] = item;
        cont++;

        printf("Produtor %lu produziu: %d (Buffer: %d/%d)\n", pthread_self(), item, cont, TAM_BUFFER);
        pthread_mutex_unlock(&mutex);
        sem_post(&itensDisponiveis);
        sleep(1);
    }
}
int main(){
    pthread_t produtores[10], consumidores[10];

    sem_init(&vagasDisponiveis, 0, TAM_BUFFER);
    sem_init(&itensDisponiveis, 0, 0);
    pthread_mutex_init(&mutex, NULL);

    for (int i = 0; i < 10; i++){
        pthread_create(&produtores[i], NULL, produtor, NULL);
        pthread_create(&consumidores[i], NULL, consumidor, NULL);
    }

    for (int i = 0; i < 10; i++){
        pthread_join(produtores[i], NULL);
    }
    for (int i = 0; i < 10; i++){
        pthread_join(consumidores[i], NULL);
    }

    sem_destroy(&vagasDisponiveis);
    sem_destroy(&itensDisponiveis);
    pthread_mutex_destroy(&mutex);
}
