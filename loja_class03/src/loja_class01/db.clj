(ns loja-class01.db)

(def pedido1 {:usuario 15
              :itens {:mochila {:id :mochila, :quantidade 2, :preco-unitario 10}
                      :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis {:id :tenis, :quantidade 1}
                      }
              })

(def pedido2 {:usuario 10
              :itens {:mochila {:id :mochila, :quantidade 2, :preco-unitario 10}
                      :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis {:id :tenis, :quantidade 1}
                      }
              })


(def pedido3 {:usuario 12
              :itens {:mochila {:id :mochila, :quantidade 12, :preco-unitario 100}
                      :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 3}
                      :tenis {:id :tenis, :quantidade 1}
                      }
              })


(def pedido4 {:usuario 15
              :itens {:mochila {:id :mochila, :quantidade 20, :preco-unitario 1}
                      :camiseta {:id :camiseta, :quantidade 3, :preco-unitario 40}
                      :tenis {:id :tenis, :quantidade 1}
                      }
              })

(defn todos-pedidos [] [pedido1, pedido2, pedido3, pedido4])
