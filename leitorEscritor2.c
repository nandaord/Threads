#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>

pthread_rwlock_t rwlock;
int recurso_compartilhado = 0;

void *leitor(void *arg) {
    while (1) {
        pthread_rwlock_rdlock(&rwlock);

        printf("Leitor %lu está lendo o valor: %d\n", pthread_self(), recurso_compartilhado);
        sleep(1);

        pthread_rwlock_unlock(&rwlock);

        sleep(1);
    }
}

void *escritor(void *arg) {
    while (1) {
        pthread_rwlock_wrlock(&rwlock);

        recurso_compartilhado++;
        printf("Escritor %lu está escrevendo o valor: %d\n", pthread_self(), recurso_compartilhado);
        sleep(2);

        pthread_rwlock_unlock(&rwlock);

        sleep(2);
    }
}

int main() {
    pthread_t leitores[10], escritores[5];

    pthread_rwlock_init(&rwlock, NULL);

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

    pthread_rwlock_destroy(&rwlock);

    return 0;
}
