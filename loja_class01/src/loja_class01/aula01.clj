(ns loja-class01.aula01)


;["Daniela", "Guilherme", "Carlos"]

;[{"Guilherme" 37, "Paulo" 39}]

(map println ["Daniela", "Guilherme", "Carlos"])
(println (first ["Daniela", "Guilherme", "Carlos"]))
(println (rest ["Daniela", "Guilherme", "Carlos"]))
(println (next ["Daniela", "Guilherme", "Carlos"]))
(println (rest []))
(println (next []))

(println (seq []))
(println (seq [1 2 3 4 5]))

(println "\nMEU MAPA")


(println "\nMEU MAPA 1")
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (funcao primeiro)
    (meu-mapa funcao (rest sequencia))))

;(meu-mapa println ["Daniela", "Guilherme", "Carlos"])

(println "\nMEU MAPA 2")

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if primeiro
     (do
       (funcao primeiro)
       (meu-mapa funcao (rest sequencia))))))

(meu-mapa println ["Daniela", "Guilherme", "Carlos"])

(println "\nMEU MAPA 3")

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))

(meu-mapa println ["Daniela", "Guilherme", "Carlos"])
(meu-mapa println [])
(meu-mapa println nil)
;(meu-mapa println (range 1000000))

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (recur funcao (rest sequencia))))))

(meu-mapa println (range 5000))
