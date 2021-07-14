import 'dart:io';

import 'package:flutter/material.dart';
import 'package:lebapp_ui/screens/producerMainArea.dart';
import '../main.dart';

//General Area User Main Page
// ignore: camel_case_types
class User_Main_Area extends StatefulWidget {

  String firstName;
  int userID;
  String profile;
  String token;

  User_Main_Area(this.firstName, this.userID,this.profile,this.token);

  @override
  _User_Main_AreaState createState() => _User_Main_AreaState(firstName,userID,profile,token);
}

class _User_Main_AreaState extends State<User_Main_Area> {

  TextStyle style = TextStyle(fontFamily: 'Montserrat', fontSize: 20.0);

  String firstName;
  int userID;
  String profile;
  String token;

  _User_Main_AreaState(this.firstName,this.userID,this.profile,this.token);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: Text('Hello '+firstName+' ! '),
            backgroundColor: Colors.teal),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Personal area.......',
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        //onPressed: _incrementCounter,
        onPressed: (){
          //button action
          Navigator.of(context).push(MaterialPageRoute(builder: (context)=>ProducerMainArea(firstName, userID, profile,token)));
        },
        child: Icon(Icons.add), // text or button icon
      ),
    );
  }
}