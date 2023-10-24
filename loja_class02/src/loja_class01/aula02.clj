(ns loja-class01.aula02)

(defn conta
  [total-ate-agora elementos]
  (recur (inc total-ate-agora) (rest elementos)))

;(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
  (recur (inc total-ate-agora)
         (rest elementos))))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora)
           (rest elementos))
    total-ate-agora))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora)
           (next elementos))
    (inc total-ate-agora)))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora)
           (next elementos))
    (inc total-ate-agora)))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))
(println (conta 0 []))

(defn conta
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora)
           (next elementos))
    total-ate-agora))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))
(println (conta 0 []))

;(defn conta
;  [total-ate-agora elementos]
;  (if (seq elementos)
;    (recur (inc total-ate-agora)
;           (next elementos))
;    total-ate-agora))
;
;(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))
;(println (conta 0 []))

(defn minha-funcao [parametro1] (println parametro1))

(defn minha-funcao
  ( [parametro1] (println "p1" parametro1))
  ( [parametro1 parametro2] (println "p2" parametro1 parametro2))
  )


(minha-funcao 1)
(minha-funcao 1 2)

(defn conta

  ([elementos]
   (conta 0 elementos))

  ( [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora)
           (next elementos))
    total-ate-agora)))

(println (conta 0 ["Daniela", "Guilherme", "Carlos"]))
(println (conta 0 []))


; for total-ate-agora 0, elementos

(defn conta
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta ["Daniela", "Guilherme", "Carlos"]))
(println (conta []))
