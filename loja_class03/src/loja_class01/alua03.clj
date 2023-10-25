(ns loja-class01.alua03
  (:require [loja-class01.db :as l.db]))

;(println (l.db/todos-pedidos))

(defn minha-funcao-de-agrupamento
  [elemento]
  (println "elemento" elemento)
  (:usuario elemento))

(println (group-by minha-funcao-de-agrupamento (l.db/todos-pedidos)))

(println (count (vals (group-by :usuario (l.db/todos-pedidos)))))

(println (map count (vals (group-by :usuario (l.db/todos-pedidos)))))

(->> ( l.db/todos-pedidos)
     (group-by :usuario)
     vals
     (map count)
     println)

(defn conta-total-por-usuario [[usuario pedidos]] (count pedidos) )

(->> ( l.db/todos-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)

(defn conta-total-por-usuario [[usuario pedidos]] [usuario (count pedidos)] )

(->> ( l.db/todos-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)


(defn conta-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario :total-de-pedidos (count pedidos)})

(->> (l.db/todos-pedidos)
     (group-by :usuario)
     (map conta-total-por-usuario)
     println)

(defn total-do-item
  [[item-id detalhes]]
  (* (get detalhes :quantidade 0) (get detalhes :preco-unitario 0)))
(defn total-do-pedido
  [pedido]
  (reduce + (map total-do-item pedido)))

(defn total-dos-pedidos
  [pedidos]
  (->> pedidos
       (map :itens)
       (map total-do-pedido)
       (reduce +)))

(defn quantia-de-pedidos-e-conta-total-por-usuario
  [[usuario pedidos]]
  {:usuario-id usuario
   :total-de-pedidos (count pedidos)
   :preco-total (total-dos-pedidos pedidos)
   }
  )

(->> ( l.db/todos-pedidos)
     (group-by :usuario)
     (map quantia-de-pedidos-e-conta-total-por-usuario)
     println)