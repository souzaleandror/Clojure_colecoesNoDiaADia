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