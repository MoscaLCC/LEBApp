import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/screens/user_main_area.dart';
import 'package:lebapp_ui/services/consultAvailableRequest.dart';
import 'dart:async';

import 'confirmRequest.dart';
import 'editRequest.dart';

//General Area User Main Page
// ignore: camel_case_types
class ExpandRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;

  ExpandRequest(this.firstName, this.userID,this.token,this.requestSelected);

  @override
  _ExpandRequestState createState() => _ExpandRequestState(firstName,userID,token, requestSelected);
}

class _ExpandRequestState extends State<ExpandRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;

  _ExpandRequestState(this.firstName, this.userID, this.token, this.requestSelected);

  @override
  Widget build(BuildContext context) {

    final assignRequestButton = Material(
      child: TextButton(
        child: Text('Assing'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () async {
          await assignRequest(userID.toString(),token,requestSelected.id.toString(),context,firstName);
          returnToLastPage(context);
        },
      ),
    );

    final editRequestButton = Material(
      child: TextButton(
        child: Text('Edit'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>EditRequest(firstName, userID,token,requestSelected,requestSelected.id)));
        },
      ),
    );

    final cancelRequestButton = Material(
      child: TextButton(
        child: Text('Cancel'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          cancelRequest(userID.toString(),token,requestSelected.id.toString(), context);
          returnToLastPage(context);
        },
      ),
    );

    final confirmDeliveryRequestButton = Material(
      child: TextButton(
        child: Text('Confirm Delivery'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {

            _displayTextInputDialog(context);
            if(_textFieldController.text != null){ // ver ???
              confirmRequest(
                  userID.toString(), token, requestSelected.id.toString(),
                  context,_textFieldController.text);
            }
            returnToLastPage(context);
        },
      ),
    );

    final getCodeRequestButton = Material(
      child: TextButton(
        child: Text('Get Code'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
            showAlertDialog(context, "Entregue o código: ",
                codeGenerator(userID.toString()));

            returnToLastPage(context);
        },
      ),
    );

    final finalizeRequestButton = Material(
      child: TextButton(
        child: Text('Finalize Delivery'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
            _displayTextInputDialog(context);
            if(requestSelected.status == "IN_TRANSIT"){
              if(_textFieldController.text != null){ // ver ???
                finalizeRequest(
                    userID.toString(), token, requestSelected.id.toString(),
                    context,_textFieldController.text);
              }
            }else{
              print("Error: request is not IN_TRANSIT");
            }

            returnToLastPage(context);
        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title:Text("Request Details")
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            buildProductImage(),
            Text(
              requestSelected.productName,
            ),
            Text(
              requestSelected.specialCharacteristics,
            ),
            Text(
              requestSelected.status,
            ),
            Text(
              requestSelected.id.toString(),
            ),
            if(requestSelected.status == "WAITING_COLLECTION" && requestSelected.ownerRequest != userID)
              //SizedBox(height: 15.0),
              assignRequestButton,
            if(requestSelected.status == "WAITING_COLLECTION" && requestSelected.ownerRequest == userID)
              //SizedBox(height: 15.0),
              editRequestButton,
            if(requestSelected.status == "WAITING_COLLECTION" && requestSelected.ownerRequest == userID)
              //SizedBox(height: 15.0),
              cancelRequestButton,
            if(requestSelected.status == "IN_COLLECTION" && requestSelected.ownerRequest == userID) // getCode
              //SizedBox(height: 15.0),
              getCodeRequestButton,
            if(requestSelected.status == "IN_COLLECTION" && requestSelected.transporter == userID) // getCode
              //SizedBox(height: 15.0),
              confirmDeliveryRequestButton,
            if(requestSelected.status == "IN_TRANSIT" && requestSelected.transporter == userID) // getCode
              //SizedBox(height: 15.0),
              finalizeRequestButton,
          ],
        ),
      ),
    );
  }
Widget buildProductImage() => Container(
  color: Colors.grey,
  child: Image.network('https://amopaocaseiro.com.br/wp-content/uploads/2020/01/pao-caseiro-para-iniciantes_02-840x560.jpg'),
);

}

void cancelRequest(String userID, String token, String reqID, BuildContext context) async {

  print(userID);
  print(reqID);

  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/interface/'+reqID); //+userID.toString()

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response =  await http.delete(url,headers: headers);
  print(response.statusCode);

  if(response.statusCode == 204){
    showAlertDialog(context, "Cancel Request", "Success");
  }else{
    print("Server Error or Invalid status");
  }
}

Future<String> assignRequest(String userID, String token, String reqID, BuildContext context, String firstName) async{

  print(userID);
  print(reqID);

  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/'+reqID+'/'+userID+'/assign'); //+userID.toString()

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response =  await http.put(url,headers: headers);
  print(response);

  if(response.statusCode == 200){
    showAlertDialog(context, "Assign Request", "Success");
    // Go back
    // Navigator.of(context).pop(MaterialPageRoute(builder: (context)=>ConsultAvailableRequest(firstName,int.parse(userID),token)));

  }else{
    print("Server Error");
    showAlertDialog(context, "Assign Request", "Error");
  }

  return "";
}

void confirmRequest(String userID, String token, String reqID,BuildContext context, String userCode) async{

  print(userID);
  print(reqID);
  print(userCode);

  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/'+reqID+'/'+userCode+'/transit'); //+userID.toString()

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response =  await http.put(url,headers: headers);
  print(response);

  if(response.statusCode == 200){
    showAlertDialog(context, "Confirm / Validate ", "Success");
  }else{
    print("Server Error");
  }
}

void finalizeRequest(String userID, String token, String reqID,BuildContext context, String userCode) async{

  print(userID);
  print(reqID);
  print(userCode);

  var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/close/'+reqID+'/'+userCode+'/'); //+userID.toString()

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response =  await http.put(url,headers: headers);
  print(response);

  if(response.statusCode == 200){
    showAlertDialog(context, "Finalize ", "Success");
  }else{
    print("Server Error");
  }
}

showAlertDialog(BuildContext context, String title, String content) {

  // set up the button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );

  // set up the AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text(title),
    content: Text(content),
    actions: [
      okButton,
    ],
  );

  // show the dialog
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}

TextEditingController _textFieldController = TextEditingController();
Future<void> _displayTextInputDialog(BuildContext context) async {

  return showDialog(
    context: context,
    builder: (context) {
      return AlertDialog(
        title: Text('Digite o código:'),
        content: TextField(
          controller: _textFieldController,
          decoration: InputDecoration(hintText: ""),
        ),
        actions: <Widget>[
          FlatButton(
            child: Text('Cancel'),
            onPressed: () {
              Navigator.pop(context);
            },
          ),
          FlatButton(
            child: Text('OK'),
            onPressed: () {
              print(_textFieldController.text);
              Navigator.pop(context);
            },
          ),
        ],
      );
    },
  );
}

String codeGenerator(String userID){

  String userCode = "";

  userCode = userID;

  return userID;
}

void returnToLastPage(BuildContext context){
  Navigator.pop(context);
}

