#### 23/10/2023

@01-Recursão e recursão de cauda

@@01
Introdução

Boas vindas a mais um curso de Clojure. Neste curso discutiremos bastante aspectos de programação funcional aplicado às coleções. Já vimos como esses elementos se mixam.
Implementaremos nosso próprio map e uma variação de um reduce, de maneira simplificada, mas ainda sim implementações nossas que utilizarão recurso de calda e loop. Aprenderemos também em como lidar com os problemas que esses recursos podem apresentar eventualmente no código.

Aprenderemos diversas outras funções que surgirão no nosso dia a dia durante o trabalho com coleções, gerar dados e assim por diante. Será que tudo que pedimos em Clojure será executado de maneira imperativa? Ou será que simplesmente descrevemos o que deve ser feito e isso é executado no momento mais conveniente?

Há um pouco dos dois casos e cada uma com sua vantagem, e enquanto pessoas que programam, ora isso será uma preocupação para nós, ora não. Teremos essa discussão ao longo do curso, e espero que seja proveitosa para você!

@@02
Implementando um map na unha com recursão

Neste curso, começaremos um projeto novo de Clojure do Leiningen, cujo título será "loja". Esse processo demora alguns minutos. Termos o diretório "src", então "loja" que por sua vez abrigará o documento core.clj:
(ns loja.core)

(defn foo
    "I don't do a whole lot."
    [x]
    (println x "Hello, World!"))COPIAR CÓDIGO
Primeiramente, executaremos o nosso Leiningen. Com o botão direito clicaremos sobre o arquivo project.clj e selecionaremos a opção "Run 'REPL for loja'. Agora podemos trabalha em loja.core.

Já conhecemos alguns tipos de coleções, por exemplo um vetor que pode ter vários elementos. Por exemplo um vetor que contém diversos nomes como :

;["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]COPIAR CÓDIGO
Também conhecemos o modelo associativo, ou dicionário. Nesta coleção poderíamos dizer que guilherme tem 37 anos e o paulo tem 39. O uso da vírgula para separar elementos no Clousure é opcional.

;["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]
;{ "guilherme" 37, "paulo" 39}COPIAR CÓDIGO
Poderíamos ter ainda outro tipo de coleção que é meramente uma sequencia de elementos. Ainda poderia ver uma coleção que, ao invés da restrição ser por acesso randômico via array, seria uma lista ligada, em que se utiliza ;'().

;["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]
;{ "guilherme" 37, "paulo" 39}
;'(1 2 3 4 5)COPIAR CÓDIGO
Outro tipo de coleção possível é aquela que contém 0 ou 1 elemento, totalmente válida. Um tipo de coleção também válida é aquela composta por um conjunto.

No cotidiano da programação, utilizamos coleções o tempo todo, por isso destacamos a importância de map, reduce e filter. Quando queremos passar por vários elementos de uma coleção executando algo em cada um delas e ainda retornar o resultado da execução para cada um deles fazemos o map. Quando queremos filtrar alguns dos elementos, utilizamos o filter e quando queremos reduzir os elementosacionamos o reduce.

E se quisermos usar um loop como em outras linguagens? Ou mesmo for? Existem recursos do gênero, mas antes aprenderemos de que maneira funcional podemos fazer algo para todos os elementos de um vetor. Por exemplo, num conjunto de nomes, gostaríamos de imprimir todos eles.

Sabemos que essa é uma ação possível:

(map println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])COPIAR CÓDIGO
O map realiza esse trabalho para nós. O map é um código funcional que realiza algo, não se trata de uma key word da linguagem, trata-se de uma função que foi implementada por alguém, ainda que carregado de otimizações.

O map passa pelo primeiro elemento e executa a função, ao final só resta println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"], e então depois println ["guilherme" "carlos" "paulo" "lucia" "ana"]), até que todos os elementos tenham sido lidos, e resta apenas printl[]

Conseguimos também coletar o primeiro elemento de um vetor, e não existe apenas uma maneira de fazer isso. Uma maneira bem simples é usar o first.

(println (first ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))COPIAR CÓDIGO
Será coletado apenas daniela. Existem maneiras de coletar todos os elementos, exceto o primeiro, como o rest e o next. Há pequenas diferenças entre os dois, principalmente quando estamos de uma sequência vazia de elementos, neste caso o rest devolve vazio, já o next nos devolve nulo. Então, o next poderia ser utilizado para descobrir quando uma sequencia termina.

Já utilizamos várias vezes o termo "sequência" ao invés de "vetor". O uso de { explicita um vetor, mas a verdade é que várias funções nos devolvem uma sequência de elementos, como esse sequência foi implementada são informações que não sabemos.

Podemos transformar um vetor vazio em uma sequência (seq), e o seq de um vetor vazio é nulo, mas o seq de um vetor com elementos resulta em uma sequencia com esses elementos.

Queremos executar, usando o first, rest ou next ser capaz de chamar o println para todos os elementos, quer dizer queremos implementar uma versão simples do nosso mapa.

Começaremos com uma função e uma sequência. Feito isso coletaremos o primeiro elemento dessa sequência (first)

(println "\n\n\n\nMEU MAPA")


(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (funcao primeiro)))COPIAR CÓDIGO
Vamos testar.

(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
COPIAR CÓDIGO
Funcionou perfeitamente, a foi impresso daniela. Não queremos imprimir o primeiro elemento, mas todo o resto da sequência. Chamaremos meu-mapa e então usaremos a função rest.

(println "\n\n\n\nMEU MAPA")


(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (funcao primeiro)
    (meu-mapa funcao(rest sequencia))
    ))COPIAR CÓDIGO
Ao testaremos nosso código, veremos que ele entrou em loop. Por mais que o loop não seja explicito, no código chamamos nós mesmos até que imprimimos anae e ficamos com uma sequencia vazia. O first de uma sequência vazia é nulo, e então ficamos nesse ciclo de loop. Portanto precisamos em algum momento parar o laço.

Estamos nos invocando recursivamente, sem parar. Precisamos interromper esse processo quando não houver mais próximo elemento a ser buscado. Realizaremos então um if

(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (if primeiro
      (funcao primeiro)
      (meu-mapa funcao(rest sequencia)))
    ))
(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])COPIAR CÓDIGO
Ao testarmos nosso código, veremos impresso daniela, apenas. Por que isso aconteceu? O if possui duas funções if e else. Queremos escrever o if com mais um bloco de código, e então inseriremos o do

(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (if primeiro
        (do
      (funcao primeiro)
      (meu-mapa funcao(rest sequencia))))
    ))
(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])COPIAR CÓDIGO
Feito isso,teremos todos os outros nomes impressos. Isso se deu porque o if verdadeiro executa a ação. Se o primeiro for nulo ou falso, a impressão é interrompida. Imprimiremos o mapa com um vetor que contém false.

(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (if primeiro
        (do
      (funcao primeiro)
      (meu-mapa funcao(rest sequencia))))
    ))
(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(meu-mapa println ["daniela" false "carlos" "paulo" "lucia" "ana"])COPIAR CÓDIGO
Novamente é impressão é interrompinda após daniela, isso ocorreu porque o primeiro elemento possui um valor falso. Logo, o if não é o melhor recurso para usarmos nessa situação, pois não conseguimos verificar o valor.

Outra maneira de usar o if é realizar a verificação de nulo.


(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (if (not(nil? primeiro))
        (do
      (funcao primeiro)
      (meu-mapa funcao(rest sequencia))))))

(meu-mapa println ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(meu-mapa println ["daniela" false "carlos" "paulo" "lucia" "ana"])COPIAR CÓDIGO
Teremos impresso na tela

daniela
guilherme
carlos
paulo
lucia
ana
daniela
false
carlos
paulo
lucia
anaCOPIAR CÓDIGO
Nesse caso o mapa deu certo, inclusive se tentarmos imprimir um vetor vazio, nada será impresso, o mesmo o ocorrerá para vetores nulos. Essa é uma maneira de trabalharmos, mas não a única. Por meio da recursão, conseguimos implementar tarefas que deverão ser executadas mais de uma vez.

@@03
Tail recursion

Aprendemos a implementar o map, e a partir disso conseguimos fazer praticamente qualquer ação que envolta implementar coleções. Contudo, devemos tomar cuidado: imagine que ao invés da sequencia de nomes, teremos um range de vários números.
(meu-mapa println (range1000)) COPIAR CÓDIGO
Nesse processo é impresso o número e a função é evocada. Isso começa a empilhar as chamadas da função, e isso pode acabar gerando problemas de sintaxe. No caso dessa função, o erro foi StackOverflowError. Se for apenas uma recursão pura, uma hora a memória do computador terá problemas. Se utilizarmos somente uma recursão pura a memória falha em algum momento, afinal os computadores são finitos e a execução precisa armazenar na memória as variáveis locais para poder chamar a próxima invocação.

A recursão é quando chamamos "nós mesmos", isso é, evocamos a própria função. Dito isso, o compilador de Clojure para Java otimiza a recursão e a transforma em um laço, que não possui o problema da "pilha de execução", pois a variável vai mudando de valor.

Podemos então utilizar o termo recur, lembrando que essa deve ser a última coisa a ser feita logo antes de retornar a função, ou no momento do else.

(defn meu-mapa
    [funcao sequencia]
    (let [primeiro (first sequencia)]
    (if (not(nil? primeiro))
    (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))COPIAR CÓDIGO
Em tempo de compilação,o Clojure irá transformar o recur em um laço. Dessa maneira, não teremos mais problemas ao executar um range1000.

Ao observarmos como um todo, a desvantagem do laço tradicional utilizado em outras linguagens, é que ele articulava diversas referências de símbolos e que possuem valores diferentes de acordo com o momento do laço. Existem casos complexos quando se trata do uso dessa função. Contudo, neste caso, o uso foi funcional, com preservação de memória. Chamamos meu-mapa com uma função e temos uma sequência neste instante.

@@04
Faça como eu fiz na aula

Chegou a hora de você seguir todos os passos realizados por mim durante esta aula. Caso já tenha feito, excelente. Se ainda não, é importante que você implemente o que foi visto no vídeo para poder continuar com a próxima aula, que tem como pré-requisito todo o código aqui escrito. Se por acaso você já domina essa parte, em cada capítulo, você poderá baixar o projeto feito até aquele ponto.

@@05
Recur

Quando podemos usar o recur para efetuar a recursão na função?
Selecione uma alternativa

Só pode aparecer uma vez por função, como último elemento.
 
Alternativa correta
Só pode aparecer como retorno da função, isto é, na cauda.
 
Exato. Se a função possui mais de um caminho (como no caso de um if), os dois caminhos podem apresentar um recur.
Alternativa correta
Em qualquer lugar dentro de uma função recursiva.
 
Parabéns, você acertou!

@@06
O que aprendemos?

O que aprendemos nesta aula:
Como o map funciona;
Utilizar a função first para pegar o primeiro elemento;
Utilizar a função rest para pegar a partir do segundo elemento;
Utilizar a função next para pegar o próximo elemento;
Utilizar a função seq para ver a sequência de elementos;
Utilizar a função do para rodar tudo que está dentro do if;
Fazer recursão;
Utilizar a função recur para dizer que estamos fazendo uma recursão.

#### 24/10/2023

@02-Aridade e loops

@@01
Projeto da aula anterior

Caso queira, você pode baixar o projeto do curso no ponto em que paramos na aula anterior.

https://github.com/alura-cursos/clojure-introducao-a-colecoes/archive/aula1.zip

@@02
Nosso count e múltiplas variações de uma mesma função por aridade distinta

Fomos introduzidos ao tail recursion e implementamos nosso próprio mapa, mas agora queremos implementar nosso próprio tipo de reduce. Da maneira que estamos trabalhando atualmente, não temos um valor acumulado apenas imprimimos informação. Queremos, por exemplo, contar o número de elementos ou somar todos os valores.
Criaremos um novo arquivo chamado loja.alura2, e queremos implementar uma contagem dos elementos, que são 6 nomes.

(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]COPIAR CÓDIGO
Começamos a nossa conta com o número 0, o total até agora e os elementos. Se temos um punhado de bolinhas de gude, mentalizamos o número zero e então vamos contando e retirando uma por uma, 1,2,3 até que as bolinhas de gude terminem.

(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
    [total-ate-agora elementos]
COPIAR CÓDIGO
Não precisamos saber qual é o primeiro elemento porque não faremos nada com ele, mas precisamos do resto. Portanto, usaremos novamente o rest por padrão, então chamaremos a própria conta. Se retiramos um dos elementos, temos o + total-ate-agrora 1, então estamos somando mais 1, também chamado de inc. Já sabemos, também, que é importante utilizarmos o termo recur. Então chamaremos conta.

(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
    [total-ate-agora elementos]
    (recur (inc total-ate-agora) (rest elementos))
    )

(conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
COPIAR CÓDIGO
Teremos uma mensagem de erro que alega a falta de 1 argumento. Precisamos declarar o total de elementos até agora. Lembrando que o objetivo da função é retornar esse total, portanto acrescentaremos também o println.

(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
    [total-ate-agora elementos]
    (recur (inc total-ate-agora) (rest elementos))
    )

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
COPIAR CÓDIGO
Executaremos novamente o código. Nada ocorreu, por que ?

Em nosso código fizemos a recorrência de maneira infinita, nunca paramos. Diferentemente do que o ocorreu em meu-mapa, paramos a execução no momento em que os elementos findam. É fundamental fazermos o critério de parada.

Lembrando que o nextde uma sequência vazia retorna nil, então ao invés de usarmos o rest, usaremos o next. Escreveremos a condicional if para estipular que, caso haja novos elementos em seguida, queremos executar o código.

(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
    [total-ate-agora elementos]
    (recur (inc total-ate-agora) (rest elementos))
    )

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))

(defn conta
    [total-ate-agora elementos]
    (if (next elementos)
        (recur (inc total-ate-agora) (next elementos))))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
COPIAR CÓDIGO
Ao executarmos o código teremos como resultado impresso nil. Por que isso o ocorreu se na verdade queríamos o total de elementos? Nosso critério de parada é quando o if cai no else, e o else - caso não tenha sido implementado - devolve nil.

(defn conta
    [total-ate-agora elementos]
    (if (next elementos)
        (recur (inc total-ate-agora) (next elementos))
        total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
COPIAR CÓDIGO
Ao executarmos o código teremos como resultado o valor 5, mas sabemos que se trata de 6 nomes. Precisamos testar para ter certeza do que houve, então contabilizaremos quantos elementos temos no total ao escrever println total-ate-agora-elementos.

(defn conta
    [total-ate-agora elementos]
    (println total-ate-agora-elementos)
    (if (next elementos)
        (recur (inc total-ate-agora) (next elementos))
        total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
COPIAR CÓDIGO
Teremos como resultado impresso:

0 [daniela guilherme carlos paulo lucia ana]
1 (guilherme carlos paulo lucia ana)
2 (carlos paulo lucia ana)
3 (paulo lucia ana)
4 (lucia ana)
5 (ana) 
5COPIAR CÓDIGO
Resta somarmos mais um elemento, afinal ana de fato não possui um elemento next, próximo. Existem muitas maneiras de realizar essa soma, aprenderemos uma delas. Podemos simplesmente escrever inc e somar mais um elemento.

(defn conta
    [total-ate-agora elementos]
    (println total-ate-agora-elementos)
    (if (next elementos)
        (recur (inc total-ate-agora) (next elementos))
        (inc total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
COPIAR CÓDIGO
O problema foi corrigido. Mas é interessante pensar nessas táticas em casos extremos: se tivéssemos um vetor vazio essa estratégia ainda seria válida? Testaremos.

(defn conta
    [total-ate-agora elementos]
    (println total-ate-agora-elementos)
    (if (next elementos)
        (recur (inc total-ate-agora) (next elementos))
        (inc total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0[]))COPIAR CÓDIGO
O resultado será, obviamente, 1. Já que inc soma um elemento ao vetor. Portanto nesse caso o vetor vazio deverá ter outro dipo de tratamento.

Podemos checar se há algum valor no vetor ao criar na condicional um seq - elemento que transforma uma coleção em uma sequência, e devolve nulo caso essa coleção seja vazia . Precisamos retirar o inc para obter a quantidade correta de elementos.

(defn conta
    [total-ate-agora elementos]
    (println total-ate-agora-elementos)
    (if (seq elementos)
        (recur (inc total-ate-agora) (next elementos))
        (total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0[]))COPIAR CÓDIGO
Dessa maneira, teremos o resultado 6 e 0. O código está funcional, mas precisamos nos atentar a alguns detalhes. Sempre precisamos ter um inicializador, no caso , 0. Já o reduce podemos utilizar o 0 ou não.

Quando temos uma função, ela recebe um parâmetro, e podemos acionar o println para este parâmetro. E assim podemos chamar essa função.

(defn minha-funcao [parametro1] (println parametro1)
(minha-funcao 1)COPIAR CÓDIGO
Podemos, ainda, dizer que a função terá duas variações, que receberá dois parâmetros. Podemos executar a função tanto como (minha-funcao 1) quanto (minha-funcão 1 2):

(defn minha funcao
    ([parametro1] (println "p1" parametro1))
    ([paremetro1 parametro2] (println "p2" parametro1 parametro2)))
(minha-funcao1)
(minha-funcao2)COPIAR CÓDIGO
Trata-se de uma poli-função cuja invocação é definida de acordo com o número de parâmetros. Trata-se de uma maneira de conferir maior flexibilidade às formas.

Podemos aplicar essa ideia em conta. Uma das variações pode receber somente os elementos e chamar a própria conta

(defn conta

    ([elementos]
     (conta 0 elementos))

    [total-ate-agora elementos]
    (println total-ate-agora-elementos)
    (if (seq elementos)
        (recur (inc total-ate-agora) (next elementos))
        (total-ate-agora))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0[]))COPIAR CÓDIGO
Dessa maneira, temos duas variações da função.

@@03
Loop e sua desvantagem

Apensar de termos estudado as variações de implementação por meio de recursividade, existem momentos em que é preferível utilizar algo equivalente ao loop, arco ou for. O Clojure possui suporte apara algumas dessas sintaxes, então conseguimos criar uma nova variação de conta que receberá os elementos e faremos a contagem por loop.
Enquanto houver elementos, somaremos 1 no total e que elementos-restantes é o next de elementos-restantes. Esse é o nosso for

; for total-ate-agora 0, elementos-restantes elementos-restantes   ;; 1 next

(defn conta
    [elementos]
    (loop))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta []))COPIAR CÓDIGO
No loop, definiremos quais são os símbolos que a cada interação deverão ser redefinidos. Iremos, então, redefinir total-ate-agora, que começará com 0. Em seguida, redefiniremos os elementos restantes, cujo valor inicial é elementos. Trata-se de um processo bem parecido com a definição de variável local.

; for total-ate-agora 0, elementos-restantes elementos-restantes   ;; 1 next

(defn conta
    [elementos]
    (loop [total-ate-agora 0
        elementos-restantes elementos]

        ))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta []))COPIAR CÓDIGO
Resta adicionarmos uma sequência de execuções. Se há elementos presentes no vetor (seq), iremos somar 1 em total-ate-agora. Além disso, iremos coletar o nest. Com esses valores, poderemos reatribuir o loop. Em outra palavras, iremos recorrer ao loop, substituindo os valores. O recur nessa situação não retorna para a função de acordo com a mesma quantidade de parâmetros que a função recebia, a função afinal recebe um único parâmetro.

; for total-ate-agora 0, elementos-restantes elementos-restantes   ;; 1 next

(defn conta
    [elementos]
    (loop [total-ate-agora 0
        elementos-restantes elementos]
     (if (seq elementos-restantes)
       (recur (omc total-ate-agora) (next elementos-restantes))
         total-ate-agora)))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta []))
COPIAR CÓDIGO
Definimos uma única função, sem variações, que recebe os elementos com recorrência apenas no loop, podemos ter quantos comandos quisermos antes do loop, eles não serão executados.

Qual é a desvantagem desse loop? Ele só é possível porque já escrevemos um código anterior, afinal poderíamos criar referência direta na função se fosse o caso. Como temos código anterior ao loop, e estamos fazendo a recorrência dentro do loop, é um sinal de que a função está complexa e não acionando boas práticas de programação.

A complexidade ciclomática está alta. Neste caso, costuma-se quebrar a função em duas.

@@04
For no Clojure

Clojure possui também um for https://clojuredocs.org/clojure.core/for que recebe uma sequência de expressões de inicialização e o corpo a ser executado. Ele funciona de forma similar ao loop, iterando pelos elementos da sequência, mas permitindo já loopar e definir novos símbolos com :when, :while e :let. Como comentamos no caso do loop, tradicionalmente você verá outras formas de executar um mesmo bloco ao invés de loop e for.

https://clojuredocs.org/clojure.core/for

@@05
Faça como eu fiz na aula

Chegou a hora de você seguir todos os passos realizados por mim durantes esta aula. Caso já tenha feito, excelente. Se ainda não, é importante que você implemente o que foi visto no vídeo para poder continuar com a próxima aula, que tem como pré-requisito todo o código aqui escrito. Se por acaso você já domina essa parte, em cada capítulo, você poderá baixar o projeto feito até aquele ponto.

O gabarito deste exercício é o passo a passo demonstrado no vídeo. Tenha certeza de que tudo está certo antes de continuar. Ficou com dúvida? Podemos te ajudar pelo nosso fórum.

@@06
Loops

Quando usar um loop e quando usar recursão?

Daremos preferência a recursão ou loops isolados.
 
Loop em geral parece ser um code smell uma vez que transparece controle de fluxo que poderia ter sido isolado em uma função.
Alternativa correta
Daremos preferência a loops e funções longas, incluindo as com recursões.
 
Alternativa correta
Ambos resolvem o mesmo problema da mesma maneira, portanto tanto faz.

@@07
O que aprendemos?

O que aprendemos nesta aula:
Como o reduce funciona;
Implementar o reduce;
Variação de parâmetros na função;
Utilizar o loop;
Fazer recorrência dentro do loop.

#### 25/10/2023

@03-Outras funções com maps

@@01
Projeto da aula anterior

Caso queira, você pode baixar o projeto do curso no ponto em que paramos na aula anterior.

@@02
Agrupando e trabalhando com esquemas de mapas mais complexos

Aprendemos algumas possibilidades de trabalho com coleções, ora com recursividade ora com laço. Nesta aula, trabalharemos com outro exemplo que contém pedidos, também começaremos a organizar alguns arquivos desde já.
Em nosso projeto teremos uma estrutura de dados simples para representar as compras que são efetuadas no site de uma loja. Criaremos um arquivo chamado db.clj, cujo namespace será ns loja.db. Neste arquivo inseriremos nossos pedidos.

Definiremos os pedidos de uma maneira simples e um grande símbolo global dentro do namespace. Um pedido pedido1 conterá o usuário que realizou a solicitação e os itens.

(ns loja.db)

(def pedido1 {:usuario 15
              :itens {:mochila { :id mochila, :quantidade 2, :preco-unitario 80}
                      :camiseta { :id camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis    { :id tenis, :quantidade 1}}})
COPIAR CÓDIGO
É comum em sistemas de loja, atribuir um id para os produtos, a fim de identificá-los de maneira mais precisa no banco de dados. Como estamos realizando um estudo in memory, usaremos o próprio key-word.

Para inserir esses conteúdos dentro de um grande mapa de maneira única, temos esse outro mapa com cada um dos itens. Poderíamos sim estar lidando com um vetor, inclusive com repetições de alguns elementos.

Criaremos vários pedidos seguindo o molde pré-estabelecido, e podemos mudar alguns valores. O grande símbolo defn que comporta todos os pedidos será colocado enquanto função, assim poderemos gerá-lo diversas vezes e teremos como retorno um vetor que conterá todos os pedidos.

(defn todos-os-pedidos []
    [pedido1, pedido2, pedido3, pedido4, pedido5, pedido6])COPIAR CÓDIGO
Criaremos um novo arquivo que chamaremos de aula3, cujo namespace será ns loja.aula3. Nosso objetivo é evocar algo que esteja em outro namespace, no caso todos-pedidos. Existem alguns cuidados que precisamos ter quando executamos algo no REPL, pois dependendo de como carregamos as execuções podem funcionar, mas apenas temporariamente.

Mas como podemos usar símbolos que foram definidos em outro namespace? Existem várias formas, uma delas é inserir o require seguido de um vetor com todos os elementos que queremos trazer.

(ns loja.aula3)
    (:require [loja.db]))

(println (loja.db/todos-os-pedidos) 
COPIAR CÓDIGO
Funcionou como esperávamos. Também é comum realizarmos importações utilizando o :as db.

(ns loja.aula3)
    (:require [loja.db :as db]))

(println (loja.db/todos-os-pedidos) 
COPIAR CÓDIGO
Não manteremos dessa maneira porque é comum quando se trata de um arquivo do seu projeto abreviar como l.db. Esse é o padrão que encontraremos no mercado.

(ns loja.aula3)
    (:require [loja.db :as l.db]))

(println (loja.db/todos-os-pedidos) 
COPIAR CÓDIGO
Até agora conhecemos processos de baixo nível, mas vamos explorar ações que realmente facilitem nosso fluxo de trabalho para que ele não fique tão manual. Iremos agora coletar todos os pedidos e agrupá-los por usuário. Encontramos facilmente na documentação do Closure o group-by e como utilizá-lo, além de alguns exemplos.

Nosso objetivo é agrupar os pedidos pelo keyword :usuario, então escreveremos:

(ns loja.aula3)
    (:require [loja.db :as l.db]))

(println (loja.db/todos-os-pedidos) 

(println (group-by :usuario (l.db/todos-os-pedidos)))
COPIAR CÓDIGO
Foi feito o agrupamento por usuário, e podemos verificar a compra de cada um deles. O group-by serve para agrupamentos, e podemos passar funções. Temos um caso clássico com um debug de println:

(ns loja.aula3)
    (:require [loja.db :as l.db]))

(println (loja.db/todos-os-pedidos) 

(println (group-by :usuario (l.db/todos-os-pedidos)))

(dfn minha-funcao-de-agrupamento 
    [elemento]
    (println "elemento" elemento) 
    (:usuario elemento))

(println (group-by minha-funcao-de-agrupamento (l.db/todos-os-pedidos)))
COPIAR CÓDIGO
Poderíamos fazer qualquer outro agrupamento, como por valor total ou primeiro produto, por exemplo. Podemos então passar uma função customizada pelo println e ver ela sendo chamada.

Queremos saber é quantos pedidos possui cada usuário, e cada um possui um vetor com os produtos pedidos. O esquema está mais ou menos da seguinte maneira, o usuário 15, 1 e 10 possuem quantos produtos?

; { 15 []
;    1 []
;   10 [] }COPIAR CÓDIGO
Como podemos coletar esses vetores? Podemos acrescentar um vals ao nosso group-by e um count.

(ns loja.aula3)
    (:require [loja.db :as l.db]))

(println (loja.db/todos-os-pedidos) 

(println (group-by :usuario (l.db/todos-os-pedidos)))

(dfn minha-funcao-de-agrupamento 
    [elemento]
    (println "elemento" elemento) 
    (:usuario elemento))

(println (group-by minha-funcao-de-agrupamento (l.db/todos-os-pedidos)))

; { 15 [x,c,y]
;    1 [x]
;   10 [x]
;   20 [x,2]}

(println (count (vals (group-by :usuario (l.db/todos-os-pedidos)))))
COPIAR CÓDIGO
Teremos como resultado impresso o número 4, não é isso que queremos, 4 é o número total de usuários. O que queremos na verdade é mapear a função count:

(println (map count (vals (group-by :usuario (l.db/todos-os-pedidos)))))COPIAR CÓDIGO
Teremos como resultado impresso 3 1 1 1, o que é um dado confuso, não conseguimos saber com precisão sobre esses números. Temos uma sequência de informações ilegível.

Precisamos tornar o resultado legível, que nos apresente uma informação clara sobre os usuários e seus respectivos pedidos. Neste caso, utilizar o vals não é a melhor alternativa. Queremos mapear a conta total por usuário, por isso faremos definiremos uma função conta-total-por-usuario.

(dfn conta-total-por-usuario
    [[usuario pedidos]]
    [usuario (count pedidos)])

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)COPIAR CÓDIGO
Temos como resultado:

([15 3] [1 1] [10 1] [20 1])COPIAR CÓDIGO
É compreensível o dado, o usuário 15 realizou 3 compras. Contudo, temos um vetor em que cada posição equivale a algo, para a esquerda temos o usuário, na direta as compras. Isso pode ser confuso.

Ao invés devolvermos um vetor, retornaremos um mapa:

(defn conta-total-por-usuario
    [[usuario pedidos]]
    {:usuario-id usuario
     :total-de-pedidos (count pedidos)})
COPIAR CÓDIGO
Dessa maneira, teremos impresso dados mais claros. Inclusive, se agora quisermos adquirir o valor total dos pedidos, podemos redefinir a função:

(dfn total-dos-pedidos
    [pedidos]
    -1)

(defn quantia-de-pedidos-e-gasto-total-por-usuario
    [[usuario pedidos]]
    {:usuario-id usuario
     :total-de-pedidos (count pedidos)
     :preco-total (total-dos-pedidos pedidos)})

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuário)
      println)
COPIAR CÓDIGO
Passar por uma série de pedidos e somar o total, é algo que já conhecemos, mas o ponto é que costumamos trabalhar com mapas estruturados, e essas estruturas fazem sentido e são padronizadas. Se quisermos realmente calcular os pedidos, faremos:

(println "PEDIDOS")

(dfn total-dos-pedidos
    [pedidos]
    (println "pedido" pedidos))

(defn quantia-de-pedidos-e-gasto-total-por-usuario
    [[usuario pedidos]]
    {:usuario-id usuario
     :total-de-pedidos (count pedidos)
     :preco-total (total-dos-pedidos pedidos)})

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuário)
      println)
COPIAR CÓDIGO
Teremos um conjunto que abarca cada pedido. Em seguida, iremos mapear o pedido e o total de cada item, e aplicaremos depois o reduce. Cada item possui uma chave e o valor, que são os detalhes. Esses detalhes nós multiplicaremos

(println "PEDIDOS")

(defn total-do-item
    [[item-id detalhes]]
    (* (get detalhes :quantidade 0)  (get detalhes :preco-unitario 0)))

(defn total-do-pedido
    [pedido]
    (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
    [pedidos]
    (->> pedidos
        (map :itens)
        (map total-do-pedido)
        (reduce +)))

(defn quantia-de-pedidos-e-gasto-total-por-usuario
    [[usuario pedidos]]
    {:usuario-id usuario
     :total-de-pedidos (count pedidos)
     :preco-total (total-dos-pedidos pedidos)})

(->> (l.db/todos-os-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-gasto-total-por-usuario)
      println)
COPIAR CÓDIGO
Será coletado o pedido, mapeado o total de cada item e somar, e então teremos o total-do-pedido.

@@03
Faça como eu fiz na aula

Chegou a hora de você seguir todos os passos realizados por mim durantes esta aula. Caso já tenha feito, excelente. Se ainda não, é importante que você implemente o que foi visto no vídeo para poder continuar com a próxima aula, que tem como pré-requisito todo o código aqui escrito. Se por acaso você já domina essa parte, em cada capítulo, você poderá baixar o projeto feito até aquele ponto.

O gabarito deste exercício é o passo a passo demonstrado no vídeo. Tenha certeza de que tudo está certo antes de continuar. Ficou com dúvida? Podemos te ajudar pelo nosso fórum.

@@04
Agrupamento

Ao agruparmos passamos uma função de agrupamento. Se queremos agrupar mapas por um keyword determinado passamos ele para a função de agrupamento. Mas se quero agrupar pelo número de certificados que um usuário possui devo fazer:


(defn agrupadora [pessoa] (count (:certificados pessoa)))

(group-by agrupadora pessoas)
 
Agrupamos pelo número de certificados que uma pessoa possui.
Alternativa correta
(defn agrupadora [pessoa] (count (:certificados pessoa)))

(group-by pessoas agrupadora)
 
Lembre-se que as funções recebem a coleção sempre como último parâmetro?
Alternativa correta
(defn agrupadora [pessoas] (map count (:certificados pessoas)))

(group-by pessoas agrupadora)
 
Lembre-se que as funções recebem a coleção sempre como último parâmetro?
Alternativa correta
(defn agrupadora [pessoas] (map count (:certificados pessoas)))

(group-by agrupadora pessoas)
 
Parabéns, você acertou!

@@05
O que aprendemos?
PRÓXIMA ATIVIDADE

O que aprendemos nesta aula:
Simular um banco em memória;
Utilizar o require para fazer a importação de classe;
Utilizar um as para abreviação;
Agrupar dados.