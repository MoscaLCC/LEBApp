import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';

//General Area User Main Page
// ignore: camel_case_types
class ConsultRequest extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;

  ConsultRequest(this.firstName, this.userID,this.profile,this.token);

  @override
  _ConsultRequestState createState() => _ConsultRequestState(firstName,userID,profile,token);
}

class _ConsultRequestState extends State<ConsultRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String profile;
  String token;

  _ConsultRequestState(this.firstName,this.userID,this.profile,this.token);

  @override
  Widget build(BuildContext context) {

    final confirmRequestButton = Material(
      child: TextButton(
        child: Text('Get'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action

          print(" ## Get object - Consult Request ## ");

          fetchRequests(userID.toString(), profile,token);

        },
      ),
    );
    return Scaffold(
      appBar: AppBar(
          title: Text('Consult Requests'),
          backgroundColor: Colors.teal),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            confirmRequestButton,
            SizedBox(
              height: 15.0,
            )
          ],
        ),
      ),
    );
  }
}

void fetchRequests(String userID, String profile, String token)  async {

  print(userID);
  print(profile);
  print(token);

  var url = Uri.parse('http://192.168.1.110:8080/api/requests/'+ userID.toString()+'/'+profile);

  Map<String,String> headers = {
    'Content-type' : 'application/json',
    'Accept': 'application/json',
    'Authorization' : token,
  };

  final response = await http.get(url,headers: headers);
  final responseJson = response.body;
  print(responseJson);
}