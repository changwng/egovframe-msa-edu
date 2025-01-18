
#rem setJava8
cd /Users/changwng/workspace.edu/egovframe-msa-edu/backend/config
#cd config
nohup java -jar build/libs/config-1.0.0.jar&
sleep 3
cd ../discovery
nohup java -jar build/libs/discovery-1.0.0.jar&
#tail -f nohup.out
cd ../apigateway
nohup java -jar build/libs/apigateway-1.0.0.jar&
sleep 1
#tail -f nohup.out
cd ../board-service
nohup java -jar build/libs/board-service-1.0.0.jar&
#tail -f nohup.out
cd ../user-service
nohup java -jar build/libs/user-service-1.0.0.jar&
#tail -f nohup.out
cd ../portal-service
nohup java -jar build/libs/portal-service-1.0.0.jar&
#tail -f nohup.out
cd ../reserve-check-service
nohup java -jar build/libs/reserve-check-service-1.0.0.jar&
cd ../reserve-item-service
nohup java -jar build/libs/reserve-item-service-1.0.0.jar&
cd ../reserve-request-service
nohup java -jar build/libs/reserve-request-service-1.0.0.jar&