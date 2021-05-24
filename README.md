# Proyecto IdentifyMutant

## Necesidad

Se requieren exponer dos capacidades las cuales permitan, primero identificar si el ADN ingresado es Humano o Mutante
esta información se debe guardar en una base de datos dynamo, la segunda capidad debe consultar la base de datos 
para traer información del total de ADN analisados

## Proyecto 

Proyecto realizado con arquitectura limpia y spring boot webflux.

##Carpeta Application

proyecto  app-service con el main aplicación y los bean para los casos de uso.

##Carpeta Domain 

**Model:**
proyecto con todos los modelos y las interfaces de la solución 

**UseCase:**
proyecto con la  logica de negocio con la solución del problema 

##Carpeta Infraestructura 

**driven-adapter**
Proyecto que implementa la interface con las operaciones del bynamoDb

**entry-points**
proyecto que expone dos operaciones :
    - Mutant
    - stats 

Pasos para ejecutar localmente:

   1. Descargar la imagen de docker dynamoDB local con el siguiente comando 
   "docker pull amazon/dynamodb-local" debemos esperar que se complete la descarga para 
   subir el contenedor con el comando "docker run -p 8000:8000 amazon/dynamodb-local".
   
   2. se debe crear la tabla en la base de datos con el siguiente comando 
      "aws dynamodb create-table \
       --table-name human \
      --attribute-definitions AttributeName=adn,AttributeType=S \
      --key-schema AttributeName=adn,KeyType=HASH \
      --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
      --endpoint-url http://localhost:8000"
      
   3. Clonar el repositorio y lo importamos al Ide de preferencia 
   
   4. Abrir el aplication.yaml y edicar la propiedad "aws.dynamodb.endpoint:" se le debe asignar el valor
   http://localhost:8000
   
   5. Correr el proyecto 


##Diagrama de arquitectura

![Clean Architecture](https://imagenbucke.s3.us-east-2.amazonaws.com/arquitectura.png)

##Diagrama de Flujo Mutant

![Clean Architecture](https://imagenbucke.s3.us-east-2.amazonaws.com/mutant.png)

##Diagrama de Flujo stats

![Clean Architecture](https://imagenbucke.s3.us-east-2.amazonaws.com/stats.png)


