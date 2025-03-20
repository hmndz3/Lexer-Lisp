Proyecto Grupal 1
Lisp
Lexer

Autores:
Pedro Caso
Diego Calderon
Harry Mendez

Link del repo: https://github.com/hmndz3/Lexer-Lisp

Justificaciones de las estructuras de datos utilizadas de JCF:
- List<E>: es una interfaz que utiliza genericos, al utilizarla se puede cambiar de implementación sin afectar al codigo; además al momento de ingresar elementos mantiene el orden por lo que buscar y encontrar los elementos es más fácil. En nuestro caso se utilizó para representar las expresiones en lisp.
- ArrayList<E>: es una implementacion de la interfaz mencionada anteriormente, a diferencia que esta esta basada en un arreglo más dinámico. El utilizar esta estructura de datos trae ventajas como un acceso rápido basado en el index en el que se encuentre lo que buscamos, mantiene el orden y permite duplicados. En nuestro caso se utilizó para almacenar listas de expresiones.
- MAP<K,V>: se utiliza para hacer una coleccion o asociación en base a una clave y un valor. Es bastante util para buscar elementos, ya que, si se conoce su clave se encontrara el valor asociado a la misma.En nuestro caso se utilizó para la implementación de HashMap y para el entorno de variables.
- HashMap<K,V>: como bien se menciona antriormente es una implementacion de MAP, a pesar de que esta no mantiene el orden de ingreso, es fácil encontrar elementos si se conoce la clave del valor que se busca, además es versatil ya que mantiene las claves unicas pero el valor puede cambiar. En nuestro caso se utilizó para variables y funciones de Lisp.
- Set<E>: es un tipo de collecion un poco diferente a List<E> ya que en este caso no permite los elementos duplicados como las estructuras mencionadas anteriormente. Tiene ventajas como agregar y eliminar elementos rápido si se conoce el indice de loq ue se busca. En nuestro caso se utilizó para detectar palabras reservadas.
