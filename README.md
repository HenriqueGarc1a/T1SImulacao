1. DESCRIÇÃO
------------
Este projeto implementa um simulador de sistema de filas com múltiplos servidores e conexões probabilísticas entre filas, baseado em um modelo G/G/m/n. A simulação é configurada dinamicamente por um arquivo JSON, que define as características de cada fila.

2. REQUISITOS
-------------
- Java 17 ou superior
- Biblioteca externa: GSON (para ler arquivos JSON)

3. COMO USAR   
-------------
Após compilar o projeto deve-se selecionar o arquivo .JSON a sua escolha para a execusão, esse arquivo deve seguir a extrutura sugerida abaixo, sendo que apenas a primeira fila(com id: 1) deve ter valores positivos em minChegada e maxChegada por conta ela receber os clientes externos.

"filas": [
    {
      "id": 1,
      "capacidade": 2147483647,
      "servidores": 1,
      "minChegada": 2,
      "maxChegada": 4,
      "minServico": 1,
      "maxServico": 2,
      "proximas": [
        { "id": 2, "prob": 0.8 },
        { "id": 3, "prob": 0.2 }
      ]
    },
]


