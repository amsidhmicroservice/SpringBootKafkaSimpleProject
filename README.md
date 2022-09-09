### Setting up Kafka Cluster on Minikube
  1) Setting up kafka cluster on your local docker machine
  2) Just go inside folder multinode-kafka-cluster and run following command
     docker-compose -f multinode\docker-compose-zookeeper-kafka.yml up -d
    
     That's it. Your kafka cluster is available on bootstrapservers localhost:29092,localhost:39092 

  4) Use any kafka explorer for viewing the messages and topics.
     Kafka Explorer tools are:
     1) OffSet Explorer
     2) Kafka Magic
     

