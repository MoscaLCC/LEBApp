import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;
import 'package:lebapp_ui/models/CreateRequestDTO.dart';
import 'package:lebapp_ui/models/DimensionsDTO.dart';
import 'package:lebapp_ui/models/RidePathDTO.dart';

class ConfirmRequest extends StatefulWidget {

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;
  int reqID;

  ConfirmRequest(this.firstName, this.userID,this.token,this.requestSelected,this.reqID);

  @override
  _ConfirmRequestState createState() => _ConfirmRequestState(firstName,userID,token,requestSelected,reqID);
}

class _ConfirmRequestState extends State<ConfirmRequest> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String token;
  CreateRequestDTO requestSelected;
  int reqID;

  _ConfirmRequestState(this.firstName,this.userID,this.token,this.requestSelected,this.reqID);

  @override
  Widget build(BuildContext context) {

    // ----- buttons ------
    final confirmRequestButton = Material(
      child: TextButton(
        child: Text('Confirm Delivery'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {

          print(" ## Confirm Delivery ## ");

          var url = Uri.parse('http://ec2-54-209-6-238.compute-1.amazonaws.com:8080/api/requests/interface/IN_COLLECTION/'+reqID.toString()+'/'+userID.toString());
          var body = json.encode(requestSelected.toJson());
          print(body);

          Map<String,String> headers = {
            'Content-type' : 'application/json',
            'Accept': 'application/json',
            'Authorization' : token,
          };

          final response = http.put(url, body: body, headers: headers);
          final responseJson = response;
          print(responseJson);
          return response;

        },
      ),
    );

    return Scaffold(
      appBar: AppBar(
          title: Text('Confirm'),
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