#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#define TAM_BUFFER 100

int buffer[TAM_BUFFER];
int cont = 0;

pthread_cond_t condicao_produtor;
pthread_cond_t condicao_consumidor;
pthread_mutex_t mutex;

void* consumidor(void *arg){
    while(1){
        pthread_mutex_lock(&mutex);

        while(cont == 0){
            pthread_cond_wait(&condicao_consumidor, &mutex);
        }

        cont--;
        int item = buffer[cont];
        printf("Consumidor %lu consumiu: %d (Buffer: %d/%d)\n", pthread_self(), item, cont, TAM_BUFFER);

        pthread_cond_signal(&condicao_produtor);
        pthread_mutex_unlock(&mutex);

        sleep(2);
    }
    // return NULL;
}

void* produtor(void* arg){
    while(1){
        int item = rand() % 100;
        pthread_mutex_lock(&mutex);

        while(cont == TAM_BUFFER){
            pthread_cond_wait(&condicao_produtor, &mutex);
        }

        buffer[cont] = item;
        cont++;
        printf("Produtor %lu produziu: %d (Buffer: %d/%d)\n", pthread_self(), item, cont, TAM_BUFFER);

        pthread_cond_signal(&condicao_consumidor);
        pthread_mutex_unlock(&mutex);

        sleep(2);
    }
    // return NULL;
}

int main(void){
    pthread_t threads_produtor[10], threads_consumidor[10];

    pthread_cond_init(&condicao_produtor, NULL);
    pthread_cond_init(&condicao_consumidor, NULL);
    pthread_mutex_init(&mutex, NULL);

    for(int i = 0; i < 10; i++){
        pthread_create(&threads_produtor[i], NULL, produtor, NULL);
        pthread_create(&threads_consumidor[i], NULL, consumidor, NULL);
    }

    for(int i = 0; i < 10; i++){
        pthread_join(threads_produtor[i], NULL);
    }

    for(int i = 0; i < 10; i++){
        pthread_join(threads_consumidor[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&condicao_produtor);
    pthread_cond_destroy(&condicao_consumidor);

    return 0;
}