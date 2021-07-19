import 'dart:io';

import 'package:flutter/material.dart';
import '../main.dart';
import 'package:lebapp_ui/services/createRequest.dart';
import 'package:lebapp_ui/services/consultRequest.dart';

//General Area User Main Page
// ignore: camel_case_types
class ProducerMainArea extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;

  ProducerMainArea(this.firstName, this.userID,this.profile,this.token);

  @override
  _ProducerMainAreaState createState() => _ProducerMainAreaState(firstName,userID,profile,token);
}

class _ProducerMainAreaState extends State<ProducerMainArea> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String profile;
  String token;

  _ProducerMainAreaState(this.firstName,this.userID,this.profile,this.token);

  @override
  Widget build(BuildContext context) {

    final createRequestButton = Material(
      child: TextButton(
        child: Text('Create Request'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>CreateRequest(firstName, userID, profile,token)));
        },
      ),
    );

    final consultRequestButton = Material(
      child: TextButton(
        child: Text('Consult Requests'),
        style: TextButton.styleFrom(
          alignment: Alignment.bottomCenter,
          primary: Colors.white,
          backgroundColor: Colors.teal,
          onSurface: Colors.grey,
        ),
        onPressed: () {
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ConsultRequest(firstName, userID, profile,token)));
        },
      ),
    );
    return Scaffold(
      appBar: AppBar(
          title: Text('Hello '+firstName+' ! '),
          backgroundColor: Colors.teal),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            createRequestButton,
            SizedBox(
              height: 15.0,
            ),
            consultRequestButton,
            SizedBox(
              height: 15.0,
            )
          ],
        ),
      ),
    );
  }
}