import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'dart:async';

//General Area User Main Page
// ignore: camel_case_types
class ExpandRequest extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;
  CreateRequestDTO requestSelected;

  ExpandRequest(this.firstName, this.userID,this.profile,this.token,this.requestSelected);

  @override
  _ExpandRequestState createState() => _ExpandRequestState(firstName,userID,profile,token, requestSelected);
}

class _ExpandRequestState extends State<ExpandRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String profile;
  String token;
  CreateRequestDTO requestSelected;

  _ExpandRequestState(this.firstName, this.userID, this.profile, this.token, this.requestSelected);

  @override
  Widget build(BuildContext context) {

    final cancelRequestButton = Material(
      child: TextButton(
        child: Text('cancel'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
            cancelRequest(userID.toString(),profile,token,requestSelected.requestID.toString());
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
            Text(
              requestSelected.productName,
            ),
            Text(
              requestSelected.description,
            ),
            Text(
              requestSelected.requestID.toString(),
            ),
            SizedBox(height: 15.0),
            cancelRequestButton,
          ],
        ),
      ),
    );
  }
}

void cancelRequest(String userID, String profile, String token, String reqID) {

  var url = Uri.parse('http://192.168.1.110:8080/api/requests/'+reqID+'/'+userID); //+userID.toString()

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response =  http.delete(url,headers: headers);
  print(response);
}