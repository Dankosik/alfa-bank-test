# Test Task for Junior Java Backend Developer Position

### Dowload java 11 if needed

**Windows**


     https://stackoverflow.com/questions/52511778/how-to-install-openjdk-11-on-windows

### Start instruction

**Linux/MacOS**

1) Open terminal
2) ```cd /path/to/your/directory```
3) ```git clone https://github.com/Dankosik/alpha-bank-test```
4) ```cd alpha-bank-test```
5) ```./gradlew bootRun``` - run application or ```./gradlew bootRun --args='--server.port=8888'``` - to select server port
6) ```./gradlew test``` - run tests




**Windows**
1) Open comand line
2) ```cd /path/to/your/directory```
3) ```git clone https://github.com/Dankosik/alpha-bank-test```
4) ```cd alpha-bank-test```
5) ```gradlew bootRun``` - run application or ```gradlew bootRun --args='--server.port=8888'``` - to select server port
6) ```gradlew test``` - run tests


### Api example

**Request**
 
```[GET] http://localhost:8080/api/v1/exchange-rate/RUB/gif```
    
**Response**
```  
 {
     "url":"https://media4.giphy.com/media/YTRUPHI7fXK6s/giphy.gifcid=1f7b3757yg4dujtj1weh7v0v3m5bittbha29gwlcdrlqvb9q&rid=giphy.gif&ct=g",
     "size":"978529",
     "width":"292",
     "height":"164"
 }
 ```
 
 **Request**
 
```[GET] http://localhost:8080/api/v1/exchange-rate/QWEASDAZF/gif```
    
**Response**
```  
 {
    "error": "NOT_FOUND",
    "message": "Currency: QWEASDAZF is not found",
    "timestamp": "2021-09-24T08:17:54.171+00:00",
    "status": 404
}
 ```
