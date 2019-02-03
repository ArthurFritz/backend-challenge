## Invillia recruitment challenge
[![Build Status](https://travis-ci.com/ArthurFritz/backend-challenge.svg?branch=master)](https://travis-ci.com/ArthurFritz/backend-challenge)

#### Considerações

Para execução do projeto é preciso de uma base Mongo local, ou um container "mongo" rodando.

No ato da compilação ele irá criar as imagens docker do projeto, cada serviço com sua última versão sendo latest, porém se usarmos uma nuvem para registro de docker, iria colocar mais um passo no plugin, para que seja enviado a tag do projeto.
Caso não queira criar as imagens docker, usar o goal do maven a seguir: `-Ddocker.skip=true`

Fiz integração com o Travis.ci para garantir a build do projeto, e poderiamos utilizar algo nessa linha para deploy sem utilizar kubernates ou gerenciamento de imagens docker.

Foi criado os serviços separados cada um com sua responsabilidade e sua base segregada.

Fiz uma implementação assincrona quando o serviço de pagamento solicita para o serviço de ordens atualizar seu status.

Utilizei o Spring para gerencimaneto das rotas exeto na documentação, que estara disponível na porta 8484 do serviço de documentação.
   * http://localhost:8484/  - Apis públicas
   * http://localhost:8484/internal.html  - Apis privadas entre os servidores

Executei todas as mensagens, e codificação em inglês.

Usei a estrutura do Spring Cloud, como o Eureka e o Zull para gerenciamento dos serviços porém, se utilizarmos de uma estrura de containers, acaba que essa responsabilidade vai para o seu orquestrator, e caindo em desuso.

Anexo ao projeto tem o arquivo ``Invillia.postman_collection.json`` que é a collection do postman usando diretamente as portas dos serviços. Caso queiram utilizar do zull, utilizar a porta 9999

Já no evento de pagamento, utilizei a integração entre os serviços, pensei em utilizar Kafka porém iria acabar gerando mais uma depência ao projeto. Sendo um MVP, não aumentei a complexidade do mesmo.

Criei exceptions onde elas mudam o status code da request, porém ainda é necessário realizar uma padronização das respostas de erro.

#### Implementações faltantes
 - Não implementei o security por falta de tempo no desenvolvimento, mas a idéia é utilizar o Spring Security, caso seja colocado em uma AWS que não tenha implementado uma camada de segurança.
 - Melhorar as mensagens de erro.
 - Incluir internacionalização das mensagens.